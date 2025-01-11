import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import Settings.Settings;

public class DesktopStarter {
	public static void main (String[] args) {
                new LwjglApplication(Game.getIntance(), "Bomberman", Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, false);
                System.setProperty("org.lwjgl.opengl.Display.allowSoftwareOpenGL", "true");
	}
}