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

        String sRet = "[";

        for (Question question : e.ensQuestion()) {

            sRet += GenererJSON.generateJSONQuestion(question);

            if(e.ensQuestion().get(e.ensQuestion().size()-1) == question)
                sRet += "\t}\n]";
            else
                sRet += "\t},";

        }

        return sRet;

    }

    public static String generateJSONQuestion(Question question) {

        String sRet = "\n{\t\n";

        //Type de question
        sRet += "\t\ttype: \"" +
                switch (question.getTypeQuestion()) {
                    case QCMSOLO     -> "qcm";
                    case QCMMULTI    -> "qcm";
                    case ELIMINATION -> "elimination";
                    case ASSOCIATION -> "liaison";
                }

                + "\",\n";

        //Si QCM, réponse multiple?
        if (question.getTypeQuestion() == TypeQuestion.QCMSOLO || question.getTypeQuestion() == TypeQuestion.QCMMULTI)
            sRet += "\t\treponse_multiple: " + (question.getTypeQuestion() == TypeQuestion.QCMMULTI)  + ",\n";


        //titre
        sRet += "\t\ttitle: \"" + formatTexte(question.getTexteQuestion()) + "\",\n";

        // Réponses/associations
        switch(question.getTypeQuestion()) {

            case ASSOCIATION :
                    sRet += GenererJSON.generateJSONAnswerAssociation(question);
                    break;
            case ELIMINATION :
                    sRet += GenererJSON.generateJSONAnswerElim(question);
                    break;
            default :
                    sRet += GenererJSON.generateJSONAnswerQCM(question);
                    break;

        }


        //Explication
        if (question.getReponse().getTexteExplication() != null)
            sRet += "\t\texplanation: \"" + formatTexte(question.getReponse().getTexteExplication()) + "\",\n";

        //Time
        sRet += "\t\ttime: \"" + question.getTempsReponse() + "\",\n";

        //Points
        sRet += "\t\tpoints: " + question.getNbPoints() + ",\n";

        //Difficulté
        sRet += "\t\tdifficulte: \"" +
                switch (question.getDifficulte()) {
                    case TRESFACILE -> "tres-facile";
                    case FACILE     -> "facile";
                    case MOYEN      -> "moyen";
                    case DIFFICILE  -> "difficile";
                }

                + "\",\n";

        // Fichiers
        if (!question.getEnsembleFichier().isEmpty()) {

            sRet += "\t\tliens: [\n";

            int i = 0;

            ArrayList<String> ensLienFichier = new ArrayList<>();

            for (Fichier f : question.getEnsembleFichier()) {

                String nomFichier  = f.nomFichier().replaceAll(GenererJSON.getFileExtension(f.nomFichier()), "");
                String lienFichier = f.nomFichier();

                /*for (String s : ensLienFichier) {

                    if (s.equals(lienFichier))
                        lienFichier = lienFichier.replaceAll(GenererJSON.getFileExtension(lienFichier), "") + "1" + GenererJSON.getFileExtension(lienFichier);


                }*/

                ensLienFichier.add(lienFichier);

                sRet += "\t\t\t{\n\t\t\t\tname:";
                sRet += "\"" + nomFichier + "\",\n";
                sRet += "\t\t\t\tlien:";
                sRet += "\"" + lienFichier +"\"\n\t\t\t}";

                i++;

                if (i < question.getEnsembleFichier().size()) sRet += ",\n";

            }

            sRet += "]";

        }

        return sRet;

    }

    public static String generateJSONAnswerQCM(Question question) {

      String sRet           = "\t\tanswers :[\n";
      QCMReponse qcmReponse = (QCMReponse) question.getReponse();

      for ( int i= 0; i < qcmReponse.getNbReponse(); i++) {

          QCMReponseItem qcmReponseItem = qcmReponse.getReponseItem(i);

          sRet += "\t\t\t{ text : \"" + formatTexte(qcmReponseItem.getTexte()) + "\", correct :" + qcmReponseItem.isValide() + " },\n";

      }

      sRet += "],\n";

      return sRet;

    }

    public static String generateJSONAnswerAssociation(Question question) {

        String sRet                           = "\t\tpairs: [\n";
        AssociationReponse associationReponse = (AssociationReponse) question.getReponse();

        for ( int i = 0; i < associationReponse.getNbReponses(); i++) {

            AssociationReponseItem associationReponseItem = associationReponse.getReponseItem(i);
            sRet += "\t\t\t{ left : \"" + formatTexte(associationReponseItem.getTexte()) + "\", right :\"" + formatTexte(associationReponseItem.getReponse().getTexte()) + "\" },\n";

        }

        sRet += "],\n";

        return sRet;

    }

    public static String generateJSONAnswerElim(Question question) {

        String sRet                           = "\t\tanswers :[\n";
        EliminationReponse eliminationReponse = (EliminationReponse) question.getReponse();

        for ( int i= 0; i < eliminationReponse.getNbReponse(); i++) {

            EliminationReponseItem eliminationReponseItem = eliminationReponse.getReponseItem(i);
            sRet += "\t\t\t{ text : \"" + formatTexte(eliminationReponseItem.getTexte()) + "\", correct :" + eliminationReponseItem.isBonneReponse() + ", ordre:" + eliminationReponseItem.getOrdreSuppression() + ", points:"+ eliminationReponseItem.getPtsSuppression()+"},\n";

        }

        sRet += "],\n";

        return sRet;

    }

    /**
     * Formatter un texte au bon format (Avec les ", retours à la ligne et les tabs)
     * @param t Texte à formatter
     * @return Texte au bon format
     */
    private static String formatTexte(String t) {

        if (t == null) return "";

        return t.trim().replaceAll("\n", "\\\\n").replaceAll("\t", "\\\\t").replaceAll("\"", "\\\\\"");

    }

    /**
     * Récupérer l'extension du fichier
     * @param nom Nom du fichier complet (Avec le chemin)
     * @return Le nom de l'extension (.png, .pdf,...)
     */
    private static String getFileExtension(String nom) {

        int lastIndexOf = nom.lastIndexOf(".");

        if (lastIndexOf == -1)
            return "";

        return nom.substring(lastIndexOf);
    }

}