import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class Main implements ActionListener {
    // Dichiarazione dei componenti grafici
    JFrame frame = new JFrame("Memory Game");

    JPanel field = new JPanel();
    JPanel menu = new JPanel();
    JPanel menu2 = new JPanel();
    JPanel menu3 = new JPanel();
    JPanel mini = new JPanel();


    JPanel start_screen = new JPanel();
    JPanel end_screen = new JPanel();
    JPanel instruct_screen = new JPanel();

    JButton btn[] = new JButton[20];
    JButton start = new JButton("Start");
    JButton over = new JButton("Exit");
    JButton easy = new JButton("Facile");
    JButton hard = new JButton("Difficile");
    JButton inst = new JButton("Come giocare");
    JButton redo = new JButton("Gioca di nuovo");
    JButton goBack = new JButton("Main Menu");

    Random randomGenerator = new Random();
    private boolean purgatory = false;
    JLabel winner;
    Boolean game_over = false;
    int level=0;
    int score=0;

    String[] board;
    int[] boardQ=new int[20];
    Boolean shown = true;
    int temp=30;
    int temp2=30;
    String a[]=new String[10];
    boolean eh=true;

    private JLabel label = new JLabel("Inserisci il livello da 1 a 10");
    private JTextField text = new JTextField(10);
    private JTextArea instructM = new JTextArea("Quando il gioco inizia, lo schermo verrà riempito\ncon coppie di pulsanti.\n Memorizza il loro posizionamento.\nUna volta che premi qualsiasi pulsante, saranno tutti nascosti. \n Il tuo obiettivo è fare clic su due pulsanti corrispondenti di fila.\nQuando lo finisci, vinci.\nOgni clic errato ti dà un punto (\n.\n BUONA FORTUNA! \n"+"per un livello singolo: inserire un livello compreso tra 1 e 10,\nseleziona facile o difficile, poi premi start.");


    public Main(){
        // Inizializzazione del frame principale
        frame.setSize(500,300);
        frame.setLocation(500,300);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start_screen.setLayout(new BorderLayout());
        menu.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu2.setLayout(new FlowLayout(FlowLayout.CENTER));
        menu3.setLayout(new FlowLayout(FlowLayout.CENTER));
        mini.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Aggiunta dei componenti al pannello start_screen
        start_screen.add(menu, BorderLayout.NORTH);
        start_screen.add(menu3, BorderLayout.CENTER);
        start_screen.add(menu2, BorderLayout.SOUTH);
        menu3.add(mini, BorderLayout.CENTER);
        menu.add(label);
        menu.add(text);
        mini.add(easy, BorderLayout.NORTH);
        mini.add(hard, BorderLayout.NORTH);
        mini.add(inst, BorderLayout.SOUTH);

        // Aggiunta degli event listener ai pulsanti
        start.addActionListener(this);
        start.setEnabled(true);
        menu2.add(start);
        over.addActionListener(this);
        over.setEnabled(true);
        menu2.add(over);
        easy.addActionListener(this);
        easy.setEnabled(true);
        hard.addActionListener(this);
        hard.setEnabled(true);
        inst.addActionListener(this);
        inst.setEnabled(true);

        // Aggiunta del pannello start_screen al frame
        frame.add(start_screen, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    public void setUpGame(int x,Boolean what){
        level=x;
        clearMain();

        // Inizializzazione del campo di gioco
        board = new String[2*x];
        for(int i=0;i<(x*2);i++){
            btn[i] = new JButton("");
            btn[i].setBackground(new Color(220, 220, 220));
            btn[i].addActionListener(this);
            btn[i].setEnabled(true);
            field.add(btn[i]);

        }

        // Definizione delle stringhe per le carte del gioco
        String[] b = {":-D","X","O","-(*.*)-","<>","<(^-^)>","=>",";^P","ABC","123"};//versione piu' difficile
        String[] c = {"quadrato","cerchio","rettangolo","cuore","diamante","fiore","picche","triangolo","poligono","tetraedro"};//versione piu' facile
        if(what) a=c;//se what é vero, rendere il gioco facile e usa c
        else a=b;//altrimenti rendilo difficile e usa b

        // Impostazione delle carte sul campo di gioco in modo casuale
        for(int i=0;i<x;i++){
            for(int z=0;z<2;z++){
                while(true){
                    int y = randomGenerator.nextInt(x*2);
                    if(board[y]==null){
                        btn[y].setText(a[i]);
                        board[y]=a[i];
                        break;
                    }
                }
            }


        }
        createBoard();

    }
    public void hideField(int x){
        // Nasconde tutti i pulsanti sul campo di gioco
        for(int i=0;i<(x*2);i++){
            btn[i].setText("");
        }
        shown=false;
    }
    public void switchSpot(int i){
        // Cambia lo stato del pulsante nella posizione i tra vuoto e con il testo corrispondente
        if(board[i]!="done"){
            if(btn[i].getText()==""){
                btn[i].setText(board[i]);

            } else {
                btn[i].setText("");

            }
        }
    }

    public boolean checkWin(){
        // Controlla se tutte le carte sono state abbinate correttamente
        for(int i=0;i<(level*2);i++){
            if (board[i]!="done")return false;
        }
        winner();
        return true;
    }
    public void winner(){
        // Gestisce l'interfaccia quando il giocatore vince
        start_screen.remove(field);
        start_screen.add(end_screen, BorderLayout.CENTER);
        end_screen.add(new TextField("Hai vinto"), BorderLayout.NORTH);
        end_screen.add(new TextField("Score: " + score), BorderLayout.SOUTH);
        end_screen.add(goBack);
        goBack.setEnabled(true);
        goBack.addActionListener(this);




    }
    public void goToMainScreen(){
        new Main();
    }
    public void createBoard(){
        // Crea la griglia di gioco
        field.setLayout(new BorderLayout());
        start_screen.add(field, BorderLayout.CENTER);

        field.setLayout(new GridLayout(5,4,2,2));
        field.setBackground(Color.black);
        field.requestFocus();
    }
    public void clearMain(){
        // Pulisce la schermata principale per aggiungere la griglia di gioco o le istruzioni
        start_screen.remove(menu);
        start_screen.remove(menu2);
        start_screen.remove(menu3);

        start_screen.revalidate();
        start_screen.repaint();
    }
    public void actionPerformed(ActionEvent click){
        Object source = click.getSource();
        if(purgatory){
            switchSpot(temp2);
            switchSpot(temp);
            score++;
            temp=(level*2);
            temp2=30;
            purgatory=false;
        }
        if(source==start){ // Avvia il gioco con il livello e la difficoltà specificati
            try{
                level = Integer.parseInt(text.getText());
            } catch (Exception e){
                level=1;
            }

            setUpGame(level, eh);
        }
        if(source==over){// Termina il programma
            System.exit(0);
        }
        if(source==inst){// Mostra le istruzioni
            clearMain();

            start_screen.add(instruct_screen, BorderLayout.NORTH);

            JPanel one = new JPanel();
            one.setLayout(new FlowLayout(FlowLayout.CENTER));
            JPanel two = new JPanel();
            two.setLayout(new FlowLayout(FlowLayout.CENTER));
            instruct_screen.setLayout(new BorderLayout());
            instruct_screen.add(one, BorderLayout.NORTH);
            instruct_screen.add(two, BorderLayout.SOUTH);

            one.add(instructM);
            two.add(goBack);
            goBack.addActionListener(this);
            goBack.setEnabled(true);
        }
        if(source==goBack){ // Torna alla schermata principale
            frame.dispose();
            goToMainScreen();
        }
        if(source==easy){ // Imposta la difficoltà su facile
            eh=true;
            easy.setForeground(Color.BLUE);
            hard.setForeground(Color.BLACK);
        } else if(source==hard){ // Imposta la difficoltà su difficile
            eh=false;
            hard.setForeground(Color.BLUE);
            easy.setForeground(Color.BLACK);
        }

        for(int i =0;i<(level*2);i++){
            // Gestisce l'interazione con i pulsanti durante il gioco
            if(source==btn[i]){
                if(shown){
                    hideField(level);
                }else{
                    switchSpot(i);
                    if(temp>=(level*2)){
                        temp=i;
                    } else {
                        if((board[temp]!=board[i])||(temp==i)){
                            temp2=i;
                            purgatory=true;


                        } else {
                            board[i]="done";
                            board[temp]="done";
                            checkWin();
                            temp=(level*2);
                        }

                    }
                }

            }


        }


    }
    public static void main(String[] args) {
        new Main();
    }

}