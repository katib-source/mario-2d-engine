package com.mario.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mario.model.level.Level;

public class TiledMapRenderer {
    private OrthogonalTiledMapRenderer tiledRenderer;
    private TiledMap currentMap;
    
    public TiledMapRenderer() {
    }
    
    public void render(Level level, OrthographicCamera camera) {
        if (!level.hasTiledMap()) {
            return; // No Tiled map to render
        }
        
        TiledMap map = level.getTiledMap();
        
        // Create or update renderer if map changed
        if (map != currentMap) {
            if (tiledRenderer != null) {
                tiledRenderer.dispose();
            }
            tiledRenderer = new OrthogonalTiledMapRenderer(map);
            currentMap = map;
        }
        
        // Set the view based on the camera
        tiledRenderer.setView(camera);
        
        // Render only visible layers (automatic frustum culling by LibGDX)
        tiledRenderer.render();
    }
    
    public void dispose() {
        if (tiledRenderer != null) {
            tiledRenderer.dispose();
        }
    }
}
