package com.mario.view;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.mario.observer.GameEvent;
import com.mario.observer.GameEventListener;
import com.mario.observer.GameEventManager;

public class AudioManager implements GameEventListener {
    private static AudioManager instance;
    private final Map<String, Sound> sounds;
    private final Map<String, Music> music;
    private Music currentMusic;
    private final boolean musicEnabled = true;
    private final boolean soundEnabled = true;
    
    private AudioManager() {
        this.sounds = new HashMap<>();
        this.music = new HashMap<>();
        loadAudio();
        registerEvents();
    }
    
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    private void registerEvents() {
        GameEventManager eventManager = GameEventManager.getInstance();
        eventManager.subscribe(GameEvent.COIN_COLLECTED, this);
        eventManager.subscribe(GameEvent.ENEMY_STOMPED, this);
        eventManager.subscribe(GameEvent.PLAYER_DAMAGED, this);
        eventManager.subscribe(GameEvent.PLAYER_DIED, this);
        eventManager.subscribe(GameEvent.PLAYER_JUMP, this);
        eventManager.subscribe(GameEvent.LEVEL_COMPLETED, this);
        eventManager.subscribe(GameEvent.POWERUP_COLLECTED, this);
        eventManager.subscribe(GameEvent.BLOCK_BROKEN, this);
    }

    @Override
    public void onGameEvent(GameEvent event, Object data) {
        switch (event) {
            case COIN_COLLECTED:
                playSound("coin");
                break;
            case ENEMY_STOMPED:
                playSound("stomp");
                break;
            case PLAYER_DAMAGED:
                playSound("powerdown");
                break;
            case PLAYER_DIED:
                playSound("die");
                break;
            case PLAYER_JUMP:
                playSound("jump");
                break;
            case LEVEL_COMPLETED:
                playSound("powerup");
                break;
            case POWERUP_COLLECTED:
                playSound("powerup");
                break;
            case BLOCK_BROKEN:
                playSound("breakblock");
                break;
        }
    }
    
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
    
    public void stopMusic() {
        if (currentMusic != null) {
            currentMusic.stop();
        }
    }
    
    public void playSound(String name) {
        playSound(name, 1.0f);
    }
    
    public void playSound(String name, float volume) {
        if (!soundEnabled) return;
        
        Sound sound = sounds.get(name);
        if (sound != null) {
            sound.play(volume);
        }
    }
    
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
