package com.mario.model.entity;

import com.mario.view.AudioManager;

/**
 * Classe représentant le joueur (Mario)
 * Gère les mouvements, sauts, vie, et score
 * 
 * Pattern: Héritage de Entity
 */
public class Player extends Entity {
    private static final float MOVE_SPEED = 150f;      // Vitesse de déplacement
    private static final float JUMP_VELOCITY = 400f;   // Vélocité de saut
    private static final float GRAVITY = -800f;        // Gravité
    private static final float MAX_FALL_SPEED = -500f; // Vitesse de chute maximale
    
    private int health;              // Points de vie
    private int score;               // Score actuel
    private boolean onGround;        // Le joueur est-il au sol?
    private boolean canJump;         // Le joueur peut-il sauter?
    private int lives;               // Nombre de vies
    private float stateTime;         // Pour l'animation
    private boolean facingRight;     // Direction du joueur
    private float invincibilityTimer; // Timer d'invincibilité après dégâts
    private static final float INVINCIBILITY_DURATION = 1.0f; // Durée d'invincibilité en secondes
    
    /**
     * Constructeur du joueur
     * @param x Position X initiale
     * @param y Position Y initiale
     */
    public Player(float x, float y) {
        super(x, y, 32, 32); // Taille standard 32x32 pixels
        this.type = "player";
        this.health = 100;
        this.score = 0;
        this.lives = 3;
        this.onGround = false;
        this.canJump = true;
        this.stateTime = 0;
        this.facingRight = true;
        this.invincibilityTimer = 0;
    }
    
    @Override
    public void update(float delta) {
        // Update animation state time
        stateTime += delta;
        
        // Update invincibility timer
        if (invincibilityTimer > 0) {
            invincibilityTimer -= delta;
        }
        
        // Update facing direction based on velocity
        if (velocity.x > 0) {
            facingRight = true;
        } else if (velocity.x < 0) {
            facingRight = false;
        }
        
        // Debug: Check if player is falling off the world
        if (position.y < -100) {
            System.out.println("Player fell off the world at Y: " + position.y);
            // Force death - bypass invincibility and ensure health is 0
            health = 0;
            invincibilityTimer = 0;
            die();
            return;
        }
        
        // Appliquer la gravité
        if (!onGround) {
            velocity.y += GRAVITY * delta;
            if (velocity.y < MAX_FALL_SPEED) {
                velocity.y = MAX_FALL_SPEED;
            }
        } else {
            // When on ground, ensure velocity.y stays at 0
            velocity.y = 0;
        }
        
        // Mettre à jour la position
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        
        // Mettre à jour les bounds
        updateBounds();
    }
    
    /**
     * Déplace le joueur vers la gauche
     */
    public void moveLeft() {
        velocity.x = -MOVE_SPEED;
    }
    
    /**
     * Déplace le joueur vers la droite
     */
    public void moveRight() {
        velocity.x = MOVE_SPEED;
    }
    
    /**
     * Arrête le mouvement horizontal
     */
    public void stopMoving() {
        velocity.x = 0;
    }
    
    /**
     * Fait sauter le joueur
     */
    public void jump() {
        if (onGround && canJump) {
            velocity.y = JUMP_VELOCITY;
            onGround = false;
            AudioManager.getInstance().playSound("jump");
        }
    }
    
    /**
     * Collecte un objet et augmente le score
     * @param points Points à ajouter
     */
    public void addScore(int points) {
        this.score += points;
    }
    
    /**
     * Inflige des dégâts au joueur
     * @param damage Montant des dégâts
     */
    public void takeDamage(int damage) {
        // Ne pas prendre de dégâts si déjà mort ou invincible
        if (health <= 0 || invincibilityTimer > 0) return;

        this.health -= damage;
        // Activer l'invincibilité temporaire
        invincibilityTimer = INVINCIBILITY_DURATION;
        
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }
    
    /**
     * Soigne le joueur
     * @param amount Montant de soin
     */
    public void heal(int amount) {
        this.health += amount;
        if (this.health > 100) {
            this.health = 100;
        }
    }
    
    /**
     * Appelé lorsque le joueur meurt
     */
    private void die() {
        // Ne décrémenter les vies qu'une seule fois
        if (lives > 0 && health <= 0 && active) {
            lives--;
            AudioManager.getInstance().playSound("die");
            System.out.println("Player died! Lives remaining: " + lives);

            if (lives <= 0) {
                active = false; // Game Over
                AudioManager.getInstance().stopMusic();
                System.out.println("GAME OVER!");
            } else {
                // Réinitialiser la santé pour la prochaine vie
                health = 100;
                invincibilityTimer = 0;
                // Réinitialiser la position (sera géré par le GameController)
            }
        } else if (lives <= 0 && health <= 0) {
            // Ensure active is set to false even if called multiple times
            active = false;
            AudioManager.getInstance().stopMusic();
        }
    }
    
    // Getters et Setters
    public int getHealth() {
        return health;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getLives() {
        return lives;
    }
    
    public boolean isOnGround() {
        return onGround;
    }
    
    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
        if (onGround) {
            velocity.y = 0;
            canJump = true;
        }
    }
    
    public float getStateTime() {
        return stateTime;
    }
    
    public boolean isFacingRight() {
        return facingRight;
    }
    
    public boolean isRunning() {
        return Math.abs(velocity.x) > 0 && onGround;
    }
    
    public boolean isJumping() {
        return !onGround && velocity.y > 0;
    }
    
    public boolean isInvincible() {
        return invincibilityTimer > 0;
    }
}
