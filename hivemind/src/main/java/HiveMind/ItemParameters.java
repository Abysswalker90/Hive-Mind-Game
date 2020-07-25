package HiveMind;

import java.io.Serializable;

public class ItemParameters implements InterfaceItemParameters, Serializable {
    
    @Override
    public String note1Name() {
        return "nota1";
    }
    @Override
    public String[] note1Aliases() {
        return new String[]{"foglio1","foglietto1","appunti1"};
    }
    
    @Override
    public String note2Name() {
        return "nota2";
    }
    @Override
    public String[] note2Aliases() {
        return new String[]{"foglio2","foglietto2","appunti2"};
    }
    
    @Override
    public String note3Name() {
        return "nota3";
    }
    @Override
    public String[] note3Aliases() {
        return new String[]{"foglio3","foglietto3","appunti3"};
    }
    
    @Override
    public String note4Name() {
        return "nota4";
    }
    @Override
    public String[] note4Aliases() {
        return new String[]{"foglio4","foglietto4","appunti4"};
    }
    
    @Override
    public String note5Name() {
        return "nota5";
    }
    @Override
    public String[] note5Aliases() {
        return new String[]{"foglio5","foglietto5","appunti5"};
    }
    
    @Override
    public String note6Name() {
        return "nota6";
    }
    @Override
    public String[] note6Aliases() {
        return new String[]{"foglio6","foglietto6","appunti6"};
    }
    
    @Override
    public String fusesName() {
        return "fusibili";
    }
    @Override
    public String[] fusesAliases() {
        return new String[]{"cavi","fili","fusi"};
    }
    
    @Override
    public String idCardName() {
        return "tessera ID";
    }
    @Override
    public String[] idCardAliases() {
        return new String[]{"carta","autenticazione","documento","tessera"};
    }
    
    @Override
    public String crowbarName() {
        return "spranga";
    }
    @Override
    public String[] crowbarAliases() {
        return new String[]{"attrezzo","strumento","crowbar"};
    }
    
    @Override
    public String screwdriverName() {
        return "cacciavite";
    }
    @Override
    public String[] screwdriverAliases() {
        return new String[]{"giravite"};
    }
    
    @Override
    public String grateName() {
        return "grata";
    }
    @Override
    public String[] grateAliases() {
        return new String[]{"griglia"};
    }
    
    @Override
    public String flashlightName() {
        return "torcia";
    }
    @Override
    public String[] flashlightAliases() {
        return new String[]{"pila","luce"};
    }
    
    @Override
    public String laserGunName() {
        return "pistola";
    }
    @Override
    public String[] laserGunAliases() {
        return new String[]{"arma"};
    }
    
    @Override
    public String strongboxName() {
        return "cassaforte";
    }
    @Override
    public String[] strongboxAliases() {
        return new String[]{"contenitore","dispositivo"};
    }
    
    @Override
    public String lockerName() {
        return "armadietto";
    }
    
    @Override
    public String[] lockerAliases() {
        return new String[]{"armadio", "mobiletto", "armadietti"};
    }
    
    @Override
    public String gateOpenerName() {
        return "pulsante";
    }
    
    @Override
    public String[] gateOpenerAliases() {
        return new String[]{"bottone", "tasto", "bottoni", "pulsanti", "comandi", "comando", "computer", "terminale"};
    }
    
    @Override
    public String loreTerminalName() {
        return "terminale";
    }
    
    @Override
    public String[] loreTerminalAliases() {
        return new String[]{"computer", "schermo"};
    }
    
    @Override
    public String firstaidkitName() {
        return "medikit";
    }
    
    @Override
    public String[] firstaidkitAliases() {
        return new String[]{"soccorso","cura", "kit", "cure", "medicazioni", "medicine"};
    }
    
    @Override
    public String fusesContainerName() {
        return "pannello";
    }
    
    @Override
    public String[] fusesContainerAliases() {
        return new String[]{"comandi", "bottoni", "quadro", "scatola", "cassetta"};
    }
    
    @Override
    public String generatorItemName() {
        return "generatore";
    }
    
    @Override
    public String[] generatorItemAliases() {
        return new String[]{"quadro","salvavita"};
    }
    
}
