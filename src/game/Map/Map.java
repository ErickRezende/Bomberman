package Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import GameObjects.Bomb.Bomb;
import Settings.Settings;

public class Map extends ApplicationAdapter {
    private static Map INSTANCE = null;
    private Vector2 size;
    private Vector2 position;

    private Bomb bomb;

    Texture texture;

    private Map(){
        this.create();
    }

    @Override
    public void create(){
        this.setDimensions();

        this.bomb = new Bomb(new Vector2(100, 100));

        this.texture = new Texture("assets/a.png");
    }

    private void setDimensions() {
        int small;

        if(Settings.WINDOW_HEIGHT <= Settings.WINDOW_WIDTH) {
            small = Settings.WINDOW_HEIGHT;
        } else {
            small = Settings.WINDOW_WIDTH;
        }

        this.size = new Vector2(
            (int)(small * 0.8f),
            (int)(small * 0.8f)
        );

        this.position = new Vector2(
            (int)((Settings.WINDOW_WIDTH / 2) - (this.size.x / 2)),
            (int)((Settings.WINDOW_HEIGHT / 2) - (this.size.y / 2))
        );

        System.out.println("\nwindow: " + Settings.WINDOW_WIDTH + ", " + Settings.WINDOW_HEIGHT + "\nsize: " + this.size.x + ", " + this.size.y + "\nposition: " + this.position.x + ", " + this.position.y);
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, this.position.x, this.position.y, 500, 500);
        bomb.draw(batch);
    }

    public void update(){
        bomb.update();
    }

    @Override
    public void dispose(){
    }

    public static Map getIntance() {
        if(INSTANCE == null){
            INSTANCE = new Map();
        }

        return INSTANCE;
    }
}
