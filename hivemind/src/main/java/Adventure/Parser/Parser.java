package Adventure.Parser;

import Adventure.Type.GameCharacter;
import Adventure.Type.GameNPC;
import Adventure.Type.GameItem;
import Adventure.Type.Command;
import java.util.List;

public class Parser {

    private int checkForCommand(String token, List<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (commands.get(i).getName().equals(token) || commands.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }

    private int checkForItems(String token, List<GameItem> items) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(token) || items.get(i).getAlias().contains(token)) {
                return i;
            }
        }
        return -1;
    }
    
    private int checkForCharacter(String token, List<GameNPC> NPCs) {
        for (int i = 0; i < NPCs.size(); i++) {
            if (NPCs.get(i) instanceof GameCharacter && (NPCs.get(i).getName().equals(token) || NPCs.get(i).getAlias().contains(token))) {
                return i;
            }
        }
        return -1;
    }

    public ParserOutput parse(String command, List<Command> commands, List<GameItem> items, List<GameItem> inventory, List<GameNPC> NPCs) {
        boolean flagExtraWords = false;
        String cmd = command.toLowerCase().trim();
        String[] tokens = cmd.split("\\s+");
        if (tokens.length > 0) {
            int inCommand = checkForCommand(tokens[0], commands);
            if (inCommand > -1) {
                if (tokens.length > 1) {
                    int inItem1 = -1;
                    int inItem2 = -1;
                    int inInvItem1 = -1;
                    int inInvItem2 = -1;
                    int inCharacter1 = -1;
                    int inCharacter2 = -1;
                    
                    for (int i=1; i<tokens.length; i++) {
                        if (inItem1<0 && inInvItem1<0 && inCharacter1<0) {
                            inItem1 = checkForItems(tokens[i], items);
                            if (inItem1<0) {
                                inInvItem1 = checkForItems(tokens[i], inventory);
                                if (inInvItem1<0) {
                                    inCharacter1 = checkForCharacter(tokens[i], NPCs);
                                    if (inCharacter1<0) {
                                        flagExtraWords = true;
                                        
                                    }
                                }
                            }
                        } else {
                            inItem2 = checkForItems(tokens[i], items);
                            if (inItem2<0) {
                                inInvItem2 = checkForItems(tokens[i], inventory);
                                if (inInvItem2<0) {
                                    inCharacter2 = checkForCharacter(tokens[i], NPCs);
                                    if (inCharacter2<0) {
                                        flagExtraWords = true;
                                    }
                                }
                            }
                        }
                    }
                   
                    if (inItem1 > -1 && inItem2 < 0 && inInvItem2 < 0 && inCharacter2 < 0) {                 //oggetto
                        return new ParserOutput(commands.get(inCommand), items.get(inItem1), null, null, null, null, null, flagExtraWords);
                    } else if (inItem1 > -1 && inItem2 > -1) {                                                              //oggetto, oggetto
                        return new ParserOutput(commands.get(inCommand), items.get(inItem1), null, null, items.get(inItem2), null, null, flagExtraWords);
                    } else if (inItem1 > -1 && inInvItem2 > -1) {                                                           //oggetto, inventario
                        return new ParserOutput(commands.get(inCommand), items.get(inItem1), null, null, null, inventory.get(inInvItem2), null, flagExtraWords);
                    } else if (inItem1 > -1 && inCharacter2 > -1) {                                                        //oggetto, personaggio
                        return new ParserOutput(commands.get(inCommand), items.get(inItem1), null, null, null, null, (GameCharacter)NPCs.get(inCharacter2), flagExtraWords);
                        
                    } else if (inInvItem1 > -1 && inItem2 < 0 && inInvItem2 < 0 && inCharacter2 < 0) {       //inventario            
                        return new ParserOutput(commands.get(inCommand), null, inventory.get(inInvItem1), null, null, null, null, flagExtraWords);
                    } else if (inInvItem1 > -1 && inItem2 > -1) {                                                           //inventario, oggetto
                        return new ParserOutput(commands.get(inCommand), null, inventory.get(inInvItem1), null, items.get(inItem2), null, null, flagExtraWords);
                    } else if (inInvItem1 > -1 && inInvItem2 > -1) {                                                        //inventario, inventario
                        return new ParserOutput(commands.get(inCommand), null, inventory.get(inInvItem1), null, null, inventory.get(inInvItem2), null, flagExtraWords);
                    } else if (inInvItem1 > -1 && inCharacter2 > -1) {                                                     //inventario, personaggio
                        return new ParserOutput(commands.get(inCommand), null, inventory.get(inInvItem1), null, null, null, (GameCharacter)NPCs.get(inCharacter2), flagExtraWords);
                    
                    } else if (inCharacter1 > -1 && inItem2 < 0 && inInvItem2 < 0 && inCharacter2 < 0) {    //personaggio
                        return new ParserOutput(commands.get(inCommand), null, null, (GameCharacter)NPCs.get(inCharacter1), null, null, null, flagExtraWords);
                    } else if (inCharacter1 > -1 && inItem2 > -1) {                                                        //personaggio, oggetto
                        return new ParserOutput(commands.get(inCommand), null, null, (GameCharacter)NPCs.get(inCharacter1), items.get(inItem2), null, null, flagExtraWords);
                    } else if (inCharacter1 > -1 && inInvItem2 > -1) {                                                     //personaggio, inventario
                        return new ParserOutput(commands.get(inCommand), null, null, (GameCharacter)NPCs.get(inCharacter1), null, inventory.get(inInvItem2), null, flagExtraWords);
                    } else if (inCharacter1 > -1 && inCharacter2 > -1) {                                                  //personaggio, personaggio
                        return new ParserOutput(commands.get(inCommand), null, null, (GameCharacter)NPCs.get(inCharacter1), null, null, (GameCharacter)NPCs.get(inCharacter2), flagExtraWords);
                    
                    } else {
                        return new ParserOutput(commands.get(inCommand), null, null, null, null, null, null, flagExtraWords);
                    }
                } else {
                    return new ParserOutput(commands.get(inCommand), null, flagExtraWords);
                }
            } else {
                return new ParserOutput(null, null, flagExtraWords);
            }
        } else {
            return null;
        }
    }

}
