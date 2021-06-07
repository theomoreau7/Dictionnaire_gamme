package modele;

import java.util.ArrayList;

public class note {
	
	private int noteHauteur = 0;
	private ArrayList<String> notes;
	
	//retourne la valeur midi de la note choisie
	public int choixNote(String nomNote) {
		//choix d'une note
		
		switch(nomNote) {
			case "C":
				noteHauteur = 48;
			break;
			case "C#":
				noteHauteur = 49;
			break;
			case "Db":
				noteHauteur = 49;
			break;
			case "D":
				noteHauteur = 50;
			break;
			case "D#":
				noteHauteur = 51;
			break;
			case "Eb":
				noteHauteur = 51;
			break;
			case "E":
				noteHauteur = 52;
			break;
			case "Fb":
				noteHauteur = 52;
			break;
			case "E#":
				noteHauteur = 53;
			break;
			case "F":
				noteHauteur = 53;
			break;
			case "F#":
				noteHauteur = 54;
			break;
			case "Gb":
				noteHauteur = 54;
			break;
			case "G":
				noteHauteur = 55;
			break;
			case "G#":
				noteHauteur = 56;
			break;
			case "Ab":
				noteHauteur = 56;
			break;
			case "A":
				noteHauteur = 57;
			break;
			case "A#":
				noteHauteur = 58;
			break;
			case "Bb":
				noteHauteur = 58;
			break;
			case "B":
				noteHauteur = 59;
			break;
			case "Cb":
				noteHauteur = 59;
			break;
			default:
				noteHauteur = 48;
			break;
		}
		return noteHauteur;
	}
	//permet de retourner un tableau de note à partir d'une chaîne de caractère
	public ArrayList<String> noteGamme(String gamme) {
		
		for(int i = 0; i<gamme.length();i++) {
			notes.set(i, gamme.substring(i, i+1));
		}
		
		
		return notes;
		
	}

}
