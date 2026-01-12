package com.mario.model.entity;

public class Goomba extends Entity implements Enemy {
    private static final float MOVE_SPEED = 50f;
    private static final float GRAVITY = -800f;
    private final int damage;
    private float direction;
    private float stateTime;
    
    public Goomba(float x, float y) {
        super(x, y, 32, 32);
        this.type = "goomba";
        this.damage = 10;
        this.direction = -1;
        this.stateTime = 0;
    }
    
    @Override
    public void update(float delta) {
        stateTime += delta;
        velocity.y += GRAVITY * delta;
        move(delta);
        
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
        updateBounds();
    }
    
    @Override
    public void move(float delta) {
        velocity.x = MOVE_SPEED * direction;
    }
    
    public void reverseDirection() {
        direction *= -1;
    }
    
    @Override
    public void onPlayerCollision(Player player) {
        player.takeDamage(damage);
    }
    
    @Override
    public void onDestroy() {
        active = false;
    }
    
    @Override
    public int getDamage() {
        return damage;
    }
    
    public void stompedByPlayer() {
        onDestroy();
    }
    
    public float getStateTime() {
        return stateTime;
    }
}
