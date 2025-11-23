package com.mario.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.mario.model.entity.*;
import com.mario.model.level.Level;
import com.mario.model.level.LevelLoader;
import com.mario.model.physics.PhysicsEngine;
import com.mario.view.GameRenderer;

/**
 * Contrôleur principal du jeu
 * Pattern: MVC - Contrôleur
 * Gère la boucle de jeu, les inputs et orchestre Model et View
 */
public class GameController extends ApplicationAdapter {
    private Level currentLevel;
    private LevelLoader levelLoader;
    private GameRenderer renderer;
    private PhysicsEngine physicsEngine;
    private InputHandler inputHandler;
    
    @Override
    public void create() {
        // Initialiser les composants
        levelLoader = new LevelLoader();
        renderer = new GameRenderer();
        physicsEngine = new PhysicsEngine();
        inputHandler = new InputHandler();
        
        // Charger le niveau initial
        loadLevel("levels/level1.json");
    }
    
    /**
     * Charge un niveau depuis un fichier JSON
     * @param levelPath Chemin vers le fichier
     */
    public void loadLevel(String levelPath) {
        try {
            currentLevel = levelLoader.loadLevel(levelPath);
            System.out.println("Niveau chargé: " + levelPath);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du niveau: " + e.getMessage());
            e.printStackTrace();
            // Créer un niveau de test si le chargement échoue
            createTestLevel();
        }
    }
    
    /**
     * Crée un niveau de test pour démonstration
     */
    private void createTestLevel() {
        System.out.println("Création d'un niveau de test...");
        currentLevel = new Level(25, 15, 32, 32);
        
        // Ajouter le joueur
        Player player = new Player(100, 400);
        currentLevel.addEntity(player);
        
        // Ajouter quelques ennemis
        currentLevel.addEntity(new Goomba(300, 200));
        currentLevel.addEntity(new Goomba(500, 200));
        
        // Ajouter des pièces
        currentLevel.addEntity(new Coin(250, 250));
        currentLevel.addEntity(new Coin(280, 250));
        currentLevel.addEntity(new Coin(310, 250));
        currentLevel.addEntity(new Coin(450, 300));
        
        // Créer un sol simple
        int[] groundData = new int[25 * 15];
        // Remplir la dernière ligne (sol)
        for (int x = 0; x < 25; x++) {
            groundData[14 * 25 + x] = 1;
            groundData[13 * 25 + x] = 1;
        }
        // Ajouter quelques plateformes
        for (int x = 5; x < 10; x++) {
            groundData[10 * 25 + x] = 1;
        }
        for (int x = 15; x < 20; x++) {
            groundData[8 * 25 + x] = 1;
        }
        
        currentLevel.addTileLayer("collision", groundData);
    }
    
    @Override
    public void render() {
        // Effacer l'écran
        Gdx.gl.glClearColor(0.5f, 0.7f, 1.0f, 1.0f); // Ciel bleu
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Obtenir le delta time
        float delta = Gdx.graphics.getDeltaTime();
        
        // Gérer les inputs
        handleInput(delta);
        
        // Mettre à jour le niveau
        update(delta);
        
        // Rendre le niveau
        renderer.render(currentLevel);
    }
    
    /**
     * Gère les entrées utilisateur
     * @param delta Temps écoulé
     */
    private void handleInput(float delta) {
        if (currentLevel == null || currentLevel.getPlayer() == null) return;
        
        Player player = currentLevel.getPlayer();
        
        // Gestion du mouvement
        inputHandler.handlePlayerInput(player);
    }
    
    /**
     * Met à jour la logique du jeu
     * @param delta Temps écoulé
     */
    private void update(float delta) {
        if (currentLevel == null) return;
        
        // Mettre à jour toutes les entités
        currentLevel.update(delta);
        
        // Gérer les collisions
        handleCollisions();
    }
    
    /**
     * Gère toutes les collisions
     */
    private void handleCollisions() {
        if (currentLevel == null || currentLevel.getPlayer() == null) return;
        
        Player player = currentLevel.getPlayer();
        
        // Collisions joueur-terrain
        physicsEngine.handlePlayerTerrainCollision(player, currentLevel.getSolidTiles());
        
        // Collisions joueur-ennemis
        physicsEngine.handlePlayerEnemyCollision(player, currentLevel);
        
        // Collisions joueur-collectibles
        physicsEngine.handlePlayerCollectibleCollision(player, currentLevel);
        
        // Collisions ennemis-terrain
        for (Entity entity : currentLevel.getEntities()) {
            if (entity instanceof Enemy && entity.isActive()) {
                physicsEngine.handleEnemyTerrainCollision(entity, currentLevel.getSolidTiles());
            }
        }
    }
    
    @Override
    public void dispose() {
        renderer.dispose();
    }
}
