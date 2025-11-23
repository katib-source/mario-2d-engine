package com.mario.model.entity;

/**
 * Interface définissant le comportement des ennemis
 * Permet de créer différents types d'ennemis avec des stratégies différentes
 * 
 * Pattern: Strategy (via l'interface)
 */
public interface Enemy {
    /**
     * Définit le comportement de déplacement de l'ennemi
     * @param delta Temps écoulé depuis la dernière frame
     */
    void move(float delta);
    
    /**
     * Appelé lorsque l'ennemi entre en collision avec le joueur
     * @param player Le joueur en collision
     */
    void onPlayerCollision(Player player);
    
    /**
     * Appelé lorsque l'ennemi est détruit
     */
    void onDestroy();
    
    /**
     * Retourne les dégâts infligés au joueur
     * @return Montant des dégâts
     */
    int getDamage();
}
