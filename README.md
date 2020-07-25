# RELAZIONE PROGETTO  
  
# "Hive Mind"   
- by **GliAccendiniSgasati** - (Giuseppe "Accendino" Preziuso, Gianluca Sonnante)  
  
**Hive Mind** è un'avventura testuale sviluppata in linguaggio Java e giocabile sia in versione linea di comando, sia tramite interfaccia grafica (con Java Swing);  
  
Il progetto è stato sviluppato con un'ottica di estensibilità (data anche la volontà del gruppo di ampliare il gioco in futuro).
  
Il progetto rispetta i canoni del paradigma dell'Object Oriented Design, sfruttando meccanismi di ereditarietà tra classi per lasciare spazio ad eventuali future estensioni. Per citare un esempio **GameNPC** contiene gli attributi che un NPC normalmente dovrebbe possedere in quanto tale, ed è poi estesa dalla classe **GameCharacter**; in futuro, ereditando dalla stessa superclasse, si potrebbero aggiungere nemici al gioco, i quali avrebbero caratteristiche base condivise con i personaggi, ma differenziandosi in alcune caratteristiche, aggiungendo un'altra classe estensione della classe generale. 
Questo non solo previene la ridondanza nel codice, ma consente lo sfruttamento del principio di sostituibilità per fare riferimento a generici NPC.  
  
Altre entità rappresentate sono state:  
- **i comandi** (ciascuno dei quali ha le proprie funzionalità, descritte più nel dettaglio di seguito);  
- **gli oggetti di gioco** (e i container, caso particolare in cui gli oggetti sono rappresentati come liste in grado di contenere a loro volta altri oggetti);  
- **l'inventario** (una lista di oggetti che il giocatore potrà portarsi dietro e riempire con gli oggetti trovati durante l'avventura);  
- **le stanze**: queste ultime sono il vero cuore del gioco in quanto contengono tutte le entità prima descritte e formano la "mappa" dell'avventura; ogni stanza infatti contiene informazioni sulla sua collocazione (ovvero quali sono le stanze collegate ad esse nei punti cardinali), diverse descrizioni rispettivamente per la prima visita, le visite successive e una più dettagliata descrizione disponibile all'uso del comando "osserva", più la lista degli oggetti e dei personaggi presenti in quella determinata stanza;  
  
I comandi implementati sono stati resi più generici possibile in modo da permettere l'interazione del giocatore con tutti gli elementi presenti nel gioco; inoltre, in alcuni casi particolari, alcuni comandi possono essere bloccati poichè richiedono l'avverarsi di una condizione per essere eseguiti (esempio: non puoi aprire un armadietto bloccato senza prima avere equipaggiato un piede di porco), o essere loro stessi la causa dell'avverarsi di una condizione: sono stati infatti utilizzati diversi flag booleani **evento**, che permettono di tenere traccia dei progressi del giocatore ed essere usati come condizioni per avanzare; per esempio, un evento sarà verificato interagendo con un determinato oggetto che permetterà di sbloccare una determinata porta, e così via.  
  
Si è inoltre codificato un **Parser** e delle interfacce per controllare l'input inserito dall'utente e permettere il corretto svolgimento del gioco. Il Parser, infatti, è abbastanza generico e controlla che la prima parola sia un comando valido (o un suo alias, scelto in base ai sinonimi più usati del comando o comunque tra parole simili nel contesto semantico) e, tra i token successivi, cerca un massimo di 2 altre entità (tra oggetti nella stanza, oggetti dell'inventario e personaggi, con i loro rispettivi alias). Altre parole vengono ignorate e se presenti segnalate da un flag booleano "extraWords". Le uniche parole che vengono prese in considerazione sono quindi i comandi e ciò con cui si interagisce.  
  
Nomi ed alias di comandi ed entità sono, quindi, stati svincolati dal gioco tramite generiche interfacce che ne permettono l'implementazione e permettono quindi una maggiore precisione e intuitività nel gameplay, aumentando il raggio di scelte possibili per un'azione.  
  
Come già detto, Hive Mind è giocabile in due diverse modalità:
- la prima è **CommandLineEngine**, che prende in input una qualsiasi estensione della classe astratta GameDescription e permette di giocare tramite linea di comando;  
- la seconda è **HiveMindGameFrame**, un'interfaccia grafica realizzata tramite Java Swing, che amplia l'esperienza del giocatore e permette una maggiore immersione poiché durante il gameplay è riprodotta della musica di sottofondo; stampa inoltre su schermo l'inventario (oggetti, note raccolte e oggetti equipaggiati) e la stanza corrente visitata dal giocatore; ha inoltre dei pulsanti coi quale si può interagire per eseguire i comandi più utilizzati, quali i movimenti nelle quattro direzioni ed osservare la stanza, eseguibili comunque testualmente se si preferisce. Questa modalità permette anche di salvare una partita e di caricarne una già avviata, sfruttando il file di salvataggio HiveMindGame.dat. Inoltre ha un'opzione per il fast text (nel caso il giocatore preferisca avere già tutto il testo stampato su schermo) e per disattivare/riattivare la musica di gioco. Nel menu "?" sono disponibili informazioni sul gioco e la lista dei comandi disponibili (alcuni comandi sono lasciati all'immaginazione del giocatore per non rendere il gioco troppo semplice).
 
Per svincolare il codice del gioco e renderlo accessibile a entrambe le modalità, il metodo *nextMove()*, che aggiorna lo stato del gioco ad ogni mossa,  restituisce una stringa costruita mediante un oggetto StringBuilder contenente l'output da far visualizzare all'utente.  
  
Una particolarità del gioco è avere ampliato i **comandi di movimento**, in 5 direzioni invece che in 4: un comando di movimento aggiuntivo, chiamato "striscia", permette, ove concesso, il movimento in una stanza che non sia necessariamente collocata nei 4 punti cardinali, simulando un "passaggio segreto".
  
La longevità di Hive Mind è stimata intorno ai 20 minuti in media e dipende dalle azioni compiute dal giocatore: il gioco ricompensa infatti la volontà di esplorare del giocatore, poichè sono presenti due finali, uno dei quali richiede l'esplorazione completa del gioco, incluse parti completamente opzionali, e la risoluzione di ulteriori enigmi per essere sbloccato. 
