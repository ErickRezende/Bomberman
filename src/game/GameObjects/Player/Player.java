package GameObjects.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import AssetsManager.AssetsManager;
import GameObjects.base.GameObject;
 
public class Player extends GameObject{
    AssetsManager assetsManager; 
    private static final float SPEED = 200f;
    private int Playerbombs;

    public Player(Vector2 position){
        super(new Vector2(position.x, position.y), new Vector2(30, 30));

        assetsManager = AssetsManager.getInstance();


        super.texture = assetsManager.getTexture("Player");
    }

    public void update(float delta) {
        move(delta);
    }

    private void move(float delta) {
        float movement = SPEED * delta; // Calcula o deslocamento baseado no tempo delta

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += movement; 
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= movement; 
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= movement; 
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += movement; 
        }

        // Evitar que o jogador saia da tela
        keepInside();
    }

    private void keepInside() {
        // Limite mínimo/máximo para o jogador não sair da tela
        position.x = Math.max(0, Math.min(position.x, Gdx.graphics.getWidth() - size.x));
        position.y = Math.max(0, Math.min(position.y, Gdx.graphics.getHeight() - size.y));
    }

    private void MoreSpeed(){

    }
}
