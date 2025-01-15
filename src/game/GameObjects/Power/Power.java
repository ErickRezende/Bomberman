package GameObjects.Power;

import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;

public class Power extends GameObject{

    private int type;

    public Power(Vector2 position, int type){
        super(new Vector2(position.x, position.y), new Vector2(64, 64));

        this.type = type;

        super.texture = AssetsManager.getInstance().getTexture("Upgrade" + type);
    }  

    public int getType(){
        return this.type;
    }
}
