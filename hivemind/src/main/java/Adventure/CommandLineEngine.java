package Adventure;

import HiveMind.HiveMindGame;
import HiveMind.CharacterParameters;
import HiveMind.CommandNames;
import HiveMind.ItemParameters;
import Adventure.Parser.Parser;
import Adventure.Parser.ParserOutput;
import Adventure.Type.CommandType;
import java.io.IOException;
import java.util.Scanner;

public class CommandLineEngine {

    private GameDescription game;

    private final Parser parser;

    public CommandLineEngine(GameDescription game) {
        this.game = game;
        try {
            this.game.init();
        } catch (Exception ex) {
            System.err.println(ex);
        }
        parser = new Parser();
    }

    public void run() {
        System.out.println("\n////   " + game.getCurrentRoom().getName() + "   ////");
        System.out.println(game.getCurrentRoom().getFirstDescription());
        game.getCurrentRoom().setVisited(true);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            ParserOutput p = parser.parse(command, game.getCommands(), game.getCurrentRoom().getItems(), game.getInventory(), game.getCurrentRoom().getNPCs());
            if (p.getCommand() != null && p.getCommand().getType() == CommandType.END) {
                if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
                    System.out.println("Forse intendevi: esci");
                } else {
                    System.out.println("Chiusura in corso...");
                    break;
                }
                
            } else if(p.getCommand() != null && p.getCommand().getType()==CommandType.SAVE){
                if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
                    System.out.println("Forse intendevi: salva");
                } else {
                    try {
                           game.save();
                           System.out.println("Salvataggio partita completato.");
                    } catch(IOException | ClassNotFoundException e) {
                        System.out.println("Si e' verificata un'eccezione.");
                    }
                }
                
            }else if(p.getCommand() != null && p.getCommand().getType() == CommandType.LOAD) {
                if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
                    System.out.println("Forse intendevi: carica");
                } else {
                    try {
                        game = game.load();
                        System.out.println("Caricamento partita completato.");
                    } catch (IOException | ClassNotFoundException ex) {
                        System.out.println("Si e' verificata un'eccezione.");
                    } 
                }
            }
           
            else{
                System.out.println(game.nextMove(p));
                if (game.isEnd()) {
                    System.exit(0);
                }
                System.out.println("================================================");
            } 
        }
    }

    public static void main(String[] args) {
        CommandLineEngine engine = new CommandLineEngine(new HiveMindGame(new CommandNames(), new ItemParameters(), new CharacterParameters()));
        engine.run();
    }

}