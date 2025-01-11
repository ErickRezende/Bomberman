package GameObjects.Player;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Map.Map;
import Map.Block;
import AssetsManager.AssetsManager;
import GameObjects.Bomb.Bomb;
import GameObjects.base.GameObject;

public class Player extends GameObject {
    private float moveCooldown = 0.1f; // Tempo mínimo entre movimentos (em segundos)
    private float cooldownTimer = 0f;
    private List<Bomb> bombs;

    public Player(Vector2 position) {
        super(position, new Vector2(64, 64)); 

        AssetsManager assetsManager = AssetsManager.getInstance();
        
        super.texture = assetsManager.getTexture("Player");

        bombs = new ArrayList<>();
    }

    
    public void update(float delta) {
        cooldownTimer -= delta; // Reduz o tempo de cooldown baseado no delta time
        if (cooldownTimer > 0) return; // Aguarda o cooldown terminar antes de permitir o movimento

        Map map = Map.getInstance();
        Vector2 newPosition = new Vector2(position); // Nova posição hipotética

        // Movimenta baseado no input
        boolean moved = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newPosition.y += 64;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newPosition.y -= 64;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newPosition.x -= 64;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newPosition.x += 64;
            moved = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Bomb newBomb = new Bomb(new Vector2(position));
            bombs.add(newBomb); // Adiciona a bomba à lista
            System.out.println("Bomba criada na posição: " + position);
        }

        // Checa se a nova posição está em um bloco válido
        if (moved) {
            Block nextBlock = map.getBlockAt(newPosition);
            if (nextBlock != null && nextBlock.type == Block.VOID) {
                position.set(newPosition); 
                cooldownTimer = moveCooldown; // Reseta o cooldown após o movimento
            }
        }
         // Atualiza todas as bombas
        for (Bomb bomb : bombs) {
            bomb.update(delta);
        }

        // Sim, tem que ter outro se seu fiz assim é pq é assim
        // Vou explicar so pq to bonzinho: é por causa do break
        for (Bomb bomb : bombs) {
            if(bomb.isTimeForBoom()){
                bombs.remove(bomb);
                break;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch); // Desenha o jogador

        // Desenha todas as bombas
        for (Bomb bomb : bombs) {
            bomb.draw(batch);
        }
    }
    
}
