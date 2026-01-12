package com.mario.model.level;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.mario.model.entity.EndTrigger;
import com.mario.model.entity.Entity;
import com.mario.model.entity.Player;

public class Level {
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;
    
    private List<Entity> entities;
    private List<Rectangle> solidTiles;
    private Player player;
    private EndTrigger endTrigger;
    
    private List<TileLayer> tileLayers;
    private List<LevelData.Tileset> tilesets;
    
    private TiledMap tiledMap;
    
    public Level(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.entities = new ArrayList<>();
        this.solidTiles = new ArrayList<>();
        this.tileLayers = new ArrayList<>();
        this.tilesets = new ArrayList<>();
    }
    
    public void addEntity(Entity entity) {
        entities.add(entity);
        if (entity instanceof Player) {
            this.player = (Player) entity;
        }
    }
    
    public void addTileLayer(String name, int[] data) {
        TileLayer layer = new TileLayer(name, data, width, height);
        tileLayers.add(layer);
        
        if (name.toLowerCase().contains("collision") || name.toLowerCase().contains("solid")) {
            createCollisionTiles(data);
        }
    }
    
    private void createCollisionTiles(int[] data) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                if (index < data.length && data[index] != 0) {
                    float tileX = x * tileWidth;
                    float tileY = (height - y - 1) * tileHeight;
                    solidTiles.add(new Rectangle(tileX, tileY, tileWidth, tileHeight));
                }
            }
        }
    }
    
    public void update(float delta) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity.isActive()) {
                entity.update(delta);
            }
        }
        
        for (int i = entities.size() - 1; i >= 0; i--) {
            if (!entities.get(i).isActive()) {
                entities.remove(i);
            }
        }
    }
    
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
    
    public List<LevelData.Tileset> getTilesets() {
        return tilesets;
    }
    
    public void setTilesets(List<LevelData.Tileset> tilesets) {
        this.tilesets = tilesets;
    }
    
    public TiledMap getTiledMap() {
        return tiledMap;
    }
    
    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }
    
    public boolean hasTiledMap() {
        return tiledMap != null;
    }
    
    public EndTrigger getEndTrigger() {
        return endTrigger;
    }
    
    public void setEndTrigger(EndTrigger endTrigger) {
        this.endTrigger = endTrigger;
    }
    
    public boolean hasEndTrigger() {
        return endTrigger != null;
    }
    
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
