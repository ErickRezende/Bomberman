package Map;

import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;
import Settings.Settings;

public class Block extends GameObject{
    public static final int VOID = 0;
    public static final int BOX = 1;
    public static final int WALL = 2;

    int type;
    Vector2 gradePos;

    public Block(Vector2 position, Vector2 gradePos, int type){
        super(position, new Vector2(Settings.BLOCKS_SIZE, Settings.BLOCKS_SIZE));

        this.gradePos = gradePos;
        this.setType(type);
    }

    public void setType(int type) {
        this.type = type;
        super.texture = (AssetsManager.getInstance()).getTexture("Block-" + type);
    }
}
