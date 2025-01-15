package GameObjects.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Map.Map;
import Settings.Settings;
import Map.Block;
import AssetsManager.AssetsManager;
import GameObjects.Bomb.Bomb;
import GameObjects.Explosion.Explosion;
import GameObjects.base.GameObject;

public class Player extends GameObject {
    private float moveCooldown = 0.05f; // Tempo mínimo entre movimentos (em segundos)
    private float cooldownTimer = 0f;
    private List<Bomb> bombs;
    private List<Explosion> booms;

    private int directionSprite = 0;

    private int power = 2;

    public Player(Vector2 position) {
        super(position, new Vector2(64, 64)); 

        AssetsManager assetsManager = AssetsManager.getInstance();

        super.textureRegions = assetsManager.getTextureRegions("Player", this.size);
        super.animation = assetsManager.getAnimation(textureRegions, 0, 0.20f);
        super.currentTRegion = assetsManager.getCurrentTRegion(animation);

        bombs = new ArrayList<>();
        booms = new ArrayList<>();
    }

    public void move(float delta){
        cooldownTimer -= delta; // Reduz o tempo de cooldown baseado no delta time
        if (cooldownTimer > 0) return; // Aguarda o cooldown terminar antes de permitir o movimento

        Map map = Map.getInstance();
        Vector2 newPosition = new Vector2(position); // Nova posição hipotética

        // Movimenta baseado no input
        boolean moved = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newPosition.y += Settings.BLOCKS_SIZE / 4;
            moved = true;

            this.directionSprite = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newPosition.y -= Settings.BLOCKS_SIZE / 4;
            moved = true;

            this.directionSprite = 0;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newPosition.x -= Settings.BLOCKS_SIZE / 4;
            moved = true;

            this.directionSprite = 3;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newPosition.x += Settings.BLOCKS_SIZE / 4;
            moved = true;

            this.directionSprite = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            Bomb newBomb = new Bomb(map.roundToGrid(this.position), this.power);

            if(this.bombs.size() <= 1) {
                bombs.add(newBomb); // Adiciona a bomba à lista
            }
        }

        for(Bomb bomb : bombs) {
            if(bomb.getPosition().x == newPosition.x && bomb.getPosition().y == newPosition.y){
                moved = false;
            }
        }

        // Checa se a nova posição está em um bloco válido
        if (moved) {
            boolean collide = false;

            for(Vector<Block> i : map.getBlocks()){
                for(Block j : i){
                    if(j.getType() != Block.VOID && j.collide(newPosition, this.size)){
                        collide = true;
                    }
                }
            }

            if(newPosition.x < map.getBlock(new Vector2(0, 0)).getPosition().x || newPosition.y < map.getBlock(new Vector2(0, 0)).getPosition().y || newPosition.x > map.getLastBlock().getPosition().x || newPosition.y > map.getLastBlock().getPosition().y){
                collide = true;
            }
            
            if (!collide) {
                position.set(newPosition); 
                cooldownTimer = moveCooldown; // Reseta o cooldown após o movimento
            }

            super.animation = AssetsManager.getInstance().getAnimation(textureRegions, directionSprite, 0.20f);
            super.update(delta);
        }  else if (this.currentTRegion != this.textureRegions[this.directionSprite][0]){
            this.currentTRegion = this.textureRegions[this.directionSprite][0];
        }
    }

    public int getPower(){
        return this.power;
    }

    public void setPower(int newPower) {
        this.power = newPower;
    }
    
    public float getCooldown(){
        return this.moveCooldown;
    }

    public void setCooldown(float cooldown){
        this.moveCooldown = cooldown;
    }

    public List<Bomb> getBomb() {
        return this.bombs;
    }

    public void update(float delta) {
        this.move(delta);

        // Atualiza todas as bombas
        for (Bomb bomb : bombs) {
            bomb.update(delta);
        }

        // Sim, tem que ter outro se seu fiz assim é pq é assim
        // Vou explicar so pq to bonzinho: é por causa do break
        for (Bomb bomb : bombs) {
            if(bomb.isTimeForBoom()){
                booms.add(new Explosion(bomb.getPosition(), bomb.getPower()));
                bombs.remove(bomb);
                break;
            }
        }

        // Atualiza todas as explosoes
        for (Explosion boom : booms) {
            boom.update(delta);
        }

        for (Explosion boom : booms) {
            if(boom.animationEnds()){
                booms.remove(boom);
                break;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.currentTRegion, this.position.x, this.position.y, (int)this.size.x, (int)this.size.y); 

        // Desenha todas as bombas
        for (Bomb bomb : bombs) {
            bomb.draw(batch);
        }

        // Desenha todas as bombas
        for (Explosion boom : booms) {
            boom.draw(batch);
        }
    }
    
}
