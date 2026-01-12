package com.mario.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Manages sprite animations for game entities
 * Creates and stores animations from sprite sheets
 */
public class SpriteAnimator {
    private Map<String, Animation<TextureRegion>> animations;
    private Map<String, TextureRegion> staticSprites;
    
    public SpriteAnimator() {
        animations = new HashMap<>();
        staticSprites = new HashMap<>();
        loadAnimations();
    }
    
    /**
     * Load all sprite animations from sprite sheets
     */
    private void loadAnimations() {
        // Load Mario animations
        loadMarioAnimations();
        
        // Load Goomba animations
        loadGoombaAnimations();
        
        // Load Turtle animations (if available)
        loadTurtleAnimations();
        
        System.out.println("Loaded " + animations.size() + " animations and " + staticSprites.size() + " static sprites");
    }
    
    /**
     * Load Mario sprite animations
     */
    private void loadMarioAnimations() {
        try {
            Texture marioSheet = new Texture(Gdx.files.internal("textures/entities/little_mario.png"));
            
            // Mario standing sprite (frame 0)
            TextureRegion marioStand = new TextureRegion(marioSheet, 0, 0, 16, 16);
            staticSprites.put("mario_stand", marioStand);
            
            // Mario jump sprite (frame 5 - pixel 80)
            TextureRegion marioJump = new TextureRegion(marioSheet, 80, 0, 16, 16);
            staticSprites.put("mario_jump", marioJump);
            
            // Mario running animation (frames 1, 2, 3)
            Array<TextureRegion> runFrames = new Array<>();
            for (int i = 1; i < 4; i++) {
                runFrames.add(new TextureRegion(marioSheet, i * 16, 0, 16, 16));
            }
            Animation<TextureRegion> marioRun = new Animation<>(0.1f, runFrames);
            animations.put("mario_run", marioRun);
            
        } catch (Exception e) {
            System.err.println("Failed to load Mario sprites: " + e.getMessage());
            createFallbackMarioSprites();
        }
    }
    
    /**
     * Load Goomba sprite animations
     */
    private void loadGoombaAnimations() {
        try {
            Texture goombaSheet = new Texture(Gdx.files.internal("textures/entities/goomba.png"));
            
            // Goomba walking animation (2 frames)
            Array<TextureRegion> walkFrames = new Array<>();
            for (int i = 0; i < 2; i++) {
                walkFrames.add(new TextureRegion(goombaSheet, i * 16, 0, 16, 16));
            }
            Animation<TextureRegion> goombaWalk = new Animation<>(0.4f, walkFrames);
            animations.put("goomba_walk", goombaWalk);
            
            // Goomba squashed sprite (frame 2)
            TextureRegion goombaSquashed = new TextureRegion(goombaSheet, 32, 0, 16, 16);
            staticSprites.put("goomba_squashed", goombaSquashed);
            
        } catch (Exception e) {
            System.err.println("Failed to load Goomba sprites: " + e.getMessage());
        }
    }
    
    /**
     * Load Turtle sprite animations
     */
    private void loadTurtleAnimations() {
        try {
            Texture turtleSheet = new Texture(Gdx.files.internal("textures/entities/turtle.png"));
            
            // Turtle walking animation (2 frames)
            Array<TextureRegion> walkFrames = new Array<>();
            walkFrames.add(new TextureRegion(turtleSheet, 0, 0, 16, 24));
            walkFrames.add(new TextureRegion(turtleSheet, 16, 0, 16, 24));
            Animation<TextureRegion> turtleWalk = new Animation<>(0.2f, walkFrames);
            animations.put("turtle_walk", turtleWalk);
            
            // Turtle shell sprite
            TextureRegion turtleShell = new TextureRegion(turtleSheet, 64, 0, 16, 24);
            staticSprites.put("turtle_shell", turtleShell);
            
        } catch (Exception e) {
            System.err.println("Failed to load Turtle sprites: " + e.getMessage());
        }
    }
    
    /**
     * Create fallback sprites when files can't be loaded
     */
    private void createFallbackMarioSprites() {
        // Create simple placeholder sprites if loading fails
        System.out.println("Using fallback sprites for Mario");
    }
    
    /**
     * Get a static sprite by name
     */
    public TextureRegion getStaticSprite(String name) {
        return staticSprites.get(name);
    }
    
    /**
     * Get the current frame of an animation
     */
    public TextureRegion getAnimationFrame(String animationName, float stateTime, boolean looping) {
        Animation<TextureRegion> animation = animations.get(animationName);
        if (animation != null) {
            return animation.getKeyFrame(stateTime, looping);
        }
        return null;
    }
    
    /**
     * Dispose of all textures
     */
    public void dispose() {
        // TextureRegions don't need to be disposed, but textures do
        // In a more complete implementation, we'd track textures separately
    }
}
