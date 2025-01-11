package Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import AssetsManager.AssetsManager;
import Screens.Stages.GameStage;
import Screens.Stages.LobbyStage;

public class GameScreen implements Screen {
    private static GameScreen INSTANCE = null;
    private AssetsManager assetsManager;
    private Stage stage;

    private GameScreen(){
        this.stage = GameStage.getInstance();
        this.assetsManager = AssetsManager.getInstance();
    }

    public void update(float deltaTime){
        this.stage.act(deltaTime);
        this.assetsManager.update(deltaTime);
    }
    
    public void draw(){
        stage.draw();
    }

    @Override
    public void render(float deltaTime){
        this.update(deltaTime);

        // Clear the screen with a black color before draw
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.draw();
    }

    @Override
    public void resize(int width, int height){/* Nothing to do */}

    @Override 
    public void pause(){/* Nothing to do */}

    @Override
    public void resume(){/* Nothing to do */} 

    @Override
    public void show(){/* Nothing to do */} 

    @Override
    public void hide(){/* Nothing to do */} 


    @Override
    public void dispose(){
        this.stage.dispose();
        this.assetsManager.dispose();
    }

    public static GameScreen getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GameScreen();
        }

        return INSTANCE;
    }
}
