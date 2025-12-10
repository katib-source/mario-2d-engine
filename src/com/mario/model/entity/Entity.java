package com.mario.model.entity;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Rectangle bounds;
    protected float width;
    protected float height;
    protected boolean active;
    protected String type;
    
    public Entity(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
        this.width = width;
        this.height = height;
        this.bounds = new Rectangle(x, y, width, height);
        this.active = true;
    }
    
    public abstract void update(float delta);
    
    protected void updateBounds() {
        bounds.setPosition(position.x, position.y);
    }
    
    public boolean collidesWith(Entity other) {
        return this.bounds.overlaps(other.getBounds());
    }
    
    public boolean collidesWith(Rectangle rect) {
        return this.bounds.overlaps(rect);
    }
    
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
