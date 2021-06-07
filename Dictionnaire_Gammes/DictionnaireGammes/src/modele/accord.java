package modele;

import java.util.ArrayList;

public class accord {

	private note noteChoix = new note();
	private int nbr;
	
	//retourne un accord suivant le nombre de note entrée
	public int[] choixAccord(String accord) {
		for(int i = 0;i<accord.length();i++) {
			//si le symbole suivant est un # ou b, on prend la note correspondante
			if(i!=accord.length()-1 && (accord.substring(i+1, i+2).equals("#") || accord.substring(i+1, i+2).equals("b"))) {
				//récupération valeur midi note
				nbr--;
			}
			nbr++;
		}
		int[] retour = new int[nbr];
		
		for(int i = 0;i<nbr;i++) {
			//si le symbole suivant est un # ou b, on prend la note correspondante
			if(i!=accord.length()-1 && (accord.substring(i+1, i+2).equals("#") || accord.substring(i+1, i+2).equals("b"))) {
				//récupération valeur midi note
				retour[i] = noteChoix.choixNote(accord.substring(i,i+2));
			}
			else {
				retour[i] = noteChoix.choixNote(accord.substring(i,i+1));
			}
		}
		return retour;
	}
	
}
