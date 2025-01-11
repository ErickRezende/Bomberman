package Screens.Stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class LobbyStage extends Stage {
    private static Stage INSTANCE = null;

    private SpriteBatch batch;

    private LobbyStage(){
        this.batch = new SpriteBatch();
    }

    @Override
    public void act(float deltaTime){ // update
    }
    
    @Override
    public void draw(){
        batch.begin();
        {
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