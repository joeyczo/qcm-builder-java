package qcm.metier;

import java.util.HashMap;
import java.util.ArrayList;

public record Evalutation(int nbQuestions, boolean evaluation, Ressource ressource, HashMap<Notion, ArrayList<Notion>> ensQuestion) {};