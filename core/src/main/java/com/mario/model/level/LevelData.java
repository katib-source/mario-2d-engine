package com.mario.model.level;

import java.util.List;


public class LevelData {
    private int width;           
    private int height;          
    private int tilewidth;       
    private int tileheight;     
    private List<Layer> layers;  
    private List<Tileset> tilesets; 
    
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
    

    public static class Layer {
        private String name;
        private String type;
        private int[] data;      
        private String dataString; 
        private List<TiledObject> objects; 
        private boolean visible;
        private int width;    
        private int height;   
        private String encoding; 
        private String compression; 

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        
        public int[] getData() { return data; }
        public void setData(int[] data) { this.data = data; }
        
        public String getDataString() { return dataString; }
        public void setDataString(String dataString) { this.dataString = dataString; }

        public List<TiledObject> getObjects() { return objects; }
        public void setObjects(List<TiledObject> objects) { this.objects = objects; }
        
        public boolean isVisible() { return visible; }
        public void setVisible(boolean visible) { this.visible = visible; }

        public int getWidth() { return width; }
        public void setWidth(int width) { this.width = width; }

        public int getHeight() { return height; }
        public void setHeight(int height) { this.height = height; }

        public String getEncoding() { return encoding; }
        public void setEncoding(String encoding) { this.encoding = encoding; }

        public String getCompression() { return compression; }
        public void setCompression(String compression) { this.compression = compression; }
    }
    

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
    

    public static class Tileset {
        private int firstgid;
        private String source;
        private String name;
        private int tilewidth;
        private int tileheight;
        private int tilecount;
        private int columns;
        private String image;
        private int margin;
        private int spacing;
        private List<TileInfo> tiles;
        
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
        
        public int getMargin() { return margin; }
        public void setMargin(int margin) { this.margin = margin; }
        
        public int getSpacing() { return spacing; }
        public void setSpacing(int spacing) { this.spacing = spacing; }
        
        public List<TileInfo> getTiles() { return tiles; }
        public void setTiles(List<TileInfo> tiles) { this.tiles = tiles; }
    }
    

    public static class TileInfo {
        private int id;
        private List<Property> properties;
        
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }
        
        public List<Property> getProperties() { return properties; }
        public void setProperties(List<Property> properties) { this.properties = properties; }
    }
}
