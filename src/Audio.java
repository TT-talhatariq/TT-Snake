import javax.security.sasl.SaslClient;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Audio {
    Clip audio;
    Audio(String path){
        File file =  new File(path);
        Scanner scanner = new Scanner(System.in);
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            audio = AudioSystem.getClip();
            audio.open(audioInputStream);

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }


    }
}
