# Progetto Chat Server-Client

Questo progetto implementa un sistema di chat server-client in Java utilizzando socket, con un'interfaccia grafica per i client e funzionalità avanzate come messaggi privati, lista dei client connessi e comandi speciali.

## Indice

1. [Introduzione](#introduzione)
2. [Funzionalità](#funzionalità)
3. [Componenti del Progetto](#componenti-del-progetto)
   - [Server](#server)
   - [Client](#client)
   - [Protocol](#protocol)
   - [ClientGUI](#clientgui)
4. [Comandi Supportati](#comandi-supportati)
5. [Architettura del Codice](#architettura-del-codice)
6. [Come Eseguire il Progetto](#come-eseguire-il-progetto)
7. [Esecuzione dei File Jar](#esecuzione-dei-file-jar)

---

## Introduzione

Questo sistema di chat consente la comunicazione tra più client attraverso un server centrale. Ogni client ha un'interfaccia grafica per inviare e ricevere messaggi. Il server gestisce i client connessi e supporta comandi speciali per migliorare l'esperienza utente.

---

## Funzionalità

- Comunicazione in tempo reale tra i client.
- Invio di messaggi privati a specifici client.
- Lista dei client connessi.
- Client dotato di GUI.
- Supporto per comandi speciali (-HELP, -LIST, -PRIVATE, -SHUTDOWN).

---

## Componenti del Progetto

### Server

Il **Server** è il componente centrale che gestisce la connessione dei client e l'instradamento dei messaggi. Utilizza un'istanza di `ServerSocket` per ascoltare le connessioni sulla porta 8080. Quando un client si connette, il server:

- Avvia un nuovo thread con un'istanza della classe `Protocol` per gestire la comunicazione con il client.
- Mantiene una lista di client connessi.

Codice principale:

```java
ServerSocket mySocket = new ServerSocket(8080);
Socket clientSockets = mySocket.accept();
Protocol p = new Protocol(clientSockets);
Thread t = new Thread(p);
t.start();
```

### Client

Il **Client** si connette al server e fornisce un'interfaccia grafica (GUI) per la comunicazione. Dopo la connessione, il client:

- Riceve un ID univoco dal server.
- Avvia thread per inviare (`ProtocolPrintWriter`) e ricevere (`ProtocolScanner`) messaggi.
- Mostra messaggi ricevuti nella GUI.

Codice principale:

```java
Socket client = new Socket(InetAddress.getLocalHost(),8080);
ClientGUI gui = new ClientGUI(out, idClient);
Thread tp = new Thread(new ProtocolPrintWriter(client));
Thread ts = new Thread(new ProtocolScanner(client, gui));
ts.start();
```

### Protocol

La classe **Protocol** gestisce la logica di comunicazione tra server e client. Ogni client connesso ha un'istanza di `Protocol` associata. Funzionalità principali:

- Invio di messaggi broadcast a tutti i client.
- Invio di messaggi privati.
- Gestione di comandi speciali.

### ClientGUI

La classe **ClientGUI** rappresenta l'interfaccia grafica del client. Utilizza Swing per creare una finestra intuitiva che consente agli utenti di:

- Scrivere messaggi.
- Vedere i messaggi ricevuti.
- Interagire con i comandi del server.



---

## Comandi Supportati

### -HELP

Mostra l'elenco dei comandi disponibili.

### -LIST

Mostra la lista dei client connessi al server.

### -PRIVATE [id] [message]

Invia un messaggio privato a un client specifico.

### -SHUTDOWN

Chiude il server e blocca tutti i client.

---

## Come Eseguire il Progetto

### Requisiti

- Java Development Kit (JDK) 22 o superiore.

### Istruzioni

1. Compilare i file Java:
   ```bash
   javac Server.java Client.java Protocol.java ClientGUI.java ProtocolPrintWriter.java ProtocolScanner.java
   ```
2. Avviare il server:
   ```bash
   java Server
   ```
3. Avviare uno o più client:
   ```bash
   java Client
   ```
4. Utilizzare la GUI per inviare messaggi e interagire con il server.

---

## Esecuzione dei File Jar

Per semplificare l'esecuzione, i file possono essere impacchettati in file `.jar` eseguibili.

### Esecuzione dei File Jar
Prima di eseguire i file bisogna recarsi nella cartella dove questi sono contenuti e aprire il cmd.

1. Avviare il server utilizzando il file `.jar` nella cartella Server_jar:
   ```
   java -jar ProgettoChat.jar
   ```
2. Avviare uno o più client utilizzando il file `.jar` nella cartella Client_jar:
   ```
   java -jar ProgettoChat.jar
   ```

---

## Note

- Assicurarsi che il server sia avviato prima di avviare i client.
- Per testare il sistema in locale, utilizzare `localhost` come indirizzo del server.
- Per eseguire in rete, sostituire `InetAddress.getLocalHost()` con l'indirizzo IP del server.

---

## Autore

Questo progetto è stato sviluppato da:

- Cappa Mattia;
- Halilovic Danis;
- Dordoni Thomas;
- Hajjioui Hatim.

