public class EliminationReponseItem {
    
    private String texte;
    private int ordreSuppression;
    private float ptsSuppression;

    public String getTexte() {
        return texte;
    }

    public int getOrdre() {
        return ordreSuppression;
    }

    public float getPtsSuppression() {
        return ptsSuppression;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    public void setPtsSuppression(float ptsSuppression) {
        this.ptsSuppression = ptsSuppression;
    }
}