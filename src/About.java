import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;

public class About extends JFrame implements MouseListener  {
    JPanel p = new JPanel();
    final static Image help = new ImageIcon("src/img/about.png").getImage();
    JLabel twitter,github, linkedin;
    ImageIcon twitter_img = new ImageIcon("src/img/twitter.png"),
            github_img = new ImageIcon("src/img/github.png"),
            linkedin_img = new ImageIcon("src/img/linkedin.png");
    About() {
        p.setBounds(110, 440, 200, 200);
        p.setOpaque(true);
        p.setBackground(Color.white);

        setSize(600, 600);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        twitter = new JLabel("");
        twitter.setIcon(twitter_img);
        twitter.setSize(60, 60);

        github = new JLabel("");
        github.setIcon(github_img);
        github.setSize(60, 60);

        linkedin = new JLabel("");
        linkedin.setIcon(linkedin_img);
        linkedin.setSize(60, 60);
        linkedin.addMouseListener(this);
        github.addMouseListener(this);
        twitter.addMouseListener(this);

        github.setCursor(new Cursor(Cursor.HAND_CURSOR)); // If I Move The Cursor In The LabelImg Change The Cursor Design By Hand
        linkedin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        twitter.setCursor(new Cursor(Cursor.HAND_CURSOR));

        p.add(twitter);
        p.add(github);
        p.add(linkedin);


        add(p);
    }


    public void paint(Graphics g){
        super.paint(g);
        g.drawImage(help, 0,0, null);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource().equals(linkedin))
        try {
            Audio clicked = new Audio("src/sounds/click.wav");
            clicked.audio.start();
            Desktop.getDesktop().browse(URI.create("https://www.linkedin.com/in/talha-tariq-7511631b0/"));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        if(e.getSource().equals(twitter))
            try {
                Audio clicked = new Audio("src/sounds/click.wav");
                clicked.audio.start();
                Desktop.getDesktop().browse(URI.create("https://twitter.com/Talha_Tariq_TT"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        if(e.getSource().equals(github))
            try {
                Audio clicked = new Audio("src/sounds/click.wav");
                clicked.audio.start();

                Desktop.getDesktop().browse(URI.create("https://github.com/TT-talhatariq"));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
