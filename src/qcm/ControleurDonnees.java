package qcm;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControleurDonnees
{
    private String lienFichier;

    public ControleurDonnees(String lienFichier)
    {
        this.lienFichier = lienFichier;
    }

    public boolean sauvegardeRessource(String nomRessource)
    {
        try
        {
            PrintWriter pw = new PrintWriter(new FileOutputStream(this.lienFichier));

            pw.println(nomRessource);

            pw.close();

            return true;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sauvegardeNotion(String nomRessource, String nomNotion)
    {

        List<String> ressourceAvecNotion = new ArrayList<String>();
        int id = -1;

        try
        {
            Scanner sc = new Scanner( new FileInputStream( this.lienFichier ) );

            while ( sc.hasNextLine() )
                ressourceAvecNotion.add( sc.nextLine() );

            for (int i = 0; i < ressourceAvecNotion.size(); i++)
            {
                if ( ressourceAvecNotion.get(i).startsWith(nomRessource) )
                    id = i;
            }



            sc.close();
        }
        catch (Exception e){ e.printStackTrace(); }

        try
        {
            PrintWriter pw = new PrintWriter(new FileOutputStream(this.lienFichier));

            if( id == -1 )
                return false;

            pw.println(ressourceAvecNotion.get(id) + nomNotion);

            pw.close();

            return true;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
