package com.mario.controller;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.mario.model.entity.Coin;
import com.mario.model.entity.Enemy;
import com.mario.model.entity.Entity;
import com.mario.model.entity.Goomba;
import com.mario.model.entity.Player;
import com.mario.model.level.Level;
import com.mario.model.level.LevelLoader;
import com.mario.model.physics.PhysicsEngine;
import com.mario.view.AudioManager;
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
    private AudioManager audioManager;
    
    @Override
    public void create() {
        // Initialiser les composants
        levelLoader = new LevelLoader();
        renderer = new GameRenderer();
        physicsEngine = new PhysicsEngine();
        inputHandler = new InputHandler();
        audioManager = AudioManager.getInstance();
        
        // Charger le niveau initial
        loadLevel("levels/level11.json");
        
        // Démarrer la musique
        audioManager.playMusic("main");
    }
    
    /**
     * Charge un niveau depuis un fichier JSON
     * @param levelPath Chemin vers le fichier JSON du niveau
     * @return Le niveau chargé
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
        System.out.println("Joueur créé à position: " + player.getPosition().x + ", " + player.getPosition().y);

        // Ajouter quelques ennemis
        currentLevel.addEntity(new Goomba(300, 200));
        currentLevel.addEntity(new Goomba(500, 200));
        System.out.println("Ennemis créés");

        // Ajouter des pièces
        currentLevel.addEntity(new Coin(250, 250));
        currentLevel.addEntity(new Coin(280, 250));
        currentLevel.addEntity(new Coin(310, 250));
        currentLevel.addEntity(new Coin(450, 300));
        System.out.println("Pièces créées");

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
        System.out.println("Niveau de test créé avec " + currentLevel.getEntities().size() + " entités");
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
        if (currentLevel == null) return;

        // Vérifier si le joueur appuie sur R pour redémarrer
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            if (currentLevel.getPlayer() == null || !currentLevel.getPlayer().isActive() || currentLevel.getPlayer().getLives() <= 0) {
                System.out.println("Restarting game...");
                loadLevel("levels/level11.json");
                return;
            }
        }

        if (currentLevel.getPlayer() == null || !currentLevel.getPlayer().isActive()) return;

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
        audioManager.dispose();
    }
}
