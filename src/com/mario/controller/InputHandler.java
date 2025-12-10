package com.mario.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mario.model.entity.Player;

public class InputHandler {
    
    public void handlePlayerInput(Player player) {
        if (player == null || !player.isActive()) return;
        
        player.stopMoving();
        
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player.moveLeft();
        }
        
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || 
            Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
            Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player.jump();
        }
    }
}
