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

    int power;

    Map map;

    float frameDuration = 0.1f;

    public Explosion(Vector2 position, int power){
        super(new Vector2(position.x, position.y), new Vector2(64, 64));

        this.animations = new Vector<Animation>();
        this.currentTRegions = new Vector<TextureRegion>();
        this.directionsPower = new Vector<Integer>();
    
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
    }

    private void setPowerDirections(){
        for(int i = 0; i < 4; i++) {
            this.directionsPower.add(this.power);
        }

        // RIGHT
        for(int i = 0; i <= this.directionsPower.get(0); i++){
            Vector2 pos = map.getBlockPosByPxls(this.position);
            pos.x += i;

            if(pos.x > map.getBlock(pos).getPosition().x || map.getBlock(pos).getType() == Block.WALL){
                this.directionsPower.set(0, i);
                break;
            }
        }

        // TOP
        for(int i = 0; i <= this.directionsPower.get(1); i++){
            Vector2 pos = map.getBlockPosByPxls(this.position);
            pos.y += i;

            if(pos.y > map.getBlock(pos).getPosition().y || map.getBlock(pos).getType() == Block.WALL){
                this.directionsPower.set(1, i);
                break;
            }
        }

        // LEFT
        for(int i = 0; i <= this.directionsPower.get(2); i++){
            Vector2 pos = map.getBlockPosByPxls(this.position);
            pos.x -= i;

            if(pos.x < 0 || map.getBlock(pos).getType() == Block.WALL){
                this.directionsPower.set(2, i);
                break;
            }
        }

        // TOP
        for(int i = 0; i <= this.directionsPower.get(3); i++){
            Vector2 pos = map.getBlockPosByPxls(this.position);
            pos.y -= i;

            if(pos.y < 0 || map.getBlock(pos).getType() == Block.WALL){
                this.directionsPower.set(3, i);
                break;
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