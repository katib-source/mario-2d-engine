package com.mario.model.physics;

import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.mario.model.entity.Collectible;
import com.mario.model.entity.Enemy;
import com.mario.model.entity.Entity;
import com.mario.model.entity.Goomba;
import com.mario.model.entity.Player;
import com.mario.model.level.Level;
import com.mario.view.AudioManager;

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
                // Calculer les chevauchements de chaque côté
                float overlapLeft = playerBounds.x + playerBounds.width - tile.x;
                float overlapRight = tile.x + tile.width - playerBounds.x;
                float overlapTop = playerBounds.y + playerBounds.height - tile.y;
                float overlapBottom = tile.y + tile.height - playerBounds.y;
                
                // Trouver le côté avec le moins de chevauchement
                float minOverlap = Math.min(
                    Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom)
                );
                
                // Résoudre la collision en fonction du côté
                if (minOverlap == overlapBottom && player.getVelocity().y <= COLLISION_TOLERANCE) {
                    // Collision par le bas (joueur tombe sur le tile)
                    float newY = tile.y + tile.height;
                    player.setPosition(playerBounds.x, newY);
                    player.getVelocity().y = 0;
                    player.setOnGround(true);
                    onGround = true;
                } else if (minOverlap == overlapTop && player.getVelocity().y > COLLISION_TOLERANCE) {
                    // Collision par le haut (joueur tape sa tête)
                    float newY = tile.y - playerBounds.height;
                    player.setPosition(playerBounds.x, newY);
                    player.getVelocity().y = 0;
                } else if (minOverlap == overlapLeft) {
                    // Collision à droite
                    float newX = tile.x - playerBounds.width;
                    player.setPosition(newX, playerBounds.y);
                    player.getVelocity().x = 0;
                } else if (minOverlap == overlapRight) {
                    // Collision à gauche
                    float newX = tile.x + tile.width;
                    player.setPosition(newX, playerBounds.y);
                    player.getVelocity().x = 0;
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
        // Ne pas gérer les collisions si le joueur est mort
        if (!player.isActive() || player.getHealth() <= 0) return;

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
                            player.addScore(100); // Points pour avoir tué l'ennemi
                            AudioManager.getInstance().playSound("stomp");
                        }
                    } else if (!player.isInvincible()) {
                        // Collision latérale - le joueur prend des dégâts
                        // Le timer d'invincibilité empêche les dégâts répétés
                        enemy.onPlayerCollision(player);
                        
                        // Petit knockback au lieu de téléportation
                        float knockbackForce = 100f;
                        if (player.getPosition().x < entity.getPosition().x) {
                            player.getVelocity().x = -knockbackForce;
                        } else {
                            player.getVelocity().x = knockbackForce;
                        }
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
                // Calculer les chevauchements de chaque côté
                float overlapLeft = enemyBounds.x + enemyBounds.width - tile.x;
                float overlapRight = tile.x + tile.width - enemyBounds.x;
                float overlapTop = enemyBounds.y + enemyBounds.height - tile.y;
                float overlapBottom = tile.y + tile.height - enemyBounds.y;

                // Trouver le côté avec le moins de chevauchement
                float minOverlap = Math.min(
                    Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom)
                );

                // Résoudre la collision
                if (minOverlap == overlapBottom && enemy.getVelocity().y < 0) {
                    // Collision par le bas (ennemi tombe sur le tile)
                    enemy.setPosition(enemyBounds.x, tile.y + tile.height);
                    enemy.getVelocity().y = 0;
                } else if (minOverlap == overlapLeft) {
                    // Collision à droite - inverser la direction
                    if (enemy instanceof Goomba) {
                        ((Goomba) enemy).reverseDirection();
                    }
                    enemy.setPosition(tile.x - enemyBounds.width, enemyBounds.y);
                } else if (minOverlap == overlapRight) {
                    // Collision à gauche - inverser la direction
                    if (enemy instanceof Goomba) {
                        ((Goomba) enemy).reverseDirection();
                    }
                    enemy.setPosition(tile.x + tile.width, enemyBounds.y);
                } else if (minOverlap == overlapTop && enemy.getVelocity().y > 0) {
                    // Collision par le haut
                    enemy.setPosition(enemyBounds.x, tile.y - enemyBounds.height);
                    enemy.getVelocity().y = 0;
                }
            }
        }
    }
}
