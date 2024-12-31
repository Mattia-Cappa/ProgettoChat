import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.PrintWriter;

//extends(eredarietà, eredita da una sola classe ) parola chiave
//permette di estendere un altra classe e puo aggiungere

public class ClientGUI extends JFrame {

    //1.Componenti(bottoni, textField, ettichette)
    private JTextArea textArea;
    private JTextField textField;
    private JButton sendbutton;
    private PrintWriter out;

    //2.costruttore --> è un metodo con lo stesso nome della classe , non restituisce nulla ne anche void
    public ClientGUI(PrintWriter out, int idClient) {
        this.out = out;

        // Configurazione della finestra
        setTitle("Client " + idClient); // metodo di JFrame, siccome lo estenod lo posso usare pure dentro la finestra
        //setSize(450, 350);
        setBounds(650, 350, 450, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); //disattiva tastino ingrandimento riuduzione, non puoi modificare le dimensioni della finestra

        // Imposta lokk and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Layoutfinestra principale
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 240, 240)); // Sfondo chiaro

        // Per vedere i messaggi
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollpane = new JScrollPane(textArea);
        scrollpane.setBorder(BorderFactory.createTitledBorder("Chat"));

        // aspetto Text Area
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textArea.setBackground(new Color(250, 250, 250));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Campo per scrivere il messaggio
        textField = new JTextField();
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5))
        );

        // Creo pulsante
        sendbutton = new JButton("Invia");
        sendbutton.setFont(new Font("SansSerif", Font.BOLD, 14));
        sendbutton.setBackground(new Color(100, 150, 250));
        sendbutton.setForeground(Color.WHITE);
        sendbutton.setFocusPainted(false);
        sendbutton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Aggiungere i componenti alla finestra
        add(scrollpane, BorderLayout.CENTER);
        // Creo pannello e sistemo le componenti
        JPanel buttonpanel = new JPanel(new BorderLayout(5, 5));
        buttonpanel.setBackground(new Color(240, 240, 240));
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
        setVisible(true);  // Per visualizzare la finestra
    }

    // Metodo per inviare i messaggi

    private void sendMessage(){
        String message = textField.getText().trim(); //Trim per evitare l'invio di spazi indesiderati
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
        textArea.setCaretPosition(textArea.getDocument().getLength()); // Scroll automatico
    }
}