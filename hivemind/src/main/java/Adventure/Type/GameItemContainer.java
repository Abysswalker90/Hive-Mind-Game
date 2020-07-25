package Adventure.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameItemContainer extends GameItem {

    private List<GameItem> list = new ArrayList<>();

    public GameItemContainer(int id) {
        super(id);
    }

    public GameItemContainer(int id, String name) {
        super(id, name);
    }

    public GameItemContainer(int id, String name, String description, String examine) {
        super(id, name, description, examine);
    }

    public GameItemContainer(int id, String name, String description, String examine, Set<String> alias) {
        super(id, name, description, examine, alias);
    }
    
    public GameItemContainer(int id, String name, String description, String examine, Set<String> alias, int contained) {
        super(id, name, description, examine, alias, contained);
    }

    public List<GameItem> getList() {
        return list;
    }

    public void setList(List<GameItem> list) {
        this.list = list;
    }

    public void add(GameItem o) {
        list.add(o);
        o.setContained(this.getId());
    }

    public void remove(GameItem o) {
        list.remove(o);
        o.setContained(-1);
    }

}
