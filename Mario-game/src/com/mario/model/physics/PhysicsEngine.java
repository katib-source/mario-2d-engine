package com.mario.model.physics;

import com.badlogic.gdx.math.Rectangle;
import com.mario.model.entity.*;
import com.mario.model.level.Level;

import java.util.List;

/**
 * Classe gérant la physique et les collisions du jeu
 * Pattern: Singleton (peut être implémenté si nécessaire)
 */
public class PhysicsEngine {
    private static final float COLLISION_TOLERANCE = 0.1f;
    
    /**
     * Gère les collisions entre le joueur et le terrain
     * @param player Le joueur
     * @param solidTiles Les tiles solides
     */
    public void handlePlayerTerrainCollision(Player player, List<Rectangle> solidTiles) {
        Rectangle playerBounds = player.getBounds();
        boolean onGround = false;
        
        for (Rectangle tile : solidTiles) {
            if (playerBounds.overlaps(tile)) {
                // Calculer les côtés de la collision
                float overlapLeft = playerBounds.x + playerBounds.width - tile.x;
                float overlapRight = tile.x + tile.width - playerBounds.x;
                float overlapTop = playerBounds.y + playerBounds.height - tile.y;
                float overlapBottom = tile.y + tile.height - playerBounds.y;
                
                // Trouver le côté avec le moins de chevauchement
                float minOverlap = Math.min(
                    Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom)
                );
                
                // Résoudre la collision
                if (minOverlap == overlapTop && player.getVelocity().y <= 0) {
                    // Collision par le bas (joueur atterrit)
                    player.setPosition(playerBounds.x, tile.y - playerBounds.height);
                    player.setOnGround(true);
                    onGround = true;
                } else if (minOverlap == overlapBottom && player.getVelocity().y > 0) {
                    // Collision par le haut (joueur tape sa tête)
                    player.setPosition(playerBounds.x, tile.y + tile.height);
                    player.getVelocity().y = 0;
                } else if (minOverlap == overlapLeft) {
                    // Collision à droite
                    player.setPosition(tile.x - playerBounds.width, playerBounds.y);
                } else if (minOverlap == overlapRight) {
                    // Collision à gauche
                    player.setPosition(tile.x + tile.width, playerBounds.y);
                }
            }
        }
        
        if (!onGround) {
            player.setOnGround(false);
        }
    }
    
    /**
     * Gère les collisions entre le joueur et les ennemis
     * @param player Le joueur
     * @param level Le niveau
     */
    public void handlePlayerEnemyCollision(Player player, Level level) {
        for (Entity entity : level.getEntities()) {
            if (entity instanceof Enemy && entity.isActive()) {
                Enemy enemy = (Enemy) entity;
                
                if (player.collidesWith(entity)) {
                    // Vérifier si le joueur saute sur l'ennemi
                    if (player.getVelocity().y < 0 && 
                        player.getPosition().y > entity.getPosition().y + entity.getHeight() / 2) {
                        // Le joueur saute sur l'ennemi
                        if (entity instanceof Goomba) {
                            ((Goomba) entity).stompedByPlayer();
                            player.getVelocity().y = 200f; // Petit rebond
                        }
                    } else {
                        // Collision latérale - le joueur prend des dégâts
                        enemy.onPlayerCollision(player);
                    }
                }
            }
        }
    }
    
    /**
     * Gère les collisions entre le joueur et les collectibles
     * @param player Le joueur
     * @param level Le niveau
     */
    public void handlePlayerCollectibleCollision(Player player, Level level) {
        for (Entity entity : level.getEntities()) {
            if (entity instanceof Collectible && entity.isActive()) {
                Collectible collectible = (Collectible) entity;
                
                if (collectible.isCollectable() && player.collidesWith(entity)) {
                    collectible.onCollect(player);
                }
            }
        }
    }
    
    /**
     * Gère les collisions des ennemis avec le terrain
     * @param enemy L'ennemi
     * @param solidTiles Les tiles solides
     */
    public void handleEnemyTerrainCollision(Entity enemy, List<Rectangle> solidTiles) {
        if (!(enemy instanceof Enemy)) return;
        
        Rectangle enemyBounds = enemy.getBounds();
        
        for (Rectangle tile : solidTiles) {
            if (enemyBounds.overlaps(tile)) {
                // Calculer les chevauchements
                float overlapLeft = enemyBounds.x + enemyBounds.width - tile.x;
                float overlapRight = tile.x + tile.width - enemyBounds.x;
                float overlapTop = enemyBounds.y + enemyBounds.height - tile.y;
                
                // Si collision latérale, inverser la direction
                if (overlapLeft < overlapTop && overlapLeft < 5) {
                    if (enemy instanceof Goomba) {
                        ((Goomba) enemy).reverseDirection();
                    }
                    enemy.setPosition(tile.x - enemyBounds.width, enemyBounds.y);
                } else if (overlapRight < overlapTop && overlapRight < 5) {
                    if (enemy instanceof Goomba) {
                        ((Goomba) enemy).reverseDirection();
                    }
                    enemy.setPosition(tile.x + tile.width, enemyBounds.y);
                } else {
                    // Collision verticale (atterrissage)
                    enemy.setPosition(enemyBounds.x, tile.y - enemyBounds.height);
                    enemy.getVelocity().y = 0;
                }
            }
        }
    }
}
