package Adventure.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Inventory implements Serializable{

    private List<GameItem> list = new ArrayList<>();

    public Inventory() {
    }

    public Inventory(List<GameItem> list) {
        this.list = list;
    }
    
    public List<GameItem> getList() {
        return list;
    }

    public void setList(List<GameItem> list) {
        this.list = list;
    }

    public void add(GameItem o) {
        list.add(o);
    }

    public void remove(GameItem o) {
        list.remove(o);
    }
    
    public GameItem get(int id) {
        GameItem obj = null;
        for (GameItem o : list) {
            if (o.getId() == id) {
                obj = o;
            }
        }
        return obj;
    }
    
    public void remove(int id) {
        List<GameItem> l = list;
        if (!l.isEmpty()) {
            Iterator<GameItem> it = l.iterator();
            while (it.hasNext()) {
                GameItem next = it.next();
                    if (next.getId() == id) {
                        it.remove();
                    }
            }
        }
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.list);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventory other = (Inventory) obj;
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }
        return true;
    }
}
