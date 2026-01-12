package com.mario.controller;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mario.model.entity.Player;

public class InputHandler {
    
    private float jumpBufferTime = 0f;
    private static final float JUMP_BUFFER_DURATION = 0.15f; 
    
    public void update(float deltaTime) {
        // Decrease jump buffer timer
        if (jumpBufferTime > 0) {
            jumpBufferTime -= deltaTime;
        }
        
        // Detect jump input and store it in buffer
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || 
            Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
            Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            jumpBufferTime = JUMP_BUFFER_DURATION;
        }
    }
    
    public void handlePlayerInput(Player player) {
        if (player == null || !player.isActive()) return;
        
        player.stopMoving();
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player.moveLeft();
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }
        
        // Use buffered jump - more responsive and forgiving
        if (jumpBufferTime > 0 && player.isOnGround()) {
            player.jump();
            jumpBufferTime = 0; // Consume the buffered jump
        }
    }
}
