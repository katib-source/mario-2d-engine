package com.mario.model.level;

import com.badlogic.gdx.math.Rectangle;
import com.mario.model.entity.Entity;
import com.mario.model.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un niveau de jeu
 * Contient les entités, les tiles, et la logique du niveau
 */
public class Level {
    private int width;           // Largeur en tiles
    private int height;          // Hauteur en tiles
    private int tileWidth;       // Largeur d'une tile en pixels
    private int tileHeight;      // Hauteur d'une tile en pixels
    
    private List<Entity> entities;           // Liste de toutes les entités
    private List<Rectangle> solidTiles;      // Tiles solides (obstacles)
    private Player player;                   // Référence au joueur
    
    private List<TileLayer> tileLayers;      // Couches de tiles
    
    /**
     * Constructeur du niveau
     * @param width Largeur en tiles
     * @param height Hauteur en tiles
     * @param tileWidth Largeur d'une tile
     * @param tileHeight Hauteur d'une tile
     */
    public Level(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.entities = new ArrayList<>();
        this.solidTiles = new ArrayList<>();
        this.tileLayers = new ArrayList<>();
    }
    
    /**
     * Ajoute une entité au niveau
     * @param entity Entité à ajouter
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity instanceof Player) {
            this.player = (Player) entity;
        }
    }
    
    /**
     * Ajoute une couche de tiles
     * @param name Nom de la couche
     * @param data Données de la couche
     */
    public void addTileLayer(String name, int[] data) {
        TileLayer layer = new TileLayer(name, data, width, height);
        tileLayers.add(layer);
        
        // Si c'est une couche de collision, créer les rectangles de collision
        if (name.toLowerCase().contains("collision") || name.toLowerCase().contains("solid")) {
            createCollisionTiles(data);
        }
    }
    
    /**
     * Crée des rectangles de collision à partir des données de tiles
     * @param data Données de tiles
     */
    private void createCollisionTiles(int[] data) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                if (index < data.length && data[index] != 0) {
                    // Tile non vide = solide
                    float tileX = x * tileWidth;
                    float tileY = (height - y - 1) * tileHeight; // Inversion Y
                    solidTiles.add(new Rectangle(tileX, tileY, tileWidth, tileHeight));
                }
            }
        }
    }
    
    /**
     * Met à jour toutes les entités du niveau
     * @param delta Temps écoulé
     */
    public void update(float delta) {
        // Mettre à jour toutes les entités
        for (Entity entity : entities) {
            if (entity.isActive()) {
                entity.update(delta);
            }
        }
        
        // Supprimer les entités inactives
        entities.removeIf(entity -> !entity.isActive());
    }
    
    // Getters
    public List<Entity> getEntities() {
        return entities;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public List<Rectangle> getSolidTiles() {
        return solidTiles;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getTileWidth() {
        return tileWidth;
    }
    
    public int getTileHeight() {
        return tileHeight;
    }
    
    public List<TileLayer> getTileLayers() {
        return tileLayers;
    }
    
    /**
     * Classe interne représentant une couche de tiles
     */
    public static class TileLayer {
        private String name;
        private int[] data;
        private int width;
        private int height;
        
        public TileLayer(String name, int[] data, int width, int height) {
            this.name = name;
            this.data = data;
            this.width = width;
            this.height = height;
        }
        
        public String getName() { return name; }
        public int[] getData() { return data; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }
}
