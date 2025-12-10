package com.mario.model.entity;

public interface Collectible {
    void onCollect(Player player);
    int getScoreValue();
    boolean isCollectable();
}
