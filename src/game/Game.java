import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import AssetsManager.AssetsManager;
import Map.Map;
import GameObjects.Player.Player;
import com.badlogic.gdx.math.Vector2;

public class Game extends ApplicationAdapter {
    private static ApplicationAdapter INSTANCE = null;

    private Map map;
    private SpriteBatch batch;
    private AssetsManager assetsManager;
    private Player player; // Adiciona o jogador

    private Game() {}

    @Override
    public void create() {
        assetsManager = AssetsManager.getInstance();

        this.map = Map.getIntance();
        this.batch = new SpriteBatch();

        // Inicializa o jogador
        this.player = new Player(new Vector2(200, 200)); // Posição inicial (100, 100)
    }

    public void update(float deltaTime) {
        // Atualiza assets, mapa e jogador
        this.assetsManager.update(deltaTime);
        this.map.update();
        this.player.update(deltaTime);
    }

    public void draw() {
        batch.begin();
        {
            this.map.draw(batch); // Desenha o mapa
            this.player.draw(batch); // Desenha o jogador
        }
        batch.end();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime(); // Calcula o delta time
        this.update(delta); // Atualiza com delta time

        // Limpa a tela com uma cor preta
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.draw(); // Desenha os elementos do jogo
    }

    @Override
    public void resize(int width, int height) {
        /* Nothing to do */
    }

    @Override
    public void pause() {
        /* Nothing to do */
    }

    @Override
    public void resume() {
        /* Nothing to do */
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.map.dispose();
        this.assetsManager.dispose();
        this.player.dispose(); // Libera recursos do jogador
    }

    public static ApplicationAdapter getIntance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }

        return INSTANCE;
    }
}
