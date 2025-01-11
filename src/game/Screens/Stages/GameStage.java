package Screens.Stages;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import GameObjects.Player.Player;
import Map.Map;

public class GameStage extends Stage {
    private static Stage INSTANCE = null;

    private Map map;
    private SpriteBatch batch;

    private Player player; // Adiciona o jogador

    private GameStage(){
        this.map = Map.getInstance();
        this.batch = new SpriteBatch();

        // Inicializa o jogador
        this.player = new Player(new Vector2(200, 200)); // Posição inicial (100, 100)
    }

    @Override
    public void act(float deltaTime){ // update
        // Atualiza mapa e jogador
        this.map.update();
        this.player.update(deltaTime);
    }
    
    @Override
    public void draw(){
        batch.begin();
        {
            this.map.draw(batch); // Desenha o mapa
            this.player.draw(batch); // Desenha o jogador
        }
        batch.end();
    }

    @Override
    public void dispose(){
        this.map.dispose();
        this.player.dispose(); // Libera recursos do jogador
    }

    public static Stage getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GameStage();
        }

        return INSTANCE;
    }
}