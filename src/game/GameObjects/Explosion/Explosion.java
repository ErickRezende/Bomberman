package GameObjects.Explosion;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;
import Map.Block;
import Map.Map;

public class Explosion extends GameObject{
    Vector<TextureRegion> currentTRegions;
    Vector<Animation> animations;

    Vector<Integer> directionsPower;
    Vector<Block> blocksToBoom;

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
        
        super.textureRegions = assetsManager.getTextureRegions("Boom", this.size);

        this.power = power;
        
        int line = 0;
        
        for(int i = 0; i < power; i++){
            if(i == 0 || i == 1){
                line = i;
            }
            if(i == power - 1){
                line = 2;
            }

            this.animations.add(assetsManager.getAnimation(textureRegions, line, frameDuration));
            this.currentTRegions.add(textureRegions[line][0]);
        }

        this.setPowerDirections();

        System.out.println(this.directionsPower);
    }

    @Override
    public void update(float deltaTime) {
        for(int i = 0; i < this.currentTRegions.size(); i++){
            currentTRegions.set(i, AssetsManager.getInstance().getCurrentTRegion(animations.get(i)));
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        for(int i = 0; i < this.currentTRegions.size(); i++){
            batch.draw(this.currentTRegions.get(i), this.position.x, this.position.y - i * this.size.y, (int)this.size.x, (int)this.size.y);        
        }

        for(int i  = 0; i < this.directionsPower.size(); i++){
            Vector2 pos;
            switch (i) {
                case 0:
                    pos  = new Vector2(-1, 0) ;
                    break;
            
                case 1:
                    pos  = new Vector2(0, -1) ;
                    break;

                case 2:
                    pos  = new Vector2(0, 1) ;
                    break;

                case 3:
                    pos  = new Vector2(1, 0) ;
                    break;


                default:
                    break;
            }


            for(int j = 0; j < this.directionsPower.get(i); j++){
                batch.draw(this.currentTRegions.get(j), this.position.x, this.position.y - j * this.size.y, (int)this.size.x, (int)this.size.y);        
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

                    map.getBlock(new Vector2(pos.x + i*k, pos.y + j*k)).setType(Block.VOID);
                    cont++;
                }

                this.directionsPower.add(cont);
            }
        }
    }

    public boolean animationEnds(){
        if(this.currentTRegions.get(0) == textureRegions[0][textureRegions[0].length - 1]){
            return true;
        }

        return false;
    }
}