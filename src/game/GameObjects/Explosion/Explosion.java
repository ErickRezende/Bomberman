package GameObjects.Explosion;

import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.Enemy.Enemy;
import GameObjects.base.GameObject;
import Map.Block;
import Map.Map;
import Screens.GameScreen;
import Screens.Stages.GameStage;
import Screens.Stages.LobbyStage;
import Screens.Stages.ResultStage;
import Settings.Settings;

public class Explosion extends GameObject{
    Vector<TextureRegion> currentTRegions;
    Vector<Animation> animations;

    Vector<Integer> directionsPower;
    Vector<Block> blocksToBoom;

    float animationTime = 0.3f; 

    int power;

    Map map;

    float frameDuration = 0.1f;

    public Explosion(Vector2 position, int power){
        super(new Vector2(position.x, position.y), new Vector2(64, 64));

        this.animations = new Vector<Animation>();
        this.currentTRegions = new Vector<TextureRegion>();
        this.directionsPower = new Vector<Integer>();

        blocksToBoom = new Vector<Block>();
    
        map = Map.getInstance();
        AssetsManager assetsManager = AssetsManager.getInstance();
        
        explodir(true);

        super.textureRegions = assetsManager.getTextureRegions("Boom", this.size);

        this.power = power;
        
 
        for(int i = 0; i < 3; i++){
            this.animations.add(assetsManager.getAnimation(textureRegions, i, frameDuration));
            this.currentTRegions.add(textureRegions[i][0]);
        }

        this.setPowerDirections();

        System.out.println(this.directionsPower);
    }

    @Override
    public void update(float deltaTime) {
        this.animationTime -= deltaTime;

        for(int i = 0; i < this.currentTRegions.size(); i++){
            currentTRegions.set(i, AssetsManager.getInstance().getCurrentTRegion(animations.get(i)));
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.currentTRegions.get(0), (int)(this.position.x), (int)(this.position.y), (int)this.size.x, (int)this.size.y);

        int cont = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if((i != 0 && j != 0) || (i == 0 && j == 0)){
                    continue;
                }

                int line = 0;

                for(int k = 1; k <= this.directionsPower.get(cont); k++){ 
                    if(k == 0 || k == 1){
                        line = k;
                    } 
                    if(k == this.directionsPower.get(cont)){
                        line = 2;
                    }

                    batch.draw(
                        this.currentTRegions.get(line),
                        (int)(this.position.x + (i*k*this.size.x)) + (((i+j)+1)/2) * this.size.x,
                        (int)(this.position.y + (j*k*this.size.y)) + ((cont+1)%2) * this.size.y,
                        0, 0,
                        (int)(this.size.x),
                        (int)(this.size.y),
                        1, 1,
                        (j+1)*(i+j) * 90
                    );
                }

                cont++;
            }
        }
    }

    private void setPowerDirections(){
        Vector2 pos = map.getBlockPosByPxls(this.position);

        // Hoje eu e Deus sabemos sabemos com isso funciona, amanhã só Ele
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if((i != 0 && j != 0) || (i == 0 && j == 0)){
                    continue;
                }
                
                int cont = 0;
                for(int k = 1; k < this.power; k++){
                    if(
                        pos.x + i*k < 0 ||
                        pos.y + j*k < 0 ||
                        pos.x + i*k > map.getLastBlock().getGradePos().x ||
                        pos.y + j*k > map.getLastBlock().getGradePos().y ||
                        map.getBlock(new Vector2(pos.x + i*k, pos.y + j*k)).getType() == Block.WALL
                    ){
                        break;
                    }

                    Vector2 blockPos = new Vector2(pos.x + i*k, pos.y + j*k);

                    if(map.getBlock(blockPos).getType() != Block.VOID){
                        map.getBlock(blockPos).setType(Block.VOID);
                        map.getBlock(blockPos).explode();
                    }

                    if(GameStage.getInstance().getPlayer().collide(map.getBlock(blockPos))){
                        explodir(false);
                        GameStage.getInstance().musica(false);
                        GameScreen.getInstance().setStage(ResultStage.getInstance());
                    }

                    for(Enemy enemy: GameStage.getInstance().getEnemies()){
                        if(enemy.collide(map.getBlock(blockPos))){
                            GameStage.getInstance().getEnemies().remove(enemy);
                            break;
                        }        
                    }
                    
                    cont++;
                }

                this.directionsPower.add(cont);
            }
        }

    }

    public boolean animationEnds(){
        if(this.animationTime <= 0){
            return true;
        }

        return false;
    }

    public void explodir(boolean controle){
        if(controle==true){
            AssetsManager.getInstance().getSounds("Explosao").play();
        }else{
            AssetsManager.getInstance().getSounds("Explosao").stop();
        }
    }
}