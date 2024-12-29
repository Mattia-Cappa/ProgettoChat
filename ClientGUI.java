import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.PrintWriter;

//extends(eredarietà, eredita da una sola classe ) parola chiave
//permette di estendere un altra classe e puo aggiungere

public class ClientGUI extends JFrame
{
    //1.Componenti(bottoni, textField, ettichette)
    private JTextArea textArea; // Vedere i mess ricevuti
    private JTextField textField; // Scrivere i mess
    private JButton sendbutton; // bottone per inviare i mess
    private PrintWriter out; // Per invio di messaggi

    //2.costruttore --> è un metodo con lo stesso nome della classe , non restituisce nulla ne anche void
    public ClientGUI(PrintWriter out, int idClient)
    {
        this.out = out;

        // Configurazione della finestra
        setTitle("Client " + idClient); // metodo di JFrame, siccome lo estenod lo posso usare pure dentro la finestra
        setSize(400,300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); //disattiva tastino ingrandimento riuduzione, non puoi modificare le dimensioni della finestra

        setLayout(new BorderLayout());

        // Per vedere i messaggi
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollpane = new JScrollPane(textArea);

        // Campo per scrivere il messaggio
        textField = new JTextField();

        // Creo pulsante
        sendbutton = new JButton("Invia");

        // Aggiungere i componenti alla finestra
        add(scrollpane, BorderLayout.CENTER);
        // Creo pannello e sistemo le componenti
        JPanel buttonpanel = new JPanel(new BorderLayout());
        buttonpanel.add(textField, BorderLayout.CENTER);
        buttonpanel.add(sendbutton, BorderLayout.EAST);
        add(buttonpanel, BorderLayout.SOUTH);


        // 3. Eventi
        sendbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true); // Per visualizzare la finestra
    }

    // Metodo per inviare i messaggi

    private void sendMessage(){
        String message = textField.getText();
        if (!message.isEmpty()){
            // deve inviare il mess al server
            out.println(message);
            // svuota il campo
            textField.setText("");
        }
    }

    // metodo per aggiungere mess ricevuti alla gui

    public void addMessage(String message){
        textArea.append(message + "\n");
    }
}
