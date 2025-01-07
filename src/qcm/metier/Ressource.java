package qcm.metier;

import qcm.utils.Uid;

import java.util.ArrayList;
import java.util.List;

public class Ressource {

	private final int LIMITE = 30;

	private String       nom;
	private String       code; //Ex : r1.01
	private List<Notion> alNotion;
	private	String       UID;

	public Ressource (String nom, String code) {

		this.nom      = nom;
		this.code     = code;
		this.alNotion = new ArrayList<Notion>();
		this.UID      = Uid.generateUid(20);

	}

	public Ressource (String nom, String code, String UID) {

		this.nom      = nom;
		this.code     = code;
		this.alNotion = new ArrayList<Notion>();
		this.UID      = UID;

	}


	/*---------------*/
	/*      GET      */
	/*---------------*/


	public String getNomCourt() {

		String nom = this.getNom();

		if ( this.nom.length() >= LIMITE ) {

			nom = nom.substring(0, LIMITE - 3);
			nom += "...";

		}

		return nom ;

	}

	public Notion getNotion( int indice ) {

		if (indice < 0 || indice >= this.getNbNotion()) return null;

		return this.alNotion.get(indice);

	}

	public String       getNom        () { return this.code + " - " + this.nom; }
	public String       getNomSansCode() { return this.nom;                     }
	public String       getCode       () { return this.code;                    }
	public List<Notion> getAlNotion   () { return this.alNotion;                }
	public String       getUID        () { return UID;                          }
	public int          getNbNotion   () { return this.alNotion.size(); }


	/*---------------*/
	/*      SET      */
	/*---------------*/

	public void setNom (String nom) {this.nom  = nom;}


	/*---------------*/
	/*     AUTRE     */
	/*---------------*/


	public void ajouterNotion(Notion n) { this.alNotion.add(n); }

}