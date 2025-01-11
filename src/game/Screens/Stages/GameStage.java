package Screens.Stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import Map.Map;

public class GameStage extends Stage {
    private static Stage INSTANCE = null;

    private Map map;
    private SpriteBatch batch;

    private GameStage(){
        this.map = Map.getInstance();
        this.batch = new SpriteBatch();
    }

    @Override
    public void act(float deltaTime){ // update
        this.map.update();
    }
    
    @Override
    public void draw(){
        batch.begin();
        {
           this.map.draw(this.batch); 
        }
        batch.end();
    }

    @Override
    public void dispose(){
        this.map.dispose();
    }

    public static Stage getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GameStage();
        }

        return INSTANCE;
    }
}