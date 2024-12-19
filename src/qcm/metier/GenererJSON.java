package qcm.metier;

public class GenererJSON {

    /**
     * Générer le code JSON complet pour une évaluation
     *
     * @param e Evaluation avec tous les paramètres
     * @return Le JSON formatté
     */
    public static String genererJson(Evalutation e) {

        String sRet = "";

        for (Question question : e.ensQuestion()) {

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
                    sRet += "\t\tpairs";
                    break;
            default :
                    sRet += "\t\tanswers";
                    break;
        }


        //Explication
        sRet += "\t\texplanation: \"" + question.getReponse().getTexteExplication() + "\",\n";

        //Time
        sRet += "\t\ttime: \"" + question.getTempsReponse() + "\",\n";

        //Points
        sRet += "\t\tpoints: " + question.getNbPoints() + ",\n";

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

    public static String generateJSONAnswer(Question question){
      String sRet ="";
      //question
        sRet += "\t\t\t{ text : \"" + question.getReponse() + "\", correct :" + question.getReponse()/*.isCorrect()*/ + " },\n";

      return sRet;
    }

    public static String generateJSONAnswerAssociation(Question question){
        return "\t\t\t{ left : \"" + question.getReponse() + "\", correct :" + question.getReponse()/*isCorrect()*/ + " },\n";
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
    }
]
 */