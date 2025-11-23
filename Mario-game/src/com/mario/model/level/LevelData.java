package com.mario.model.level;

import java.util.List;

/**
 * Classe représentant un niveau chargé depuis Tiled
 * Contient toutes les informations d'une carte
 */
public class LevelData {
    private int width;           // Largeur en tiles
    private int height;          // Hauteur en tiles
    private int tilewidth;       // Largeur d'une tile
    private int tileheight;      // Hauteur d'une tile
    private List<Layer> layers;  // Liste des couches
    private List<Tileset> tilesets; // Liste des tilesets
    
    // Getters et Setters
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    
    public int getTilewidth() { return tilewidth; }
    public void setTilewidth(int tilewidth) { this.tilewidth = tilewidth; }
    
    public int getTileheight() { return tileheight; }
    public void setTileheight(int tileheight) { this.tileheight = tileheight; }
    
    public List<Layer> getLayers() { return layers; }
    public void setLayers(List<Layer> layers) { this.layers = layers; }
    
    public List<Tileset> getTilesets() { return tilesets; }
    public void setTilesets(List<Tileset> tilesets) { this.tilesets = tilesets; }
    
    /**
     * Classe interne représentant une couche
     */
    public static class Layer {
        private String name;
        private String type;
        private int[] data;      // Pour les couches de tiles
        private List<TiledObject> objects; // Pour les couches d'objets
        private boolean visible;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public int[] getData() { return data; }
        public void setData(int[] data) { this.data = data; }
        
        public List<TiledObject> getObjects() { return objects; }
        public void setObjects(List<TiledObject> objects) { this.objects = objects; }
        
        public boolean isVisible() { return visible; }
        public void setVisible(boolean visible) { this.visible = visible; }
    }
    
    /**
     * Classe interne représentant un objet Tiled
     */
    public static class TiledObject {
        private float x;
        private float y;
        private float width;
        private float height;
        private String name;
        private String type;
        private List<Property> properties;
        
        public float getX() { return x; }
        public void setX(float x) { this.x = x; }
        
        public float getY() { return y; }
        public void setY(float y) { this.y = y; }
        
        public float getWidth() { return width; }
        public void setWidth(float width) { this.width = width; }
        
        public float getHeight() { return height; }
        public void setHeight(float height) { this.height = height; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public List<Property> getProperties() { return properties; }
        public void setProperties(List<Property> properties) { this.properties = properties; }
    }
    
    /**
     * Classe interne représentant une propriété personnalisée
     */
    public static class Property {
        private String name;
        private String type;
        private Object value;
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public Object getValue() { return value; }
        public void setValue(Object value) { this.value = value; }
    }
    
    /**
     * Classe interne représentant un tileset
     */
    public static class Tileset {
        private int firstgid;
        private String source;
        private String name;
        private int tilewidth;
        private int tileheight;
        private int tilecount;
        private int columns;
        private String image;
        
        public int getFirstgid() { return firstgid; }
        public void setFirstgid(int firstgid) { this.firstgid = firstgid; }
        
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public int getTilewidth() { return tilewidth; }
        public void setTilewidth(int tilewidth) { this.tilewidth = tilewidth; }
        
        public int getTileheight() { return tileheight; }
        public void setTileheight(int tileheight) { this.tileheight = tileheight; }
        
        public int getTilecount() { return tilecount; }
        public void setTilecount(int tilecount) { this.tilecount = tilecount; }
        
        public int getColumns() { return columns; }
        public void setColumns(int columns) { this.columns = columns; }
        
        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }
    }
}
