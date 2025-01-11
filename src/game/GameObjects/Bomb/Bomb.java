package GameObjects.Bomb;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;

public class Bomb extends GameObject {
    private AssetsManager assetsManager; 

    private float timeToBoom = 3.0f;

    public Bomb(Vector2 position){
        super(new Vector2(position.x, position.y), new Vector2(64, 64));

        assetsManager = AssetsManager.getInstance();

        super.textureRegions = assetsManager.getTextureRegions("Bomb", this.size);
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

        timeToBoom -= deltaTime;
    }

    public boolean isTimeForBoom(){
        return (timeToBoom <= 0);
    }
}
