package com.mario.model.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.mario.model.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable du chargement des niveaux depuis les fichiers JSON Tiled
 * Pattern: Factory - Crée des entités à partir des données JSON
 */
public class LevelLoader {
    private Gson gson;
    
    public LevelLoader() {
        this.gson = new Gson();
    }
    
    /**
     * Charge un niveau depuis un fichier JSON
     * @param levelPath Chemin vers le fichier JSON du niveau
     * @return Le niveau chargé
     */
    public Level loadLevel(String levelPath) {
        FileHandle file = Gdx.files.internal(levelPath);
        String jsonContent = file.readString();
        
        LevelData levelData = gson.fromJson(jsonContent, LevelData.class);
        
        return createLevelFromData(levelData);
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
        
        // Charger les layers
        for (LevelData.Layer layer : levelData.getLayers()) {
            if (layer.getType().equals("tilelayer")) {
                // Couche de tiles (terrain)
                level.addTileLayer(layer.getName(), layer.getData());
            } else if (layer.getType().equals("objectgroup")) {
                // Couche d'objets (entités)
                loadEntitiesFromLayer(layer, level);
            }
        }
        
        return level;
    }
    
    /**
     * Charge les entités depuis une couche d'objets
     * Pattern: Factory Method - Crée différents types d'entités
     * @param layer Couche d'objets
     * @param level Niveau où ajouter les entités
     */
    private void loadEntitiesFromLayer(LevelData.Layer layer, Level level) {
        if (layer.getObjects() == null) return;
        
        for (LevelData.TiledObject obj : layer.getObjects()) {
            Entity entity = createEntityFromObject(obj);
            if (entity != null) {
                level.addEntity(entity);
            }
        }
    }
    
    /**
     * Crée une entité à partir d'un objet Tiled
     * Pattern: Factory Method - Permet l'extensibilité
     * @param obj Objet Tiled
     * @return L'entité créée
     */
    private Entity createEntityFromObject(LevelData.TiledObject obj) {
        String type = obj.getType() != null ? obj.getType().toLowerCase() : "";
        
        // Ajustement des coordonnées Y (Tiled utilise un système inversé)
        float x = obj.getX();
        float y = obj.getY();
        
        switch (type) {
            case "player":
                return new Player(x, y);
                
            case "coin":
                int scoreValue = getIntProperty(obj, "scoreValue", 10);
                return new Coin(x, y, scoreValue);
                
            case "goomba":
                return new Goomba(x, y);
                
            // Possibilité d'ajouter d'autres types d'entités ici
            // case "koopa":
            //     return new Koopa(x, y);
            
            default:
                System.out.println("Type d'entité inconnu: " + type);
                return null;
        }
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
}
