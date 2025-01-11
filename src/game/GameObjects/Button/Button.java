package GameObjects.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;
import Settings.Settings;

public class Button extends GameObject{
    public static final float CENTER = 0.5f;
    public static final float BOTTOM = 0.0f, LEFT = 0.0f;
    public static final float RIGHT = 1.0f, TOP = 1.0f;
    
    public Button(Vector2 size, float xRelativePos, float yRelativePos, String texture){
        this(
            new Vector2(
                (int)(Settings.WINDOW_WIDTH * xRelativePos),
                (int)(Settings.WINDOW_HEIGHT * yRelativePos)
            ),
            size, 
            texture
        );
    }

    public Button(Vector2 position, Vector2 size, String texture){
        super(
            new Vector2(
                (int)(position.x - size.x / 2),
                (int)(position.y - size.y / 2)
            ),
            size
        );

        super.texture = AssetsManager.getInstance().getTexture(texture);
    }

    @Override
    public void update() {
        if(Gdx.input.isTouched())
            System.out.println(Gdx.input.getX() + ", " + Gdx.input.getY());
    }
}
