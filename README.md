# Mario Game Engine - LibGDX

A 2D platformer game engine built with LibGDX in Java. Levels are configured via Tiled Map Editor.

## Architecture

MVC pattern with Factory Method for entity creation.

## Quick Start

```bash
# Windows
.\gradlew.bat run

# Linux/Mac
./gradlew run
```

## Creating Levels

1. Create map in Tiled Map Editor
2. Add layers: `Collision` (tiles), `Entities` (objects)
3. Supported entity types: `player`, `goomba`, `coin`
4. Save as `.tmx` in `assets/levels/`

## Adding New Entities

1. Create class extending `Entity`
2. Register in `LevelLoader.createEntityFromObject()`
3. Use in Tiled with matching `type`

## Features

- TMX level loading
- Collision & physics
- Enemies & collectibles
- Score system & HUD
- Audio system
- Camera following player

## License

Educational project.
