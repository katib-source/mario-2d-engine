package com.mario.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.mario.model.entity.Coin;
import com.mario.model.entity.Entity;
import com.mario.model.entity.Goomba;
import com.mario.model.entity.Player;
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
    private TextureManager textureManager;
    private TilesetRenderer tilesetRenderer;
    private SpriteAnimator spriteAnimator;
    private Level currentLevel; // Référence au niveau pour charger les tilesets

    /**
     * Constructeur du renderer
     */
    public GameRenderer() {
        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();
        this.camera = new OrthographicCamera();
        // Original Mario dimensions: 400x208
        this.camera.setToOrtho(false, 400, 208);
        this.font = new BitmapFont();
        this.font.setColor(Color.WHITE);
        this.textureManager = TextureManager.getInstance();
        this.tilesetRenderer = new TilesetRenderer();
        this.spriteAnimator = new SpriteAnimator();
    }
    
    /**
     * Rend le niveau et toutes ses entités
     * @param level Le niveau à rendre
     */
    public void render(Level level) {
        // Charger les tilesets si le niveau change
        if (currentLevel != level) {
            currentLevel = level;
            tilesetRenderer.loadTilesets(level);
        }
        
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
        // If player is dead or inactive, keep camera at a valid position
        if (!player.isActive() || player.getLives() <= 0) {
            // Keep camera centered on level for game over screen
            float halfWidth = camera.viewportWidth / 2;
            float halfHeight = camera.viewportHeight / 2;
            camera.position.set(halfWidth, halfHeight, 0);
            return;
        }
        
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
        batch.begin();

        // Rendre les tiles depuis les tilesets (couche graphique)
        tilesetRenderer.renderTileLayer(batch, level, "Graphic Layer");
        
        // Rendre aussi les tiles procédurales pour les couches de collision si nécessaire
        Texture groundTexture = textureManager.getTexture("tile_ground");

        // Rendre les tiles avec textures (fallback pour test)
        // Cette partie n'est utilisée que si le tileset n'est pas chargé
        if (level.getTilesets() == null || level.getTilesets().isEmpty()) {
            for (Rectangle tile : level.getSolidTiles()) {
                if (groundTexture != null) {
                    batch.draw(groundTexture, tile.x, tile.y, tile.width, tile.height);
                }
            }
        }
        
        batch.end();
    }
    
    /**
     * Rend toutes les entités
     * @param level Le niveau
     */
    private void renderEntities(Level level) {
        batch.begin();

        for (Entity entity : level.getEntities()) {
            if (entity.isActive()) {
                renderEntity(entity);
            }
        }
        
        batch.end();
    }
    
    /**
     * Rend une entité individuelle
     * @param entity L'entité à rendre
     */
    private void renderEntity(Entity entity) {
        Rectangle bounds = entity.getBounds();
        
        if (entity instanceof Player) {
            renderPlayer((Player) entity, bounds);
        } else if (entity instanceof Coin) {
            Texture texture = textureManager.getTexture("coin");
            if (texture != null) {
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        } else if (entity instanceof Goomba) {
            renderGoomba((Goomba) entity, bounds);
        }
    }
    
    /**
     * Renders the player with animation
     */
    private void renderPlayer(Player player, Rectangle bounds) {
        // Skip rendering every other frame if invincible (flashing effect)
        if (player.isInvincible()) {
            int flashRate = 100; // milliseconds
            if ((System.currentTimeMillis() / flashRate) % 2 == 0) {
                return; // Don't render this frame (creates flashing)
            }
        }
        
        TextureRegion frame = null;
        
        if (player.isJumping()) {
            // Jumping sprite
            frame = spriteAnimator.getStaticSprite("mario_jump");
        } else if (player.isRunning()) {
            // Running animation
            frame = spriteAnimator.getAnimationFrame("mario_run", player.getStateTime(), true);
        } else {
            // Standing sprite
            frame = spriteAnimator.getStaticSprite("mario_stand");
        }
        
        if (frame != null) {
            // Flip sprite based on direction
            if (!player.isFacingRight() && !frame.isFlipX()) {
                frame.flip(true, false);
            } else if (player.isFacingRight() && frame.isFlipX()) {
                frame.flip(true, false);
            }
            
            // Scale from 16x16 to 32x32
            batch.draw(frame, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            // Fallback to simple texture
            Texture texture = textureManager.getTexture("player");
            if (texture != null) {
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }
    
    /**
     * Renders a Goomba with animation
     */
    private void renderGoomba(Goomba goomba, Rectangle bounds) {
        TextureRegion frame = spriteAnimator.getAnimationFrame("goomba_walk", goomba.getStateTime(), true);
        
        if (frame != null) {
            // Scale from 16x16 to 32x32
            batch.draw(frame, bounds.x, bounds.y, bounds.width, bounds.height);
        } else {
            // Fallback to simple texture
            Texture texture = textureManager.getTexture("goomba");
            if (texture != null) {
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }
        }
    }
    
    /**
     * Rend l'interface utilisateur (HUD)
     * @param level Le niveau
     */
    private void renderHUD(Level level) {
        batch.begin();
        
        if (level.getPlayer() != null) {
            Player player = level.getPlayer();

            // Afficher le score (adjusted for 400x208 viewport)
            font.draw(batch, "Score: " + player.getScore(), 10, 200);

            // Afficher la santé
            font.draw(batch, "Health: " + player.getHealth(), 10, 185);

            // Afficher les vies
            font.draw(batch, "Lives: " + player.getLives(), 10, 170);

            // Afficher GAME OVER si le joueur est mort
            if (!player.isActive() || player.getLives() <= 0) {
                font.getData().setScale(2f); // Texte plus grand
                font.setColor(Color.RED);
                font.draw(batch, "GAME OVER", 120, 120);
                font.getData().setScale(1f);
                font.setColor(Color.WHITE);
                font.draw(batch, "Press R to Restart", 110, 90);
                font.getData().setScale(1f); // Réinitialiser la taille
            }
        }

        batch.end();
    }
    
    /**
     * Libère les ressources
     */
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
        textureManager.dispose();
        tilesetRenderer.dispose();
        spriteAnimator.dispose();
    }
    
    public OrthographicCamera getCamera() {
        return camera;
    }
}
