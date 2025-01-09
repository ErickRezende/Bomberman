import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Map.Map;
import Settings.Settings;

public class Game extends ApplicationAdapter{
    private static ApplicationAdapter INSTANCE = null;
    private Map map;
    private SpriteBatch batch;

    private Game(){}

    @Override
    public void create(){
        this.map = Map.getIntance();
        this.batch = new SpriteBatch();
    }

    public void update(){
        this.map.update();
    }
    
    public void draw(){
        batch.begin();
        {
            this.map.draw(batch);
        }
        batch.end();
    }

    @Override
    public void render(){
        this.update();

        // Clear the screen with a black color before draw
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.draw();
    }

    @Override
    public void resize(int width, int height){
        Settings.WINDOW_HEIGHT = height;
        Settings.WINDOW_WIDTH = width;

        this.map.resize(width, height);
    }

    @Override 
    public void pause(){/* Nothing to do */}

    @Override
    public void resume(){/* Nothing to do */} 

    @Override
    public void dispose(){
        this.batch.dispose();
        this.map.dispose();
    }

    public static ApplicationAdapter getIntance() {
        if(INSTANCE == null){
            INSTANCE = new Game();
        }

        return INSTANCE;
    }
}
