package AssetsManager;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
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
        //assetManager.load("musicPath.png", Texture.class);

        // Espera que todos os assets sejam carregados
        assetManager.finishLoading();

        // Obter as texturas carregadas
        textures.put("Bomb", assetManager.get("assets/bomb.png", Texture.class));

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
        return TextureRegion.split(textures.get(key),
                /* textures.get(key).getWidth() / 4 */ (int) size.x,
                /* textures.get(key).getHeight() / 6 */(int) size.y);
    }

    public TextureRegion[][] getTextureRegions(String key) {
        return getTextureRegions(key, new Vector2(48, 48));
    }

    
    // Debugar isso aqui pq esse libgdx fumou peda
    /*public Animation<> getAnimation(TextureRegion[][] textureRegion, int line, float frameDuration) {
        return (new Animation<TextureRegion>(frameDuration, textureRegion[line]));
    }

    public TextureRegion getCurrentTRegion(Animation<TextureRegion> animation) {
        return animation.getKeyFrame(stateTime, true);
    }*/

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