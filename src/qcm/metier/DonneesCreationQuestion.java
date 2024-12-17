package qcm.metier;

public record DonneesCreationQuestion(
        double              nbPoints,
        String              tempsReponse,
        Ressource           ressource,
        Notion              notion,
        DifficulteQuestion  diff,
        TypeQuestion        type,
        Question            qst
) {
}
