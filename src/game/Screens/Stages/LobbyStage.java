package Screens.Stages;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import AssetsManager.AssetsManager;
import GameObjects.Button.Button;
import Screens.GameScreen;

public class LobbyStage extends Stage {
    private static Stage INSTANCE = null;

    private SpriteBatch batch;

    private Texture texture;

    private Button button;

    private LobbyStage(){
        this.batch = new SpriteBatch();

        abertura(true);

        AssetsManager assetsManager = AssetsManager.getInstance();

        this.texture = assetsManager.getTexture("Menu");   

        button = new Button(new Vector2(512, 512), 0.6f, 0.2f, "Jogar");
    }

    @Override
    public void act(float deltaTime){ // update
        if(button.isClicked()) {
            abertura(false);
            GameScreen.getInstance().setStage(GameStage.getInstance());
        }

    }
    
    @Override
    public void draw(){
        batch.begin();
        {
            batch.draw(texture, 0, 0, 1024, 768);
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

public void abertura(boolean controle){
    if(controle==true){
        AssetsManager.getInstance().getSounds("Abertura").setLooping(true);
        AssetsManager.getInstance().getSounds("Abertura").play();
    }else{
        AssetsManager.getInstance().getSounds("Abertura").stop();
    }
}
}