package com.mario.model.entity;

import com.badlogic.gdx.math.Rectangle;

public class EndTrigger {
    
    private final Rectangle bounds;
    private String nextLevel; // Optional: path to next level (e.g., "level2.tmx")
    
    public EndTrigger(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.nextLevel = null;
    }
    
    public EndTrigger(Rectangle bounds) {
        this.bounds = new Rectangle(bounds);
        this.nextLevel = null;
    }
    
    public EndTrigger(Rectangle bounds, String nextLevel) {
        this.bounds = new Rectangle(bounds);
        this.nextLevel = nextLevel;
    }
    
    public Rectangle getBounds() {
        return bounds;
    }
    
    public String getNextLevel() {
        return nextLevel;
    }
    
    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }
    
    public boolean hasNextLevel() {
        return nextLevel != null && !nextLevel.isEmpty();
    }
}
