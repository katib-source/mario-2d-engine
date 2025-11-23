package com.mario.model.entity;

/**
 * Interface définissant le comportement d'une entité pouvant être collectée
 * Utilisée pour les pièces, power-ups, etc.
 * 
 * Pattern: Interface (Polymorphisme)
 */
public interface Collectible {
    /**
     * Appelé lorsque l'entité est collectée par le joueur
     * @param player Le joueur qui collecte l'objet
     */
    void onCollect(Player player);
    
    /**
     * Retourne la valeur de score de cet objet
     * @return Points attribués lors de la collecte
     */
    int getScoreValue();
    
    /**
     * Vérifie si l'objet peut encore être collecté
     * @return true si l'objet est toujours disponible
     */
    boolean isCollectable();
}
