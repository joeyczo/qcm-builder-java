package qcm.metier;

import java.util.ArrayList;

public record Evalutation(

        int nbQuestions,                        // Nombre de questions qui seront générés
        boolean evaluation,                     // Indique si le questionnaire est en mode évalué ou non
        Ressource ressource,                    // Ressource parent
        ArrayList<Question> ensQuestion,        // Liste des questions par Notion
        String pathExp                          // Chemin vers l'exportation

) {}