package com.mario.model.entity;

import com.mario.view.AudioManager;

public class Player extends Entity {
    private static final float MOVE_SPEED = 150f;
    private static final float JUMP_VELOCITY = 400f;
    private static final float GRAVITY = -800f;
    private static final float MAX_FALL_SPEED = -500f;
    private static final float INVINCIBILITY_DURATION = 1.0f;
    
    private int health;
    private int score;
    private boolean onGround;
    private boolean canJump;
    private int lives;
    private float stateTime;
    private boolean facingRight;
    private float invincibilityTimer;
    
    public Player(float x, float y) {
        super(x, y, 32, 32);
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
        stateTime += delta;
        
        if (invincibilityTimer > 0) {
            invincibilityTimer -= delta;
        }
        
        if (velocity.x > 0) {
            facingRight = true;
        } else if (velocity.x < 0) {
            facingRight = false;
        }
        
        if (position.y < -100) {
            health = 0;
            invincibilityTimer = 0;
            die();
            return;
        }
        
        if (!onGround) {
            velocity.y += GRAVITY * delta;
            if (velocity.y < MAX_FALL_SPEED) {
                velocity.y = MAX_FALL_SPEED;
            }
        } else {
            velocity.y = 0;
        }
        
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        updateBounds();
    }
    
    public void moveLeft() {
        velocity.x = -MOVE_SPEED;
    }
    
    public void moveRight() {
        velocity.x = MOVE_SPEED;
    }
    
    public void stopMoving() {
        velocity.x = 0;
    }
    
    public void jump() {
        if (onGround && canJump) {
            velocity.y = JUMP_VELOCITY;
            onGround = false;
            AudioManager.getInstance().playSound("jump");
        }
    }
    
    public void addScore(int points) {
        this.score += points;
    }
    
    public void takeDamage(int damage) {
        if (health <= 0 || invincibilityTimer > 0) return;

        this.health -= damage;
        invincibilityTimer = INVINCIBILITY_DURATION;
        
        if (this.health <= 0) {
            this.health = 0;
            die();
        }
    }
    
    public void heal(int amount) {
        this.health += amount;
        if (this.health > 100) {
            this.health = 100;
        }
    }
    
    private void die() {
        if (lives > 0 && health <= 0 && active) {
            lives--;
            AudioManager.getInstance().playSound("die");

            if (lives <= 0) {
                active = false;
                AudioManager.getInstance().stopMusic();
            } else {
                health = 100;
                invincibilityTimer = 0;
            }
        } else if (lives <= 0 && health <= 0) {
            active = false;
            AudioManager.getInstance().stopMusic();
        }
    }
    
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
