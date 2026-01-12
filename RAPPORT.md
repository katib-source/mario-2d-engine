# Développement d'un Moteur de Jeu 2D avec LibGDX

## Projet Mario - Platformer Engine

**Étudiants :**

- [Nom Prénom 1] — Développeur principal (Controller, Physics)
- [Nom Prénom 2] — Développeur (Model, Entities)
- [Nom Prénom 3] — Développeur (View, Level Design)

**Formation :** Licence Informatique / Master Génie Logiciel  
**Année universitaire :** 2024-2025  
**Date de rendu :** Janvier 2025

**Lien GitHub :** https://github.com/[username]/mario-2d-engine

---

## Table des Matières

1. [Introduction](#1-introduction)
2. [Présentation du Projet](#2-présentation-du-projet)
3. [Workflow Tiled](#3-workflow-tiled)
4. [Compilation et Exécution](#4-compilation-et-exécution)
5. [Architecture Technique et UML](#5-architecture-technique-et-uml)
6. [Comment Étendre la Librairie](#6-comment-étendre-la-librairie)
7. [Répartition des Tâches](#7-répartition-des-tâches)
8. [Conclusion et Pistes d'Amélioration](#8-conclusion-et-pistes-damélioration)
9. [Annexes](#9-annexes)

---

## 1. Introduction

### 1.1 Contexte

Ce projet s'inscrit dans le cadre du cours de Programmation Orientée Objet. L'objectif est de développer un moteur de jeu 2D de type platformer en utilisant la librairie **LibGDX** en Java. Le moteur permet de créer des jeux similaires à Mario, où tous les niveaux, ennemis, et objets collectables peuvent être configurés via l'éditeur de cartes **Tiled**, sans modifier le code source.

### 1.2 Problématique

Comment concevoir un moteur de jeu extensible qui :

- Respecte les principes de la programmation orientée objet (POO)
- Permet l'ajout de nouveaux éléments sans modifier le code existant (Open/Closed)
- Sépare clairement les responsabilités (architecture MVC)
- Facilite la création de niveaux via un éditeur externe (Tiled)

### 1.3 Objectifs

| Objectif                                                           | Statut |
| ------------------------------------------------------------------ | ------ |
| Créer un moteur de jeu de plateforme jouable                       | ✅     |
| Charger des niveaux depuis Tiled (format TMX)                      | ✅     |
| Implémenter un joueur avec mouvements et saut                      | ✅     |
| Ajouter des ennemis avec IA basique                                | ✅     |
| Intégrer des objets collectables                                   | ✅     |
| Gérer les collisions et la physique                                | ✅     |
| Appliquer les design patterns (Factory, Strategy, Template Method) | ✅     |
| Implémenter une architecture MVC stricte                           | ✅     |

---

## 2. Présentation du Projet

### 2.1 Technologies Utilisées

| Technologie          | Version | Rôle                                       |
| -------------------- | ------- | ------------------------------------------ |
| **Java**             | 11+     | Langage de programmation principal         |
| **LibGDX**           | 1.12.1  | Framework de développement de jeux 2D      |
| **Gradle**           | 8.x     | Système de build et gestion de dépendances |
| **Tiled Map Editor** | 1.10.x  | Éditeur de niveaux (format TMX)            |
| **Git & GitHub**     | -       | Contrôle de version et collaboration       |

### 2.2 Justification des Choix Techniques

**LibGDX** a été choisi pour :

- Sa maturité et sa large communauté
- Son support multi-plateforme (Desktop, Android, iOS, Web)
- Son intégration native avec le format TMX de Tiled
- Sa gestion complète du rendu 2D, de l'audio et des entrées

**Tiled** a été choisi pour :

- Son interface intuitive et professionnelle
- Son format TMX (XML) bien documenté
- Sa compatibilité directe avec LibGDX via `TmxMapLoader`
- Sa gratuité et son caractère open-source

### 2.3 Fonctionnalités Implémentées

**Gameplay :**

- Déplacement du joueur (gauche/droite) avec physique réaliste
- Saut avec gravité simulée
- Système de vies, santé et score
- Ennemis (Goomba) avec IA de patrouille
- Pièces collectables avec valeur configurable
- Zone de fin de niveau (EndTrigger) pour la progression

**Technique :**

- Chargement dynamique des niveaux depuis TMX
- Support de 3 niveaux pré-configurés (level1, level2, level3)
- Système de collision joueur-terrain, joueur-ennemi, joueur-collectible
- HUD affichant score, santé et vies restantes
- Caméra suivant le joueur avec limites de carte
- Système audio complet (musiques de fond, effets sonores)
- Gestion centralisée des textures (TextureManager)
- Animations de sprites (SpriteAnimator)

---

## 3. Workflow Tiled

### 3.1 Configuration de l'Éditeur Tiled

1. **Créer une nouvelle carte** : `File → New → New Map`

   - Orientation : Orthogonal
   - Tile size : 16×16 pixels
   - Map size : Libre (ex: 240×13 tiles)

2. **Importer le tileset** : `Map → Add Tileset`
   - Source : `assets/levels/tileset_gutter.png`
   - Tile size : 16×16 avec spacing de 2px et margin de 1px

### 3.2 Couches Requises

| Nom de la couche | Type         | Description                               |
| ---------------- | ------------ | ----------------------------------------- |
| `Ground`         | Tile Layer   | Tiles solides (sol, murs, plateformes)    |
| `Background`     | Tile Layer   | Éléments décoratifs non-solides           |
| `Entities`       | Object Group | Objets du jeu (player, goomba, coin, end) |

### 3.3 Types d'Objets Supportés

Dans la couche `Entities`, créer des objets avec les types suivants :

| Type     | Description                  | Propriétés optionnelles        |
| -------- | ---------------------------- | ------------------------------ |
| `player` | Position de départ du joueur | -                              |
| `goomba` | Ennemi Goomba                | -                              |
| `coin`   | Pièce à collecter            | `scoreValue` (int, défaut: 10) |
| `end`    | Zone de fin de niveau        | `nextLevel` (string)           |

### 3.4 Propriétés des Tiles

Les tiles avec la propriété `blocked` sont considérées comme solides pour les collisions :

```xml
<tile id="0">
  <properties>
    <property name="blocked" value=""/>
  </properties>
</tile>
```

### 3.5 Export et Intégration

1. Sauvegarder au format TMX : `File → Save As → level4.tmx`
2. Placer le fichier dans `assets/levels/`
3. Modifier `currentLevelNumber` dans `GameController` ou utiliser `EndTrigger` pour la progression

---

## 4. Compilation et Exécution

### 4.1 Prérequis

- **Java JDK 11** ou supérieur
- **Gradle** (inclus via wrapper `gradlew.bat`)

### 4.2 Compilation

```powershell
# Windows (PowerShell ou CMD)
.\gradlew.bat build

# Linux/Mac
./gradlew build
```

### 4.3 Exécution

```powershell
# Windows
.\gradlew.bat run

# OU script simplifié
run.bat

# Linux/Mac
./gradlew run
```

### 4.4 Création d'un JAR Exécutable

```powershell
.\gradlew.bat jar
java -jar build\libs\Mario-game-1.0.0.jar
```

### 4.5 Contrôles du Jeu

| Touche               | Action            |
| -------------------- | ----------------- |
| `←` / `Q`            | Déplacer à gauche |
| `→` / `D`            | Déplacer à droite |
| `Espace` / `↑` / `Z` | Sauter            |
| `ESC`                | Quitter           |

---

## 5. Architecture Technique et UML

### 5.1 Architecture MVC

Le projet suit strictement le pattern **Model-View-Controller** :

```
┌─────────────────────────────────────────────────────┐
│                   CONTROLLER                        │
│   GameController (ApplicationAdapter)               │
│   InputHandler                                      │
│   - Boucle de jeu principale                        │
│   - Gestion des entrées utilisateur                 │
│   - Orchestration Model ↔ View                      │
└─────────────┬─────────────────────────┬─────────────┘
              │                         │
              ↓                         ↓
┌─────────────────────────┐  ┌─────────────────────────┐
│         MODEL           │  │          VIEW           │
│  Entity (abstract)      │  │  GameRenderer           │
│  Player, Goomba, Coin   │  │  TiledMapRenderer       │
│  Level, LevelLoader     │  │  TilesetRenderer        │
│  LevelData              │  │  SpriteAnimator         │
│  PhysicsEngine          │  │  TextureManager         │
│                         │  │  AudioManager           │
└─────────────────────────┘  └─────────────────────────┘
```

### 5.2 Diagramme de Classes

Le diagramme UML complet est disponible dans [uml/project.puml](uml/project.puml).

**Classes principales :**

| Package         | Classe              | Responsabilité                         |
| --------------- | ------------------- | -------------------------------------- |
| `controller`    | `GameController`    | Boucle de jeu, orchestration MVC       |
| `controller`    | `InputHandler`      | Gestion des entrées clavier            |
| `model.entity`  | `Entity` (abstract) | Classe de base pour toutes les entités |
| `model.entity`  | `Player`            | Joueur avec vie, score, mouvements     |
| `model.entity`  | `Goomba`            | Ennemi avec IA de patrouille           |
| `model.entity`  | `Coin`              | Objet collectable                      |
| `model.entity`  | `EndTrigger`        | Zone de fin de niveau                  |
| `model.level`   | `Level`             | Container des entités et tiles         |
| `model.level`   | `LevelLoader`       | Chargement TMX (Factory Method)        |
| `model.level`   | `LevelData`         | DTO pour parsing JSON                  |
| `model.physics` | `PhysicsEngine`     | Collisions et gravité                  |
| `view`          | `GameRenderer`      | Rendu graphique principal              |
| `view`          | `AudioManager`      | Gestion audio (Singleton)              |
| `view`          | `TextureManager`    | Cache de textures (Singleton)          |
| `view`          | `SpriteAnimator`    | Animations de sprites                  |
| `view`          | `TiledMapRenderer`  | Rendu des cartes TMX                   |
| `view`          | `TilesetRenderer`   | Rendu des tilesets                     |

### 5.3 Design Patterns Implémentés

| Pattern             | Localisation                           | Justification                                |
| ------------------- | -------------------------------------- | -------------------------------------------- |
| **MVC**             | Architecture globale                   | Séparation des responsabilités               |
| **Template Method** | `Entity.update()`                      | Squelette d'algorithme dans classe abstraite |
| **Strategy**        | Interfaces `Enemy`, `Collectible`      | Comportements interchangeables               |
| **Factory Method**  | `LevelLoader.createEntityFromObject()` | Création dynamique d'entités                 |
| **Singleton**       | `AudioManager`, `TextureManager`       | Instance unique globale                      |
| **Mediator**        | `GameController`                       | Coordination des composants                  |

### 5.4 Principes SOLID

| Principe                  | Application                                                                             |
| ------------------------- | --------------------------------------------------------------------------------------- |
| **S**ingle Responsibility | Chaque classe a une seule responsabilité (Player gère le joueur, GameRenderer le rendu) |
| **O**pen/Closed           | Nouveau ennemi = nouvelle classe + case dans Factory, sans modifier le reste            |
| **L**iskov Substitution   | Player, Goomba, Coin interchangeables via référence Entity                              |
| **I**nterface Segregation | Interfaces Enemy et Collectible spécialisées                                            |
| **D**ependency Inversion  | PhysicsEngine dépend de l'interface Enemy, pas des classes concrètes                    |

### 5.5 Diagrammes de Séquence

Deux diagrammes de séquence sont fournis dans le dossier `uml/` :

1. **[sequence_game_loop.puml](uml/sequence_game_loop.puml)** : Flux de la boucle de jeu principale

   - GameController → InputHandler → PhysicsEngine → Level → GameRenderer

2. **[sequence_level_loading.puml](uml/sequence_level_loading.puml)** : Chargement d'un niveau
   - GameController → LevelLoader → TmxMapLoader → Level (création des entités)

---

## 6. Comment Étendre la Librairie

### 6.1 Ajouter un Nouvel Ennemi

**Étape 1 : Créer la classe**

```java
package com.mario.model.entity;

public class Koopa extends Entity implements Enemy {
    private final int damage = 15;
    private float direction = 1;

    public Koopa(float x, float y) {
        super(x, y, 24, 32);
    }

    @Override
    public void update(float delta) {
        move(delta);
        updateBounds();
    }

    @Override
    public void move(float delta) {
        velocity.x = direction * 40;
        position.x += velocity.x * delta;
    }

    @Override
    public void onPlayerCollision(Player player) {
        player.takeDamage(damage);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    public void reverseDirection() {
        direction = -direction;
    }
}
```

**Étape 2 : Modifier le LevelLoader**

Dans `LevelLoader.java`, méthode `createEntityFromObject()` :

```java
case "koopa":
    return new Koopa(x, y);
```

**Étape 3 : Utiliser dans Tiled**

Créer un objet de type `koopa` dans la couche `Entities`.

**Aucune autre modification nécessaire !**

### 6.2 Ajouter un Power-Up

**Étape 1 : Créer une interface**

```java
public interface PowerUp {
    void applyEffect(Player player);
    int getDuration();
}
```

**Étape 2 : Créer la classe**

```java
public class Mushroom extends Entity implements PowerUp, Collectible {
    @Override
    public void applyEffect(Player player) {
        player.heal(25);
    }

    @Override
    public void onCollect(Player player) {
        applyEffect(player);
        active = false;
    }
}
```

**Étape 3 : Modifier le Factory**

```java
case "mushroom":
    return new Mushroom(x, y);
```

### 6.3 Ajouter un Nouveau Niveau

1. Créer le niveau dans Tiled en suivant les conventions (section 3)
2. Sauvegarder comme `level4.tmx` dans `assets/levels/`
3. Le niveau sera automatiquement accessible via la progression

---

## 7. Répartition des Tâches

| Membre         | Responsabilités                   | Fichiers Principaux                                                          |
| -------------- | --------------------------------- | ---------------------------------------------------------------------------- |
| **[Prénom 1]** | Architecture, Controller, Physics | `GameController.java`, `InputHandler.java`, `PhysicsEngine.java`             |
| **[Prénom 2]** | Modèle, Entités, Level Loading    | `Entity.java`, `Player.java`, `Goomba.java`, `Coin.java`, `LevelLoader.java` |
| **[Prénom 3]** | Vue, Rendu, Audio, Level Design   | `GameRenderer.java`, `AudioManager.java`, `TextureManager.java`, `*.tmx`     |

### 7.1 Timeline du Projet

| Semaine | Livrables                                                |
| ------- | -------------------------------------------------------- |
| 1-2     | Architecture MVC, classes Entity de base, premier niveau |
| 3-4     | Physique, collisions, système de score                   |
| 5-6     | Système audio, animations, niveaux 2 et 3                |
| 7       | Tests, documentation, rapport                            |

---

## 8. Conclusion et Pistes d'Amélioration

### 8.1 Bilan

Ce projet nous a permis de :

- Appliquer concrètement les concepts de POO (héritage, polymorphisme, interfaces, classes abstraites)
- Mettre en œuvre une architecture MVC stricte et maintenable
- Utiliser des design patterns reconnus (Factory, Strategy, Template Method, Singleton)
- Développer un moteur de jeu extensible sans modification du code existant
- Travailler en équipe avec Git

### 8.2 Difficultés Rencontrées

| Difficulté                      | Solution                                        |
| ------------------------------- | ----------------------------------------------- |
| Gestion des collisions précises | Implémentation d'un moteur physique dédié       |
| Chargement des niveaux TMX      | Utilisation de la librairie LibGDX TmxMapLoader |
| Synchronisation audio/gameplay  | Centralisation via AudioManager Singleton       |

### 8.3 Pistes d'Amélioration

**Court terme :**

- Ajouter de nouveaux ennemis (Koopa, Piranha Plant)
- Implémenter des power-ups (Mushroom, Star)
- Améliorer les animations des sprites
- Ajouter un écran de menu principal

**Long terme :**

- Éditeur de niveaux intégré
- Mode multijoueur local
- Sauvegarde de la progression
- Port sur Android/iOS via LibGDX

---

## 9. Annexes

### Annexe A : Structure des Fichiers

```
mario-2d-engine/
├── src/com/mario/
│   ├── Main.java
│   ├── controller/
│   │   ├── GameController.java
│   │   └── InputHandler.java
│   ├── model/
│   │   ├── entity/
│   │   │   ├── Entity.java
│   │   │   ├── Player.java
│   │   │   ├── Goomba.java
│   │   │   ├── Coin.java
│   │   │   ├── EndTrigger.java
│   │   │   ├── Enemy.java
│   │   │   └── Collectible.java
│   │   ├── level/
│   │   │   ├── Level.java
│   │   │   ├── LevelData.java
│   │   │   └── LevelLoader.java
│   │   └── physics/
│   │       └── PhysicsEngine.java
│   └── view/
│       ├── GameRenderer.java
│       ├── AudioManager.java
│       ├── TextureManager.java
│       ├── SpriteAnimator.java
│       ├── TiledMapRenderer.java
│       └── TilesetRenderer.java
├── assets/
│   ├── levels/
│   │   ├── level1.tmx
│   │   ├── level2.tmx
│   │   ├── level3.tmx
│   │   └── tileset_gutter.png
│   ├── audio/
│   │   ├── music/
│   │   └── sounds/
│   └── textures/
│       └── entities/
├── uml/
│   ├── project.puml
│   ├── sequence_game_loop.puml
│   ├── sequence_level_loading.puml
│   └── README.md
├── build.gradle
├── settings.gradle
├── gradlew.bat
├── README.md
├── ARCHITECTURE.md
└── RAPPORT.md
```

### Annexe B : Références

- LibGDX Documentation : https://libgdx.com/wiki/
- Tiled Map Editor : https://www.mapeditor.org/
- Design Patterns : Gang of Four
- Clean Code : Robert C. Martin

### Annexe C : Diagrammes UML

Voir le dossier [uml/](uml/) pour les diagrammes PlantUML :

- `project.puml` — Diagramme de classes
- `sequence_game_loop.puml` — Séquence de la boucle de jeu
- `sequence_level_loading.puml` — Séquence de chargement d'un niveau

Instructions de rendu dans [uml/README.md](uml/README.md).

---

_Document généré le 12 janvier 2025_
