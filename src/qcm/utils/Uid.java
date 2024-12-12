package qcm.utils;

public class Uid {

    /**
     * Générer un identifiant aléatoire de taille x
     * @param taille Taille de l'identifiant
     * @return L'identifiant généré aléatoirement
     */
    public static String generateUid(int taille) {

        String chars = "abcedfghijklmnopqrstuvwxyzABCEDFGHIJKMNOPQRSTUVXYZ0123456789?!@$€";
        String res   = "";

        for ( int i = 0; i < taille; i++)
            res += chars.charAt((int) (Math.random() * chars.length()));

        return res;

    }

}
