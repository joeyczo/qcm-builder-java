package qcm.metier;

public enum TypeQuestion 
{
	QCMSOLO			("QCM à réponse unique"),
	QCMMULTI		("QCM à réponse multiple"),
	ASSOCIATION  	("Association"),
	ELIMINATION  	("Élimination");

	private String nomType;

	TypeQuestion(String t) { this.nomType = t; }

	public String getNomType() { return this.nomType;}
}
