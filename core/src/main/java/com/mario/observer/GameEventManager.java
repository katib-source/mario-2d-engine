package com.mario.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEventManager {
    private static GameEventManager instance;
    private final Map<GameEvent, List<GameEventListener>> listeners;

    private GameEventManager() {
        listeners = new HashMap<>();
        for (GameEvent event : GameEvent.values()) {
            listeners.put(event, new ArrayList<>());
        }
    }

    public static GameEventManager getInstance() {
        if (instance == null) {
            instance = new GameEventManager();
        }
        return instance;
    }

    public void subscribe(GameEvent event, GameEventListener listener) {
        List<GameEventListener> eventListeners = listeners.get(event);
        if (!eventListeners.contains(listener)) {
            eventListeners.add(listener);
        }
    }

    public void unsubscribe(GameEvent event, GameEventListener listener) {
        listeners.get(event).remove(listener);
    }

    public void notify(GameEvent event) {
        notify(event, null);
    }

    public void notify(GameEvent event, Object data) {
        List<GameEventListener> eventListeners = listeners.get(event);
        for (int i = 0; i < eventListeners.size(); i++) {
            eventListeners.get(i).onGameEvent(event, data);
        }
    }

    public void clear() {
        for (GameEvent event : GameEvent.values()) {
            listeners.get(event).clear();
        }
    }
}
