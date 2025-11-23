package com.mario;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mario.controller.GameController;

/**
 * Point d'entrée principal de l'application Mario Game Engine
 * Initialise la configuration LibGDX et lance le jeu
 */
public class Main {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("Mario Game Engine - LibGDX");
        config.setWindowedMode(800, 600);
        config.setForegroundFPS(60);
        config.useVsync(true);
        
        new Lwjgl3Application(new GameController(), config);
    }
}
