package HiveMind;

import java.io.Serializable;

public class CommandNames implements InterfaceCommandNames, Serializable {
    @Override
    public String north() {
        return "nord";
    }
    @Override
    public String[] northAliases() {
        return new String[]{"n", "N", "Nord", "NORD"};
    }
    @Override
    public String south() {
        return "sud";
    }
    @Override
    public String[] southAliases() {
        return new String[]{"s", "S", "Sud", "SUD"};
    }
    @Override
    public String east() {
        return "est";
    }
    @Override
    public String[] eastAliases() {
        return new String[]{"e", "E", "Est", "EST"};
    }
    @Override
    public String west() {
        return "ovest";
    }
    @Override
    public String[] westAliases() {
        return new String[]{"o", "O", "Ovest", "OVEST"};
    }
    @Override
    public String crawl() {
        return "striscia";
    }
    @Override
    public String[] crawlAliases() {
        return new String[]{"entra", "passa", "infilati", "imbucati", "avanza", "vai"};
    }
    @Override
    public String inventory() {
        return "inventario";
    }
    @Override
    public String[] inventoryAliases() {
        return new String[]{"inv", "i", "I"};
    }
    @Override
    public String end() {
        return "esci";
    }
    @Override
    public String[] endAliases() {
        return new String[]{"end", "fine", "exit"};
    }
    @Override
    public String help() {
        return "help";
    }
    @Override
    public String[] helpAliases() {
        return new String[]{"aiuto", "comandi"};
    }
    @Override
    public String look() {
        return "osserva";
    }
    @Override
    public String[] lookAliases() {
        return new String[]{};
    }
    @Override
    public String examine() {
        return "esamina";
    }
    @Override
    public String[] examineAliases() {
        return new String[]{"guarda", "vedi", "leggi", "cerca", "descrivi", "ispeziona", "analizza", "scruta"};
    }
    @Override
    public String pick_up() {
        return "raccogli";
    }
    @Override
    public String[] pick_upAliases() {
        return new String[]{"prendi","arraffa","agguanta","preleva"};
    }
    @Override
    public String open() {
        return "apri";
    }
    @Override
    public String[] openAliases() {
        return new String[]{"forza", "spalanca", "sfonda"};
    }
    @Override
    public String close() {
        return "chiudi";
    }
    @Override
    public String[] closeAliases() {
        return new String[]{};
    }
    @Override
    public String drop() {
        return "lascia";
    }
    @Override
    public String[] dropAliases() {
        return new String[]{"butta","getta","poggia","abbandona","disfati"};
    }
    @Override
    public String put() {
        return "metti";
    }
    @Override
    public String[] putAliases() {
        return new String[]{"inserisci","infila","riponi", "collega"};
    }
    @Override
    public String push() {
        return "premi";
    }
    @Override
    public String[] pushAliases() {
        return new String[]{"spingi","attiva","pigia","schiaccia"};
    }
    @Override
    public String attack() {
        return "attacca";
    }
    @Override
    public String[] attackAliases() {
        return new String[]{"spara","colpisci","danneggia","distruggi","ammazza","uccidi","stermina","massacra","fredda","sopprimi",
        "annienta","assassina"};
    }
    @Override
    public String equip() {
        return "equipaggia";
    }
    @Override
    public String[] equipAliases() {
        return new String[]{"mettiti","indossa","caccia"};
    }
    @Override
    public String unequip() {
        return "disequipaggia";
    }
    @Override
    public String[] unequipAliases() {
        return new String[]{"togliti","togli"};
    }
    @Override
    public String talk_to() {
        return "parla";
    }
    @Override
    public String[] talk_toAliases() {
        return new String[]{"conversa","chiama","chiedi","discuti","chiacchiera"};
    }
    @Override
    public String give() {
        return "dai";
    }
    @Override
    public String[] giveAliases() {
        return new String[]{"dona","regala","consegna"};
    }
    @Override
    public String insert_pass_strongbox() {
        return "528491";
    }
    @Override
    public String[] insert_pass_strongboxAliases() {
        return new String[]{};
    }
    @Override
    public String insert_pass_archive() {
        return "201204";
    }
    @Override
    public String[] insert_pass_archiveAliases() {
        return new String[]{};
    }
    @Override
    public String insert_hex() {
        return "42";
    }
    @Override
    public String[] insert_hexAliases() {
        return new String[]{};
    }
    @Override
    public String use() {
        return "usa";
    }
    @Override
    public String[] useAliases() {
        return new String[]{"utilizza","sfrutta","adopera","impiega"};
    }
    @Override
    public String save() {
        return "salva";
    }
    @Override
    public String[] saveAliases() {
        return new String[]{"save","salvataggio"};
    }
    @Override
    public String load() {
        return "carica";
    }
    @Override
    public String[] loadAliases() {
        return new String[]{"load","caricamento"};
    }
}
