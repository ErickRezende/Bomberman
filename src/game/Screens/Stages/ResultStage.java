package Screens.Stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.Stage;

import AssetsManager.AssetsManager;


public class ResultStage extends Stage {
    private static Stage INSTANCE = null;

    private SpriteBatch batch;

    private Texture texture;


    private ResultStage(){
        this.batch = new SpriteBatch();


        AssetsManager assetsManager = AssetsManager.getInstance();

        this.texture = assetsManager.getTexture("Derrota");   
        AssetsManager.getInstance().getSounds("Derrota1").play();
    }
    public void draw(){
        batch.begin();
        {   
            batch.draw(texture, 0, 0, 1024, 768);
            
        }
        batch.end();
    }

    @Override
    public void dispose(){/* Nothing to dispose */}

    public static Stage getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ResultStage();
        }

        return INSTANCE;
    }


}