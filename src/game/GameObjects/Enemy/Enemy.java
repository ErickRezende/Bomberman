package GameObjects.Enemy;

import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.Bomb.Bomb;
import GameObjects.base.GameObject;
import Map.Block;
import Map.Map;
import Screens.Stages.GameStage;
import Settings.Settings;

public class Enemy extends GameObject {
    private int moveDirection;

    private float velocity = 0.07f;

    public Enemy(Vector2 position) {
        super(position, new Vector2(64, 64));
        
        this.moveDirection = 0;

        AssetsManager assetsManager = AssetsManager.getInstance();

        super.textureRegions = assetsManager.getTextureRegions("Enemy", this.size);
        super.animation = assetsManager.getAnimation(textureRegions, 0, 0.20f);
        super.currentTRegion = assetsManager.getCurrentTRegion(animation);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.currentTRegion, this.position.x, this.position.y, (int)this.size.x, (int)this.size.y);        
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        this.velocity -= deltaTime;

        if(this.velocity > 0){
            return;
        }

        this.velocity = 0.07f;

        while(true) {
            Vector2 newPosition = new Vector2(position); // Nova posição hipotética

            switch (this.moveDirection) {
                case 0: // RIGHT
                    newPosition.x += Settings.BLOCKS_SIZE / 8;    
                    break;
            
                case 1: // UP
                    newPosition.y += Settings.BLOCKS_SIZE / 8;    
                    break;
                    
                case 2: // LEFT
                    newPosition.x -= Settings.BLOCKS_SIZE / 8;    
                    break;
                
                case 3: // DOWN
                    newPosition.y -= Settings.BLOCKS_SIZE / 8;    
                    break;
                
            }
            boolean collide = false;

            for(Vector<Block> i : Map.getInstance().getBlocks()){
                for(Block j : i){
                    if(j.getType() != Block.VOID && j.collide(newPosition, this.size)){
                        collide = true;
                    }
                }
            }

            if(newPosition.x < Map.getInstance().getBlock(new Vector2(0, 0)).getPosition().x || newPosition.y < Map.getInstance().getBlock(new Vector2(0, 0)).getPosition().y || newPosition.x > Map.getInstance().getLastBlock().getPosition().x || newPosition.y > Map.getInstance().getLastBlock().getPosition().y){
                collide = true;
            }

            if (!collide) {
                position.set(newPosition);
                break;

            } else {
                this.moveDirection = (int)(Math.random() * 4);
                newPosition.set(this.position);
            }
        }
    }
}
