
import java.io.File;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author Richard
 */
public class Test {
    
    public static void main(String[] args) throws InterruptedException {
        JFXPanel fxPanel = new JFXPanel();
        
        Media media1 = new Media(new File("C:\\DVBBS & Borgeous - Tsunami.mp3").toURI().toString());
        MediaPlayer player1 = new MediaPlayer(media1);
        player1.play();
    }
    
}
