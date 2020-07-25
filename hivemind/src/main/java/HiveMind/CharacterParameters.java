package HiveMind;

import java.io.Serializable;

public class CharacterParameters implements InterfaceCharacterParameters, Serializable {

    @Override
    public String CloneName() {
        return "clone";
    }

    @Override
    public String[] CloneAliases() {
        return new String[]{"uomo", "persona", "l'uomo", "all'uomo", "Sergej", "soldato"};
    }

    @Override
    public String corpseHallName() {
        return "cadavere";
    }

    @Override
    public String[] corpseHallAliases() {
        return new String[]{"morto", "guardia", "corpo"};
    }
    @Override
    public String corpseWestWingName() {
        return "cadavere";
    }

    @Override
    public String[] corpseWestWingAliases() {
        return new String[]{"morto", "guardia", "corpo"};
    }

    @Override
    public String corpseBathroomName() {
        return "cadavere";
    }

    @Override
    public String[] corpseBathroomAliases() {
        return new String[]{"morto", "guardia", "corpo"};
    }

    @Override
    public String corpseLabName() {
        return "cadavere";
    }

    @Override
    public String[] corpseLabAliases() {
        return new String[]{"morto", "scienziato", "dottore", "corpo", "Sokolov"};
    }
}
