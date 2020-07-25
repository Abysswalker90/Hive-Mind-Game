package HiveMind;

import Adventure.GameDescription;
import Adventure.Parser.ParserOutput;
import Adventure.Type.GameCharacter;
import Adventure.Type.GameNPC;
import Adventure.Type.GameItem;
import Adventure.Type.GameItemContainer;
import Adventure.Type.Command;
import Adventure.Type.CommandType;
import Adventure.Type.Room;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.List;

public class HiveMindGame extends GameDescription {

    InterfaceCommandNames commandNames;
    InterfaceItemParameters itemParameters;
    InterfaceCharacterParameters characterParameters;

    private final int MAX_EQUIPPABLE = 2;

    private final int ID_ROOM_LIFT = 0;
    private final int ID_ROOM_HALL = 1;
    private final int ID_ROOM_WESTWING = 2;
    private final int ID_ROOM_BATHROOM = 3;
    private final int ID_ROOM_EASTWING = 4;
    private final int ID_ROOM_GENERATOR = 5;
    private final int ID_ROOM_LAB = 6;
    private final int ID_ROOM_CONTROL = 7;
    private final int ID_ROOM_ARCHIVE = 8;
    private final int ID_ROOM_CLONEROOM = 9;

    private final int ID_ITEM_NOTE1 = 0;
    private final int ID_ITEM_NOTE2 = 1;
    private final int ID_ITEM_NOTE3 = 2;
    private final int ID_ITEM_NOTE4 = 3;
    private final int ID_ITEM_NOTE5 = 4;
    private final int ID_ITEM_NOTE6 = 5;
    private final int ID_ITEM_CROWBAR = 6;
    private final int ID_ITEM_FLASHLIGHT = 7;
    private final int ID_ITEM_FUSES = 8;
    private final int ID_ITEM_GENERATOR = 9;
    private final int ID_ITEM_SCREWDRIVER = 10;
    private final int ID_ITEM_GRATE = 11;
    private final int ID_ITEM_IDCARD = 12;
    private final int ID_ITEM_LASERGUN = 13;
    private final int ID_ITEM_LOCKER = 14;
    private final int ID_ITEM_STRONGBOX = 15;
    private final int ID_ITEM_GATE_OPENER = 16;
    private final int ID_ITEM_LORE_TERMINAL = 17;
    private final int ID_ITEM_FIRST_AID_KIT = 18;
    private final int ID_ITEM_FUSES_CONTAINER = 19;

    private final int ID_CHARACTER_CORPSE_HALL = 0;
    private final int ID_CHARACTER_CORPSE_WESTWING = 1;
    private final int ID_CHARACTER_CORPSE_BATHROOM = 2;
    private final int ID_CHARACTER_CORPSE_LAB = 3;
    private final int ID_CHARACTER_CLONE = 4;

    private boolean eventGotCrowbar = false;
    private boolean eventGotScrewdriver = false;
    private boolean eventBathroomVisitedAfterScrewdriver = false;
    private boolean eventGrateOpened = false;
    private boolean eventGotFlashlight = false;
    private boolean eventGotIDCard = false;
    private boolean eventGeneratorActivated = false;
    private boolean eventEastWingVisitedAfterGenerator = false;
    private boolean eventStrongboxUnlocked = false;
    private boolean eventArchiveUnlocked = false;
    private boolean eventHexSolved = false;
    
    public boolean eventGoodEnding = false;
    
    private int talkedToItem = 0; //contatore per easter egg
    
    public HiveMindGame(InterfaceCommandNames commandNames, InterfaceItemParameters itemParameters, InterfaceCharacterParameters characterParameters) {
        this.commandNames = commandNames;
        this.itemParameters = itemParameters;
        this.characterParameters = characterParameters;
    }

    @Override
    public void init() throws Exception {
        //COMMANDS
        Command north = new Command(CommandType.NORD, commandNames.north());
        north.setAlias(commandNames.northAliases());
        getCommands().add(north);
        Command south = new Command(CommandType.SOUTH, commandNames.south());
        south.setAlias(commandNames.southAliases());
        getCommands().add(south);
        Command east = new Command(CommandType.EAST, commandNames.east());
        east.setAlias(commandNames.eastAliases());
        getCommands().add(east);
        Command west = new Command(CommandType.WEST, commandNames.west());
        west.setAlias(commandNames.westAliases());
        getCommands().add(west);
        Command crawl = new Command(CommandType.CRAWL, commandNames.crawl());
        crawl.setAlias(commandNames.crawlAliases());
        getCommands().add(crawl);
        Command inventory = new Command(CommandType.INVENTORY, commandNames.inventory());
        inventory.setAlias(commandNames.inventoryAliases());
        getCommands().add(inventory);
        Command end = new Command(CommandType.END, commandNames.end());
        end.setAlias(commandNames.endAliases());
        getCommands().add(end);
        Command help = new Command(CommandType.HELP, commandNames.help());
        help.setAlias(commandNames.helpAliases());
        getCommands().add(help);
        Command look = new Command(CommandType.LOOK, commandNames.look());
        look.setAlias(commandNames.lookAliases());
        getCommands().add(look);
        Command examine = new Command(CommandType.EXAMINE, commandNames.examine());
        examine.setAlias(commandNames.examineAliases());
        getCommands().add(examine);
        Command pickup = new Command(CommandType.PICK_UP, commandNames.pick_up());
        pickup.setAlias(commandNames.pick_upAliases());
        getCommands().add(pickup);
        Command open = new Command(CommandType.OPEN, commandNames.open());
        open.setAlias(commandNames.openAliases());
        getCommands().add(open);
        Command close = new Command(CommandType.CLOSE, commandNames.close());
        close.setAlias(commandNames.closeAliases());
        getCommands().add(close);
        Command drop = new Command(CommandType.DROP, commandNames.drop());
        drop.setAlias(commandNames.dropAliases());
        getCommands().add(drop);
        Command put = new Command(CommandType.PUT, commandNames.put());
        put.setAlias(commandNames.putAliases());
        getCommands().add(put);
        Command push = new Command(CommandType.PUSH, commandNames.push());
        push.setAlias(commandNames.pushAliases());
        getCommands().add(push);
        Command attack = new Command(CommandType.ATTACK, commandNames.attack());
        attack.setAlias(commandNames.attackAliases());
        getCommands().add(attack);
        Command equip = new Command(CommandType.EQUIP, commandNames.equip());
        equip.setAlias(commandNames.equipAliases());
        getCommands().add(equip);
        Command unequip = new Command(CommandType.UNEQUIP, commandNames.unequip());
        unequip.setAlias(commandNames.unequipAliases());
        getCommands().add(unequip);
        Command talk = new Command(CommandType.TALK_TO, commandNames.talk_to());
        talk.setAlias(commandNames.talk_toAliases());
        getCommands().add(talk);
        Command give = new Command(CommandType.GIVE, commandNames.give());
        give.setAlias(commandNames.giveAliases());
        getCommands().add(give);
        Command insertPassStrongbox = new Command(CommandType.INSERT_PASS_STRONGBOX, commandNames.insert_pass_strongbox());
        insertPassStrongbox.setAlias(commandNames.insert_pass_strongboxAliases());
        getCommands().add(insertPassStrongbox);
        Command insertPassArchive = new Command(CommandType.INSERT_PASS_ARCHIVE, commandNames.insert_pass_archive());
        insertPassArchive.setAlias(commandNames.insert_pass_archiveAliases());
        getCommands().add(insertPassArchive);
        Command insertHex = new Command(CommandType.INSERT_HEX, commandNames.insert_hex());
        insertHex.setAlias(commandNames.insert_hexAliases());
        getCommands().add(insertHex);
        Command use = new Command(CommandType.USE, commandNames.use());
        use.setAlias(commandNames.useAliases());
        getCommands().add(use);
        Command save = new Command(CommandType.SAVE, commandNames.save());
        save.setAlias(commandNames.saveAliases());
        getCommands().add(save);
        Command load = new Command(CommandType.LOAD, commandNames.load());
        load.setAlias(commandNames.loadAliases());
        getCommands().add(load);

        //ROOMS
        
        Room lift = new Room(ID_ROOM_LIFT, "Ascensore", false);
        lift.setFirstDescription("\nTi svegli.\n\n"
                + "<<Dove sono?>> e' la prima cosa che pensi, guardandoti intorno. Sei in\n"
                + "quello che sembra essere un ascensore, al buio quasi completo.\n"
                + "Non ricordi nulla di quello che e' successo. Provi istintivamente a premere\n"
                + "i pulsanti dell'ascensore, ma non funzionano. Capisci che in qualche modo\n"
                + "i cavi che lo reggevano devono essersi staccati e sei rimasto\n"
                + "intrappolato li'.\n"
                + "\nNoti che nella tua tasca c'e' un foglietto.");
        lift.setDescription("L'ascensore dal quale sei arrivato in questo posto...");
        lift.setLook("\nUn ascensore rotto. Noti che la scatola fusibili e' aperta, qualcuno\n"
                + "sembra ancora in buone condizioni.\n"
                + "Le porte sono socchiuse ma inagibili, non dovrebbe essere difficile\n"
                + "forzarle a mano.");

        Room hall = new Room(ID_ROOM_HALL, "Atrio", false);
        hall.setFirstDescription("Ti trovi davanti a un'enorme stanza che a prima vista\n"
                + "sembra un'anticamera per qualche tipo di laboratorio nascosto: scorgi sui\n"
                + "muri e sul pavimento dei cavi che irradiano una leggera luce blu, che\n"
                + "confluiscono tutti in una grande porta a scorrimento che al momento\n"
                + "sembra sbarrata.\n"
                + "\nCon orrore noti un cadavere poggiato al muro. Presenta un buco nel\n"
                + "petto, con ustioni gravi attorno ad esso e sugli organi interni. Sembra\n"
                + "essere morto di recente.");
        hall.setDescription("L'atrio del laboratorio.");
        hall.setLook("La porta sbarrata sembra necessitare di qualche tipo di meccanismo per\n"
                + "poter essere aperta. Noti lungo il muro un grosso poster di propaganda\n"
                + "scritto in russo.\n"
                + "\nIl cadavere ha in mano una spranga di ferro, potrebbe tornarti utile.\n"
                + "Guardandoti intorno scorgi altre due porte ad est e ovest che sembrano\n"
                + "essere accessibili.");

        Room westWing = new Room(ID_ROOM_WESTWING, "Ala Ovest", false);
        westWing.setFirstDescription("Ti dirigi verso l'ala ovest del laboratorio, illuminata debolmente\n"
                + "da plafoniere al neon; noti intorno a te diverse camere con posto\n"
                + "letto vuote, i materassi insanguinati sparpagliati sul pavimento,\n"
                + "dei carrelli con vari attrezzi da chirurgo e farmaci e una fila\n"
                + "di armadietti.\n"
                + "\nL'atmosfera sembrerebbe calma... se non fosse per il cadavere disteso\n"
                + "sul pavimento.");
        westWing.setDescription("L'ala ovest del laboratorio. Debolmente illuminata.");
        westWing.setLook("La maggior parte degli armadietti e' aperta e vuota, tranne uno,\n"
                + "che sembra bloccato.\n"
                + "Forse puoi forzarlo in qualche modo...\n"
                + "Il cadavere sembra essere quello di una guardia, e nel taschino\n"
                + "dell'uniforme ha una torcia funzionante.\n"
                + "\nNoti due porte: una a nord sembra avere bisogno di identificazione,\n"
                + "l'altra a sud e' aperta.");

        Room bathroom = new Room(ID_ROOM_BATHROOM, "Bagno", false);
        bathroom.setFirstDescription("I bagni. Giudicando dal caos deve esserci stata una violenta colluttazione\n"
                + "di recente: alcuni sanitari sono rotti, diverse porte sono spalancate,\n"
                + "crivellate dagli spari, forse usate come copertura. Perlustrando, i tuoi\n"
                + "sospetti sono confermati dalla visione di un altro cadavere, appoggiato\n"
                + "con la spalla ad un lavandino.");
        bathroom.setDescription("I bagni. La maggior parte dei sanitari sono distrutti.");
        bathroom.setLook("Ti avvicini al cadavere. Nella tasca del giubbotto sporge un foglio di\n"
                + "carta. A terra, a poca distanza dalla sua mano, una pistola al plasma,\n"
                + "probabilmente l'arma usata dallo sfortunato durante la colluttazione.\n"
                + "\nGuardandoti intorno noti la grata di un condotto d'aerazione, sotto\n"
                + "la quale puoi scorgere delle impronte di mani insanguinate sul muro.\n"
                + "Pensi che sia abbastanza larga per permettere il passaggio di una persona,\n"
                + "strisciando, ma per il momento e' inaccessibile.\n"
                + "\nOsservandola da vicino, forse potresti riuscire a rimuovere le viti con\n"
                + "un cacciavite.");

        Room eastWing = new Room(ID_ROOM_EASTWING, "Ala Est", false);
        eastWing.setFirstDescription("L'ala est e' completamente buia. Questo posto ti mette i brividi...\n"
                + "non riesci a vedere a un palmo dal tuo naso. Meglio tornare indietro.");
        eastWing.setDescription("L'ala est del laboratorio. Questo posto ti mette i brividi.");
        eastWing.setLook("Non riesci a vedere nulla. Hai bisogno di luce.");

        Room generatorRoom = new Room(ID_ROOM_GENERATOR, "Sala quadri elettrici", true);
        generatorRoom.setFirstDescription("La cupa atmosfera dell'ala est e' traslata anche in questo piccolo\n"
                + "locale, che dev'essere la sala dei quadri elettrici.\n"
                + "\nDecine di interruttori e levette ti circondano, ma nessuna sembra\n"
                + "funzionare. Qualcosa deve aver fatto saltare la corrente.");
        generatorRoom.setDescription("La sala dei quadri elettrici. Piena di interruttori e leve.");
        generatorRoom.setLockedDescription("Non vedi nulla... meglio non rischiare di fare passi falsi.");
        generatorRoom.setLook("Vedi un grosso oggetto nella penombra simile a un generatore elettrico.\n"
                + "Ti avvicini e fai luce su di esso, e noti che e' disattivato e che sembra\n"
                + "manchi qualche fusibile.");

        Room lab = new Room(ID_ROOM_LAB, "Laboratorio", true);
        lab.setFirstDescription("Alla fine del condotto vedi una grata, attraverso la quale puoi osservare\n"
                + "quello che sembra uno dei laboratori della struttura. Dopo un primo invano\n"
                + "tentativo di apertura decidi di forzarla, scagliandoti piu' volte con\n"
                + "tutto il tuo peso su di essa.\n"
                + "\nDopo qualche tentativo, la grata si sfonda e cadi sul pavimento, prendendo\n"
                + "anche una bella botta. Intorno a te l'enorme laboratorio, pieno di strane\n"
                + "attrezzature, fogli sparsi, delle grosse capsule rotte e banchi colmi di\n"
                + "provette di sostanze ignote; un generale caos regna nell'ambiente.\n"
                + "L'unica porta del laboratorio e' chiusa. Dev'essere la porta chiusa\n"
                + "dall'interno che hai scoperto nell'ala est!\n"
                + "\nLa sblocchi premendo un pulsante, adesso puoi avere accesso anche\n"
                + "dall'altro lato.\n");
        lab.setDescription("Uno dei laboratori all'interno della struttura.");
        lab.setLockedDescription("Non vedi nulla... meglio non rischiare di fare passi falsi.");
        lab.setLook("Un ammasso di oggetti tecnologici e strumenti che ti riesce impossibile\n"
                + "comprendere.\n"
                + "\nNel marasma generale non ti eri accorto del cadavere di un uomo col camice.\n"
                + "Ormai ci stai facendo l'abitudine.\n"
                + "\nA giudicare dal contesto, doveva essere lui a gestire questo luogo.\n"
                + "Supponi che il foglio nella sua mano\n"
                + "debba essere il resoconto di qualche esperimento. Nel taschino intravedi\n"
                + "una tessera ID...\n"
                + "Potrebbe tornarti molto utile.");

        Room controlRoom = new Room(ID_ROOM_CONTROL, "Sala comandi", true);
        controlRoom.setFirstDescription("<<DING! AUTENTICATO. BENVENUTO DOTTOR SOKOLOV.>>\n"
                + "\nAll'apertura della porta, si presenta a te una sala con un gran numero di\n"
                + "terminali e comandi, nonche' schermi che mostrano la vista di alcune delle\n"
                + "telecamere all'interno dell'edificio.");
        controlRoom.setDescription("La sala comandi. Un'infinita' di pulsanti e terminali ti fanno\n"
                + "sentire spaesato.");
        controlRoom.setLockedDescription("Questa porta e' chiusa. Al suo fianco c'e' un piccolo scanner.\n"
                + "Ti avvicini e provi ad attivarlo, non si sa mai.\n"
                + "\n<<ACCESSO NEGATO. RICHIESTO ID PERSONALE AUTORIZZATO.>>");
        controlRoom.setLook("Spinto dalla curiosita', cerchi nei computer i filmati delle telecamere.\n"
                + "Nei piu' recenti con orrore assisti alle sparatorie che hanno portato\n"
                + "alla morte dei cadaveri che hai trovato esplorando il laboratorio.\n"
                + "\nCerchi tra i comandi qualcosa che ti possa aiutare. Dopo una lunga\n"
                + "ricerca trovi il computer addetto al controllo delle porte elettroniche.\n"
                + "\nUno di questi pulsanti presenta un avviso in russo e un avviso di\n"
                + "pericolo al suo fianco. Ci siamo! Dev'essere sicuramente il meccanismo per\n"
                + "sbloccare la porta nell'atrio.\n"
                + "\nNoti alcuni fogli vicini a uno dei terminali e, a nord, la sagoma di una\n"
                + "porta che richiede una password...");

        Room archive = new Room(ID_ROOM_ARCHIVE, "Archivio", true);
        archive.setFirstDescription("Dopo aver inserito la password, la sagoma si rivela essere una porta a\n"
                + "scorrimento che aprendosi ti porta a un piccolo locale zeppo di scaffali\n"
                + "contenenti archivi e una scrivania con un terminale. Questo posto forse ha\n"
                + "qualche risposta alle tue infinite domande.");
        archive.setDescription("Un archivio tenuto nascosto all'interno della sala comandi.");
        archive.setLockedDescription("Ti serve un codice di 6 cifre per entrare. Dove puoi averle viste...?"
                + "\n\t\t _____________________\n"
                + "\t\t|  _________________  |\n"
                + "\t\t| | PASS         0  | |\n"
                + "\t\t| |_________________| |\n"
                + "\t\t|     ___ ___ ___     |\n"
                + "\t\t|    | 7 | 8 | 9 |    |\n"
                + "\t\t|    |___|___|___|    |\n"
                + "\t\t|    | 4 | 5 | 6 |    |\n"
                + "\t\t|    |___|___|___|    |\n"
                + "\t\t|    | 1 | 2 | 3 |    |\n"
                + "\t\t|    |___|___|___|    |\n"
                + "\t\t|        | 0 |        |\n"
                + "\t\t|        |___|        |\n"
                + "\t\t|_____________________|");
        archive.setLook("Dai uno sguardo alla miriade di scaffali nella stanza.\n"
                + "Su uno di essi trovi... un kit di primo soccorso?\n"
                + "La quasi totalita' dei dati archiviati e' per te inutile e poco interessante,\n"
                + "quindi decidi di accedere al terminale.\n"
                + "\nAppena acceso, il terminale ti richiede un codice d'accesso. Senza\n"
                + "nemmeno darti il tempo di pensare a dove poterlo trovare, il terminale\n"
                + "stesso ti presenta una schermata nella quale e' probabilmente nascosto\n"
                + "il codice d'accesso.");

        Room cloneRoom = new Room(ID_ROOM_CLONEROOM, "Sala clonazione", true); 
        cloneRoom.setFirstDescription("La lenta apertura della porta ti mette una leggera ansia e ti fa stare\n"
                + "in guardia.\n"
                + "\nUna volta aperta, l'enorme stanza si rivela a te: lungo le pareti\n"
                + "delle capsule ripiene di uno strano liquido; alcune sono rotte o\n"
                + "inagibili.\n"
                + "\nTi guardi intorno. Tra un'enorme pila di cadaveri c'e' un uomo ancora\n"
                + "vivo. Nudo e ferito, sembra essersi trascinato al muro lasciando\n"
                + "indietro una scia di sangue.\n");
        cloneRoom.setDescription("L'ultima stanza del laboratorio. Affrettati a finire il tuo lavoro!");
        cloneRoom.setLockedDescription("Una grande porta chiusa da qualche meccanismo.");
        cloneRoom.setLook("Una stanza piena di capsule lungo le pareti, tutte collegate ad un\n"
                + "supercomputer.\n"
                + "\nA giudicare dalla situazione, il misterioso uomo e' stato l'unico che\n"
                + "e' riuscito a sopravvivere al massacro.\n"
                + "Carichi la tua pistola e ti prepari a premere il grilletto, ma\n"
                + "fermandoti a guardarlo... ti accorgi che ha il tuo volto.");

        lift.setNorth(hall);
        hall.setEast(eastWing);
        hall.setWest(westWing);
        hall.setNorth(cloneRoom);
        hall.setSouth(lift);
        westWing.setSouth(bathroom);
        westWing.setNorth(controlRoom);
        westWing.setEast(hall);
        bathroom.setCrawl(lab);
        bathroom.setNorth(westWing);
        eastWing.setWest(hall);
        eastWing.setNorth(generatorRoom);
        eastWing.setSouth(lab);
        generatorRoom.setSouth(eastWing);
        lab.setNorth(eastWing);
        lab.setCrawl(bathroom);
        controlRoom.setSouth(westWing);
        controlRoom.setNorth(archive);
        archive.setSouth(controlRoom);
        cloneRoom.setSouth(hall);

        getRooms().add(lift);
        getRooms().add(hall);
        getRooms().add(westWing);
        getRooms().add(bathroom);
        getRooms().add(eastWing);
        getRooms().add(generatorRoom);
        getRooms().add(lab);
        getRooms().add(controlRoom);
        getRooms().add(archive);
        getRooms().add(cloneRoom);
        
        //ITEMS

        GameItem note1 = new GameItem(ID_ITEM_NOTE1, itemParameters.note1Name());
        note1.setAlias(itemParameters.note1Aliases());
        note1.setDescription("nota 1");
        note1.setExamine("16/11/2931 - Territorio di Krasnodar, Russia europea meridionale\n\n"
                       + "Dopo 40 anni di carriera vengo di nuovo richiamato in servizio. Speravo\n"
                       + "di poter passare piu' tempo con la mia famiglia, ora che sono pensionato,\n"
                       + "ma l'armata mi ha assegnato un'ultima missione da completare con urgenza.\n"
                       + "Avevano un evidente bisogno di me. Mi sono fatto strada tra i massimi\n"
                       + "ranghi dell'esercito, non solo per le mie capacita' fisiche fuori dal\n"
                       + "normale, ma anche per la fedelta' che il Governo ripone nei miei\n"
                       + "confronti. Ritornare in madrepatria per una missione segreta era l'ultima\n"
                       + "cosa che mi aspettavo di fare, sinceramente. Mi chiedo, tuttavia, perche'\n"
                       + "avessero proprio bisogno di me per una missione di eliminazione\n"
                       + "cosi' semplice...\t2");
        note1.setPickupable(true);

        GameItem note2 = new GameItem(ID_ITEM_NOTE2, itemParameters.note2Name());
        note2.setAlias(itemParameters.note2Aliases());
        note2.setDescription("nota 2");
        note2.setExamine
                 ("|| LABORATORIO DI CLONAZIONE A776, ZONA: RUSSIA CAUCASICA ||\n\n"
                + "> Rapporto clonazione 9681/2931 effettuata in data 16/11/2931\n"
                + "\tore 15:07: condizioni regolari.\n\n"
                + ">> Nome: Katrina Voyevoda\n"
                + ">> Sesso: F\n"
                + ">> Data inizio crescita cellule: 07/04/2931\n"
                + ">> Data completamento clonazione: 16/11/2931\n"
                + ">> Condizioni fisiche: stabili\n"
                + ">>> Anomalie: nulla da riportare\n"
                + ">> Condizioni mentali: stabili\n"
                + ">>> Anomalie: nulla da riportare\n"
                + ">> Soggetto originale: XX414087450\n");
        note2.setPickupable(true);

        GameItem note3 = new GameItem(ID_ITEM_NOTE3, itemParameters.note3Name());
        note3.setAlias(itemParameters.note3Aliases());
        note3.setDescription("nota 3");
        note3.setExamine("La planimetria dell'edificio.\n"
                + "\n"
                + "┌───────────────────────────┐\n"
                + "│  ┌──.──┐ ┌─────┐ ┌─────┐  │   1: Ascensore\n"
                + "│  │     │ │     │ │     │  │   2: Atrio\n"
                + "│  │  8  │ │  9  │ │  6  │  │   3: Ala Ovest\n"
                + "│  └─┐ ┌─┘ └─┐ ┌─┘ └─┐ ┌─┘  │   4: Bagno\n"
                + "│  ┌─┘ └─┐ ┌─┘ └─┐ ┌─┘ └─┐  │   5: Ala Est\n"
                + "│  │     └─┘     └─┘     │  │   6: Sala quadri elettrici\n"
                + "│  │  3  ┌─┐  2  ┌─┐  5  │  │   7: Laboratorio\n"
                + "│  └─┐ ┌─┘ └─┐ ┌─┘ └─┐ ┌─┘  │   8: Sala comandi\n"
                + "│  ┌─┘ └─┐ ┌─┘ └─┐ ┌─┘ └─┐  │   9: Sala clonazione\n"
                + "│  │     │ │     │ │     │  │   \n"
                + "│  │  4  │ │  1  │ │  7  │  │   \n"
                + "│  └─────┘ └─────┘ └─────┘  │   \n"
                + "│           528491          │   \n"
                + "└───────────────────────────┘\n"
                + "\n"
                + "Due cose attirano la tua attenzione: sul foglio sono stati appuntati a\n"
                + "penna un puntino sulla sala comandi e una serie di numeri sul fondo della\n"
                + "mappa. Qualsiasi cosa volessero significare, l'uomo non doveva essere una\n"
                + "persona molto sveglia, da vivo...\n"
                + "\nL'ultima cifra della serie numerica e' evidenziata in rosso.");
        note3.setPickupable(true);

        GameItem note4 = new GameItem(ID_ITEM_NOTE4, itemParameters.note4Name());
        note4.setAlias(itemParameters.note4Aliases());
        note4.setDescription("nota 4");
        note4.setExamine
                 ("Un ritaglio di un articolo del giornale \"L'Alba Nuova\", datato 26/05/2927:\n\n"
                + "Il presidente degli Stati Uniti Jeff Davis impone leggi razziali\n"
                + "contro i cloni\n"
                + "\t\t\tGLORIA ALL'UMANITA'!\n"
                + "Popolo della Terra, l'umanita' sta subendo la piu' grave crisi\n"
                + "del millennio: la clonazione di massa per scopi di eugenetica\n"
                + "sta annichilendo la razza superiore che li ha creati! E' un\n"
                + "insulto per la nostra specie, e dobbiamo eradicarla! In\n"
                + "quanto membro fondatore del Partito Mondiale della Confraternita,\n"
                + "faro' tutto cio' che e' in mio potere per ripristinare l'ordine\n"
                + "naturale stabilito da Dio, cancelleremo ogni fabbrica di cloni\n"
                + "in tutto il mondo, e condanneremo ogni comportamento finalizzato\n"
                + "alla loro protezione. La rinascita dell'umanita' inizia da adesso!\n"
                + "Hallelujah! Gioite, o fratelli! Riprendiamoci il nostro pianeta!\n"
                + "\t\t\t\t2");
        note4.setPickupable(true);

        GameItem note5 = new GameItem(ID_ITEM_NOTE5, itemParameters.note5Name());
        note5.setAlias(itemParameters.note5Aliases());
        note5.setDescription("nota 5");
        note5.setExamine
                 ("Un documento classificato. Con sorpresa, scopri che si tratta dei tuoi\n"
                + "dati anagrafici:\n\n"
                + ">>Nome: Sergej ▓▓▓▓▓▓▓\n"
                + ">>Data di nascita: 29/04/▓▓▓▓\n"
                + ">>Sesso: M\n"
                + ">>Luogo di nascita: Volgograd, Russia\n\n"
                + ">>Note: \n"
                + "  Il soggetto e' dotato di una spiccata intelligenza e di capacita' fisiche\n"
                + "  fuori dal normale. E' stato considerato il soldato piu' forte dell'Armata\n"
                + "  Russa, un esemplare perfetto per ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n"
                + "  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n"
                + "  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n"
                + "  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n"
                + "  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n"
                + "  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n"
                + "  ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n\n"
                + "Non riesci a leggere il resto del documento, qualcuno ha cancellato alcune\n"
                + "righe sensibili.\t0");
        note5.setPickupable(true);

        GameItem note6 = new GameItem(ID_ITEM_NOTE6, itemParameters.note6Name());
        note6.setAlias(itemParameters.note6Aliases());
        note6.setDescription("nota 6");
        note6.setExamine("18/11/2931, ore 19:39 - Laboratorio A776\n"
                       + "\n"
                       + "Notizie dall'esterno rivelano che il nostro laboratorio e' stato scoperto.\n" 
                       + "Abbiamo pochi minuti per evacuare ed eliminare le prove prima di essere\n"
                       + "tutti massacrati. Qualcuno deve mettere in salvo i soggetti...\n" 
                       + "Il mondo non puo' perdere il frutto delle nostre ricerche! L'umanita'\n" 
                       + "ha bisogno di noi, al diavolo la Confraternita! Se butteranno giu' questo\n" 
                       + "posto, con esso verra' buttato giu' l'Archivio... qualcuno deve scoprire\n" 
                       + "la verita'...\n"
                       + "Devo proteggere Sergej, e' tutto cio' che resta dell'umanita' originale.\n"
                       + "Le telecamere hanno appena rivelato degli intrusi. Siamo spacciati.\t4");
        note6.setPickupable(true);
        note6.setPickupable(true);

        GameItem crowbar = new GameItem(ID_ITEM_CROWBAR, itemParameters.crowbarName());
        crowbar.setAlias(itemParameters.crowbarAliases());
        crowbar.setDescription("una spranga di ferro");
        crowbar.setExamine("Una spranga di ferro leggermente piegata alle estremita'.\n"
                + "Postresti usarla come piede di porco.");
        crowbar.setPickupable(true);
        crowbar.setEquippable(true);

        GameItem flashlight = new GameItem(ID_ITEM_FLASHLIGHT, itemParameters.flashlightName());
        flashlight.setAlias(itemParameters.flashlightAliases());
        flashlight.setDescription("una torcia da taschino");
        flashlight.setExamine("Una torcia da taschino. Puoi utilizzarla senza l'uso delle mani.");
        flashlight.setPickupable(true);

        GameItem fuses = new GameItem(ID_ITEM_FUSES, itemParameters.fusesName());
        fuses.setAlias(itemParameters.fusesAliases());
        fuses.setDescription("dei fusibili");
        fuses.setExamine("Svariati fusibili estratti dal quadro comandi dell'ascensore.\n"
                + "Possono tornare utili.");
        fuses.setPickupable(true);

        GameItem screwdriver = new GameItem(ID_ITEM_SCREWDRIVER, itemParameters.screwdriverName());
        screwdriver.setAlias(itemParameters.screwdriverAliases());
        screwdriver.setDescription("un cacciavite");
        screwdriver.setExamine("Un cacciavite a taglio con evidenti segni di usura.");
        screwdriver.setPickupable(true);

        GameItem grate = new GameItem(ID_ITEM_GRATE, itemParameters.grateName());
        grate.setAlias(itemParameters.grateAliases());
        grate.setDescription("una grata di ferro");
        grate.setExamine("Una grata di ferro che chiude il condotto. Delle viti la tengono ben\n"
                + "stretta al muro.");
        grate.setPickupable(false);
        grate.setOpenable(false);
        bathroom.getItems().add(grate);

        GameItem idCard = new GameItem(ID_ITEM_IDCARD, itemParameters.idCardName());
        idCard.setAlias(itemParameters.idCardAliases());
        idCard.setDescription("una tessera ID");
        idCard.setExamine("Il badge identificativo del professor Sokolov.\n"
                + "Puoi usarlo per autenticarti dove richiesto.");
        idCard.setPickupable(true);

        GameItem laserGun = new GameItem(ID_ITEM_LASERGUN, itemParameters.laserGunName());
        laserGun.setAlias(itemParameters.laserGunAliases());
        laserGun.setDescription("una pistola al plasma");
        laserGun.setExamine("Stando alle ferite trovate sui cadaveri dev'essere un'arma molto potente.\n"
                + "Meglio non separarsene...");
        laserGun.setPickupable(true);
        laserGun.setEquippable(true);

        GameItemContainer locker = new GameItemContainer(ID_ITEM_LOCKER, itemParameters.lockerName());
        locker.setAlias(itemParameters.lockerAliases());
        locker.setDescription("un armadietto");
        locker.setExamine("Se avessi qualcosa per fare leva, potresti riuscire a forzare questo\n"
                + "armadietto.");
        locker.setPickupable(false);
        locker.setOpenable(false);
        westWing.getItems().add(locker);
        locker.getList().add(note3);

        GameItemContainer strongbox = new GameItemContainer(ID_ITEM_STRONGBOX, itemParameters.strongboxName());
        strongbox.setAlias(itemParameters.strongboxAliases());
        strongbox.setDescription("una cassaforte elettronica");
        strongbox.setExamine("Una cassaforte elettronica. Necessita di corrente e, probabilmente, di\n"
                + "una combinazione.");
        strongbox.setPickupable(false);
        strongbox.setOpenable(false);
        strongbox.setReachable(false);
        eastWing.getItems().add(strongbox);
        strongbox.getList().add(note5);
        strongbox.getList().add(screwdriver);

        GameItem gateOpener = new GameItem(ID_ITEM_GATE_OPENER, itemParameters.gateOpenerName());
        gateOpener.setAlias(itemParameters.gateOpenerAliases());
        gateOpener.setDescription("il computer di comando dell'edificio");
        gateOpener.setExamine("Il computer di comando. Puoi sbloccare la porta inaccessibile nell'atrio\n"
                            + "premendo il bottone rosso al centro della tastiera.");
        gateOpener.setPickupable(false);
        gateOpener.setPushable(true);
        gateOpener.setPush(false);
        controlRoom.getItems().add(gateOpener);

        GameItem loreTerminal = new GameItem(ID_ITEM_LORE_TERMINAL, itemParameters.loreTerminalName());
        loreTerminal.setAlias(itemParameters.loreTerminalAliases());
        loreTerminal.setDescription("un terminale");
        loreTerminal.setExamine("Un terminale poggiato su una scrivania dell'archivio. Puoi usarlo.");
        loreTerminal.setPickupable(false);
        archive.getItems().add(loreTerminal);

        GameItem firstAidKit = new GameItem(ID_ITEM_FIRST_AID_KIT, itemParameters.firstaidkitName());
        firstAidKit.setAlias(itemParameters.firstaidkitAliases());
        firstAidKit.setDescription("un kit di pronto soccorso");
        firstAidKit.setExamine("Una cassetta piena di medicazioni, bende, cerotti, disinfettanti\n"
                + "e antibiotici. Potrebbe essere sufficiente per salvare una vita.");
        firstAidKit.setPickupable(true);
        archive.getItems().add(firstAidKit);

        GameItemContainer fusesContainer = new GameItemContainer(ID_ITEM_FUSES_CONTAINER, itemParameters.fusesContainerName());
        fusesContainer.setAlias(itemParameters.fusesContainerAliases());
        fusesContainer.setDescription("il pannello di controllo dell'ascensore");
        fusesContainer.setExamine("Il pannello di controllo dell'ascensore. Dopo l'impatto, si e' aperto\n"
                + "leggermente. Potresti aprirlo, ma le sole mani non ti bastano per forzarlo.");
        fusesContainer.setPickupable(false);
        fusesContainer.setOpenable(false);
        fusesContainer.setOpen(false);
        lift.getItems().add(fusesContainer);
        fusesContainer.getList().add(fuses);

        GameItemContainer generatorItem = new GameItemContainer(ID_ITEM_GENERATOR, itemParameters.generatorItemName());
        generatorItem.setAlias(itemParameters.generatorItemAliases());
        generatorItem.setDescription("il generatore elettrico dell'ala est");
        generatorItem.setExamine("Il generatore elettrico dell'ala est. Al momento e' guasto, ma potresti\n"
                + "ripararlo cercando dei fusibili nuovi.\n"
                + "Ti ricordi di aver visto il pannello dell'ascensore lievemente incrinato.\n");
        generatorItem.setPickupable(false);
        generatorItem.setOpenable(false);
        generatorItem.setOpen(true);
        generatorItem.setReachable(true);
        generatorRoom.getItems().add(generatorItem);

        //CHARACTERS
        
        GameCharacter corpseHall = new GameCharacter(ID_CHARACTER_CORPSE_HALL, characterParameters.corpseHallName());
        corpseHall.setAlive(false);
        corpseHall.setAlias(characterParameters.corpseHallAliases());
        corpseHall.setExamine("Una guardia morta.");
        corpseHall.setLook("\nC'e' una guardia morta affiancata al muro. Ha un buco sul petto.");
        corpseHall.setFirstDialogue("Vuoi davvero parlare con un morto?");
        corpseHall.putDialogue("idle", "Non risponde. Mi farei due domande.");
        corpseHall.putDialogue("giveWrong", "Perché vuoi dare un oggetto a un morto?");
        corpseHall.addInventory(note2);
        corpseHall.addInventory(crowbar);
        hall.getNPCs().add(corpseHall);

        GameCharacter corpseWestWing = new GameCharacter(ID_CHARACTER_CORPSE_WESTWING, characterParameters.corpseWestWingName());
        corpseWestWing.setAlive(false);
        corpseWestWing.setAlias(characterParameters.corpseWestWingAliases());
        corpseWestWing.setExamine("Una guardia morta.");
        corpseWestWing.setLook("\nUna guardia morta con una torcia nel taschino.");
        corpseWestWing.setFirstDialogue("Vuoi davvero parlare con un morto?");
        corpseWestWing.putDialogue("idle", "Non risponde. Mi farei due domande.");
        corpseWestWing.putDialogue("giveWrong", "Perché vuoi dare un oggetto a un morto?");
        corpseWestWing.addInventory(flashlight);
        westWing.getNPCs().add(corpseWestWing);

        GameCharacter corpseBathroom = new GameCharacter(ID_CHARACTER_CORPSE_BATHROOM, characterParameters.corpseBathroomName());
        corpseBathroom.setAlive(false);
        corpseBathroom.setAlias(characterParameters.corpseBathroomAliases());
        corpseBathroom.setExamine("Una guardia morta.");
        corpseBathroom.setLook("\nUn uomo accasciato a terra, con il braccio proteso sul lavandino.\n"
                + "Presenta ferite gravi dietro la schiena.");
        corpseBathroom.setFirstDialogue("Vuoi davvero parlare con un morto?");
        corpseBathroom.putDialogue("idle", "Non risponde. Mi farei due domande.");
        corpseBathroom.putDialogue("giveWrong", "Perché vuoi dare un oggetto a un morto?");
        corpseBathroom.addInventory(note4);
        corpseBathroom.addInventory(laserGun);
        bathroom.getNPCs().add(corpseBathroom);

        GameCharacter corpseLab = new GameCharacter(ID_CHARACTER_CORPSE_LAB, characterParameters.corpseLabName());
        corpseLab.setAlive(false);
        corpseLab.setAlias(characterParameters.corpseLabAliases());
        corpseLab.setExamine("Un ricercatore morto. Meritava davvero di morire...?");
        corpseLab.setLook("\nIl corpo di un ricercatore. Il suo camice e' macchiato di sangue, e a\n"
                + "giudicare dalla sua posizione, pare che stesse per portare in salvo\n"
                + "qualcosa, in fretta e furia, prima essere ucciso.\n"
                + "\n"
                + "Era davvero necessario uccidere uno scienziato...?");
        corpseLab.setFirstDialogue("Vuoi davvero parlare con un morto?");
        corpseLab.putDialogue("idle", "Non risponde. Mi farei due domande.");
        corpseLab.putDialogue("giveWrong", "Perché vuoi dare un oggetto a un morto?");
        corpseLab.addInventory(note6);
        corpseLab.addInventory(idCard);
        lab.getNPCs().add(corpseLab);

        GameCharacter clone = new GameCharacter(ID_CHARACTER_CLONE, characterParameters.CloneName());
        clone.setAlias(characterParameters.CloneAliases());
        clone.setAlive(true);
        clone.setExamine("Un uomo identico a te."); //modifichiamolo insieme
        clone.setLook("\nDinanzi a te si eregge una scena raccapricciante.\n"
                + "Insieme ad altre decine di cadaveri, trovi il tuo clone, carponi, ancora\n"
                + "ansimante. Non sembra gli resti molto da vivere.\n"
                + "Provi una certa pena per lui, ma gli ordini sono ordini.");
        clone.setFirstDialogue("Appena ti avvicini, l'uomo tossisce furiosamente. Alcuni lievi schizzi di\n"
                + "sangue trabordano dagli angoli della sua bocca.\n"
                + "Con una voce fioca e dolorante, l'uomo rantola:\n"
                + "<<Ti aspettavo. *coff* Questo momento doveva arrivare prima o poi, era\n"
                + "destino. Per anni abbiamo tentato di ripristinare l'umanita' e di tenerlo\n"
                + "nascosto al mondo. Non c'e' piu' bisogno del mio intervento ormai.\n"
                + "E' finita.>>");
        clone.putDialogue("idle", "<<Se hai intenzione di uccidermi, fallo e basta.\n"
                                + "Non *rantolo* farmi soffrire piu' di cosi'...>>");
        clone.putDialogue(
   "giveCorrect", "<<Perche' vuoi che io continui a vivere? Cosa pensi che succedera'\n"
                + "una volta usciti da qui? Ci ammazzeranno! Siamo uomini morti Sergej!\n"
                + "Lo sei anche tu. Mi ucciderai, ma poi tu... Tu non hai comunque\n"
                + "un futuro. Sei un soldato, Sergej. Lo sarai per tutta la vita...>>\n"
                + "\n"
                + "Insisti a voler curare l'uomo. Senza dire una parola cominci\n"
                + "a tamponare e disinfettare le sue ferite. Dopo aver aspettato che\n"
                + "si riprendesse, cercate di uscire dal sotterraneo tramite un'uscita\n"
                + "di emergenza nascosta sopra una cappa nel laboratorio.\n"
                + "\n"
                + "Dopo aver trascinato con fatica Sergej lungo una serie di scale\n"
                + "arrugginite e in disuso, riuscite a vedere la prima luce del\n"
                + "mattino. Cosa ne sara' di voi non ne avete idea, ma cercherete di\n"
                + "continuare la vostra vita lontano dal regime. L'umanita' per come\n"
                + "la conosciamo ormai e' estinta, ma questo non ti ha fermato dal\n"
                + "voler dare un segnale positivo.\n"
                + "Anche se, di quanto accaduto, il mondo intero non sapra' nulla...\n"
                + "\n");
        clone.putDialogue("giveWrong", "L'uomo non reagisce.");
        clone.putDialogue(
        "attack", "Prima di essere sparato, l'uomo tira un sospiro di sollievo e\n"
                + "accenna un sorriso sereno. Miri alla testa e premi il grilletto.\n"
                + "Lo sparo illumina la stanza per un attimo.\n"
                + "\nNon hai il tempo di realizzare che hai appena ucciso un uomo\n"
                + "inerme, che la ricetrasmittente collegata alla tua uniforme\n"
                + "comincia a captare un segnale. Con una pessima qualita' audio,\n"
                + "dovuta probabilmente dalla profondita' della collocazione della\n"
                + "clinica, cominci a sentire le prime note di un'inno suprematista,\n"
                + "e qualche secondo dopo, un voce pre-registrata. <<Fratello!\n"
                + "Congratulazioni per aver portato a termine la tua missione!\n"
                + "La Confraternita e' fiera di avere soldati del tuo spessore!\n"
                + "\nOra che per mano tua tutti i cloni sono stati eradicati dal\n"
                + "pianeta, l'umanita' e' pronta a risplendere come un tempo!\n"
                + "Gloria e fama ti attendono una volta tornato a casa! [...]>>\n"
                + "\nNon fai in tempo a sentire il resto del messaggio che,\n"
                + "mentre le parole scorrono nelle tue orecchie, cominci a\n"
                + "sentirti male.\n"
                + "Ti accasci a terra, con dolori lungo tutto il corpo.\n"
                + "Hai freddo. Non hai piu' energie. Non hai idea del perche'.\n"
                + "Improvvisamente, ti accorgi di star morendo.\n"
                + "Hai portato a termine la tua missione.\n\n"
                + "Ma a quale costo...?\n\n");
        clone.putDialogue("pickUp", "L'uomo non reagisce. Non ha tempo per le tue stronzate.");
        clone.putDialogue("open", "L'uomo non reagisce. Non ha tempo per le tue stronzate.");
        clone.putDialogue("put", "L'uomo non reagisce. Non ha tempo per le tue stronzate.");
        cloneRoom.getNPCs().add(clone);

        getInventory().add(note1);

        setCurrentRoom(lift);

    }

    @Override
    public String nextMove(ParserOutput p) {
        StringBuilder output = new StringBuilder();

        if (p.getCommand() == null) {
            output.append("Inserisci un comando valido come prima parola!");

        } else {

            if (p.getCommand().getType() == CommandType.NORD) {
                output = commandNorth(p, output);

            } else if (p.getCommand().getType() == CommandType.SOUTH) {
                output = commandSouth(p, output);

            } else if (p.getCommand().getType() == CommandType.EAST) {
                output = commandEast(p, output);

            } else if (p.getCommand().getType() == CommandType.WEST) {
                output = commandWest(p, output);

            } else if (p.getCommand().getType() == CommandType.CRAWL) {
                output = commandCrawl(p, output);

            } else if (p.getCommand().getType() == CommandType.INVENTORY) {
                output = commandInventory(p, output);

            } else if (p.getCommand().getType() == CommandType.LOOK) {
                output = commandLook(p, output);
                
            } else if (p.getCommand().getType() == CommandType.HELP) {
                output = commandHelp(p, output);    

            } else if (p.getCommand().getType() == CommandType.EXAMINE) {
                output = commandExamine(p, output);

            } else if (p.getCommand().getType() == CommandType.PICK_UP) {
                output = commandPickUp(p, output);

            } else if (p.getCommand().getType() == CommandType.ATTACK) {
                output = commandAttack(p, output);

            } else if (p.getCommand().getType() == CommandType.OPEN) {
                output = commandOpen(p, output);

            } else if (p.getCommand().getType() == CommandType.CLOSE) {
                output = commandClose(p, output);

            } else if (p.getCommand().getType() == CommandType.DROP) {
                output = commandDrop(p, output);

            } else if (p.getCommand().getType() == CommandType.PUT) {
                output = commandPut(p, output);

            } else if (p.getCommand().getType() == CommandType.PUSH) {
                output = commandPush(p, output);

            } else if (p.getCommand().getType() == CommandType.EQUIP) {
                output = commandEquip(p, output);

            } else if (p.getCommand().getType() == CommandType.UNEQUIP) {
                output = commandUnequip(p, output);

            } else if (p.getCommand().getType() == CommandType.TALK_TO) {
                output = commandTalkTo(p, output);

            } else if (p.getCommand().getType() == CommandType.GIVE) {
                output = commandGive(p, output);

            } else if (p.getCommand().getType() == CommandType.INSERT_PASS_STRONGBOX) {
                output = commandInsertPassStrongbox(p, output);

            } else if (p.getCommand().getType() == CommandType.INSERT_PASS_ARCHIVE) {
                output = commandInsertPassArchive(p, output);
                
            } else if (p.getCommand().getType() == CommandType.INSERT_HEX) {
                output = commandInsertHex(p, output);

            } else if (p.getCommand().getType() == CommandType.USE) {
                output = commandUse(p, output);

            }

            if (getCurrentRoom().getId() == ID_ROOM_EASTWING) {
                if (eventGeneratorActivated) {
                    for (GameItem it : getCurrentRoom().getItems()) {
                        if (it.getId() == ID_ITEM_STRONGBOX && !eventEastWingVisitedAfterGenerator) {
                            eventEastWingVisitedAfterGenerator = true;
                            output.append("\nLo schermo della cassaforte si e' illuminato. Ora e' possibile provare\n"
                                    + "ad aprirla.\n");
                            getCurrentRoom().setLook("Ora che c'e' corrente puoi provare ad aprire la cassaforte.\n"
                                    + "Riesci a fare luce su due porte a nord e sud, ma avvicinandoti a\n"
                                    + "quella a sud capisci che e' chiusa dall'interno.\n"
                                    + "\nPer qualche motivo guardare troppo a lungo in questa stanza ti fa salire\n"
                                    + "una strana ansia.");
                            it.setExamine("Una cassaforte elettronica. Ora e' accesa, ma c'e' bisogno di una\n"
                                    + "combinazione. Un tastierino numerico si e' illuminato.\n"
                                    + "\t\t _____________________\n"
                                    + "\t\t|  _________________  |\n"
                                    + "\t\t| | PASS         0  | |\n"
                                    + "\t\t| |_________________| |\n"
                                    + "\t\t|     ___ ___ ___     |\n"
                                    + "\t\t|    | 7 | 8 | 9 |    |\n"
                                    + "\t\t|    |___|___|___|    |\n"
                                    + "\t\t|    | 4 | 5 | 6 |    |\n"
                                    + "\t\t|    |___|___|___|    |\n"
                                    + "\t\t|    | 1 | 2 | 3 |    |\n"
                                    + "\t\t|    |___|___|___|    |\n"
                                    + "\t\t|        | 0 |        |\n"
                                    + "\t\t|        |___|        |\n"
                                    + "\t\t|_____________________|\n");
                            it.setReachable(true);
                        }
                    }
                }
            } else if (getCurrentRoom().getId() == ID_ROOM_BATHROOM) {
                if (eventGotScrewdriver) {
                    for (GameItem it : getCurrentRoom().getItems()) {
                        if (it.getId() == ID_ITEM_GRATE && !eventBathroomVisitedAfterScrewdriver) {
                            eventBathroomVisitedAfterScrewdriver = true;
                            output.append("\nOra che hai un cacciavite puoi provare ad aprire la grata.");
                            it.setExamine("Una grata di ferro che chiude il condotto.\n"
                                    + "Puoi svitare le viti che la tengono ben stretta al muro.");
                            it.setOpenable(true);
                        }
                    }
                }

            }
        }
        output.append("\n");
        return output.toString();
    }

    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    private StringBuilder printNPCLooks(StringBuilder output) {
        if (getCurrentRoom().getNPCs().size() > 0) {
            for (GameNPC npc : getCurrentRoom().getNPCs()) {
                output.append("\n" + npc.getLook());
            }
        }
        return output;
    }

    private StringBuilder checkMovement(StringBuilder output, boolean noroom, boolean move, Room roomLocked) {
        if (noroom) {
            output.append("C'e' un muro di fronte a te. Non puoi procedere oltre.");
        } else if (move) {
            output.append("////   " + getCurrentRoom().getName() + "   ////");
            if (getCurrentRoom().isVisited()) {
                output.append("\n" + getCurrentRoom().getDescription());
                output = printNPCLooks(output);
            } else {
                output.append("\n" + getCurrentRoom().getFirstDescription());
                getCurrentRoom().setVisited(true);
            }

        } else if (roomLocked != null) {
            output.append(roomLocked.getLockedDescription());
        }

        return output;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    //nextMove commands:
    private StringBuilder commandNorth(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getNorth() != null) {
            if (!getCurrentRoom().getNorth().isLocked()) {
                setCurrentRoom(getCurrentRoom().getNorth());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getNorth();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }

    private StringBuilder commandSouth(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getSouth() != null) {
            if (!getCurrentRoom().getSouth().isLocked()) {
                setCurrentRoom(getCurrentRoom().getSouth());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getSouth();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }

    private StringBuilder commandEast(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getEast() != null) {
            if (!getCurrentRoom().getEast().isLocked()) {
                setCurrentRoom(getCurrentRoom().getEast());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getEast();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }

    private StringBuilder commandWest(ParserOutput p, StringBuilder output) {
        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getWest() != null) {
            if (!getCurrentRoom().getWest().isLocked()) {
                setCurrentRoom(getCurrentRoom().getWest());
                move = true;
            } else {
                roomLocked = getCurrentRoom().getWest();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }

    private StringBuilder commandCrawl(ParserOutput p, StringBuilder output) {

        boolean noroom = false;
        boolean move = false;
        Room roomLocked = null;

        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Forse volevi dire: " + p.getCommand().getName());
        } else if (getCurrentRoom().getCrawl() != null) {
            if (!getCurrentRoom().getCrawl().isLocked()) {
                setCurrentRoom(getCurrentRoom().getCrawl());
                move = true;
            } else if (getCurrentRoom().getCrawl().getId() == ID_ROOM_LAB && getCurrentRoom().getId() == ID_ROOM_BATHROOM) {
                if (!eventGrateOpened) {
                    output.append("Non puoi entrare nel condotto senza togliere la grata che lo blocca.");
                } else {
                    setCurrentRoom(getCurrentRoom().getCrawl());
                    move = true;
                    getRooms().get(ID_ROOM_LAB).setLocked(false);
                }
            } else {
                roomLocked = getCurrentRoom().getCrawl();
            }
        } else {
            noroom = true;
        }

        output = checkMovement(output, noroom, move, roomLocked);
        return output;
    }

    private StringBuilder commandInventory(ParserOutput p, StringBuilder output) {
        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Scrivi solo \"inventario\" se vuoi visualizzarlo.");
        } else if (getInventory().size() > 0) {
            output.append("\nOggetti equipaggiati:\n");
        for (GameItem it : getInventory()) {
            if (it.isEquipped()) {
                output.append("- " + it.getName());
                if (it.isOpen()) {
                    output.append(" (aperto)");
                }
                output.append("\n");
            }
        }

        output.append("-----------------------------------------\n");

        output.append("Oggetti nell'inventario:\n");
        for (GameItem it : getInventory()) {
            if (it.getContained() == -1 && !it.isEquipped() && !it.isNote()) {
                output.append("- " + it.getName());
                if (it.isOpen()) {
                    output.append(" (aperto)");
                }
                output.append("\n");
            }
        }

        output.append("-----------------------------------------\n");

        output.append("Note raccolte:\n");
        for (GameItem it : getInventory()) {
            if (it.getContained() == -1 && !it.isEquipped() && it.isNote()) {
                output.append("- " + it.getName());
                output.append("\n");
            }
        }
        } else {
            output.append("Il tuo inventario e' vuoto.");
        }

        return output;
    }
    
    private StringBuilder commandHelp(ParserOutput p, StringBuilder output) {
        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Scrivi solo \"help\" se vuoi una lista di comandi.");
        } else {
            output.append(
                  "\t\t--- Come giocare ad Hive Mind ---\n"
                + "\n"
                + "E' possibile usare questi comandi testuali anche senza premere i relativi\n"
                + "pulsanti:\n"
                + "\n"
                + ">> nord - Spostati in direzione nord\n"
                + ">> est - Spostati in direzione est\n"
                + ">> ovest - Spostati in direzione ovest\n"
                + ">> sud - Spostati in direzione sud\n"
                + ">> osserva - permette di guardarti intorno ed esaminare\n"
                + "             l'ambiente circostante.\n"
                + ">> carica - carica un salvataggio\n"
                + ">> salva - salva la partita corrente\n"
                + ">> help - stampa una lista dei comandi\n"
                + "\n"
                + "Altri comandi:\n"
                + "\n"
                + ">> esamina [qualcosa] - esamina qualcosa presente nella stanza, o prendi\n"
                + "                        oggetti da un cadavere\n"
                + ">> inventario - visualizza l'inventario\n"
                + ">> equipaggia [oggetto] - equipaggia un oggetto dell'inventario \n"
                + "                          (massimo 2 alla volta)\n"
                + ">> togli [oggetto] - disequipaggia un oggetto\n"
                + ">> apri [oggetto contenitore] - apri un oggetto specifico\n"
                + ">> chiudi [oggetto contenitore] - chiudi un oggetto specifico\n"
                + ">> lascia [oggetto] - lascia un oggetto in una stanza\n"
                + ">> metti [oggetto] in [oggetto contenitore] - metti un oggetto in un\n"
                + "                                              contenitore valido\n"
                + ">> prendi [oggetto] - prendi un oggetto a terra nella stanza o in un\n"
                + "                      contenitore\n"
                + ">> parla a [personaggio] - parla ad un personaggio nella stanza\n"
                + ">> dai [oggetto] a [persona] - dai un oggetto nel tuo inventario ad\n"
                + "                               un personaggio\n"
                + ">> usa [oggetto] -  usa oggetti esterni al tuo inventario\n"
                + "\n"
                + "Altri comandi piu' specifici dovranno essere trovati dal giocatore."); 
        }

        return output;
    }

    private StringBuilder commandLook(ParserOutput p, StringBuilder output) {
        if (p.getItem1() != null || p.getInvItem1() != null || p.getCharacter1() != null || p.hasExtraWords()) {
            output.append("Scrivi solo \"osserva\" se vuoi guardarti intorno.\n"
                        + "Altrimenti usa \"guarda [oggetto]\" per esaminarlo.");
        } else {
            output.append(getCurrentRoom().getLook());
            output = printNPCLooks(output);

            boolean first = true;
            for (GameItem it : getCurrentRoom().getItems()) {
                if (it.isDropped()) {
                    if (first) {
                        output.append("\nInoltre, hai lasciato in questa stanza: ");
                        first = false;
                    }
                    output.append("\n- " + it.getName());
                }
            }
        }

        return output;
    }

    private StringBuilder commandExamine(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi esaminare solo una cosa per volta.");

        } else if (p.getItem1() != null && !(p.getItem1() instanceof GameItemContainer)) {
            if (p.getItem1().isReachable()) {
                output.append(p.getItem1().getExamine());
            } else {
                output.append(p.getItem1().getUnreachableDescription());
            }

        } else if (p.getItem1() != null && p.getItem1() instanceof GameItemContainer) {
            output.append(p.getItem1().getExamine());

            if (((GameItemContainer) p.getItem1()).isOpen()) {
                boolean first = true;
                for (GameItem it : getCurrentRoom().getItems()) {
                    if (it.getContained() == p.getItem1().getId()) {
                        if (first) {
                            output.append("\n" + p.getItem1().getName() + " contiene:");
                            first = false;
                        }
                        output.append("\n- " + it.getName());
                    }
                }

                if (first) {
                    output.append(p.getItem1().getName() + " e' vuoto.");
                }
            }

        } else if (p.getInvItem1() != null && !(p.getInvItem1() instanceof GameItemContainer)) {
            output.append(p.getInvItem1().getExamine());

        } else if (p.getInvItem1() != null && p.getInvItem1() instanceof GameItemContainer) {
            output.append(p.getInvItem1().getExamine());

            if (((GameItemContainer) p.getInvItem1()).isOpen()) {
                boolean first = true;
                for (GameItem it : getInventory()) {
                    if (it.getContained() == p.getInvItem1().getId()) {
                        if (first) {
                            output.append("\n" + p.getInvItem1().getName() + " contiene:");
                            first = false;
                        }
                        output.append("\n- " + it.getName());
                    }
                }

                if (first) {
                    output.append(p.getInvItem1().getName() + " e' vuoto.");
                }
            }

        } else if (p.getCharacter1() != null) {
            output.append(p.getCharacter1().getExamine());

            List<GameItem> invCharacter = p.getCharacter1().getInventory();
            if (!invCharacter.isEmpty()) {
                Iterator<GameItem> itInvCharacter = invCharacter.iterator();
                while (itInvCharacter.hasNext()) {
                    GameItem nextItemCharacter = itInvCharacter.next();
                    getInventory().add(nextItemCharacter);
                    output.append("\nHai preso " + nextItemCharacter.getDescription() + " dal " + p.getCharacter1().getName() + ".");
                    itInvCharacter.remove();
                    if (nextItemCharacter.getId() == ID_ITEM_FLASHLIGHT) {
                        eventGotFlashlight = true;
                        if (eventGotFlashlight) {
                            getRooms().get(ID_ROOM_EASTWING).setLook("Noti una cassaforte elettronica, ma senza corrente non hai nessun modo di\n"
                                    + "aprirla. Riesci a fare luce su due porte a nord e sud, ma avvicinandoti a\n"
                                    + "quella a sud capisci che e' chiusa dall'interno.\n"
                                    + "\nPer qualche motivo guardare troppo a lungo in questa stanza ti fa salire\n"
                                    + "una strana ansia.");
                            getRooms().get(ID_ROOM_EASTWING).setDescription("La luce non ha migliorato la situazione, l'atmosfera spettrale di\n"
                                    + "quest'ala del laboratorio si abbatte su di te in tutta la sua\n"
                                    + "minacciosita'.");
                            getRooms().get(ID_ROOM_EASTWING).setFirstDescription("Per fortuna hai una torcia! Se non l'avessi presa qui ci sarebbe stato\n"
                                    + "un buio pesto. Nonostante la fioca luce data dalla torcia, questo posto\n"
                                    + "ti mette comunque i brividi.");
                            getRooms().get(ID_ROOM_GENERATOR).setLocked(false);
                            getRooms().get(ID_ROOM_LAB).setLockedDescription("La porta e' chiusa dall'interno. Non puoi aprirla da qui.");
                            output.append("\nFinalmente puoi fare un po' di luce nell'ala est...");
                        }
                    } else if (nextItemCharacter.getId() == ID_ITEM_IDCARD) {
                        eventGotIDCard = true;
                        if (eventGotIDCard) {
                            getRooms().get(ID_ROOM_CONTROL).setLocked(false);
                            output.append("\nForse questo ID puo' essere usato per autenticarsi...");
                        }
                    } else if (nextItemCharacter.getId() == ID_ITEM_CROWBAR) {
                        eventGotCrowbar = true;
                    }

                }
            }

        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            output.append("Specifica un oggetto da esaminare, o scrivi solo \"osserva\" per guardarti\n"
                    + "intorno.");
        }
        return output;
    }

    private StringBuilder commandPickUp(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi raccogliere un solo oggetto alla volta!");
        } else if (p.getInvItem1() != null && p.getInvItem1().getContained() == -1) {
            output.append("Possiedi gia' questo oggetto.");
        } else if (p.getInvItem1() != null && p.getInvItem1().getContained() != -1) {
            p.getInvItem1().setContained(-1);
            output.append("Hai tirato fuori " + p.getInvItem1().getName() + " nell'inventario.");
        } else if (p.getCharacter1() != null) {
            if (p.getCharacter1().containsDialogue("pickUp")) {
                output.append(p.getCharacter1().getDialogue("pickUp"));
            } else {
                output.append("Non puoi certo raccogliere un personaggio...");
            }
        } else if (p.getItem1() != null) {
            if (p.getItem1().isReachable()) {
                if (p.getItem1().isPickupable()) {

                    if (!(p.getItem1().getId() == ID_ITEM_SCREWDRIVER)) {
                        p.getItem1().setContained(-1);
                        getInventory().add(p.getItem1());
                        getCurrentRoom().getItems().remove(p.getItem1());
                        p.getItem1().setDropped(false);
                        output.append("Hai raccolto: " + p.getItem1().getDescription());

                        if (p.getItem1() instanceof GameItemContainer) {
                            List<GameItem> l = getCurrentRoom().getItems();
                            if (!l.isEmpty()) {
                                Iterator<GameItem> it = l.iterator();
                                while (it.hasNext()) {
                                    GameItem next = it.next();
                                    if (p.getItem1().getId() != next.getId()) {
                                        if (p.getItem1().getId() == next.getContained()) {
                                            getInventory().add(next);
                                            it.remove();
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        p.getItem1().setContained(-1);
                        getInventory().add(p.getItem1());
                        getCurrentRoom().getItems().remove(p.getItem1());
                        p.getItem1().setDropped(false);
                        output.append("Hai raccolto: " + p.getItem1().getDescription());
                        eventGotScrewdriver = true;
                        output.append("\nQuesto cacciavite ti sara' sicuramente utile per aprire qualcosa.");
                    }
                } else {
                    output.append("Non puoi raccogliere questo oggetto.");
                }
            } else {
                output.append(p.getItem1().getUnreachableDescription());
            }
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            output.append("Specifica cosa vuoi raccogliere.");
        }

        return output;
    }

    private StringBuilder commandAttack(ParserOutput p, StringBuilder output) {
        if (p.getItem1() != null) {
            output.append("Non puoi sparare a un oggetto.");
        } else if (p.getInvItem1() != null) {
            output.append("Non puoi sparare ad un oggetto nel tuo inventario.");

        } else if (p.getCharacter1() != null) {
            if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
                output.append("Puoi attaccare solo un nemico!");
            } else if (p.getCharacter2() != null) {
                output.append("Puoi attaccare un solo nemico alla volta!");
            } else {
                boolean found = false;
                for (GameItem it : getInventory()) {
                    if (it.getId() == ID_ITEM_LASERGUN) {
                        found = true;
                        if (it.isEquipped()) {
                            if (p.getCharacter1().getId() == ID_CHARACTER_CLONE) {
                                if (p.getCharacter1().isAlive()) {
                                    output.append(p.getCharacter1().getDialogue("attack"));
                                    p.getCharacter1().setAlive(false);
                                    eventGoodEnding = false;
                                    output.append(endingCredits());
                                    setEnd(true);

                                } else {
                                    output.append("E' gia' morto.");
                                }
                            } else if (!p.getCharacter1().isAlive()) {
                                output.append("E' gia' morto.");
                            }
                        } else {
                            output.append("Equipaggia la pistola.");
                        }
                    }
                }
                if (!found) {
                    output.append("Non hai nulla con cui sparare.");
                }
            }

        }

        return output;
    }

    private StringBuilder commandOpen(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi aprire solo una cosa alla volta!");
        } else {
            if (p.getItem1() != null) {
                if (p.getItem1().isReachable()) {
                    if (p.getItem1().isOpenable()) {
                        if (!p.getItem1().isOpen()) {
                            if (p.getItem1() instanceof GameItemContainer) {
                                if ((p.getItem1().getId() == ID_ITEM_LOCKER || p.getItem1().getId() == ID_ITEM_FUSES_CONTAINER) && eventGotCrowbar) {

                                    p.getItem1().setOpen(true);
                                    output.append("Hai aperto: " + p.getItem1().getName());
                                    GameItemContainer c = (GameItemContainer) p.getItem1();
                                    if (!c.getList().isEmpty()) {
                                        output.append("\n" + c.getName() + " contiene:");
                                        Iterator<GameItem> it = c.getList().iterator();
                                        while (it.hasNext()) {
                                            GameItem next = it.next();
                                            getCurrentRoom().getItems().add(next);
                                            next.setDropped(true);
                                            output.append("\n- " + next.getName());
                                            it.remove();
                                        }
                                    }
                                } else if (p.getItem1().getId() == ID_ITEM_STRONGBOX && eventStrongboxUnlocked) {

                                    p.getItem1().setOpen(true);
                                    output.append("Hai aperto: " + p.getItem1().getName());
                                    GameItemContainer c = (GameItemContainer) p.getItem1();
                                    if (!c.getList().isEmpty()) {
                                        output.append("\n" + c.getName() + " contiene:");
                                        Iterator<GameItem> it = c.getList().iterator();
                                        while (it.hasNext()) {
                                            GameItem next = it.next();
                                            getCurrentRoom().getItems().add(next);
                                            next.setDropped(true);
                                            output.append("\n- " + next.getName());
                                            it.remove();
                                        }
                                    }
                                }
                            } else if (!(p.getItem1() instanceof GameItemContainer) && p.getItem1().getId() == ID_ITEM_GRATE
                                    && eventGotScrewdriver) {
                                p.getItem1().setOpen(true);
                                eventGrateOpened = true;
                                output.append("Hai tolto le viti che fissavano la grata al muro. Ora dovresti essere in\n"
                                        + "grado di passare nel condotto.");
                                p.getItem1().setExamine("Hai rimosso la grata, ora puoi strisciare nel condotto.");

                            } else {
                                output.append("Hai aperto: " + p.getItem1().getName());
                                p.getItem1().setOpen(true);
                            }
                        } else {
                            output.append("Questo oggetto e' gia' aperto!");
                        }
                    } else {
                        output.append("Questo oggetto non si puo' aprire!");
                    }
                } else {
                    output.append(p.getItem1().getUnreachableDescription());
                }

            } else if (p.getInvItem1() != null) {
                if (p.getInvItem1().isOpenable()) {
                    if (!p.getInvItem1().isOpen()) {
                        if (p.getInvItem1() instanceof GameItemContainer) {
                            p.getInvItem1().setOpen(true);
                            output.append("Hai aperto nel tuo inventario: " + p.getInvItem1().getName());
                            GameItemContainer c = (GameItemContainer) p.getInvItem1();
                            if (!c.getList().isEmpty()) {
                                output.append("\n" + c.getName() + " contiene:");
                                Iterator<GameItem> it = c.getList().iterator();
                                while (it.hasNext()) {
                                    GameItem next = it.next();
                                    getInventory().add(next);
                                    output.append("\n- " + next.getName());
                                    it.remove();
                                }
                            }
                        } else {
                            output.append("Hai aperto nel tuo inventario: " + p.getInvItem1().getName());
                            p.getInvItem1().setOpen(true);
                        }
                    } else {
                        output.append("Questo oggetto e' gia' aperto!");
                    }
                } else {
                    output.append("Questo oggetto non si puo' aprire!");
                }

            } else if (p.getCharacter1() != null) {
                if (p.getCharacter1().containsDialogue("open")) {
                    output.append(p.getCharacter1().getDialogue("open"));
                } else {
                    output.append("Non puoi aprire un personaggio...");
                }
            } else if (p.hasExtraWords()) {
                output.append("Non vedo niente del genere qua intorno...");
            } else {
                output.append("Specifica cosa vuoi aprire.");
            }
        }

        return output;
    }

    private StringBuilder commandClose(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi chiudere solo una cosa alla volta!");
        } else {
            if (p.getItem1() != null) {
                if (p.getItem1().isReachable()) {
                    if (p.getItem1().isOpenable()) {
                        if (p.getItem1().isOpen()) {
                            if (p.getItem1() instanceof GameItemContainer) {
                                List<GameItem> l = getCurrentRoom().getItems();
                                if (!l.isEmpty()) {
                                    Iterator<GameItem> it = l.iterator();
                                    while (it.hasNext()) {
                                        GameItem next = it.next();
                                        if (p.getItem1().getId() != next.getId()) {
                                            if (p.getItem1().getId() == next.getContained()) {
                                                ((GameItemContainer) p.getItem1()).add(next);
                                                it.remove();
                                            }
                                        }
                                    }
                                    p.getItem1().setOpen(false);
                                }
                            } else {
                                p.getItem1().setOpen(false);
                            }
                            output.append("Hai chiuso: " + p.getItem1().getName());
                        } else {
                            output.append("Questo oggetto e' gia' chiuso!");
                        }
                    } else {
                        output.append("Questo oggetto non si puo' chiudere!");
                    }
                } else {
                    output.append(p.getItem1().getUnreachableDescription());
                }

            } else if (p.getInvItem1() != null) {
                if (p.getInvItem1().isOpenable()) {
                    if (p.getInvItem1().isOpen()) {
                        if (p.getInvItem1() instanceof GameItemContainer) {
                            List<GameItem> l = getInventory();
                            if (!l.isEmpty()) {
                                Iterator<GameItem> it = l.iterator();
                                while (it.hasNext()) {
                                    GameItem next = it.next();
                                    if (p.getInvItem1().getId() != next.getId()) {
                                        if (p.getInvItem1().getId() == next.getContained()) {
                                            ((GameItemContainer) p.getInvItem1()).add(next);
                                            it.remove();
                                        }
                                    }
                                }
                                p.getInvItem1().setOpen(false);
                            }
                        } else {
                            p.getInvItem1().setOpen(false);
                        }
                        output.append("Hai chiuso nel tuo inventario: " + p.getInvItem1().getName());
                    } else {
                        output.append("Questo oggetto e' gia' chiuso!");
                    }
                } else {
                    output.append("Questo oggetto non si puo' chiudere!");
                }

            } else if (p.getCharacter1() != null) {
                output.append("Non puoi chiudere un personaggio...");

            } else if (p.hasExtraWords()) {
                output.append("Non vedo niente del genere qua intorno...");

            } else {
                output.append("Specifica cosa vuoi chiudere.");
            }
        }

        return output;
    }

    private StringBuilder commandDrop(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi lasciare un solo oggetto alla volta.");
        } else if (p.getItem1() != null) {
            output.append("Non puoi lasciare un oggetto che non hai nell'inventario.");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi lasciare un personaggio!");
        } else if (p.getInvItem1() != null) {
            if (!p.getInvItem1().isEquipped()) {
                getCurrentRoom().getItems().add(p.getInvItem1());
                getInventory().remove(p.getInvItem1());
                p.getInvItem1().setContained(-1);
                p.getInvItem1().setDropped(true);
                output.append("Hai lasciato " + p.getInvItem1().getName() + " in: " + getCurrentRoom().getName().toLowerCase());
            } else {
                output.append("Disequipaggia l'oggetto prima di lasciarlo!");
            }
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qua intorno...");
        } else {
            output.append("Specifica cosa vuoi lasciare.");
        }

        return output;
    }

    private StringBuilder commandPut(ParserOutput p, StringBuilder output) {
        if (p.getItem1() != null) {
            output.append("Prendi questo oggetto prima di metterlo da qualche parte...");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi mettere un personaggio in un contenitore!");
        } else if (p.getCharacter2() != null) {
            output.append("Non puoi mettere un oggetto in un personaggio!");
        } else if (p.getInvItem1() != null) {
            if (!p.getInvItem1().isEquipped()) {
                if (p.getItem2() != null || p.getInvItem2() != null) {
                    if (p.getItem2() instanceof GameItemContainer || p.getInvItem2() instanceof GameItemContainer) {
                        if (p.getItem2() != null && p.getItem2().isOpen()) {
                            if (p.getItem2().isReachable()) {
                                if (!(p.getItem2().getId() == ID_ITEM_GENERATOR)) {
                                    getCurrentRoom().getItems().add(p.getInvItem1());
                                    getInventory().remove(p.getInvItem1());
                                    output.append("Hai inserito " + p.getInvItem1().getName() + " in " + p.getItem2().getName());

                                } else if (p.getInvItem1().getId() == ID_ITEM_FUSES && p.getItem2().getId() == ID_ITEM_GENERATOR
                                        && !eventGeneratorActivated) {
                                    eventGeneratorActivated = true;
                                    ((GameItemContainer) p.getItem2()).add(p.getInvItem1());
                                    if (eventGeneratorActivated) {
                                        output.append("\nE luce fu. Hai ridato corrente all'ala est!");
                                        p.getItem2().setExamine("Il generatore ha ripreso a funzionare. Bel lavoro.\n");
                                        getCurrentRoom().setLook("Vedi un grosso oggetto nella penombra simile a un generatore elettrico,\n"
                                                               + "che pero' hai gia' attivato. Non hai altro da fare qui.\n");
                                    }
                                } else {
                                    output.append("Perche' vorresti mettere questo oggetto qui dentro?");
                                }
                            } else {
                                output.append(p.getItem2().getUnreachableDescription());
                            }
                        } else if (p.getInvItem2() != null && p.getInvItem2().isOpen()) {
                            if (p.getInvItem1().getId() != p.getInvItem2().getId()) {

                                ((GameItemContainer) p.getInvItem2()).add(p.getInvItem1());
                                output.append("Hai inserito " + p.getInvItem1().getName() + " in " + p.getInvItem2().getName() + " nell'inventario");

                            } else {
                                output.append("Non puoi mettere un oggetto dentro se' stesso!");
                            }

                        } else if (p.getItem2() != null && !p.getItem2().isOpen()) {
                            if (p.getItem2().isReachable()) {
                                output.append("Questo contenitore e' chiuso!");
                            } else {
                                output.append(p.getItem2().getUnreachableDescription());
                            }
                        } else if (p.getInvItem2() != null && !p.getInvItem2().isOpen()) {
                            output.append("Questo contenitore e' chiuso!");
                        }
                    } else {
                        output.append("Come pensi di inserire qui quest'oggetto?");
                    }
                } else {
                    output.append("Specifica un contenitore in cui inserire l'oggetto!");
                }
            } else {
                output.append("Disequipaggia l'oggetto prima di inserirlo.");
            }
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qui...");
        } else {
            output.append("Specifica un oggetto dall'inventario.");
        }

        return output;
    }

    private StringBuilder commandPush(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi premere un solo oggetto alla volta!");

        } else if (p.getItem1() != null && p.getItem1().isPushable()) {
            if (p.getItem1().isReachable()) {
                output.append("Hai premuto: " + p.getItem1().getName());
                if (p.getItem1().getId() == ID_ITEM_GATE_OPENER) {
                    if (!p.getItem1().isPush()) {
                        p.getItem1().setPush(true);
                        getRooms().get(ID_ROOM_CLONEROOM).setLocked(false);
                        output.append("\nSei riuscito ad aprire la stanza bloccata nell'atrio. Chissa' cosa ti\n"
                                + "aspetta...");
                    } else {
                        p.getItem1().setPush(false);
                        getRooms().get(ID_ROOM_CLONEROOM).setLocked(true);
                        output.append("\nForse preso dall'ansia, hai nuovamente chiuso la porta. Forse, pero',\n"
                                + "dovresti finire il tuo lavoro...?");
                    }
                } else {
                    output.append(p.getItem1().getUnreachableDescription());
                }
            }
        } else if (p.getInvItem1() != null && p.getInvItem1().isPushable()) {
            p.getInvItem1().setPush(true);
            output.append("Hai premuto: " + p.getInvItem1().getName());
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi premere un personaggio!");
        } else if (p.hasExtraWords()) {
            output.append("Non vedo niente del genere qui...");
        } else {
            output.append("Specifica cosa vuoi premere.");
        }

        return output;
    }

    private StringBuilder commandEquip(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi specificare solo un oggetto da equipaggiare alla volta!");
        } else if (p.getItem1() != null) {
            output.append("Prendi questo oggetto prima di equipaggiarlo...");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi equipaggiare un personaggio!");
        } else if (p.getInvItem1() == null && !p.hasExtraWords()) {
            output.append("Specifica quale oggetto equipaggiare dal tuo inventario.");
        } else if (p.getInvItem1() == null && p.hasExtraWords()) {
            output.append("Non hai niente del genere da equipaggiare...");
        } else if (!p.getInvItem1().isEquippable()) {
            output.append("Questo oggetto non e' equipaggiabile!");
        } else if (p.getInvItem1().getContained() != -1) {
            output.append("Tira fuori l'oggetto dal contenitore prima di equipaggiarlo!");
        } else {
            int equippedItems = 0;
            for (GameItem it : getInventory()) {
                if (it.isEquipped()) {
                    equippedItems++;
                }
            }

            if (equippedItems < MAX_EQUIPPABLE) {
                p.getInvItem1().setEquipped(true);
                output.append("Hai equipaggiato: " + p.getInvItem1().getName());
                switch (p.getInvItem1().getId()) {
                    case ID_ITEM_CROWBAR:
                        output.append("\nOra che hai equipaggiato la spranga, la prossima volta potrai aprire gli\n"
                                + "oggetti che hanno bisogno di essere forzati.");
                        for (GameItem it : getRooms().get(ID_ROOM_WESTWING).getItems()) {
                            if (it.getId() == ID_ITEM_LOCKER) {
                                it.setOpenable(true);
                            }
                        }
                        for (GameItem it : getRooms().get(ID_ROOM_LIFT).getItems()) {
                            if (it.getId() == ID_ITEM_FUSES_CONTAINER) {
                                it.setOpenable(true);
                            }
                        }
                }
            } else {
                output.append("Hai gia' equipaggiato il numero massimo di oggetti!\nTogliti qualcosa prima di procedere.");
            }
        }

        return output;
    }

    private StringBuilder commandUnequip(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi disequipaggiare un solo oggetto alla volta!");
        } else if (p.getItem1() != null) {
            output.append("Non hai equipaggiato questo oggetto!");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi disequipaggiare un personaggio!");
        } else if (p.getInvItem1() == null && !p.hasExtraWords()) {
            output.append("Specifica quale oggetto disequipaggiare.");
        } else if (p.getInvItem1() == null && p.hasExtraWords()) {
            output.append("Non hai equipaggiato nulla del genere...");
        } else if (!p.getInvItem1().isEquippable()) {
            output.append("Questo oggetto non e' equipaggiabile!");
        } else if (!p.getInvItem1().isEquipped()) {
            output.append("Non hai equipaggiato questo oggetto!");
        } else {
            p.getInvItem1().setEquipped(false);
            output.append("Hai disequipaggiato: " + p.getInvItem1().getName());
            switch (p.getInvItem1().getId()) {
                case ID_ITEM_CROWBAR:
                    for (GameItem it : getRooms().get(ID_ROOM_WESTWING).getItems()) {
                        if (it.getId() == ID_ITEM_LOCKER) {
                            it.setOpenable(false);
                        }
                    }
                    for (GameItem it : getRooms().get(ID_ROOM_LIFT).getItems()) {
                        if (it.getId() == ID_ITEM_FUSES_CONTAINER) {
                            it.setOpenable(false);
                        }
                    }
            }
        }

        return output;
    }

    private StringBuilder commandTalkTo(ParserOutput p, StringBuilder output) {
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi parlare con un solo personaggio alla volta!");
        } else if (p.getItem1() != null && talkedToItem == 0) {
            output.append("La tensione è certamente alta... se ti fa star meglio parlare con un\n"
                    + "oggetto, fai pure.");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem == 0) {
            output.append("La tensione è certamente alta... se ti fa star meglio parlare col tuo\n"
                    + "inventario, fai pure.");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem == 1) {
            output.append("Hai... di nuovo parlato con un oggetto?");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem == 1) {
            output.append("Hai... di nuovo parlato con un oggetto?");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem == 2) {
            output.append("D'accordo, ti piace parlare agli oggetti.\n"
                    + "Forse dovresti rivedere le tue priorità nella vita.");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem == 2) {
            output.append("D'accordo, ti piace parlare agli oggetti.\n"
                    + "Forse dovresti rivedere le tue priorità nella vita.");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem == 3) {
            output.append("Cominci a disquisire con l'oggetto di argomenti filosofici e delle\n"
                    + "domande fondamentali sul senso della vita, su Dio e sull'universo.\n"
                    + "Ovviamente non risponde.");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem == 3) {
            output.append("Cominci a disquisire con l'oggetto di argomenti filosofici e delle\n"
                    + "domande fondamentali sul senso della vita, su Dio e sull'universo.\n"
                    + "Ovviamente non risponde.");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem == 4) {
            output.append("Provando a parlare tante volte con l'oggetto, esso comincia a sussurrarti:\n"
                    + "<<Devi lasciarmi andare. L'incidente e' stato 10 anni fa.\n"
                    + "Non era colpa tua!>>");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem == 4) {
            output.append("Provando a parlare tante volte con l'oggetto, esso comincia a sussurrarti:\n"
                    + "<<Devi lasciarmi andare. L'incidente e' stato 10 anni fa.\n"
                    + "Non era colpa tua!>>");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem == 5) {
            output.append("Nella tua testa senti il rimbombo di flauti blasfemi e inintelligibili\n"
                    + "melodie celestiali. Sei assalito dai sussurri gutturali di un'indecifrabile\n"
                    + "lingua aliena: «Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn!»");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem == 5) {
            output.append("Nella tua testa senti il rimbombo di flauti blasfemi e inintelligibili\n"
                    + "melodie celestiali. Sei assalito dai sussurri gutturali di un'indecifrabile\n"
                    + "lingua aliena: «Ph'nglui mglw'nafh Cthulhu R'lyeh wgah'nagl fhtagn!»");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem > 5 && talkedToItem < 10 ) {
            output.append("Non piu' in vita.");
            talkedToItem++;
        } else if (p.getInvItem1() != null && talkedToItem > 5 && talkedToItem < 10) {
            output.append("Non piu' in vita.");
            talkedToItem++;
        } else if (p.getItem1() != null && talkedToItem >= 10) {
            output.append("Okay, forse e' l'ora di continuare a giocare. Basta.");
        } else if (p.getInvItem1() != null && talkedToItem >= 10) {
            output.append("Okay, forse e' l'ora di continuare a giocare. Basta.");
        } else if (p.getCharacter1() != null) {
            if (p.getCharacter1().isFirstDialogue()) {
                p.getCharacter1().setFirstDialogueFlag(false);
                output.append(p.getCharacter1().getFirstDialogue());
            } else {
                output.append(p.getCharacter1().getDialogue("idle"));
            }
        } else if (p.hasExtraWords()) {
            output.append("Non ho trovato nessuno del genere qui...\nsara' un tuo amico immaginario.");
        } else {
            output.append("Specifica con chi vuoi parlare!");
        }

        return output;
    }

    private StringBuilder commandGive(ParserOutput p, StringBuilder output) {
        if (p.getInvItem1() != null) {
            if (p.getCharacter2() != null) {
                if (!p.getInvItem1().isEquipped()) {
                    switch (p.getCharacter2().getId()) {
                        case ID_CHARACTER_CLONE:
                            if (p.getInvItem1().getId() == ID_ITEM_FIRST_AID_KIT) {
                                p.getCharacter2().addInventory(p.getInvItem1());
                                getInventory().remove(p.getInvItem1());
                                output.append(p.getCharacter2().getDialogue("giveCorrect"));
                                eventGoodEnding = true;
                                output.append(endingCredits());
                                setEnd(true);
                            } else {
                                output.append(p.getCharacter2().getDialogue("giveWrong"));
                            }
                            break;
                        default:
                            output.append(p.getCharacter2().getDialogue("giveWrong"));
                            break;
                    }
                } else {
                    output.append("Disequipaggia l'oggetto prima di darlo a qualcuno!");
                }
            } else if (p.getItem2() != null) {
                output.append("Non puoi dare un oggetto a un oggetto!");
            } else if (p.getInvItem2() != null) {
                output.append("Non puoi dare un oggetto a un oggetto del tuo inventario!");
            } else if (p.hasExtraWords()) {
                output.append("Non ho trovato un personaggio a cui dare l'oggetto!");
            } else {
                output.append("Specifica un personaggio a cui dare questo oggetto.");
            }
        } else if (p.getItem1() != null) {
            output.append("Non possiedi questo oggetto!");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi dare un personaggio!");
        } else if (p.hasExtraWords()) {
            output.append("Non hai niente del genere da dare...");
        } else {
            output.append("Specifica un oggetto da dare a un personaggio.");
        }

        return output;
    }

    private StringBuilder commandInsertPassStrongbox(ParserOutput p, StringBuilder output) {

        if (!(getCurrentRoom().getId() == ID_ROOM_EASTWING && eventGeneratorActivated)) {
            output.append("Inserisci un comando valido come prima parola!");
        } else if (getCurrentRoom().getId() == ID_ROOM_EASTWING && !eventStrongboxUnlocked) {
            eventStrongboxUnlocked = true;
            for (GameItem it : getCurrentRoom().getItems()) {
                if (it.getId() == ID_ITEM_STRONGBOX) {
                    it.setOpenable(true);
                    it.setExamine("Una cassaforte elettronica. Ora puoi aprirla.");
                }
            }

            output.append("La combinazione e' corretta! Hai sbloccato la cassaforte.");
        }
        return output;
    }

    private StringBuilder commandInsertPassArchive(ParserOutput p, StringBuilder output) {
        if (!(getCurrentRoom().getId() == ID_ROOM_CONTROL)) {
            output.append("Inserisci un comando valido come prima parola!");
        } else if ((getCurrentRoom().getId() == ID_ROOM_CONTROL) && !eventArchiveUnlocked) {
            eventArchiveUnlocked = true;
            getRooms().get(ID_ROOM_ARCHIVE).setLocked(false);
            output.append("La combinazione e' corretta! Hai sbloccato la porta...");
        }
        return output;
    }
    
    private StringBuilder commandInsertHex(ParserOutput p, StringBuilder output) {
        if (!(getCurrentRoom().getId() == ID_ROOM_ARCHIVE)){
            output.append("Inserisci un comando valido come prima parola!");
        } else if ((getCurrentRoom().getId() == ID_ROOM_ARCHIVE) && !eventHexSolved){
            eventHexSolved = true;
            output.append("Hai indovinato il valore corretto!\n"
                    + "Alla fine 42 e' davvero la risposta alla domanda fondamentale...\n"
                    + "\nProva a riutilizzare il terminale adesso!");
        }
        return output;
    }

    private StringBuilder commandUse(ParserOutput p, StringBuilder output) { 
        if (p.getItem2() != null || p.getInvItem2() != null || p.getCharacter2() != null) {
            output.append("Puoi usare un solo oggetto alla volta!");
        } else if (p.getItem1() != null && p.getItem1().getId() == ID_ITEM_LORE_TERMINAL) {
            if (!eventHexSolved) {
                output.append("Il terminale ti presenta una serie di valori esadecimali.\nCercane uno che si ripete 5 volte!\n\n"
                        + "\t\t\tE0 69 1E 2D 78 A5 B4 B5\n"
                        + "\t\t\tC3 6E 82 90 B4 F9 42 FF\n" 
                        + "\t\t\tD8 D9 DB A1 A3 56 77 6E\n" 
                        + "\t\t\t48 42 BD 4D 42 DB EF A9\n" 
                        + "\t\t\tA1 A3 10 F5 B5 82 E0 C6\n" 
                        + "\t\t\t48 E0 69 FF D8 9C 56 77\n" 
                        + "\t\t\tFF 42 82 90 2D 32 E0 90\n" 
                        + "\t\t\t80 B4 C7 32 AA 2D 42 2D");
            } else {
                output.append("\t\t\t>>DATI CONFIDENZIALI<<\n"
                            + "\n"
                            + "Qui il prof. Sokolov. Spero che qualcuno legga questo\n"
                            + "messaggio che il mondo intero dovrebbe sapere.\n"
                            + "\n"
                            + "Nel 26esimo secolo, l'umanita' ha cominciato a clonare\n"
                            + "con successo gli esseri umani. Nel corso dei secoli, il\n"
                            + "processo di clonazione e' diventato tanto rapido dal\n"
                            + "sostituire inesorabilmente i cloni agli esseri umani.\n"
                            + "Clonando gli esseri umani, venivano aggiunti sempre piu'\n"
                            + "geni che miglioravano a dismisura le capacita' fisiche e\n"
                            + "mentali del clone, oltre che ad aumentare le loro difese\n"
                            + "immunitarie. Tuttavia, i cloni tendevano a comportarsi\n"
                            + "in modo sempre piu' violento e bellicoso. Per cercare di\n"
                            + "ovviare a questo problema, nacque il progetto \"Hive Mind\",\n"
                            + "che consisteva nel connettere il pensiero di un essere umano\n"
                            + "con i cloni da esso generati, creando una rete neurale per\n"
                            + "tenerli sotto controllo. Tuttavia, uccidere l'essere umano\n"
                            + "padre disattivava le funzioni cerebrali dei cloni figli,\n"
                            + "di fatto uccidendoli.\n"
                            + "I laboratori di clonazione non solo servivano a clonare,\n"
                            + "ma anche a preservare gli ultimi esseri umani, conservati\n"
                            + "criogeneticamente ed accuratamente selezionati in base\n"
                            + "alle loro caratteristiche fisiche.\n"
                            + "\n"
                            + "La clonazione di massa ha avuto effetti devastanti, portando\n"
                            + "in pochi anni la popolazione umana mondiale ad una minoranza\n"
                            + "del 5%. Una cellula terroristica suprematista, chiamata\n"
                            + "\"La Confraternita\", nacque poco dopo la diffusione nelle\n"
                            + "citta' dei primi cloni. La loro ideologia, che consisteva\n"
                            + "inizialmente nel guadagnare gli stessi diritti degli umani,\n"
                            + "sfocio' in una vera e propria rivolta che porto' a numerose\n"
                            + "guerre civili in tutto il mondo. La Confraternita non perse\n"
                            + "la sua identita' nel tempo, tuttavia, nel corso dei secoli,\n"
                            + "si fece strada tra le nuove generazioni la convinzione di\n"
                            + "essere la razza originale, e gli esseri umani, ormai in netto\n"
                            + "svantaggio numerico, vennero considerati una razza inferiore,\n"
                            + "in quanto piu' deboli e vulnerabili.\n"
                            + "\n"
                            + "Con l'ascesa al potere della Confraternita nei ranghi piu'\n"
                            + "alti della politica mondiale, i governi che si successero\n"
                            + "nel tempo attuarono un'opera di propaganda per convincere\n"
                            + "la popolazione che gli esseri umani rimasti fossero essi\n"
                            + "stessi i cloni e che la loro sola esistenza fosse un\n"
                            + "ostacolo per lo sviluppo dell'umanita', e con loro, chi\n"
                            + "li proteggeva. Cominciarono a perseguire gli umani\n"
                            + "rimanenti, in particolar modo creando un'unita' speciale\n"
                            + "dei servizi segreti al solo scopo di eliminare gli ultimi\n"
                            + "laboratori di clonazione clandestini sparsi per il mondo,\n"
                            + "nei quali gli umani venivano conservati.\n"
                            + "\n"
                            + "Sergej Romanov, il miglior soldato che la Russia abbia\n"
                            + "mai avuto. Un uomo possente, intelligente ma soprattutto\n"
                            + "un uomo dalla morale di ferro, che spesso entrava in\n"
                            + "conflitto con la fedelta' che riponeva per la Madrepatria.\n"
                            + "E' sempre stato ricordato come un uomo buono, gentile,\n"
                            + "uno stratega che riusciva ad ottenere cio' che voleva\n"
                            + "con la minore perdita di vite possibile. L'uomo perfetto.\n"
                            + "Si e' offerto volontario come il primo essere umano a venir\n"
                            + "clonato, nel 2558. E da allora, il suo corpo e' ancora\n"
                            + "conservato qui. Molti dei suoi figli sono stati mandati al\n"
                            + "fronte, altri si sono rifiutati di combattere, e hanno\n"
                            + "deciso di proseguire la loro vita lontani dalle armi.\n"
                            + "Per quasi 400 anni, Romanov ha generato un numero\n"
                            + "inimmaginabile di suoi simili. Ed e' stato scelto lui,\n"
                            + "appunto, per collaudare i primi prototipi dell'Hive Mind.\n"
                            + "\n"
                            + "Tuttavia, il progetto si e' dimostrato un'arma a doppio\n"
                            + "taglio, poiche' questo li ha resi estremamente manipolabili.\n"
                            + "Hive Mind nacque per fare del bene, per porre fine alle\n"
                            + "numerosissime guerre civili sparse nel mondo, tuttavia,\n"
                            + "il Governo Statunitense e' riuscito ad interferire con la\n"
                            + "rete neurale, e li ha trasformati in perfette macchine da\n"
                            + "guerra, che obbedivano ai soli ordini del presidente Davis.\n"
                            + "\n"
                            + "Ho lasciato un medikit in questa stanza. Il corpo di Sergej\n"
                            + "e' abbastanza resistente da sopravvivere ad una raffica di\n"
                            + "colpi, ma non e' immortale. In caso dovesse arrivare il\n"
                            + "plotone di esecuzione, ho lasciato degli indizi per condurre\n"
                            + "qui un uomo meritevole di sapere queste informazioni.\n"
                            + "So che qualcuno tornera' per uccidere Sergej. Ma sono anche\n"
                            + "abbastanza sicuro che quel qualcuno sara' un suo figlio.\n"
                            + "A maggior ragione, dovrebbe salvare suo padre.\n"
                            + "\n"
                            + "Vorrei che tutto il mondo smettesse di credere alle\n"
                            + "stronzate che vengono propinate fin da bambini.\n"
                            + "Ho sognato un mondo nuovo. Un mondo migliore.\n"
                            + "Siamo l'ultimo baluardo dell'umanita'.\n"
                            + "\n"
                            + "\n"
                            + "A chiunque stia leggendo questo messaggio:\n"
                            + "\n"
                            + "Confido in voi.");
            }
        } else if (p.getItem1() != null && p.getItem1().getId() != ID_ITEM_LORE_TERMINAL) {
            output.append("Non puoi usare questo oggetto!");
        } else if (p.getCharacter1() != null) {
            output.append("Non puoi usare un personaggio!");

        } else if (p.hasExtraWords()) {
            output.append("Oggetto non trovato!");
        } else {
            output.append("Specifica cosa vuoi usare.");
        }

        return output;
    }

    // ------------------------------------------------------------------------------------------------------------------------------------
    // ------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void save() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("HiveMindGame.dat"));
        out.writeObject(this);
        out.close();
    }

    @Override
    public GameDescription load() throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("HiveMindGame.dat"));
        HiveMindGame game = (HiveMindGame) in.readObject();
        in.close();
        return game;
    }

    private StringBuilder endingCredits() {
        StringBuilder output = new StringBuilder();

        output.append(
                  "            _  _ _ _  _ ____    _  _ _ _  _ ___              \n" +
                  "__ __ __    |__| | |  | |___    |\\/| | |\\ | |  \\    __ __ __ \n" +
                  "            |  | |  \\/  |___    |  | | | \\| |__/             \n" +
                  "                                                             "
                + "\n\n\t\tGrazie per aver giocato!"
                + "\n\t\t\t---THE END");

        if (!eventGoodEnding) {
            output.append("...?---");
        } else {
            output.append("---");
        }

        return output;

    }
}
