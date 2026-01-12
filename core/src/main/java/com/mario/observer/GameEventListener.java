package com.mario.observer;

public interface GameEventListener {
    void onGameEvent(GameEvent event, Object data);
}
