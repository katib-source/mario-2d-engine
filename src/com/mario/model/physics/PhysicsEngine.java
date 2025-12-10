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

public class PhysicsEngine {
    private static final float COLLISION_TOLERANCE = 0.1f;
    
    public void handlePlayerTerrainCollision(Player player, List<Rectangle> solidTiles) {
        Rectangle playerBounds = player.getBounds();
        boolean onGround = false;
        
        for (int i = 0; i < solidTiles.size(); i++) {
            Rectangle tile = solidTiles.get(i);
            if (playerBounds.overlaps(tile)) {
                float overlapLeft = playerBounds.x + playerBounds.width - tile.x;
                float overlapRight = tile.x + tile.width - playerBounds.x;
                float overlapTop = playerBounds.y + playerBounds.height - tile.y;
                float overlapBottom = tile.y + tile.height - playerBounds.y;
                
                float minOverlap = Math.min(
                    Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom)
                );
                
                if (minOverlap == overlapBottom && player.getVelocity().y <= COLLISION_TOLERANCE) {
                    float newY = tile.y + tile.height;
                    player.setPosition(playerBounds.x, newY);
                    player.getVelocity().y = 0;
                    player.setOnGround(true);
                    onGround = true;
                } else if (minOverlap == overlapTop && player.getVelocity().y > COLLISION_TOLERANCE) {
                    float newY = tile.y - playerBounds.height;
                    player.setPosition(playerBounds.x, newY);
                    player.getVelocity().y = 0;
                } else if (minOverlap == overlapLeft) {
                    float newX = tile.x - playerBounds.width;
                    player.setPosition(newX, playerBounds.y);
                    player.getVelocity().x = 0;
                } else if (minOverlap == overlapRight) {
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
    
    public void handlePlayerEnemyCollision(Player player, Level level) {
        if (!player.isActive() || player.getHealth() <= 0) return;

        List<Entity> entities = level.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Enemy && entity.isActive()) {
                Enemy enemy = (Enemy) entity;
                
                if (player.collidesWith(entity)) {
                    if (player.getVelocity().y < 0 && 
                        player.getPosition().y > entity.getPosition().y + entity.getHeight() / 2) {
                        if (entity instanceof Goomba) {
                            ((Goomba) entity).stompedByPlayer();
                            player.getVelocity().y = 200f;
                            player.addScore(100);
                            AudioManager.getInstance().playSound("stomp");
                        }
                    } else if (!player.isInvincible()) {
                        enemy.onPlayerCollision(player);
                        
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
    
    public void handlePlayerCollectibleCollision(Player player, Level level) {
        List<Entity> entities = level.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Collectible && entity.isActive()) {
                Collectible collectible = (Collectible) entity;
                
                if (collectible.isCollectable() && player.collidesWith(entity)) {
                    collectible.onCollect(player);
                }
            }
        }
    }
    
    public void handleEnemyTerrainCollision(Entity enemy, List<Rectangle> solidTiles) {
        if (!(enemy instanceof Enemy)) return;
        
        Rectangle enemyBounds = enemy.getBounds();
        
        for (int i = 0; i < solidTiles.size(); i++) {
            Rectangle tile = solidTiles.get(i);
            if (enemyBounds.overlaps(tile)) {
                float overlapLeft = enemyBounds.x + enemyBounds.width - tile.x;
                float overlapRight = tile.x + tile.width - enemyBounds.x;
                float overlapTop = enemyBounds.y + enemyBounds.height - tile.y;
                float overlapBottom = tile.y + tile.height - enemyBounds.y;

                float minOverlap = Math.min(
                    Math.min(overlapLeft, overlapRight),
                    Math.min(overlapTop, overlapBottom)
                );

                if (minOverlap == overlapBottom && enemy.getVelocity().y < 0) {
                    enemy.setPosition(enemyBounds.x, tile.y + tile.height);
                    enemy.getVelocity().y = 0;
                } else if (minOverlap == overlapLeft) {
                    if (enemy instanceof Goomba) {
                        ((Goomba) enemy).reverseDirection();
                    }
                    enemy.setPosition(tile.x - enemyBounds.width, enemyBounds.y);
                } else if (minOverlap == overlapRight) {
                    if (enemy instanceof Goomba) {
                        ((Goomba) enemy).reverseDirection();
                    }
                    enemy.setPosition(tile.x + tile.width, enemyBounds.y);
                } else if (minOverlap == overlapTop && enemy.getVelocity().y > 0) {
                    enemy.setPosition(enemyBounds.x, tile.y - enemyBounds.height);
                    enemy.getVelocity().y = 0;
                }
            }
        }
    }
}
