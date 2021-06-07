package modele;

public class instrument {
	private int noInstrument = 0;
	
	
	public int choixinstrument(String nomInstrument) {
		
		//choix d'une note
		switch(nomInstrument) {
			case "1":
				noInstrument = 0;
			break;
			case "2":
				noInstrument = 24;
			break;
			case "3":
				noInstrument = 16;
			break;
			case "4":
				noInstrument = 56;
			break;
			default:
				noInstrument = 0;
			break;
		}
		return noInstrument;
	}

}
