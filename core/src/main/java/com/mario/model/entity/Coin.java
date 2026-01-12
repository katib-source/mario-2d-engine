package com.mario.model.entity;

import com.mario.observer.GameEvent;
import com.mario.observer.GameEventManager;

public class Coin extends Entity implements Collectible {
    private int scoreValue;
    private boolean collected;
    
    public Coin(float x, float y, int scoreValue) {
        super(x, y, 16, 16);
        this.type = "coin";
        this.scoreValue = scoreValue;
        this.collected = false;
    }
    
    public Coin(float x, float y) {
        this(x, y, 10);
    }
    
    @Override
    public void update(float delta) {
    }
    
    @Override
    public void onCollect(Player player) {
        if (!collected) {
            player.addScore(scoreValue);
            collected = true;
            active = false;
            GameEventManager.getInstance().notify(GameEvent.COIN_COLLECTED, this);
        }
    }
    
    @Override
    public int getScoreValue() {
        return scoreValue;
    }
    
    @Override
    public boolean isCollectable() {
        return !collected && active;
    }
}
