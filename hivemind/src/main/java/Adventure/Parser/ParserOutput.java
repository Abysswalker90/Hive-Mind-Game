package Adventure.Parser;

import Adventure.Type.GameCharacter;
import Adventure.Type.GameItem;
import Adventure.Type.Command;

public class ParserOutput{

    private Command command;

    private GameItem item1 = null;
    private GameItem invItem1 = null;
    private GameCharacter character1 = null;
    
    private GameItem item2 = null;
    private GameItem invItem2 = null;
    private GameCharacter character2 = null;
    
    private boolean extraWords;

    public ParserOutput(Command command, GameItem item, boolean extraWords) {
        this.command = command;
        this.item1 = item;
        this.extraWords = extraWords;
    }

    public ParserOutput(Command command, GameItem item1, GameItem invItem1, GameCharacter character1, GameItem item2, GameItem invItem2, GameCharacter character2, boolean extraWords) {
        this.command = command;
        this.item1 = item1;
        this.invItem1 = invItem1;
        this.character1 = character1;
        this.item2 = item2;
        this.invItem2 = invItem2;
        this.character2 = character2;
        this.extraWords = extraWords;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public GameItem getItem1() {
        return item1;
    }
    
    public GameItem getItem2() {
        return item2;
    }

    public void setItem1(GameItem item) {
        this.item1 = item;
    }
    
    public void setItem2(GameItem item) {
        this.item2 = item;
    }

    public GameItem getInvItem1() {
        return invItem1;
    }
    
    public GameItem getInvItem2() {
        return invItem2;
    }

    public void setInvItem1(GameItem invItem) {
        this.invItem1 = invItem;
    }
    
    public void setInvItem2(GameItem invItem) {
        this.invItem2 = invItem;
    }

    public GameCharacter getCharacter1() {
        return character1;
    }

    public void setCharacter1(GameCharacter character1) {
        this.character1 = character1;
    }

    public GameCharacter getCharacter2() {
        return character2;
    }

    public void setCharacter2(GameCharacter character2) {
        this.character2 = character2;
    }
    
    public boolean hasExtraWords() {
        return extraWords;
    }

    public void setExtraWords(boolean extraWords) {
        this.extraWords = extraWords;
    }
}
