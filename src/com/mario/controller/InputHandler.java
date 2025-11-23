package com.mario.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mario.model.entity.Player;

/**
 * Classe gérant les entrées utilisateur
 * Sépare la logique d'input du contrôleur principal
 */
public class InputHandler {
    
    /**
     * Gère les inputs du joueur
     * @param player Le joueur à contrôler
     */
    public void handlePlayerInput(Player player) {
        if (player == null || !player.isActive()) return;
        
        // Réinitialiser la vélocité horizontale
        player.stopMoving();
        
        // Mouvement gauche
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.Q)) {
            player.moveLeft();
        }
        
        // Mouvement droite
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        }
        
        // Saut
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || 
            Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
            Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player.jump();
        }
    }
}
