package com.mario.model.entity;

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
    }
    
    @Override
    public void update(float delta) {
        // Appliquer la gravité
        if (!onGround) {
            velocity.y += GRAVITY * delta;
            if (velocity.y < MAX_FALL_SPEED) {
                velocity.y = MAX_FALL_SPEED;
            }
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
        this.health -= damage;
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
        lives--;
        if (lives <= 0) {
            active = false; // Game Over
        } else {
            // Réinitialiser la santé pour la prochaine vie
            health = 100;
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
}
