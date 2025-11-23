package com.mario.model.entity;

/**
 * Classe représentant un ennemi de type Goomba
 * Se déplace de gauche à droite et change de direction aux obstacles
 * 
 * Pattern: Implémentation d'interface + Héritage
 */
public class Goomba extends Entity implements Enemy {
    private static final float MOVE_SPEED = 50f;  // Vitesse de déplacement
    private static final float GRAVITY = -800f;   // Gravité
    private int damage;                           // Dégâts infligés
    private float direction;                      // Direction: -1 (gauche) ou 1 (droite)
    
    /**
     * Constructeur du Goomba
     * @param x Position X
     * @param y Position Y
     */
    public Goomba(float x, float y) {
        super(x, y, 32, 32); // Taille 32x32 pixels
        this.type = "goomba";
        this.damage = 10;
        this.direction = -1; // Commence en allant vers la gauche
    }
    
    @Override
    public void update(float delta) {
        // Appliquer la gravité
        velocity.y += GRAVITY * delta;
        
        // Déplacement horizontal
        move(delta);
        
        // Mettre à jour la position
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        
        // Mettre à jour les bounds
        updateBounds();
    }
    
    @Override
    public void move(float delta) {
        // Se déplace dans la direction actuelle
        velocity.x = MOVE_SPEED * direction;
    }
    
    /**
     * Change la direction de déplacement
     */
    public void reverseDirection() {
        direction *= -1;
    }
    
    @Override
    public void onPlayerCollision(Player player) {
        // Infliger des dégâts au joueur
        player.takeDamage(damage);
    }
    
    @Override
    public void onDestroy() {
        // Désactiver l'ennemi
        active = false;
    }
    
    @Override
    public int getDamage() {
        return damage;
    }
    
    /**
     * Appelé lorsque le joueur saute sur l'ennemi
     */
    public void stompedByPlayer() {
        onDestroy();
    }
}
