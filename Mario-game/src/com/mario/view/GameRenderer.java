package com.mario.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mario.model.entity.*;
import com.mario.model.level.Level;

/**
 * Classe responsable du rendu graphique
 * Pattern: MVC - Vue
 */
public class GameRenderer {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera camera;
    private BitmapFont font;
    
    /**
     * Constructeur du renderer
     */
    public GameRenderer() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 600);
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
    }
    
    /**
     * Rend le niveau et toutes ses entités
     * @param level Le niveau à rendre
     */
    public void render(Level level) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        
        // Centrer la caméra sur le joueur
        if (level.getPlayer() != null) {
            centerCameraOnPlayer(level.getPlayer(), level);
        }
        
        // Rendre les tiles du niveau
        renderTiles(level);
        
        // Rendre les entités
        renderEntities(level);
        
        // Rendre le HUD
        renderHUD(level);
    }
    
    /**
     * Centre la caméra sur le joueur
     * @param player Le joueur
     * @param level Le niveau
     */
    private void centerCameraOnPlayer(Player player, Level level) {
        float targetX = player.getPosition().x + player.getWidth() / 2;
        float targetY = player.getPosition().y + player.getHeight() / 2;
        
        // Limiter la caméra aux bords du niveau
        float halfWidth = camera.viewportWidth / 2;
        float halfHeight = camera.viewportHeight / 2;
        
        float levelWidth = level.getWidth() * level.getTileWidth();
        float levelHeight = level.getHeight() * level.getTileHeight();
        
        targetX = Math.max(halfWidth, Math.min(targetX, levelWidth - halfWidth));
        targetY = Math.max(halfHeight, Math.min(targetY, levelHeight - halfHeight));
        
        camera.position.set(targetX, targetY, 0);
    }
    
    /**
     * Rend les tiles du niveau
     * @param level Le niveau
     */
    private void renderTiles(Level level) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        // Rendre les tiles solides (pour le moment, en rectangles simples)
        shapeRenderer.setColor(Color.BROWN);
        for (Rectangle tile : level.getSolidTiles()) {
            shapeRenderer.rect(tile.x, tile.y, tile.width, tile.height);
        }
        
        shapeRenderer.end();
    }
    
    /**
     * Rend toutes les entités
     * @param level Le niveau
     */
    private void renderEntities(Level level) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        
        for (Entity entity : level.getEntities()) {
            if (entity.isActive()) {
                renderEntity(entity);
            }
        }
        
        shapeRenderer.end();
    }
    
    /**
     * Rend une entité individuelle
     * @param entity L'entité à rendre
     */
    private void renderEntity(Entity entity) {
        // Pour le moment, rendu simple avec des rectangles colorés
        // Dans une version complète, on utiliserait des textures/sprites
        
        if (entity instanceof Player) {
            shapeRenderer.setColor(Color.RED);
        } else if (entity instanceof Coin) {
            shapeRenderer.setColor(Color.GOLD);
        } else if (entity instanceof Goomba) {
            shapeRenderer.setColor(Color.FIREBRICK);
        } else {
            shapeRenderer.setColor(Color.WHITE);
        }
        
        Rectangle bounds = entity.getBounds();
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    /**
     * Rend l'interface utilisateur (HUD)
     * @param level Le niveau
     */
    private void renderHUD(Level level) {
        if (level.getPlayer() == null) return;
        
        batch.begin();
        
        Player player = level.getPlayer();
        
        // Afficher le score
        font.draw(batch, "Score: " + player.getScore(), 10, 590);
        
        // Afficher la santé
        font.draw(batch, "Health: " + player.getHealth(), 10, 570);
        
        // Afficher les vies
        font.draw(batch, "Lives: " + player.getLives(), 10, 550);
        
        batch.end();
    }
    
    /**
     * Libère les ressources
     */
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
    
    public OrthographicCamera getCamera() {
        return camera;
    }
}
