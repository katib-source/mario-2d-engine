# Mario Game Engine - LibGDX

## 📋 Description du Projet

Ce projet est un **moteur de jeu 2D de type platformer** développé avec la librairie **LibGDX** en Java. Le moteur permet de créer des jeux de plateforme similaires à Mario, où tous les niveaux, ennemis, et objets collectables peuvent être configurés via l'éditeur de cartes **Tiled**, sans avoir besoin de modifier le code Java.

### 🎯 Objectifs Pédagogiques

- **Programmation Orientée Objet (POO)** : Utilisation d'héritage, polymorphisme, interfaces, classes abstraites
- **Architecture MVC** : Séparation claire entre Modèle, Vue, et Contrôleur
- **Extensibilité** : Ajout facile de nouveaux types d'entités via le pattern Factory
- **Chargement de données** : Lecture et parsing de fichiers JSON (format Tiled)
- **Design Patterns** : Factory Method, Strategy, Template Method

## 🏗️ Architecture du Projet

Le projet suit une architecture **MVC (Modèle-Vue-Contrôleur)** bien structurée :

```
src/com/mario/
├── Main.java                      # Point d'entrée
├── model/                         # MODÈLE
│   ├── entity/                    # Entités du jeu
│   │   ├── Entity.java           # Classe abstraite de base
│   │   ├── Player.java           # Le joueur (Mario)
│   │   ├── Goomba.java           # Ennemi Goomba
│   │   ├── Coin.java             # Pièce à collecter
│   │   ├── Enemy.java            # Interface Enemy
│   │   └── Collectible.java      # Interface Collectible
│   ├── level/                     # Gestion des niveaux
│   │   ├── Level.java            # Classe représentant un niveau
│   │   ├── LevelData.java        # Structure de données TMX
│   │   └── LevelLoader.java      # Chargement depuis Tiled
│   └── physics/                   # Moteur physique
│       └── PhysicsEngine.java    # Gestion collisions/gravité
├── view/                          # VUE
│   ├── GameRenderer.java         # Rendu graphique principal
│   ├── TiledMapRenderer.java     # Rendu des cartes Tiled
│   ├── TilesetRenderer.java      # Rendu des tilesets
│   ├── SpriteAnimator.java       # Gestion des animations
│   ├── TextureManager.java       # Gestion des textures
│   └── AudioManager.java         # Gestion audio (Singleton)
├── controller/                    # CONTRÔLEUR
│   ├── GameController.java       # Boucle de jeu principale
│   └── InputHandler.java         # Gestion des inputs
└── utils/                         # UTILITAIRES
    └── TilesetGenerator.java     # Génération de tilesets

assets/
├── levels/                        # Niveaux Tiled
│   ├── level1.tmx                # Niveau 1 (format TMX)
│   ├── level2.tmx                # Niveau 2
│   └── level3.tmx                # Niveau 3
├── audio/                         # Ressources audio
│   ├── music/                    # Musiques de fond
│   └── sounds/                   # Effets sonores
└── textures/                      # Textures du jeu
    └── entities/                 # Sprites des entités
```

### 📦 Concepts POO Utilisés

1. **Héritage** : `Player`, `Goomba`, `Coin` héritent de `Entity`
2. **Polymorphisme** : Manipulation des entités via la classe de base
3. **Interfaces** : `Enemy` et `Collectible` définissent des comportements
4. **Classe Abstraite** : `Entity` avec méthode abstraite `update()`
5. **Encapsulation** : Attributs protégés avec getters/setters
6. **Factory Method** : `LevelLoader` crée dynamiquement les entités depuis JSON

## 🚀 Installation et Exécution

### Prérequis

- **Java JDK 11** ou supérieur
- **Gradle** (inclus via wrapper)

### Compilation et Exécution

#### Sous Windows (PowerShell ou CMD)

```powershell
# Compiler le projet
.\gradlew.bat build

# Exécuter le jeu
.\gradlew.bat run
```

#### Sous Windows (Script simplifié)

Un script `run.bat` est fourni pour faciliter l'exécution :

```batch
run.bat
```

#### Sous Linux/Mac

```bash
# Rendre le script exécutable
chmod +x gradlew

# Compiler
./gradlew build

# Exécuter
./gradlew run
```

### Créer un JAR exécutable

```powershell
.\gradlew.bat jar
java -jar build\libs\Mario-game-1.0.0.jar
```

## 🎮 Commandes de Jeu

- **Flèche Gauche / Q** : Déplacer Mario à gauche
- **Flèche Droite / D** : Déplacer Mario à droite
- **Espace / Flèche Haut / Z** : Sauter
- **ESC** : Quitter (si implémenté)

## 🗺️ Créer un Nouveau Niveau avec Tiled

### 1. Structure du fichier TMX

Le moteur charge automatiquement les niveaux au format **TMX** (Tiled Map XML) depuis **Tiled Map Editor**. Voici comment créer un nouveau niveau :

### 2. Couches (Layers) à créer dans Tiled

- **Collision** (tilelayer) : Tiles solides (sol, murs, plateformes)
- **Entities** (objectgroup) : Objets du jeu (joueur, ennemis, pièces)

### 3. Types d'objets supportés

Dans la couche d'objets "Entities", vous pouvez placer :

| Type     | Description       | Propriétés optionnelles |
| -------- | ----------------- | ----------------------- |
| `player` | Le joueur (Mario) | -                       |
| `goomba` | Ennemi Goomba     | -                       |
| `coin`   | Pièce à collecter | `scoreValue` (int)      |

### 4. Exemple d'objet avec propriété personnalisée

Pour une pièce de 25 points :

```json
{
  "type": "coin",
  "x": 448,
  "y": 352,
  "properties": [
    {
      "name": "scoreValue",
      "type": "int",
      "value": 25
    }
  ]
}
```

### 5. Export depuis Tiled

1. Créer votre carte dans Tiled Map Editor
2. Fichier → Enregistrer sous → Format TMX
3. Placer le fichier `.tmx` dans `assets/levels/`
4. Modifier `GameController.java` pour charger votre niveau
5. Les niveaux disponibles : `level1.tmx`, `level2.tmx`, `level3.tmx`

## 🔧 Ajouter de Nouveaux Types d'Entités

Le moteur est **extensible** ! Pour ajouter un nouveau type d'ennemi ou d'objet :

### Étape 1 : Créer la classe

```java
package com.mario.model.entity;

public class Koopa extends Entity implements Enemy {
    // Votre implémentation
}
```

### Étape 2 : Modifier le LevelLoader

Dans `LevelLoader.java`, méthode `createEntityFromObject()` :

```java
case "koopa":
    return new Koopa(x, y);
```

### Étape 3 : Utiliser dans Tiled

Dans Tiled, créer un objet avec `type = "koopa"`.

**Aucun changement dans le reste du code n'est nécessaire !**

## 🎵 Système Audio

Le moteur intègre un système audio complet géré par `AudioManager` (pattern Singleton) :

### Fonctionnalités Audio

- **Musiques de fond** : Lecture en boucle de musiques d'ambiance
- **Effets sonores** : Sons pour sauts, collectes, dégâts, etc.
- **Contrôle du volume** : Activation/désactivation séparée musique et sons
- **Gestion centralisée** : Un seul point d'accès via `AudioManager.getInstance()`

### Utilisation

```java
// Obtenir l'instance
AudioManager audio = AudioManager.getInstance();

// Jouer un effet sonore
audio.playSound("jump");

// Jouer une musique
audio.playMusic("level1");

// Activer/désactiver
audio.setMusicEnabled(false);
audio.setSoundEnabled(true);
```

### Structure des fichiers audio

```
assets/audio/
├── music/          # Fichiers .ogg ou .mp3
└── sounds/         # Fichiers .wav ou .ogg
```

## 📊 Fonctionnalités Implémentées

- ✅ Chargement de niveaux depuis TMX (Tiled Map Editor)
- ✅ Support de multiples niveaux (level1, level2, level3)
- ✅ Système de collision avec le terrain
- ✅ Gravité et physique de platformer
- ✅ Joueur avec mouvements et saut
- ✅ Ennemis avec patterns de mouvement
- ✅ Objets collectables (pièces)
- ✅ Système de score et de vie
- ✅ HUD affichant score, santé et vies
- ✅ Caméra suivant le joueur
- ✅ **Système audio complet** (musiques et effets sonores)
- ✅ **Gestion avancée des textures et sprites**
- ✅ **Système d'animation des sprites**
- ✅ **Rendu optimisé avec TiledMapRenderer**
- ✅ **AudioManager avec pattern Singleton**
- ✅ Architecture MVC propre et documentée

## 📝 Structure des Dossiers

```
Mario-game/
├── src/                    # Code source Java
│   └── com/mario/          # Package principal
│       ├── model/          # Modèle (entités, niveaux, physique)
│       ├── view/           # Vue (rendu, audio, animations)
│       ├── controller/     # Contrôleur (logique de jeu)
│       └── utils/          # Utilitaires (génération tilesets)
├── assets/                 # Ressources du jeu
│   ├── levels/             # Fichiers TMX des niveaux
│   ├── audio/              # Musiques et sons
│   │   ├── music/          # Musiques de fond
│   │   └── sounds/         # Effets sonores
│   └── textures/           # Sprites et textures
│       └── entities/       # Sprites des entités
├── build/                  # Fichiers compilés (généré)
├── build.gradle            # Configuration Gradle
├── settings.gradle         # Paramètres Gradle
├── gradlew.bat             # Wrapper Gradle (Windows)
├── run.bat                 # Script d'exécution rapide
├── ARCHITECTURE.md         # Documentation architecture
└── README.md               # Ce fichier
```

## 🧪 Tests

Pour tester le moteur sans fichier TMX, un niveau de test est automatiquement créé en mémoire si le fichier `level1.tmx` n'est pas trouvé. Le projet contient actuellement trois niveaux de démonstration (`level1.tmx`, `level2.tmx`, `level3.tmx`).

## 📚 Documentation du Code

Tout le code est **abondamment commenté** avec :

- Documentation Javadoc pour chaque classe et méthode publique
- Commentaires expliquant les choix de conception
- Indication des design patterns utilisés

## 🔗 Lien GitHub

[Insérez ici le lien vers votre dépôt GitHub]

## 👥 Contributeurs

- [Votre nom] - [Votre contribution]

## 📄 Licence

Ce projet est réalisé dans un cadre pédagogique.

## 🙏 Remerciements

- LibGDX pour la librairie de jeu
- Tiled pour l'éditeur de cartes
- [Vos professeurs/tuteurs]

---

**Note** : Ce projet privilégie la **clarté** et la **maintenabilité** du code plutôt que la complexité. L'accent est mis sur l'application correcte des concepts de POO et l'architecture logicielle.
