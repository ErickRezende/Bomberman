package AssetsManager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AssetsManager extends ApplicationAdapter {
    private static AssetsManager INSTANCE = null;

    private AssetManager assetManager;
    private float stateTime;

    private Map<String, Texture> textures;
    private Map<String, Music> sounds;

    private AssetsManager(){
        this.create();
    }

    @Override
    public void create() {
        assetManager = new AssetManager();
        textures = new HashMap<String, Texture>();
        sounds = new HashMap<String, Music>();

        // Carrega as texturas
        assetManager.load("assets/bomb.png", Texture.class);
        assetManager.load("assets/explosion.png", Texture.class);
        assetManager.load("assets/player.png", Texture.class);
        assetManager.load("assets/enemy.png", Texture.class);
        assetManager.load("assets/block-0.png", Texture.class);
        assetManager.load("assets/block-1.png", Texture.class);
        assetManager.load("assets/block-2.png", Texture.class);
        assetManager.load("assets/playButton.png", Texture.class);
        assetManager.load("assets/upgrade0.png", Texture.class);
        assetManager.load("assets/upgrade1.png", Texture.class);
        assetManager.load("assets/menu.png", Texture.class);
        assetManager.load("assets/jogar.png", Texture.class);
        assetManager.load("assets/sair.png", Texture.class);
        assetManager.load("assets/derrota.png", Texture.class);
        assetManager.load("assets/vitoria.png", Texture.class);
        assetManager.load("assets/vitoria.mp3", Music.class);
        assetManager.load("assets/derrota.mp3", Music.class);
        assetManager.load("assets/abertura.mp3", Music.class);
        assetManager.load("assets/explosao.mp3", Music.class);
        assetManager.load("assets/jogo.mp3", Music.class);
        assetManager.load("assets/aichaves.mp3", Music.class);


        //assetManager.load("musicPath.png", Texture.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("Bomb", assetManager.get("assets/bomb.png", Texture.class));
        textures.put("Boom", assetManager.get("assets/explosion.png", Texture.class));
        textures.put("Player", assetManager.get("assets/player.png", Texture.class));
        textures.put("Enemy", assetManager.get("assets/enemy.png", Texture.class));
        textures.put("Block-0", assetManager.get("assets/block-0.png", Texture.class));
        textures.put("Block-1", assetManager.get("assets/block-1.png", Texture.class));
        textures.put("Block-2", assetManager.get("assets/block-2.png", Texture.class));
        textures.put("PlayButton", assetManager.get("assets/playButton.png", Texture.class));
        textures.put("Upgrade0", assetManager.get("assets/upgrade0.png", Texture.class));
        textures.put("Upgrade1", assetManager.get("assets/upgrade1.png", Texture.class));
        textures.put("Menu", assetManager.get("assets/menu.png", Texture.class));
        textures.put("Vitoria", assetManager.get("assets/vitoria.png", Texture.class));
        textures.put("Derrota", assetManager.get("assets/derrota.png", Texture.class));
        textures.put("Sair", assetManager.get("assets/sair.png", Texture.class));
        textures.put("Jogar", assetManager.get("assets/jogar.png", Texture.class));
        sounds.put("Jogo", assetManager.get("assets/jogo.mp3", Music.class));
        sounds.put("Vitoria1", assetManager.get("assets/vitoria.mp3", Music.class));
        sounds.put("Derrota1", assetManager.get("assets/derrota.mp3", Music.class));
        sounds.put("Explosao", assetManager.get("assets/explosao.mp3", Music.class));
        sounds.put("Abertura", assetManager.get("assets/abertura.mp3", Music.class));
        sounds.put("Aichaves", assetManager.get("assets/aichaves.mp3", Music.class));

        //sounds.put("Name", assetManager.get("musicPath.mp3", Music.class));        
        
        stateTime = 0f;
    }

    public void update(float deltaTime) {
        // Atualiza o tempo de animação
        stateTime += deltaTime;
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public Music getSounds(String key) {
        return sounds.get(key);

    }

    public TextureRegion[][] getTextureRegions(String key, Vector2 size) {
        return TextureRegion.split(
            textures.get(key),
            (int) size.x,
            (int) size.y
        );
    }

    public TextureRegion[][] getTextureRegions(String key) {
        return getTextureRegions(key, new Vector2(48, 48));
    }

    
    public Animation getAnimation(TextureRegion[][] textureRegion, int line, float frameDuration) {
        return (new Animation(frameDuration, textureRegion[line]));
    }

    public TextureRegion getCurrentTRegion(Animation animation) {
        return animation.getKeyFrame(stateTime, true);
    }

    public TextureRegion getCurrentTRegion(Animation animation, boolean looping) {
        return animation.getKeyFrame(stateTime, looping);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public static AssetsManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AssetsManager();
        }

        return INSTANCE;
    }
}