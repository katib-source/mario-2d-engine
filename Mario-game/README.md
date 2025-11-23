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
│   │   ├── LevelData.java        # Structure de données JSON
│   │   └── LevelLoader.java      # Chargement depuis Tiled
│   └── physics/                   # Moteur physique
│       └── PhysicsEngine.java    # Gestion collisions/gravité
├── view/                          # VUE
│   └── GameRenderer.java         # Rendu graphique
└── controller/                    # CONTRÔLEUR
    ├── GameController.java       # Boucle de jeu principale
    └── InputHandler.java         # Gestion des inputs

assets/
└── levels/
    └── level1.json               # Niveau exemple (format Tiled)
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

### 1. Structure du fichier JSON

Le moteur charge automatiquement les niveaux au format JSON exporté depuis **Tiled**. Voici comment créer un nouveau niveau :

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

1. Créer votre carte dans Tiled
2. Fichier → Exporter → JSON
3. Placer le fichier dans `assets/levels/`
4. Modifier `GameController.java` ligne 35 pour charger votre niveau

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

## 📊 Fonctionnalités Implémentées

- ✅ Chargement de niveaux depuis JSON (Tiled)
- ✅ Système de collision avec le terrain
- ✅ Gravité et physique de platformer
- ✅ Joueur avec mouvements et saut
- ✅ Ennemis avec patterns de mouvement
- ✅ Objets collectables (pièces)
- ✅ Système de score et de vie
- ✅ HUD affichant score, santé et vies
- ✅ Caméra suivant le joueur
- ✅ Architecture MVC propre et documentée

## 📝 Structure des Dossiers

```
Mario-game/
├── src/                    # Code source Java
│   └── com/mario/          # Package principal
├── assets/                 # Ressources du jeu
│   └── levels/             # Fichiers JSON des niveaux
├── lib/                    # Dépendances externes
├── build/                  # Fichiers compilés (généré)
├── build.gradle            # Configuration Gradle
├── settings.gradle         # Paramètres Gradle
├── gradlew.bat             # Wrapper Gradle (Windows)
├── run.bat                 # Script d'exécution rapide
└── README.md               # Ce fichier
```

## 🧪 Tests

Pour tester le moteur sans fichier JSON, un niveau de test est automatiquement créé en mémoire si le fichier `level1.json` n'est pas trouvé.

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
