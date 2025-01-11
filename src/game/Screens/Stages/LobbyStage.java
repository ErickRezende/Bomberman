package Screens.Stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import GameObjects.Button.Button;
import Screens.GameScreen;

public class LobbyStage extends Stage {
    private static Stage INSTANCE = null;

    private SpriteBatch batch;

    private Button button;

    private LobbyStage(){
        this.batch = new SpriteBatch();

        button = new Button(new Vector2(512, 256), Button.CENTER, Button.CENTER, "PlayButton");
    }

    @Override
    public void act(float deltaTime){ // update
        if(button.isClicked()) {
            GameScreen.getInstance().setStage(GameStage.getInstance());
        }
    }
    
    @Override
    public void draw(){
        batch.begin();
        {
            button.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose(){/* Nothing to dispose */}

    public static Stage getInstance() {
        if(INSTANCE == null){
            INSTANCE = new LobbyStage();
        }

        return INSTANCE;
    }
}