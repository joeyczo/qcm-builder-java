package qcm.metier;

import java.util.ArrayList;

public class GenererJSON {

    /**
     * Générer le code JSON complet pour une évaluation
     *
     * @param e Evaluation avec tous les paramètres
     * @return Le JSON formaté
     */
    public static String genererJson(Evalutation e) {

        String sRet = "";

        for (Question question : e.ensQuestion()) {
            sRet += GenererJSON.generateJSONQuestion(question);
            if(e.ensQuestion().getLast() == question)
                sRet += "\t}\n]";
            else
                sRet += "\t},";
        }

        return sRet;
    }

    public static String generateJSONQuestion(Question question) {
        String sRet = "[\n{\t\n";

        //Type de question
        sRet += "\t\ttype: \" " +
                switch (question.getTypeQuestion()) {
                    case QCMSOLO -> "qcm";
                    case QCMMULTI -> "qcm";
                    case ELIMINATION -> "elimination";
                    case ASSOCIATION -> "liaison";
                }
                + "\",\n";

        //Si QCM, réponse multiple?
        if (question.getTypeQuestion() == TypeQuestion.QCMSOLO || question.getTypeQuestion() == TypeQuestion.QCMMULTI)
            sRet += "\t\treponse_multiple: " + (question.getTypeQuestion() == TypeQuestion.QCMMULTI)  + ",\n";


        //titre
        sRet += "\t\ttitle: \"" + question.getTexteQuestion() + "\",\n";

        // Réponses/associations
        switch(question.getTypeQuestion()) {
            case TypeQuestion.ASSOCIATION :
                    sRet += GenererJSON.generateJSONAnswerAssociation(question);
                    break;
            case TypeQuestion.ELIMINATION :
                    sRet += GenererJSON.generateJSONAnswerElim(question);
                    break;
            default :
                    sRet += GenererJSON.generateJSONAnswerQCM(question);
                    break;
        }


        //Explication
        sRet += "\t\texplanation: \"" + question.getReponse().getTexteExplication() + "\",\n";

        //Time
        sRet += "\t\ttime: \"" + question.getTempsReponse() + "\",\n";

        //Points
        sRet += "\t\tpoints: \"" + question.getNbPoints() + "\",\n";

        //Difficulté
        sRet += "\t\tdifficulte: \" " +
                switch (question.getDifficulte()) {
                    case TRESFACILE -> "tres-facile";
                    case FACILE -> "facile";
                    case MOYEN -> "moyen";
                    case DIFFICILE -> "difficile";
                }
                + "\",\n";

        return sRet;
    }

    public static String generateJSONAnswerQCM(Question question){
      String sRet ="\t\tanswers :[\n";
      QCMReponse qcmReponse = (QCMReponse) question.getReponse();

      for ( int i= 0; i < qcmReponse.getNbReponse(); i++) {
          QCMReponseItem qcmReponseItem = qcmReponse.getReponse(i);

          sRet += "\t\t\t{ text : \"" + qcmReponseItem.getTexte() + "\", correct :" + qcmReponseItem.isValide() + " },\n";
      }
      sRet += "],\n";
      return sRet;
    }

    public static String generateJSONAnswerAssociation(Question question){
        String sRet = "\t\tpairs: [\n";
        AssociationReponse associationReponse = (AssociationReponse) question.getReponse();

        for ( int i = 0; i < associationReponse.getNbReponses(); i++) {

            AssociationReponseItem associationReponseItem = associationReponse.getReponse(i);
            sRet += "\t\t\t{ left : \"" + associationReponseItem.getTexte() + "\", right :\"" + associationReponseItem.getReponse().getTexte() + "\" },\n";
        }
        sRet += "],\n";
        return sRet;
    }

    public static String generateJSONAnswerElim(Question question){
        String sRet ="\t\tanswers :[\n";
        EliminationReponse eliminationReponse = (EliminationReponse) question.getReponse();

        for ( int i= 0; i < eliminationReponse.getNbReponse(); i++) {

            EliminationReponseItem eliminationReponseItem = eliminationReponse.getReponseItem(i);
            sRet += "\t\t\t{ text : \"" + eliminationReponseItem.getTexte() + "\", correct :" + eliminationReponseItem.isBonneReponse() + ", ordre:" + eliminationReponseItem.getOrdreSuppression() + ", points"+ eliminationReponseItem.getPtsSuppression()+"},\n";
        }
        sRet += "],\n";
        return sRet;
    }

    public static void main(String[] args) {
        // Crée une instance valide de QCMReponse
        QCMReponse qcmReponse = new QCMReponse();
        qcmReponse.ajouterItem(new QCMReponseItem("JavaScript", true));
        qcmReponse.ajouterItem(new QCMReponseItem("HTML", false));
        qcmReponse.ajouterItem(new QCMReponseItem("Python", true));
        qcmReponse.ajouterItem(new QCMReponseItem("CSS", false));


        Notion n = new Notion("nom", new Ressource("nom", "code"));
        Question q = new Question(
                "Quels sont les langages de programmation ?",
                "00:40",
                2.0,
                TypeQuestion.QCMMULTI,
                qcmReponse,
                DifficulteQuestion.FACILE,
                n
        );

        ArrayList<Question> al = new ArrayList<>();
        al.add(q);
        Evalutation e = new Evalutation(1, true, new Ressource("nom", "code"), al, "path");

        String s = GenererJSON.genererJson(e);
        System.out.println(s);
    }
}
//
/* EXEMPLES DE JSON NECESSAIRES
[
    {
        type: "qcm",                                         //Type de la question ici qcm donc en fontion de l'attribut "reponse_multiple", il sera possible de sélectionner plusieurs réponses
        title: "Quels sont les langages de programmation ?", //Intitulé de la question
        answers: [											 //Tableau pour répertorier les questions et si elles sont correctes ou non
            { text:"JavaScript", correct: true },
            { text: "HTML", 	 correct: false},
            { text: "Python", 	 correct: true },
            { text: "CSS", 		 correct: false},
        ],
        explanation: "",									//Explication de la réponse
        reponse_multiple : true,							//En cas de qcm
        time: "00:40",										//Temps que l'étudiant a pour répondre (mettre 0 pour désactiver)
        points: 2,											//Points que rapporte la question
        difficulte: "facile",								//Difficulté de la question (facile, normal, difficile)
    },
    {
        type: "liaison",
        title: "Reliez les animaux avec leurs cris",
        pairs: [
            { left: "Chat", right: "Miaou"   },
            { left: "Chien", right: "Ouaf" },
            { left: "Vache", right: "Meuuuh" },
            { left: "Cheval", right: "Huuuu" },
            { left: "Lion", right: "ROOOAAAAR" },
            { left: "Rouge gorge", right: "Cui-cui" },
            { left: "Poule", right: "Côt côt" },
        ],
        points: 4,
        time: "01:30",
        difficulte: "facile",
    },
    {
        type: "elimination",
        title: "Comment faire appel au constructeur de la super-classe ?",
        answers: [
            { text: "this.super([[paramètre]])", correct: false, ordre:0 },
            { text: "super([[paramètre]])", correct: true, ordre:0 },
            { text: "this.createClass([[paramètre]])", correct: false, ordre: 1, points:0.5 },
            { text: "super.createClass([[paramètre]])", correct: false, ordre: 2, points:0.25 },
        ],
        explanation: "super([[paramètre]]) peut être utilisé uniquement au sein d'un constructeur.",
        reponse_multiple : false,
        time: "00:40",
        points: 3,
        difficulte: "moyen",
    }
]
 */