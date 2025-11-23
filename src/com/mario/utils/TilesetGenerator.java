package com.mario.utils;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;

/**
 * Utilitaire pour créer un tileset simple pour Tiled
 * Exécutez cette classe une fois pour générer le fichier tileset.png
 */
public class TilesetGenerator {

    public static void generateTileset() {
        // Créer un tileset de 4 tiles (2x2) de 32x32 pixels chacune
        int tileSize = 32;
        int columns = 4;
        int rows = 1;

        Pixmap pixmap = new Pixmap(tileSize * columns, tileSize * rows, Pixmap.Format.RGBA8888);

        // Tile 1: Vide (transparent)
        // Pas de code nécessaire, déjà transparent

        // Tile 2: Sol/Terre (position x=32)
        pixmap.setColor(new Color(0.55f, 0.35f, 0.2f, 1));
        pixmap.fillRectangle(tileSize, 0, tileSize, tileSize);
        pixmap.setColor(new Color(0.4f, 0.25f, 0.15f, 1));
        pixmap.drawRectangle(tileSize, 0, tileSize, tileSize);

        // Tile 3: Brique (position x=64)
        pixmap.setColor(new Color(0.7f, 0.3f, 0.1f, 1));
        pixmap.fillRectangle(tileSize * 2, 0, tileSize, tileSize);
        pixmap.setColor(new Color(0.5f, 0.5f, 0.5f, 1));
        pixmap.drawLine(tileSize * 2, tileSize/2, tileSize * 3, tileSize/2);
        pixmap.drawLine(tileSize * 2 + tileSize/2, 0, tileSize * 2 + tileSize/2, tileSize);

        // Tile 4: Question block (position x=96)
        pixmap.setColor(new Color(1f, 0.8f, 0f, 1));
        pixmap.fillRectangle(tileSize * 3, 0, tileSize, tileSize);
        pixmap.setColor(new Color(0.8f, 0.6f, 0f, 1));
        pixmap.drawRectangle(tileSize * 3, 0, tileSize, tileSize);
        // Dessiner un "?"
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(tileSize * 3 + 13, 8, 6, 3);
        pixmap.fillRectangle(tileSize * 3 + 16, 11, 3, 6);
        pixmap.fillRectangle(tileSize * 3 + 13, 17, 6, 3);
        pixmap.fillRectangle(tileSize * 3 + 13, 23, 6, 3);

        // Sauvegarder le tileset
        try {
            PixmapIO.writePNG(Gdx.files.local("tileset.png"), pixmap);
            System.out.println("Tileset créé: tileset.png");
            System.out.println("Copiez ce fichier dans assets/textures/tiles/");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création du tileset: " + e.getMessage());
        }

        pixmap.dispose();
    }
}

