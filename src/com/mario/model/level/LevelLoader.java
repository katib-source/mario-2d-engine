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
import com.mario.model.entity.Entity;
import com.mario.model.entity.Goomba;
import com.mario.model.entity.Player;

/**
 * Classe responsable du chargement des niveaux depuis les fichiers JSON Tiled
 * Pattern: Factory - Crée des entités à partir des données JSON
 */
public class LevelLoader {
    private Gson gson;
    
    public LevelLoader() {
        // Créer un GSON avec un désérialiseur personnalisé pour les couches
        this.gson = new GsonBuilder()
            .registerTypeAdapter(LevelData.Layer.class, new LayerDeserializer())
            .create();
    }

    /**
     * Désérialiseur personnalisé pour gérer les données de couche
     * qui peuvent être soit un tableau d'entiers, soit une chaîne base64
     */
    private static class LayerDeserializer implements JsonDeserializer<LevelData.Layer> {
        @Override
        public LevelData.Layer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            LevelData.Layer layer = new LevelData.Layer();

            var obj = json.getAsJsonObject();

            // Désérialiser les champs standard
            if (obj.has("name")) layer.setName(obj.get("name").getAsString());
            if (obj.has("type")) layer.setType(obj.get("type").getAsString());
            if (obj.has("visible")) layer.setVisible(obj.get("visible").getAsBoolean());
            if (obj.has("width")) layer.setWidth(obj.get("width").getAsInt());
            if (obj.has("height")) layer.setHeight(obj.get("height").getAsInt());
            if (obj.has("encoding")) layer.setEncoding(obj.get("encoding").getAsString());
            if (obj.has("compression")) layer.setCompression(obj.get("compression").getAsString());

            // Gérer le champ "data" qui peut être un tableau ou une chaîne
            if (obj.has("data")) {
                JsonElement dataElement = obj.get("data");
                if (dataElement.isJsonPrimitive() && dataElement.getAsJsonPrimitive().isString()) {
                    // Données compressées en base64
                    layer.setDataString(dataElement.getAsString());
                } else if (dataElement.isJsonArray()) {
                    // Données non compressées (tableau d'entiers)
                    var dataArray = dataElement.getAsJsonArray();
                    int[] data = new int[dataArray.size()];
                    for (int i = 0; i < dataArray.size(); i++) {
                        data[i] = dataArray.get(i).getAsInt();
                    }
                    layer.setData(data);
                }
            }

            // Gérer les objets pour les object layers
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
    
    /**
     * Charge un niveau depuis un fichier JSON ou TMX
     * @param levelPath Chemin vers le fichier du niveau
     * @return Le niveau chargé
     */
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
    
    /**
     * Charge un niveau depuis un fichier JSON
     * @param levelPath Chemin vers le fichier JSON du niveau
     * @return Le niveau chargé
     */
    private Level loadJsonLevel(String levelPath) {
        FileHandle file = Gdx.files.internal(levelPath);
        String jsonContent = file.readString();
        
        LevelData levelData = gson.fromJson(jsonContent, LevelData.class);
        
        return createLevelFromData(levelData);
    }
    
    /**
     * Charge un niveau depuis un fichier TMX (Tiled Map Editor)
     * @param levelPath Chemin vers le fichier TMX du niveau
     * @return Le niveau chargé
     */
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
            
            // Store the TiledMap reference for rendering
            level.setTiledMap(tiledMap);
            
            // Process each layer
            for (com.badlogic.gdx.maps.MapLayer layer : tiledMap.getLayers()) {
                String layerName = layer.getName().toLowerCase();
                System.out.println("Processing layer: " + layer.getName() + " (lowercase: " + layerName + ")");
                
                // Tile layers - check for blocked tiles
                if (layer instanceof com.badlogic.gdx.maps.tiled.TiledMapTileLayer) {
                    loadTmxTileLayerCollision((com.badlogic.gdx.maps.tiled.TiledMapTileLayer) layer, level);
                }
                // Object layers - for entities and collision
                else {
                    if (layerName.contains("player") || layerName.contains("spawn")) {
                        loadTmxPlayerLayer(layer, level);
                    } else if (layerName.contains("enemies") || layerName.contains("goomba") || layerName.contains("turtle")) {
                        loadTmxEnemyLayer(layer, level);
                    } else if (layerName.contains("coin")) {
                        loadTmxCoinLayer(layer, level);
                    } else if (layerName.contains("ground") || layerName.contains("collision")) {
                        loadTmxCollisionLayer(layer, level);
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
                Player player = new Player(rect.x, rect.y);
                level.addEntity(player);
                System.out.println("Player spawned at: " + rect.x + ", " + rect.y);
                break;
            }
        }
    }
    
    private void loadTmxEnemyLayer(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        int enemyCount = 0;
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();
                level.addEntity(new Goomba(rect.x, rect.y));
                enemyCount++;
            }
        }
        System.out.println("Loaded " + enemyCount + " enemies from layer: " + layer.getName());
    }
    
    private void loadTmxCoinLayer(com.badlogic.gdx.maps.MapLayer layer, Level level) {
        int coinCount = 0;
        for (com.badlogic.gdx.maps.MapObject object : layer.getObjects()) {
            if (object instanceof com.badlogic.gdx.maps.objects.RectangleMapObject) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) object).getRectangle();
                level.addEntity(new Coin(rect.x, rect.y));
                coinCount++;
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
    
    /**
     * Load collision from tiles that have the "blocked" property
     * @param tileLayer The tile layer to scan
     * @param level The level to add collision to
     */
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
                    
                    // Check if this tile has the "blocked" property
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
    
    /**
     * Crée un objet Level à partir des données JSON
     * @param levelData Données du niveau
     * @return Le niveau créé
     */
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
                // Couche de tiles (terrain) - décompresser si nécessaire
                int[] tileData = decompressTileData(layer);
                if (tileData.length > 0) {
                    level.addTileLayer(layer.getName(), tileData);
                }
            } else if (layer.getType().equals("objectgroup")) {
                // Couche d'objets (entités)
                String layerName = layer.getName().toLowerCase();

                // Gérer les différents types de couches d'objets
                if (layerName.contains("entities") || layerName.contains("goombas") ||
                    layerName.contains("turtles") || layerName.contains("coins")) {
                    loadEntitiesFromLayer(layer, level, levelData);
                } else if (layerName.contains("ground") || layerName.contains("pipes") ||
                          layerName.contains("bricks")) {
                    // Ces couches contiennent de la géométrie de collision
                    loadCollisionFromObjectLayer(layer, level, levelData);
                }
            }
        }
        
        return level;
    }
    
    /**
     * Charge les entités depuis une couche d'objets
     * Pattern: Factory Method - Crée différents types d'entités
     * @param layer Couche d'objets
     * @param level Niveau où ajouter les entités
     * @param levelData Données du niveau pour la conversion de coordonnées
     */
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
    
    /**
     * Crée une entité à partir d'un objet Tiled
     * Pattern: Factory Method - Permet l'extensibilité
     * @param obj Objet Tiled
     * @param levelData Données du niveau pour la conversion de coordonnées
     * @param entityType Type d'entité à créer
     * @return L'entité créée
     */
    private Entity createEntityFromObject(LevelData.TiledObject obj, LevelData levelData, String entityType) {
        if (entityType == null || entityType.isEmpty()) {
            System.out.println("Objet sans type ignoré: " + obj.getName());
            return null;
        }

        String type = entityType.toLowerCase();

        // Convertir les coordonnées Y de Tiled (top-left origin) vers LibGDX (bottom-left origin)
        // Dans Tiled, Y augmente vers le bas. Dans LibGDX, Y augmente vers le haut.
        float x = obj.getX();
        float levelHeightInPixels = levelData.getHeight() * levelData.getTileheight();
        float y = levelHeightInPixels - obj.getY() - obj.getHeight();

        switch (type) {
            case "player":
                return new Player(x, y);
                
            case "coin":
                int scoreValue = getIntProperty(obj, "scoreValue", 10);
                return new Coin(x, y, scoreValue);
                
            case "goomba":
                return new Goomba(x, y);

            case "turtle":
            case "koopa":
                // Pour l'instant on crée un Goomba, vous pourrez créer une classe Turtle plus tard
                System.out.println("Turtle créé comme Goomba (implémentez la classe Turtle pour le support complet)");
                return new Goomba(x, y);

            default:
                System.out.println("Type d'entité inconnu: " + type);
                return null;
        }
    }
    
    /**
     * Charge la géométrie de collision depuis une couche d'objets
     * @param layer Couche d'objets
     * @param level Niveau où ajouter la géométrie
     * @param levelData Données du niveau pour la conversion de coordonnées
     */
    private void loadCollisionFromObjectLayer(LevelData.Layer layer, Level level, LevelData levelData) {
        if (layer.getObjects() == null) return;
        
        float levelHeightInPixels = levelData.getHeight() * levelData.getTileheight();
        
        int objectsLoaded = 0;
        for (LevelData.TiledObject obj : layer.getObjects()) {
            // Skip objects with zero height or width
            if (obj.getWidth() <= 0 || obj.getHeight() <= 0) continue;
            
            // Convertir les coordonnées Y de Tiled vers LibGDX
            // In Tiled: Y=0 is top, Y increases downward
            // In LibGDX: Y=0 is bottom, Y increases upward
            // We need to flip the Y coordinate
            float x = obj.getX();
            float tiledY = obj.getY();
            float objectHeight = obj.getHeight();
            
            // Convert: LibGDX Y = (levelHeight - TiledY)
            // The top of the object in Tiled becomes the bottom in LibGDX
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
    
    /**
     * Récupère une propriété entière depuis un objet Tiled
     * @param obj Objet Tiled
     * @param propertyName Nom de la propriété
     * @param defaultValue Valeur par défaut
     * @return La valeur de la propriété
     */
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
    
    /**
     * Récupère une propriété chaîne depuis un objet Tiled
     * @param obj Objet Tiled
     * @param propertyName Nom de la propriété
     * @param defaultValue Valeur par défaut
     * @return La valeur de la propriété
     */
    private String getStringProperty(LevelData.TiledObject obj, String propertyName, String defaultValue) {
        if (obj.getProperties() == null) return defaultValue;
        
        for (LevelData.Property prop : obj.getProperties()) {
            if (prop.getName().equals(propertyName)) {
                return prop.getValue().toString();
            }
        }
        return defaultValue;
    }

    /**
     * Décompresse les données de tiles si elles sont compressées
     * @param layer La couche contenant les données
     * @return Tableau d'entiers des IDs de tiles
     */
    private int[] decompressTileData(LevelData.Layer layer) {
        // Si les données sont déjà un tableau, les retourner directement
        if (layer.getData() != null && layer.getData().length > 0) {
            return layer.getData();
        }

        // Sinon, décompresser depuis la chaîne encodée
        String dataString = layer.getDataString();
        String encoding = layer.getEncoding();
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
                int decompressedSize = inflater.inflate(decompressedData);
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
