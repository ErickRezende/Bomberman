import com.badlogic.gdx.Game;

import Screens.GameScreen;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    private static Game INSTANCE = null;

	@Override
	public void create() {
		setScreen(GameScreen.getInstance());
	}

    public static Game getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Main();
        }

        return INSTANCE;
    }
}