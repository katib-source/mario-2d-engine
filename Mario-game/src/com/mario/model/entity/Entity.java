package com.mario.model.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Classe abstraite représentant une entité générique dans le jeu
 * Toutes les entités (joueur, ennemis, collectibles) héritent de cette classe
 * 
 * Pattern: Template Method et Héritage
 */
public abstract class Entity {
    protected Vector2 position;      // Position (x, y) de l'entité
    protected Vector2 velocity;      // Vélocité (dx, dy) de l'entité
    protected Rectangle bounds;      // Boîte de collision
    protected float width;           // Largeur de l'entité
    protected float height;          // Hauteur de l'entité
    protected boolean active;        // L'entité est-elle active?
    protected String type;           // Type de l'entité
    
    /**
     * Constructeur de l'entité
     * @param x Position X initiale
     * @param y Position Y initiale
     * @param width Largeur de l'entité
     * @param height Hauteur de l'entité
     */
    public Entity(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        this.active = true;
    }
    
    /**
     * Met à jour l'entité à chaque frame
     * Méthode abstraite à implémenter par les classes filles
     * @param delta Temps écoulé depuis la dernière frame
     */
    public abstract void update(float delta);
    
    /**
     * Met à jour la boîte de collision en fonction de la position
     */
    protected void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }
    
    /**
     * Vérifie si cette entité entre en collision avec une autre
     * @param other L'autre entité
     * @return true si collision détectée
     */
    public boolean collidesWith(Entity other) {
        return this.bounds.overlaps(other.getBounds());
    }
    
    /**
     * Vérifie si cette entité entre en collision avec un rectangle
     * @param rect Le rectangle à tester
     * @return true si collision détectée
     */
    public boolean collidesWith(Rectangle rect) {
        return this.bounds.overlaps(rect);
    }
    
    // Getters et Setters
    public Vector2 getPosition() {
        return position;
    }
    
    public void setPosition(float x, float y) {
        this.position.set(x, y);
        updateBounds();
    }
    
    public Vector2 getVelocity() {
        return velocity;
    }
    
    public void setVelocity(float dx, float dy) {
        this.velocity.set(dx, dy);
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
