package com.mario.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire de textures pour le jeu
 * Pattern: Singleton - Une seule instance pour gérer toutes les textures
 */
public class TextureManager {
    private static TextureManager instance;
    private Map<String, Texture> textures;

    private TextureManager() {
        textures = new HashMap<>();
        loadTextures();
    }

    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }

    /**
     * Charge toutes les textures du jeu
     */
    private void loadTextures() {
        // Créer des textures procédurales temporaires
        // Vous pouvez remplacer cela par de vraies images plus tard
        textures.put("player", createPlayerTexture());
        textures.put("goomba", createGoombaTexture());
        textures.put("coin", createCoinTexture());
        textures.put("tile_ground", createGroundTileTexture());
        textures.put("tile_brick", createBrickTileTexture());

        System.out.println("Textures chargées: " + textures.size() + " textures");
    }

    /**
     * Crée une texture procédurale pour le joueur (Mario)
     */
    private Texture createPlayerTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

        // Corps rouge
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(8, 8, 16, 16);

        // Chapeau rouge
        pixmap.setColor(new Color(0.8f, 0.1f, 0.1f, 1));
        pixmap.fillRectangle(6, 4, 20, 8);

        // Visage (beige)
        pixmap.setColor(new Color(1f, 0.85f, 0.7f, 1));
        pixmap.fillRectangle(10, 12, 12, 8);

        // Yeux
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(12, 16, 3, 3);
        pixmap.fillRectangle(17, 16, 3, 3);

        // Moustache
        pixmap.fillRectangle(10, 13, 12, 2);

        // Jambes bleues
        pixmap.setColor(Color.BLUE);
        pixmap.fillRectangle(10, 24, 5, 8);
        pixmap.fillRectangle(17, 24, 5, 8);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Crée une texture procédurale pour le Goomba
     */
    private Texture createGoombaTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

        // Corps marron
        pixmap.setColor(new Color(0.55f, 0.27f, 0.07f, 1));
        pixmap.fillRectangle(4, 8, 24, 18);

        // Forme de champignon (arrondie en haut)
        pixmap.fillRectangle(6, 6, 20, 4);
        pixmap.fillRectangle(8, 4, 16, 4);

        // Yeux blancs
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(8, 14, 6, 6);
        pixmap.fillRectangle(18, 14, 6, 6);

        // Pupilles noires
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(10, 16, 3, 3);
        pixmap.fillRectangle(20, 16, 3, 3);

        // Sourcils en colère
        pixmap.fillRectangle(8, 12, 8, 2);
        pixmap.fillRectangle(16, 12, 8, 2);

        // Pieds
        pixmap.setColor(new Color(0.4f, 0.2f, 0.05f, 1));
        pixmap.fillRectangle(6, 26, 8, 6);
        pixmap.fillRectangle(18, 26, 8, 6);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Crée une texture procédurale pour la pièce
     */
    private Texture createCoinTexture() {
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);

        // Fond doré
        pixmap.setColor(Color.GOLD);
        pixmap.fillCircle(8, 8, 7);

        // Bordure plus foncée
        pixmap.setColor(new Color(0.8f, 0.6f, 0f, 1));
        pixmap.drawCircle(8, 8, 7);
        pixmap.drawCircle(8, 8, 6);

        // Reflet brillant
        pixmap.setColor(Color.YELLOW);
        pixmap.fillCircle(6, 5, 2);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Crée une texture procédurale pour les tuiles de sol
     */
    private Texture createGroundTileTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

        // Fond marron
        pixmap.setColor(new Color(0.55f, 0.35f, 0.2f, 1));
        pixmap.fill();

        // Bordure plus foncée
        pixmap.setColor(new Color(0.4f, 0.25f, 0.15f, 1));
        pixmap.drawRectangle(0, 0, 32, 32);

        // Motif de terre/herbe
        pixmap.setColor(new Color(0.45f, 0.3f, 0.18f, 1));
        pixmap.fillRectangle(4, 4, 6, 2);
        pixmap.fillRectangle(14, 8, 8, 2);
        pixmap.fillRectangle(8, 16, 5, 2);
        pixmap.fillRectangle(20, 20, 7, 2);
        pixmap.fillRectangle(5, 24, 6, 2);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Crée une texture procédurale pour les briques
     */
    private Texture createBrickTileTexture() {
        Pixmap pixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);

        // Fond brique
        pixmap.setColor(new Color(0.7f, 0.3f, 0.1f, 1));
        pixmap.fill();

        // Lignes de mortier
        pixmap.setColor(new Color(0.5f, 0.5f, 0.5f, 1));
        pixmap.drawLine(0, 16, 32, 16);
        pixmap.drawLine(16, 0, 16, 16);
        pixmap.drawLine(16, 16, 16, 32);

        // Ombres pour effet 3D
        pixmap.setColor(new Color(0.5f, 0.2f, 0.05f, 1));
        pixmap.drawRectangle(0, 0, 32, 32);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Récupère une texture par son nom
     */
    public Texture getTexture(String name) {
        return textures.get(name);
    }

    /**
     * Charge une texture depuis un fichier
     * Utilisez cette méthode quand vous avez de vraies images
     */
    public void loadTextureFromFile(String name, String path) {
        try {
            Texture texture = new Texture(Gdx.files.internal(path));
            textures.put(name, texture);
            System.out.println("Texture chargée: " + name + " depuis " + path);
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de la texture: " + path);
            e.printStackTrace();
        }
    }

    /**
     * Libère toutes les textures
     */
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        textures.clear();
    }
}

