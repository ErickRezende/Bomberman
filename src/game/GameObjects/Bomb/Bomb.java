package GameObjects.Bomb;

import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;

public class Bomb extends GameObject {
    AssetsManager assetsManager; 

    public Bomb(Vector2 position){
        super(new Vector2(position.x, position.y), new Vector2(16, 16));

        assetsManager = AssetsManager.getInstance();


        super.texture = assetsManager.getTexture("Bomb");
        //super.textureRegions = assetsManager.getTextureRegions("Bomb");
        //super.animation = assetsManager.getAnimation(textureRegions, 0, 0.15f);
        //super.currentTRegion = assetsManager.getCurrentTRegion(animation);
    }
}
