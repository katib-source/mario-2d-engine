package com.mario.model.entity;

import com.badlogic.gdx.math.Rectangle;

public class EntityFactory {
    private static EntityFactory instance;

    private EntityFactory() {
    }

    public static EntityFactory getInstance() {
        if (instance == null) {
            instance = new EntityFactory();
        }
        return instance;
    }

    public Entity createEntity(String type, float x, float y) {
        return createEntity(type, x, y, 0, 0, null);
    }

    public Entity createEntity(String type, float x, float y, EntityProperties properties) {
        return createEntity(type, x, y, 0, 0, properties);
    }

    public Entity createEntity(String type, float x, float y, float width, float height, EntityProperties properties) {
        if (type == null || type.isEmpty()) {
            return null;
        }

        String normalizedType = type.toLowerCase().trim();

        switch (normalizedType) {
            case "player":
                return createPlayer(x, y);
            case "coin":
                return createCoin(x, y, properties);
            case "goomba":
                return createGoomba(x, y);
            case "turtle":
            case "koopa":
                return createGoomba(x, y);
            default:
                System.out.println("Unknown entity type: " + normalizedType);
                return null;
        }
    }

    public Entity createFromRectangle(String type, Rectangle rect) {
        return createFromRectangle(type, rect, null);
    }

    public Entity createFromRectangle(String type, Rectangle rect, EntityProperties properties) {
        return createEntity(type, rect.x, rect.y, rect.width, rect.height, properties);
    }

    public EndTrigger createEndTrigger(float x, float y, float width, float height, EntityProperties properties) {
        Rectangle bounds = new Rectangle(x, y, width > 0 ? width : 32, height > 0 ? height : 32);
        EndTrigger endTrigger = new EndTrigger(bounds);
        if (properties != null && properties.getNextLevel() != null) {
            endTrigger.setNextLevel(properties.getNextLevel());
        }
        return endTrigger;
    }

    public EndTrigger createEndTriggerFromRectangle(Rectangle rect, EntityProperties properties) {
        return createEndTrigger(rect.x, rect.y, rect.width, rect.height, properties);
    }

    private Player createPlayer(float x, float y) {
        return new Player(x, y);
    }

    private Coin createCoin(float x, float y, EntityProperties properties) {
        int scoreValue = 10;
        if (properties != null && properties.getScoreValue() > 0) {
            scoreValue = properties.getScoreValue();
        }
        return new Coin(x, y, scoreValue);
    }

    private Goomba createGoomba(float x, float y) {
        return new Goomba(x, y);
    }

    public static class EntityProperties {
        private int scoreValue;
        private String nextLevel;
        private float speed;
        private int health;

        public EntityProperties() {
        }

        public int getScoreValue() {
            return scoreValue;
        }

        public EntityProperties setScoreValue(int scoreValue) {
            this.scoreValue = scoreValue;
            return this;
        }

        public String getNextLevel() {
            return nextLevel;
        }

        public EntityProperties setNextLevel(String nextLevel) {
            this.nextLevel = nextLevel;
            return this;
        }

        public float getSpeed() {
            return speed;
        }

        public EntityProperties setSpeed(float speed) {
            this.speed = speed;
            return this;
        }

        public int getHealth() {
            return health;
        }

        public EntityProperties setHealth(int health) {
            this.health = health;
            return this;
        }
    }
}
