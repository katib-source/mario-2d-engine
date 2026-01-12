package com.mario.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mario.model.level.Level;
import com.mario.model.level.LevelData;

public class TilesetRenderer {
    private final Map<String, Texture> tilesetTextures;
    private final Map<String, TextureRegion[]> tilesetRegions;
    
    public TilesetRenderer() {
        this.tilesetTextures = new HashMap<>();
        this.tilesetRegions = new HashMap<>();
    }
    
    public void loadTilesets(Level level) {
        if (level.getTilesets() == null) return;
        
        for (LevelData.Tileset tileset : level.getTilesets()) {
            String tilesetName = tileset.getName();
            
            // Ne pas recharger si déjà chargé
            if (tilesetTextures.containsKey(tilesetName)) {
                continue;
            }
            
            // Charger la texture du tileset
            String imagePath = "levels/" + tileset.getImage();
            try {
                Texture texture = new Texture(Gdx.files.internal(imagePath));
                tilesetTextures.put(tilesetName, texture);
                
                // Créer les régions de texture pour chaque tile
                TextureRegion[] regions = createTileRegions(texture, tileset);
                tilesetRegions.put(tilesetName, regions);
                
                System.out.println("Tileset chargé: " + tilesetName + " (" + regions.length + " tiles)");
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement du tileset: " + imagePath);
                System.err.println("Erreur: " + e.getMessage());
            }
        }
    }
    
    private TextureRegion[] createTileRegions(Texture texture, LevelData.Tileset tileset) {
        int tileWidth = tileset.getTilewidth();
        int tileHeight = tileset.getTileheight();
        int columns = tileset.getColumns();
        int tileCount = tileset.getTilecount();
        int margin = tileset.getMargin();
        int spacing = tileset.getSpacing();
        
        TextureRegion[] regions = new TextureRegion[tileCount];
        
        for (int i = 0; i < tileCount; i++) {
            int row = i / columns;
            int col = i % columns;
            
            // Calculer la position en tenant compte de la marge et de l'espacement
            int x = margin + col * (tileWidth + spacing);
            int y = margin + row * (tileHeight + spacing);
            
            regions[i] = new TextureRegion(texture, x, y, tileWidth, tileHeight);
        }
        
        return regions;
    }
    
    public void renderTileLayer(SpriteBatch batch, Level level, String layerName) {
        if (level.getTilesets() == null || level.getTilesets().isEmpty()) {
            return;
        }
        
        // Trouver la couche
        Level.TileLayer layer = null;
        for (Level.TileLayer tl : level.getTileLayers()) {
            if (tl.getName().equalsIgnoreCase(layerName)) {
                layer = tl;
                break;
            }
        }
        
        if (layer == null) return;
        
        // Obtenir le premier tileset (on suppose qu'il n'y en a qu'un pour simplifier)
        LevelData.Tileset tileset = level.getTilesets().get(0);
        TextureRegion[] regions = tilesetRegions.get(tileset.getName());
        
        if (regions == null) return;
        
        int[] data = layer.getData();
        int width = layer.getWidth();
        int height = layer.getHeight();
        int tileWidth = level.getTileWidth();
        int tileHeight = level.getTileHeight();
        
        // Rendre chaque tile
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int index = y * width + x;
                if (index >= data.length) continue;
                
                int tileId = data[index];
                if (tileId == 0) continue; // Tile vide
                
                // Convertir le tile ID en index de région (tile ID commence à 1)
                int regionIndex = tileId - tileset.getFirstgid();
                if (regionIndex < 0 || regionIndex >= regions.length) continue;
                
                // Calculer la position du tile
                // Attention: Tiled utilise top-left origin, LibGDX utilise bottom-left
                float posX = x * tileWidth;
                float posY = (height - y - 1) * tileHeight;
                
                // Dessiner le tile
                batch.draw(regions[regionIndex], posX, posY, tileWidth, tileHeight);
            }
        }
    }
    
    public void dispose() {
        for (Texture texture : tilesetTextures.values()) {
            texture.dispose();
        }
        tilesetTextures.clear();
        tilesetRegions.clear();
    }
}
