package Map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import Settings.Settings;

public class Map extends ApplicationAdapter {
    private static Map INSTANCE = null;
    private Vector2 size;
    private Vector2 position;
    private Vector2 gradeSize;
    private Vector<Vector<Block>> blocks;

    private Map(){
        this.create();
    }

    @Override
    public void create(){
        this.setDimensions();

        this.blocks = new Vector<Vector<Block>>();

        Vector<Vector<Integer>> mapMatrix = this.openMap("map1");

        for (int i = 0; i < gradeSize.x; i++) {
            Vector<Block> line = new Vector<Block>();
            for(int j = 0; j < gradeSize.y; j++){
                line.add(new Block(
                    new Vector2(
                        this.position.x + Settings.BLOCKS_SIZE * i,
                        this.position.y + Settings.BLOCKS_SIZE * j
                    ),
                    new Vector2(
                        i,
                        j
                    ),
                    (mapMatrix.get(i)).get(j)
                ));

            }
            this.blocks.add(line);
        }
    }
    public Block getBlockAt(Vector2 position) {
        // Converte posição para coordenadas na grade
        int gridX = (int) ((position.x - this.position.x) / Settings.BLOCKS_SIZE);
        int gridY = (int) ((position.y - this.position.y) / Settings.BLOCKS_SIZE);

        // Verifica se a posição está dentro dos limites da grade
        if (gridX >= 0 && gridX < gradeSize.x && gridY >= 0 && gridY < gradeSize.y) {
            return blocks.get(gridX).get(gridY); // Retorna o bloco na posição
        }
        return null; // Fora dos limites
    }

    private void setDimensions() {
        int small;

        if(Settings.WINDOW_HEIGHT <= Settings.WINDOW_WIDTH) {
            small = Settings.WINDOW_HEIGHT;
        } else {
            small = Settings.WINDOW_WIDTH;
        }

        this.size = new Vector2(
            ((int)(small / Settings.BLOCKS_SIZE) - 1) * Settings.BLOCKS_SIZE,
            ((int)(small / Settings.BLOCKS_SIZE) - 1) * Settings.BLOCKS_SIZE
        );

        this.position = new Vector2(
            (int)((Settings.WINDOW_WIDTH / 2) - (this.size.x / 2)),
            (int)((Settings.WINDOW_HEIGHT / 2) - (this.size.y / 2))
        );

        this.gradeSize = new Vector2(
            (int)(this.size.x / Settings.BLOCKS_SIZE),
            (int)(this.size.y / Settings.BLOCKS_SIZE )
        );
    }

    public void draw(SpriteBatch batch){
        for(Vector<Block> line : this.blocks){
            for(Block block : line){
                block.draw(batch);
            }
        }
    }

    public void update(){
        for(Vector<Block> line : this.blocks){
            for(Block block : line){
                block.update();
            }
        }
    }

    public Vector<Vector<Integer>> openMap(String mapName) {
        String mapPath = "maps/" + mapName + ".map";
        BufferedReader buffer = null;
        String line = "";

        Vector<Vector<Integer>> mapMatrix = new Vector<Vector<Integer>>();

        try {

            buffer = new BufferedReader(new FileReader(mapPath));
            while ((line = buffer.readLine()) != null) {

                String[] splitedLine = line.split(",");
                
                Vector<Integer> integerLine = new Vector<Integer>();
                
                for(String field : splitedLine){
                    integerLine.add(Integer.parseInt(field));
                }

                mapMatrix.add(integerLine);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return mapMatrix;
    }

    @Override
    public void dispose(){/* Nothing to dispose */}

    public static Map getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Map();
        }

        return INSTANCE;
    }
}
