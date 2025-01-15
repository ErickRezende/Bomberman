package Screens.Stages;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import AssetsManager.AssetsManager;
import GameObjects.Bomb.Bomb;
import GameObjects.Enemy.Enemy;
import GameObjects.Player.Player;
import GameObjects.Power.Power;
import Map.Map;
import Screens.GameScreen;

public class GameStage extends Stage {
    private static GameStage INSTANCE = null;

    private Map map;
    private SpriteBatch batch;

    private Player player; // Adiciona o jogador

    private ArrayList<Enemy> enemies;

     private Texture texture,texture2; 
    
    private ArrayList<Power> powers;

    private GameStage(){
        this.map = Map.getInstance();
        this.batch = new SpriteBatch();

        this.powers = new ArrayList<Power>();
        this.enemies = new ArrayList<Enemy>();

        AssetsManager assetsManager = AssetsManager.getInstance();
        musica(true);

        
        this.texture = assetsManager.getTexture("Derrota");   
        this.texture2 = assetsManager.getTexture("Vitoria");   

        // Inicializa o jogador
        this.player = new Player(new Vector2(160, 160));

        enemies.add(new Enemy(new Vector2(map.getBlock(new Vector2(map.getLastBlock().getGradePos())).getPosition())));
        enemies.add(new Enemy(new Vector2(map.getBlock(new Vector2(map.getLastBlock().getGradePos())).getPosition().x,map.getBlock(new Vector2(0,0)).getPosition().y)));
        enemies.add(new Enemy(new Vector2(map.getBlock(new Vector2(0,0)).getPosition().x,map.getBlock(new Vector2(map.getLastBlock().getGradePos())).getPosition().y)));
    }

    @Override
    public void act(float deltaTime){ // update
        // Atualiza mapa e jogador
        this.map.update();
        this.player.update(deltaTime);

        for(Enemy enemy: enemies){
            enemy.update(deltaTime);
      }
      

        for(Power power : this.powers){
            if(power.collide(player)){
                if(power.getType() == 0){
                    player.setPower(player.getPower() + 1);
                    powers.remove(power);
                    break; 
                } else if(power.getType() == 1){
                    player.setCooldown((player.getCooldown() - 0.01f));
                    powers.remove(power);
                    break; 
                }
                
            } 
        }

        for(Enemy enemy: enemies){
            if(enemy.collide(player)){
                musica(false);
                 GameScreen.getInstance().setStage(ResultStage.getInstance());
            }     
      }

      if(enemies.size()==0){
        musica(false);
        GameScreen.getInstance().setStage(WinStage.getInstance());  
      }

    }
    
    @Override
    public void draw(){
        batch.begin();
        {      
            this.map.draw(batch); // Desenha o mapa
            this.player.draw(batch);// Desenha o jogador
            for(Enemy enemy: enemies){
                enemy.draw(batch);
          }

            for(Power power : this.powers){
                power.draw(batch);
            }
        }
        batch.end();
    }

    @Override
    public void dispose(){
        this.map.dispose();
        this.player.dispose(); // Libera recursos do jogador
    }

    public Player getPlayer(){
        return this.player;
    }

    public ArrayList<Power> getPowers() {
        return this.powers;
    }

    public static GameStage getInstance() {
        if(INSTANCE == null){
            INSTANCE = new GameStage();
        }

        return INSTANCE;
    }

    public ArrayList<Enemy> getEnemies(){
        return this.enemies;
    }

    public void musica(boolean controle){
        if(controle==true){
            AssetsManager.getInstance().getSounds("Jogo").setLooping(true);
            AssetsManager.getInstance().getSounds("Jogo").play();
        }else{
            AssetsManager.getInstance().getSounds("Jogo").stop();
        }
    }

}


