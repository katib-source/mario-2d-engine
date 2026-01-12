package com.mario.model.entity;

public interface Enemy {
    void move(float delta);
    void onPlayerCollision(Player player);
    void onDestroy();
    int getDamage();
}
