package qcm.metier;

import qcm.utils.Uid;

import java.util.ArrayList;

public class Question {

	private String             texteQuestion;
	private String             tempsReponse;
	private String             UID;
	private double             nbPoints;
	private TypeQuestion       typeQuestion;
	private Reponse            reponse;
	private DifficulteQuestion difficulte;
	private Notion             notion;
	private ArrayList<Fichier> fichiers;


	public Question(String texteQuestion, String tempsReponse, double nbPoints, TypeQuestion typeQuestion, Reponse reponse, DifficulteQuestion difficulte, Notion notion, ArrayList<Fichier> ensFichier) {

		this.texteQuestion  = texteQuestion;
		this.tempsReponse   = tempsReponse;
		this.UID            = Uid.generateUid(10);
		this.nbPoints       = nbPoints;
		this.typeQuestion   = typeQuestion;
		this.reponse        = reponse;
		this.difficulte     = difficulte;
		this.notion         = notion;
		this.fichiers       = ensFichier;

	}

	public Question(String uid, String texteQuestion, String tempsReponse, double nbPoints, TypeQuestion typeQuestion, Reponse reponse, DifficulteQuestion difficulte, Notion notion, ArrayList<Fichier> ensFichier) {

		this.texteQuestion  = texteQuestion;
		this.tempsReponse   = tempsReponse;
		this.UID            = uid;
		this.nbPoints       = nbPoints;
		this.typeQuestion   = typeQuestion;
		this.reponse        = reponse;
		this.difficulte     = difficulte;
		this.notion         = notion;
		this.fichiers       = ensFichier;

	}

	/* ------------ */
	/*   Getters    */
	/* ------------ */

	public String             getTexteQuestion  ()          { return this.texteQuestion;       }
	public String             getTempsReponse   ()          { return this.tempsReponse;        }
	public String             getUID            ()          { return this.UID;                 }
	public double             getNbPoints       ()          { return nbPoints;                 }
	public TypeQuestion       getTypeQuestion   ()          { return this.typeQuestion;        }
	public Reponse            getReponse        ()          { return this.reponse;             }
	public DifficulteQuestion getDifficulte     ()          { return this.difficulte;          }
	public Notion             getNotion         ()          { return this.notion;              }
	public Fichier            getFichier        (int index) { return this.fichiers.get(index); }
	public ArrayList<Fichier> getEnsembleFichier()          { return this.fichiers;            }

	/* ------------ */
	/*	 Setters 	*/
	/* ------------ */

	public void setReponse (Reponse reponse) { this.reponse  = reponse;  }
	public void setNbPoints(double nbPoints) { this.nbPoints = nbPoints; }

}