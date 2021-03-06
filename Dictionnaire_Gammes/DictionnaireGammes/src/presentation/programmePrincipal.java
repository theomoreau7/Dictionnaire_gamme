package presentation;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;
import java.util.Scanner;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

import javax.sound.midi.Soundbank;
import javax.sound.midi.Synthesizer;

import modele.note;
import modele.instrument;
import modele.accord;

public class programmePrincipal {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Dictionnaire gamme et accord MIDI");
		System.out.println("version 1.5");
		System.out.println("Theo Moreau 2021");
		
		
		//r?cup?ration et ouverture synth? par d?faut
		Synthesizer synth = null;
		Instrument[] instrument;
		Soundbank soundbank ;
		
		//initialisation variable musique
		//hauteur de note
		int noteHauteur = 48;
		String nomNote = "C";
		//instrument
		int noInstrument = 1;
		//velocit? de note
		int velo = 100;
		String nomInstrument;
		//notes de la gamme
		String note[] = new String[16];
		//gamme
		String gamme;
		//accord
		String accord;
		//fonction choix note
		note noteChoix = new note(); 
		//fonction choix instrument
		instrument instrumentChoix = new instrument();
		//nom pour recherche gamme/accord
		String nomGamme;
		String nomAccord;
		
		//variable menu/programme
		//pour quitter
		boolean exit = false;
		//scan clavier ordi
		Scanner input = new Scanner(System.in);
		String textMenuChoix = "0";
		int menuChoix = 0;
		String save = "oui";
		
		//ouverture du midi
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//r?cup?ration banque de son
		soundbank =synth.getDefaultSoundbank();
		MidiChannel[] mch = synth.getChannels();
		

		
		while(exit==false){
			
			//choix instrument note
			System.out.println("choisir un instrument parmi les suivants : ");
			System.out.println("piano : 1 \n");
			System.out.println("guitare : 2 \n");
			System.out.println("orgue : 3 \n");
			System.out.println("trompette : 4 \n");
			
			nomInstrument = input.nextLine();
			
			//MENU
			System.out.println("Que voulez-vous faire?");
			System.out.println("0 : lecture d'une note");
			System.out.println("1 : lecture d'une gamme");
			System.out.println("2 : lecture d'une gamme sauvegard?e");
			System.out.println("3 : lecture d'un accord");
			System.out.println("4 : lecture d'un accord sauvegard?");
			
			//choix de l'action
			textMenuChoix = input.nextLine();
			if(textMenuChoix.equals("0")||textMenuChoix.equals("1")||textMenuChoix.equals("2")
			 ||textMenuChoix.equals("3")||textMenuChoix.equals("4"))
				menuChoix = Integer.parseInt(textMenuChoix); 
			else
				menuChoix = 0;
			
			switch(menuChoix) {
				//lecture note
				case 0:
					
					System.out.println("choisir une note  : ");
					System.out.println("Notes possibles : ");
					System.out.println("C-D-E-F-G-A-B diese ou bemol");
					nomNote = input.nextLine();
					
					if(nomNote.length()<2) {
						nomNote += "_";
					}
					
					//prise en compte de l'instrument
					mch[0].programChange(0,instrumentChoix.choixinstrument(nomInstrument));
					//activation note
					mch[0].noteOn(noteChoix.choixNote(nomNote),velo);
					try {
							Thread.sleep(2000);
					} catch (InterruptedException e) {
							// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mch[0].noteOff(noteChoix.choixNote(nomNote));
				break;
				//lecture gamme
				case 1:
					
					System.out.println("entrer les notes de la gamme : ");
					System.out.println("Notes possibles : ");
					System.out.println("C-D-E-F-G-A-B diese ou bemol");
					gamme = input.nextLine();
					
					//prise en compte de l'instrument
					mch[0].programChange(0,instrumentChoix.choixinstrument(nomInstrument));
					
					for(int i = 0;i<gamme.length();i++) {
						//si le symbole suivant est un # ou b, on prend la note correspondante
						if(i!=gamme.length()-1 && (gamme.substring(i+1, i+2).equals("#") || gamme.substring(i+1, i+2).equals("b"))) {
				
							//activation note
							System.out.println(gamme.substring(i,i+2));
							mch[0].noteOn(noteChoix.choixNote(gamme.substring(i,i+2)),velo);
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mch[0].noteOff(noteChoix.choixNote(gamme.substring(i,i+2)));
							++i;
						}
						else {
							
							//activation note
							System.out.println(gamme.substring(i,i+1));
							mch[0].noteOn(noteChoix.choixNote(gamme.substring(i,i+1)),velo);
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							mch[0].noteOff(noteChoix.choixNote(gamme.substring(i,i+1)));
						}
					}
					
					//demande de sauvegarde de la gamme
					System.out.println("Voulez-vous sauvegarder cette gamme? (oui/non)");
					save = input.nextLine().toUpperCase();

					if(save.equals("OUI")) {
						String nom;
						System.out.println("Nom de la gamme : ");
						nom = input.nextLine();
						nom+="\n";
						gamme+="\n";
						Path path = Paths.get("save","dico_gamme");
						
						Files.write(path, nom.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
						Files.write(path, gamme.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
					}
					
					
				break;
				//lecture gamme sauv?e
				case 2:
					

//					Path path = Paths.get("save","dico_gamme");
//					System.out.println(path);
					
					//verification existance dossier
					File save_folder = new File("save");
					if(!save_folder.exists()) {
						if(!save_folder.mkdir()) {
							System.out.println("probleme creation dossier save");
							break;
						}
					}
					
					//verification existance fichier sauvegarde
					File save_file = new File("save\\dico_gamme");
					if(!save_file.exists()) {	
						if(!save_file.createNewFile()){
							System.out.println("probleme creation fichier sauvegarde gamme");
							break;
						}
					}
					

					
					System.out.println("Gamme disponible :\n");
					Path path = Paths.get("save","dico_gamme");
					List<String> allGammes = Files.readAllLines(path);
					for(int i=0;i<allGammes.size();i=i+2) {
						System.out.println(allGammes.get(i));
					}
					
					System.out.println("nom de la gamme ? : ");
					nomGamme = input.nextLine();
					
					//si ok vrai, gamme trouv?e
					boolean ok=false;
					
					for(int j=0;j<allGammes.size();j=j+2) {
						if(nomGamme.equals(allGammes.get(j))) {
							
							ok = true;
							gamme = allGammes.get(j+1);
							//prise en compte de l'instrument
							mch[0].programChange(0,instrumentChoix.choixinstrument(nomInstrument));
							
							for(int i = 0;i<gamme.length();i++) {
								//si le symbole suivant est un # ou b, on prend la note correspondante
								if(i!=gamme.length()-1 && (gamme.substring(i+1, i+2).equals("#") || gamme.substring(i+1, i+2).equals("b"))) {
						
									//activation note
									System.out.println(gamme.substring(i,i+2));
									mch[0].noteOn(noteChoix.choixNote(gamme.substring(i,i+2)),velo);
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									mch[0].noteOff(noteChoix.choixNote(gamme.substring(i,i+2)));
									++i;
								}
								else {
									
									//activation note
									System.out.println(gamme.substring(i,i+1));
									mch[0].noteOn(noteChoix.choixNote(gamme.substring(i,i+1)),velo);
									try {
										Thread.sleep(500);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									mch[0].noteOff(noteChoix.choixNote(gamme.substring(i,i+1)));
								}
							}
						}
					}
					//si gamme inconnue
					if(!ok) {
						System.out.println("gamme non trouvee");
					}
					

					
				break;
				//lecture accord
				case 3:
					
					//fonction entr?e accord
					accord accordChoix = new accord();
					//int[] notesAccord = new int[3];
					int[] notesAccord;
					
					System.out.println("entrer les notes de l'accord : ");
					System.out.println("Notes possibles : ");
					System.out.println("C-D-E-F-G-A-B diese ou bemol");
					accord = input.nextLine();
					
					//prise en compte de l'instrument
					mch[0].programChange(0,instrumentChoix.choixinstrument(nomInstrument));
					notesAccord = accordChoix.choixAccord(accord);
					for(int i = 0; i < notesAccord.length; i++) {
						mch[0].noteOn(notesAccord[i],velo);
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(int i = 0; i < notesAccord.length; i++) {
						mch[0].noteOff(notesAccord[i],velo);
					}
					
					//demande de sauvegarde de la gamme
					System.out.println("Voulez-vous sauvegarder cet accord? (oui/non)");
					save = input.nextLine().toUpperCase();

					if(save.equals("OUI")) {
						String nom;
						System.out.println("Nom de l'accord : ");
						nom = input.nextLine();
						nom+="\n";
						accord+="\n";
						Path path2 = Paths.get("save","dico_accord");
						
						Files.write(path2, nom.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
						Files.write(path2, accord.getBytes(),StandardOpenOption.CREATE,StandardOpenOption.WRITE,StandardOpenOption.APPEND);
					}
					
				break;
				//lecture accord sauv?
				case 4:
					
					//fonction entr?e accord
					accord accordChoix2 = new accord();
					//int[] notesAccord = new int[3];
					int[] notesAccord2;
					
					//verification existance dossier
					File save_folder_chord = new File("save");
					if(!save_folder_chord.exists()) {
						if(!save_folder_chord.mkdir()) {
							System.out.println("probleme creation dossier save");
							break;
						}
					}
					
					//verification existance fichier sauvegarde
					File save_file_chord = new File("save\\dico_accord");
					if(!save_file_chord.exists()) {
						if(!save_file_chord.createNewFile()){
							System.out.println("probleme creation fichier sauvegarde accord");
							break;
						}
					}
						
					System.out.println("Accords disponible :");
					Path path3 = Paths.get("save","dico_accord");
					List<String> allAccords = Files.readAllLines(path3);
					
					for(int i=0;i<allAccords.size();i=i+2) {
						System.out.println(allAccords.get(i));
					}
					
					System.out.println("\nnom de l'accord ? : ");
					nomAccord = input.nextLine();
					
					//si ok vrai, accord trouv?
					boolean ok2=false;
					
					for(int j=0;j<allAccords.size();j=j+2) {
						if(nomAccord.equals(allAccords.get(j))) {
							
							ok2 = true;
							accord = allAccords.get(j+1);
							//prise en compte de l'instrument
							mch[0].programChange(0,instrumentChoix.choixinstrument(nomInstrument));
							
							notesAccord2 = accordChoix2.choixAccord(accord);
							for(int i = 0; i < notesAccord2.length; i++) {
								mch[0].noteOn(notesAccord2[i],velo);
							}
							try {
								Thread.sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							for(int i = 0; i < notesAccord2.length; i++) {
								mch[0].noteOff(notesAccord2[i],velo);
							}
						}
					}
					//si accord inconnu
					if(!ok2) {
						System.out.println("accord non trouve");
					}
				break;
			}
			
			System.out.println("Quitter? O/N");
			nomInstrument = input.nextLine();
			if("O".compareTo(nomInstrument) == 0) {
				exit = true;
			}
			
			
		}

	}

}
