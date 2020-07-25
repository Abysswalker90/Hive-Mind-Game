package Adventure;

import Adventure.Parser.ParserOutput;
import Adventure.Type.GameItem;
import Adventure.Type.Command;
import Adventure.Type.Inventory;
import Adventure.Type.Room;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class GameDescription implements Serializable{
    
    private boolean end = false;
    
    private final List<Room> rooms = new ArrayList<>();

    private final List<Command> commands = new ArrayList<>();

    private final Inventory inventory = new Inventory();

    private Room currentRoom;
    
    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }
    
    public List<Room> getRooms() {
        return rooms;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public List<GameItem> getInventory() {
        return inventory.getList();
    }

    public abstract void init() throws Exception;

    public abstract String nextMove(ParserOutput p);
    
    public abstract void save() throws FileNotFoundException, IOException, ClassNotFoundException;
    
    public abstract GameDescription load() throws FileNotFoundException, IOException, ClassNotFoundException;
}