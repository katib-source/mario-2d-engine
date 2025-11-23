package com.mario.model.entity;

import com.mario.view.AudioManager;

/**
 * Classe représentant une pièce à collecter
 * Implémente l'interface Collectible
 * 
 * Pattern: Implémentation d'interface + Héritage
 */
public class Coin extends Entity implements Collectible {
    private int scoreValue;      // Valeur en points
    private boolean collected;   // A été collectée?
    
    /**
     * Constructeur de la pièce
     * @param x Position X
     * @param y Position Y
     * @param scoreValue Valeur de la pièce
     */
    public Coin(float x, float y, int scoreValue) {
        super(x, y, 16, 16); // Taille 16x16 pixels
        this.type = "coin";
        this.scoreValue = scoreValue;
        this.collected = false;
    }
    
    /**
     * Constructeur avec valeur par défaut
     * @param x Position X
     * @param y Position Y
     */
    public Coin(float x, float y) {
        this(x, y, 10); // Valeur par défaut: 10 points
    }
    
    @Override
    public void update(float delta) {
        // Les pièces peuvent avoir une animation de rotation
        // Pour l'instant, elles restent statiques
    }
    
    @Override
    public void onCollect(Player player) {
        if (!collected) {
            player.addScore(scoreValue);
            collected = true;
            active = false; // Désactiver la pièce après collecte
            AudioManager.getInstance().playSound("coin");
        }
    }
    
    @Override
    public int getScoreValue() {
        return scoreValue;
    }
    
    @Override
    public boolean isCollectable() {
        return !collected && active;
    }
}
