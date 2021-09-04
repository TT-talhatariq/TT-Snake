/*
This class consists UI, Control, L
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final Button start = new Button("Start"), help = new Button("Help"), new_game = new Button("New Game"), about = new Button("About"), pausee = new Button("Pause"), play = new Button("Play");
    static final int Width = 850;
    static final int Height = 700;
    static final int UNIT_Size = 35;
    static final int Game_Unit = (Width*Height)/ UNIT_Size;
    static  final int delay = 150;
    final int x [] = new int[50];
    final int y[] = new int[50];
    int bodyParts = 5;
    int appleEaten = 0;
    int appleX, appleY;
    Image food;
    char direction = 'R';
    boolean running, pause = false;
    int Score = 0;
    Timer timer;
    final static Image logo = new ImageIcon("src/img/logo.png").getImage();

    GamePanel(){
        setPreferredSize(new Dimension(1050, Height));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        setBackground(new Color(230,230,250));
        setLayout(null);

        start.addActionListener(this);
        new_game.addActionListener(this);
        help.addActionListener(this);
        about.addActionListener(this);
        pausee.addActionListener(this);
        play.addActionListener(this);

        start.setBounds(900, 230,120,70);
        pausee.setBounds(900, 230,120,70);
        play.setBounds(900, 230,120,70);
        new_game.setBounds(900, 350,120,70);
        help.setBounds(900, 480,120,70);
        about.setBounds(900,600,120,70);

        new_game.setFont(new Font("Georgia", Font.PLAIN, 16));
        new_game.setVisible(false);
        pausee.setVisible(false);
        play.setVisible(false);


        add(start);
        add(help);
        add(new_game);
        add(about);
        add(pausee);
        add(play);
    }

    public void startGame(){
        newapple();
        running  = true;
        timer = new Timer(delay, this);
        timer.start();
        start.setVisible(false);
        pausee.setVisible(true);
        new_game.setVisible(true);
    }

    public void New_game(){
        timer.stop();
        running = false;
        Score = 0;
        bodyParts = 5;
        Arrays.fill(x, 0);
        Arrays.fill(y, 0);
        direction = 'R';
        startGame();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;
        //Rows Boxex
        for(int i=0; i<Width/UNIT_Size+1; i++) {
            for (int j = 0; j < Height/UNIT_Size; j++) {

                if ((i+j) % 2 == 0)
                    gd.setColor(new Color(170, 215, 81));
                else
                    gd.setColor(new Color(162, 209, 73));

                gd.fillRect(i * UNIT_Size, j * UNIT_Size, UNIT_Size, UNIT_Size);

            }
        }
        gd.setColor(Color.red);
        gd.setStroke(new BasicStroke(3));
        gd.drawLine(Width+26,0,Width+26,Height);
        //Snake Drawing
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0 ){
                    gd.setColor(new Color(248, 55, 55));
                    gd.fillRect(x[i], y[i], UNIT_Size - 3, UNIT_Size - 3);
                    gd.setColor(Color.white);
                    gd.setStroke(new BasicStroke(3));
                    gd.drawOval(x[i] + 4, y[i] + 8, 8, 8);
                    gd.drawOval(x[i] + 20, y[i] + 8, 8, 8);
                    gd.setStroke(new BasicStroke(2));
                    gd.drawLine(x[i] + 4, y[i] + 25, x[i] + 28, y[i] + 25);
                } else {
                    gd.setColor(new Color(83, 130, 241));
                    gd.fillRect(x[i], y[i], UNIT_Size - 3, UNIT_Size - 3);

            }
        }
        //New Apple
        gd.drawImage(food, appleX, appleY, null);

        //Score
        gd.setColor(new Color(93, 1, 1));
        gd.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
        gd.drawString("Score:" + Score, 660 ,40);

        gd.drawImage(logo, 880,0,null);

        if(!running && new_game.isVisible()){
            Gameover(g);
        }
    }
    public  void move(){

        for(int i=bodyParts; i>0; i--){
            x[i]  = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
                case 'R':
                    x[0] = x[0] + UNIT_Size;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_Size;
                    break;
                case 'U':
                    y[0] = y[0] - UNIT_Size;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_Size;
                    break;
        }

    }
    public void newapple() {
        final String[] Food_Images = new String[]{"src/img/ic_orange.png", "src/img/ic_apple.png", "src/img/ic_cherry.png",
                "src/img/ic_berry.png", "src/img/ic_coconut_.png", "src/img/ic_peach.png", "src/img/ic_watermelon.png", "src/img/ic_orange.png",
                "src/img/ic_pomegranate.png"};
        Random random = new Random();
        appleX = random.nextInt(Width / UNIT_Size) * UNIT_Size;
        appleY = random.nextInt(Height / UNIT_Size) * UNIT_Size;
        food = new ImageIcon(Food_Images[random.nextInt(9)]).getImage().getScaledInstance(35,35,5);

    }
    public void checkApple(){
        if(x[0]==appleX && y[0] ==appleY){
            Audio clicked = new Audio("src/sounds/eat.wav");
            clicked.audio.start();
            newapple();
            bodyParts++;
            appleEaten++;
            Score+=5;
        }

    }
    public void checkCollision(){
        for(int i=bodyParts; i>0; i--) {
            //If Body Touches itself
            if (x[0] == x[i] && y[0] == y[i])
                running = false;

            //Collision with sides
            if (x[0] >= Width - 35)         //Right Border
                running = false;
            if (x[0] < 0)              //Left Border
                running = false;
            if (y[0] < 0)              //Up Border
                running = false;
            if (y[0] >= Height)
                running = false;    //Down Border

            if (!running) {
                Audio clicked = new Audio("src/sounds/die.wav");
                clicked.audio.start();
                timer.stop();
            }
        }
    }
    public void Gameover(Graphics g){
        g.setColor(new Color(157, 6, 6));
        g.setFont(new Font("Georgia", Font.BOLD, 60));
        FontMetrics fontMetrics  = getFontMetrics(g.getFont());
        g.drawString("Game Over", (Width-fontMetrics.stringWidth("Game Over"))/2, (int) ((Height-fontMetrics.stringWidth("Game Over"))/1.5));
    }
    void play(){
        play.setVisible(false);
        pausee.setVisible(true);
        timer.start();
    }
    void pause() {
        play.setVisible(true);
        pausee.setVisible(false);
        timer.stop();
    }
    void audio(){
        Audio clicked = new Audio("src/sounds/click.wav");
        clicked.audio.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start)
            startGame();

        if(e.getSource() == new_game){
            New_game();
            repaint();
        }
        if(e.getSource() == about){
            if(new_game.isVisible() && pausee.isVisible())
                pause();
            new About();
        }
        if(running) {
            move();
            checkCollision();
            checkApple();
        }
        repaint();

        if(e.getSource() == help){
            if(new_game.isVisible() && pausee.isVisible())
                pause();;
           new Help();
        }
        if(e.getSource() == pausee)
            pause();
        if(e.getSource() == play)
            play();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                        direction = 'R';
                    break;

                case KeyEvent.VK_UP:
                    if(direction!='D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                        direction = 'D';
                    break;
                case KeyEvent.VK_N:
                    if(!start.isVisible()) {
                     audio();
                        New_game();
                    }

                    break;
                case KeyEvent.VK_ENTER:
                    if(!new_game.isVisible()) {
                        audio();
                        startGame();
                    }
                    break;
                case KeyEvent.VK_P:
                    if(pausee.isVisible()){
                        audio();
                        pause();
                    }
                    else{
                        audio();
                        play();
                    }
                    break;
            }
        }
    }
}
