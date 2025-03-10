package GameObjects.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;


public abstract class GameObject {
    protected Vector2 position;
    protected Vector2 size;
    
    protected TextureRegion[][] textureRegions;
    protected Animation animation;
    protected TextureRegion currentTRegion;

    protected Texture texture;

    public GameObject(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
    }

    public void update(){ }

    public void draw(SpriteBatch batch) {
        batch.draw(this.texture, this.position.x, this.position.y, (int)this.size.x, (int)this.size.y);
    }

    public void update(float deltaTime) {
        this.currentTRegion = AssetsManager.getInstance().getCurrentTRegion(animation);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getSize() {
        return this.size;
    }

    public TextureRegion getCurrentTRegion() {
        return this.currentTRegion;
    }

    public boolean collide(GameObject otherObj){
        return this.collide(otherObj.getPosition(), otherObj.getSize());
    }

    public boolean collide(Vector2 otherPos, Vector2 otherSize){
        if(
            otherPos.x < this.position.x + this.size.x &&
            otherPos.x + otherSize.x > this.position.x &&
            otherPos.y < this.position.y + this.size.y &&
            otherPos.y + otherSize.x > this.position.y
        ){
            return true;
        }

        return false;
    }

    public void dispose() {/* Nothing to dispose */}
}
