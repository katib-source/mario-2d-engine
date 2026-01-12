package com.mario.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Manages all game audio - music and sound effects
 * Pattern: Singleton
 */
public class AudioManager {
    private static AudioManager instance;
    private Map<String, Sound> sounds;
    private Map<String, Music> music;
    private Music currentMusic;
    private boolean musicEnabled = true;
    private boolean soundEnabled = true;
    
    private AudioManager() {
        sounds = new HashMap<>();
        music = new HashMap<>();
        loadAudio();
    }
    
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    
    /**
     * Load all audio files
     */
    private void loadAudio() {
        try {
            // Load music
            music.put("main", Gdx.audio.newMusic(Gdx.files.internal("audio/music/mario_music.ogg")));
            
            // Load sound effects
            sounds.put("coin", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/coin.wav")));
            sounds.put("jump", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/bump.wav")));
            sounds.put("stomp", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/stomp.wav")));
            sounds.put("breakblock", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/breakblock.wav")));
            sounds.put("powerup", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/powerup.wav")));
            sounds.put("powerup_spawn", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/powerup_spawn.wav")));
            sounds.put("powerdown", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/powerdown.wav")));
            sounds.put("die", Gdx.audio.newSound(Gdx.files.internal("audio/sounds/mariodie.wav")));
            
            System.out.println("Audio loaded: " + music.size() + " music tracks, " + sounds.size() + " sound effects");
        } catch (Exception e) {
            System.err.println("Error loading audio: " + e.getMessage());
        }
    }
    
    /**
     * Play background music
     */
    public void playMusic(String name) {
        if (!musicEnabled) return;
        
        Music newMusic = music.get(name);
        if (newMusic != null) {
            if (currentMusic != null) {
                currentMusic.stop();
            }
            currentMusic = newMusic;
            currentMusic.setLooping(true);
            currentMusic.setVolume(0.3f);
            currentMusic.play();
        }
    }
    
    /**
     * Stop current music
     */
    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }
    
    /**
     * Play a sound effect
     */
    public void playSound(String name) {
        playSound(name, 1.0f);
    }
    
    /**
     * Play a sound effect with custom volume
     */
    public void playSound(String name, float volume) {
        if (!soundEnabled) return;
        
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.play(volume);
        }
    }
    
    /**
     * Dispose of all audio resources
     */
    public void dispose() {
        for (Sound sound : sounds.values()) {
            sound.dispose();
        }
        for (Music m : music.values()) {
            m.dispose();
        }
        sounds.clear();
        music.clear();
    }
}
