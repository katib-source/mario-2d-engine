package com.mario.model.level;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.Inflater;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Rectangle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mario.model.entity.Coin;
import com.mario.model.entity.EndTrigger;
import com.mario.model.entity.Entity;
import com.mario.model.entity.EntityFactory;
import com.mario.model.entity.Goomba;
import com.mario.model.entity.Player;


public class LevelLoader {
    private final Gson gson;
    private final EntityFactory entityFactory;
    
    public LevelLoader() {
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LevelData.Layer.class, new LayerDeserializer())
            .create();
        this.entityFactory = EntityFactory.getInstance();
    }

    private static class LayerDeserializer implements JsonDeserializer<LevelData.Layer> {
        @Override
        public LevelData.Layer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            LevelData.Layer layer = new LevelData.Layer();

            var obj = json.getAsJsonObject();

            
            if (obj.has("name")) layer.setName(obj.get("name").getAsString());
            if (obj.has("type")) layer.setType(obj.get("type").getAsString());
            if (obj.has("visible")) layer.setVisible(obj.get("visible").getAsBoolean());
            if (obj.has("width")) layer.setWidth(obj.get("width").getAsInt());
            if (obj.has("height")) layer.setHeight(obj.get("height").getAsInt());
            if (obj.has("encoding")) layer.setEncoding(obj.get("encoding").getAsString());
            if (obj.has("compression")) layer.setCompression(obj.get("compression").getAsString());

            if (obj.has("data")) {
                JsonElement dataElement = obj.get("data");
                if (dataElement.isJsonPrimitive() && dataElement.getAsJsonPrimitive().isString()) {
                    layer.setDataString(dataElement.getAsString());
                } else if (dataElement.isJsonArray()) {
                    var dataArray = dataElement.getAsJsonArray();
                    int[] data = new int[dataArray.size()];
                    for (int i = 0; i < dataArray.size(); i++) {
                        data[i] = dataArray.get(i).getAsInt();
                    }
                    layer.setData(data);
                }
            }

            if (obj.has("objects")) {
                var objectsArray = obj.get("objects").getAsJsonArray();
                List<LevelData.TiledObject> objects = new ArrayList<>();
                for (JsonElement objElement : objectsArray) {
                    LevelData.TiledObject tiledObj = context.deserialize(objElement, LevelData.TiledObject.class);
                    objects.add(tiledObj);
                }
                layer.setObjects(objects);
            }

            return layer;
        }
    }

    public Level loadLevel(String levelPath) {
        // Detect file type and use appropriate loader
        if (levelPath.endsWith(".tmx")) {
            System.out.println("Loading TMX file: " + levelPath);
            return loadTmxLevel(levelPath);
        } else {
            System.out.println("Loading JSON file: " + levelPath);
            return loadJsonLevel(levelPath);
        }
    }

    private Level loadJsonLevel(String levelPath) {
        FileHandle file = Gdx.files.internal(levelPath);
        String jsonContent = file.readString();
        
        LevelData levelData = gson.fromJson(jsonContent, LevelData.class);
        
        return createLevelFromData(levelData);
    }

    private Level loadTmxLevel(String levelPath) {
        try {
            com.badlogic.gdx.maps.tiled.TmxMapLoader tmxLoader = new com.badlogic.gdx.maps.tiled.TmxMapLoader();
            com.badlogic.gdx.maps.tiled.TiledMap tiledMap = tmxLoader.load(levelPath);
            
            com.badlogic.gdx.maps.MapProperties mapProps = tiledMap.getProperties();
            int mapWidth = mapProps.get("width", Integer.class);
            int mapHeight = mapProps.get("height", Integer.class);
            int tileWidth = mapProps.get("tilewidth", Integer.class);
            int tileHeight = mapProps.get("tileheight", Integer.class);
            
            Level level = new Level(mapWidth, mapHeight, tileWidth, tileHeight);
            
            level.setTiledMap(tiledMap);
            
            // Process each layer
            for (com.badlogic.gdx.maps.MapLayer layer : tiledMap.getLayers()) {
                String layerName = layer.getName().toLowerCase();
                System.out.println("Processing layer: " + layer.getName() + " (lowercase: " + layerName + ")");
                
                // Tile layers
                if (layer instanceof com.badlogic.gdx.maps.tiled.TiledMapTileLayer) {
                    loadTmxTileLayerCollision((com.badlogic.gdx.maps.tiled.TiledMapTileLayer) layer, level);
                }
                // Object layers 
                else {
                    if (layerName.contains("player") || layerName.contains("spawn")) {
                        loadTmxPlayerLayer(layer, level);
                    } else if (layerName.contains("enemies") || layerName.contains("goomba") || layerName.contains("turtle")) {
                        loadTmxEnemyLayer(layer, level);
                    } else if (layerName.contains("coin")) {
                        loadTmxCoinLayer(layer, level);
                    } else if (layerName.contains("ground") || layerName.contains("collision")) {
                        loadTmxCollisionLayer(layer, level);
                    } else if (layerName.equals("end")) {
                        loadTmxEndTrigger(layer, level);
                    }
                }
            }
            
            System.out.println("TMX map loaded successfully: " + mapWidth + "x" + mapHeight);
            return level;
        } catch (Exception e) {
            System.err.println("Failed to load TMX file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load TMX file: " + levelPath, e);
        }
    }
    
    private void loadTmxPlayerLayer(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        System.out.println("Loading player from layer: " + layer.getName());
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();
                Entity player = entityFactory.createFromRectangle("player", rect);
                if (player != null) {
                    level.addEntity(player);
                    System.out.println("Player spawned at: " + rect.x + ", " + rect.y);
                }
                break;
            }
        }
    }
    
    private void loadTmxEnemyLayer(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        int enemyCount = 0;
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();
                Entity enemy = entityFactory.createFromRectangle("goomba", rect);
                if (enemy != null) {
                    level.addEntity(enemy);
                    enemyCount++;
                }
            }
        }
        System.out.println("Loaded " + enemyCount + " enemies from layer: " + layer.getName());
    }
    
    private void loadTmxCoinLayer(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        int coinCount = 0;
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();
                Entity coin = entityFactory.createFromRectangle("coin", rect);
                if (coin != null) {
                    level.addEntity(coin);
                    coinCount++;
                }
            }
        }
        System.out.println("Loaded " + coinCount + " coins from layer: " + layer.getName());
    }
    
    private void loadTmxCollisionLayer(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        int objectCount = 0;
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();
                level.getSolidTiles().add(rect);
                objectCount++;
            }
        }
        System.out.println("Loaded " + objectCount + " collision objects from layer: " + layer.getName());
    }
    
    private void loadTmxEndTrigger(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();      
                EntityFactory.EntityProperties properties = new EntityFactory.EntityProperties();
                Object nextLevelProp = object.getProperties().get("nextLevel");
                if (nextLevelProp != null) {
                    properties.setNextLevel(nextLevelProp.toString());
                }
                EndTrigger endTrigger = entityFactory.createEndTriggerFromRectangle(rect, properties);
                level.setEndTrigger(endTrigger);
                System.out.println("End trigger loaded at: " + rect.x + ", " + rect.y + 
                    " (" + rect.width + "x" + rect.height + ")" +
                    (endTrigger.hasNextLevel() ? " -> " + endTrigger.getNextLevel() : ""));
                break;
            }
        }
    }

    private void loadTmxTileLayerCollision(com.badlogic.gdx.maps.tiled.TiledMapTileLayer tileLayer, Level level) {
        int blockedTileCount = 0;
        int layerWidth = tileLayer.getWidth();
        int layerHeight = tileLayer.getHeight();
        int tileWidth = (int) tileLayer.getTileWidth();
        int tileHeight = (int) tileLayer.getTileHeight();
        
        for (int x = 0; x < layerWidth; x++) {
            for (int y = 0; y < layerHeight; y++) {
                com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    com.badlogic.gdx.maps.MapProperties tileProps = cell.getTile().getProperties();
                    
                    // Check if this tile has the blocked property
                    if (tileProps.containsKey("blocked")) {
                        // Create collision rectangle for this tile
                        float tileX = x * tileWidth;
                        float tileY = y * tileHeight;
                        Rectangle collisionRect = new Rectangle(tileX, tileY, tileWidth, tileHeight);
                        level.getSolidTiles().add(collisionRect);
                        blockedTileCount++;
                    }
                }
            }
        }
        
        if (blockedTileCount > 0) {
            System.out.println("Loaded " + blockedTileCount + " blocked tiles from layer: " + tileLayer.getName());
        }
    }

    private Level createLevelFromData(LevelData levelData) {
        Level level = new Level(
            levelData.getWidth(),
            levelData.getHeight(),
            levelData.getTilewidth(),
            levelData.getTileheight()
        );
        
        // Stocker les tilesets dans le niveau
        if (levelData.getTilesets() != null) {
            level.setTilesets(levelData.getTilesets());
        }
        
        // Charger les layers
        for (LevelData.Layer layer : levelData.getLayers()) {
            if (layer.getType().equals("tilelayer")) {
                int[] tileData = decompressTileData(layer);
                if (tileData.length > 0) {
                    level.addTileLayer(layer.getName(), tileData);
                }
            } else if (layer.getType().equals("objectgroup")) {
                String layerName = layer.getName().toLowerCase();

                if (layerName.contains("entities") || layerName.contains("goombas") ||
                    layerName.contains("turtles") || layerName.contains("coins")) {
                    loadEntitiesFromLayer(layer, level, levelData);
                } else if (layerName.contains("ground") || layerName.contains("pipes") ||
                          layerName.contains("bricks")) {
                    loadCollisionFromObjectLayer(layer, level, levelData);
                }
            }
        }
        
        return level;
    }
    
    private void loadEntitiesFromLayer(LevelData.Layer layer, Level level, LevelData levelData) {
        if (layer.getObjects() == null) return;
        
        String layerName = layer.getName().toLowerCase();

        for (LevelData.TiledObject obj : layer.getObjects()) {
            // Déterminer le type en fonction du nom de la couche si le type n'est pas défini
            String entityType = obj.getType();
            if (entityType == null || entityType.isEmpty()) {
                if (layerName.contains("goombas")) {
                    entityType = "goomba";
                } else if (layerName.contains("turtles")) {
                    entityType = "turtle";
                } else if (layerName.contains("coins")) {
                    entityType = "coin";
                } else if (obj.getName() != null && !obj.getName().isEmpty()) {
                    // Vérifier le nom de l'objet
                    String objName = obj.getName().toLowerCase();
                    if (objName.contains("mario") || objName.contains("player")) {
                        entityType = "player";
                    }
                }
            }

            Entity entity = createEntityFromObject(obj, levelData, entityType);
            if (entity != null) {
                level.addEntity(entity);
            }
        }
        
        System.out.println("Loaded entities from layer '" + layer.getName() + "': " + 
                          (layer.getObjects() != null ? layer.getObjects().size() : 0) + " objects");
    }
    

    private Entity createEntityFromObject(LevelData.TiledObject obj, LevelData levelData, String entityType) {
        if (entityType == null || entityType.isEmpty()) {
            System.out.println("Objet sans type ignoré: " + obj.getName());
            return null;
        }

        float x = obj.getX();
        float levelHeightInPixels = levelData.getHeight() * levelData.getTileheight();
        float y = levelHeightInPixels - obj.getY() - obj.getHeight();

        EntityFactory.EntityProperties properties = new EntityFactory.EntityProperties();
        int scoreValue = getIntProperty(obj, "scoreValue", 10);
        properties.setScoreValue(scoreValue);

        return entityFactory.createEntity(entityType, x, y, obj.getWidth(), obj.getHeight(), properties);
    }
    

    private void loadCollisionFromObjectLayer(LevelData.Layer layer, Level level, LevelData levelData) {
        if (layer.getObjects() == null) return;
        
        float levelHeightInPixels = levelData.getHeight() * levelData.getTileheight();
        
        int objectsLoaded = 0;
        for (LevelData.TiledObject obj : layer.getObjects()) {
            // Skip objects with zero height or width
            if (obj.getWidth() <= 0 || obj.getHeight() <= 0) continue;
            
            // Convertir les coordonnées Y de Tiled vers LibGDX
        
            float x = obj.getX();
            float tiledY = obj.getY();
            float objectHeight = obj.getHeight();
            
            // Convert: LibGDX Y = (levelHeight - TiledY)
    
            float y = levelHeightInPixels - tiledY;
            
            // Créer un rectangle de collision
            Rectangle collisionRect = new Rectangle(x, y - objectHeight, obj.getWidth(), objectHeight);
            level.getSolidTiles().add(collisionRect);
            objectsLoaded++;
            
            // Debug: Print first few collision rects
            if (objectsLoaded <= 3) {
                System.out.println("  Tiled: y=" + tiledY + ", h=" + objectHeight + " -> LibGDX: y=" + (y - objectHeight) + ", h=" + objectHeight);
            }
        }
        
        System.out.println("Loaded " + objectsLoaded + " collision objects from layer: " + layer.getName());
    }

    private int getIntProperty(LevelData.TiledObject obj, String propertyName, int defaultValue) {
        if (obj.getProperties() == null) return defaultValue;
        
        for (LevelData.Property prop : obj.getProperties()) {
            if (prop.getName().equals(propertyName)) {
                if (prop.getValue() instanceof Number) {
                    return ((Number) prop.getValue()).intValue();
                }
            }
        }
        return defaultValue;
    }

    private int[] decompressTileData(LevelData.Layer layer) {
        // Si les données sont déjà un tableau, les retourner directement
        if (layer.getData() != null && layer.getData().length > 0) {
            return layer.getData();
        }

        // Sinon, décompresser depuis la chaîne encodée
        String dataString = layer.getDataString();
        String compression = layer.getCompression();

        if (dataString == null || dataString.isEmpty()) {
            System.out.println("Pas de données de tiles pour la couche: " + layer.getName());
            return new int[0];
        }

        try {
            // Décoder Base64
            byte[] decodedData = Base64.getDecoder().decode(dataString);

            // Décompresser si nécessaire
            if ("zlib".equals(compression)) {
                Inflater inflater = new Inflater();
                inflater.setInput(decodedData);
                byte[] decompressedData = new byte[layer.getWidth() * layer.getHeight() * 4];
                inflater.inflate(decompressedData);
                inflater.end();
                decodedData = decompressedData;
            }

            // Convertir les bytes en tableau d'entiers (little-endian)
            int tileCount = layer.getWidth() * layer.getHeight();
            int[] tileData = new int[tileCount];
            for (int i = 0; i < tileCount; i++) {
                int offset = i * 4;
                tileData[i] = (decodedData[offset] & 0xFF) |
                             ((decodedData[offset + 1] & 0xFF) << 8) |
                             ((decodedData[offset + 2] & 0xFF) << 16) |
                             ((decodedData[offset + 3] & 0xFF) << 24);
            }

            System.out.println("Décompressé " + tileCount + " tiles pour la couche: " + layer.getName());
            return tileData;

        } catch (Exception e) {
            System.err.println("Erreur lors de la décompression des données de tiles: " + e.getMessage());
            e.printStackTrace();
            return new int[0];
        }
    }
}
