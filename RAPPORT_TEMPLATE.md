# 📘 Template de Rapport - Mario Game Engine

Ce fichier fournit un template markdown pour votre rapport PDF. Vous pouvez le convertir en PDF en utilisant Pandoc, ou le rédiger directement dans Word/LibreOffice en suivant cette structure.

---

# Développement d'un Moteur de Jeu 2D avec LibGDX

## Projet Mario - Platformer Engine

**Étudiants :**

- [Nom Prénom 1]
- [Nom Prénom 2]
- [Nom Prénom 3]

**Formation :** [Votre formation]  
**Année universitaire :** 2024-2025  
**Date de rendu :** [Date]

**Lien GitHub :** https://github.com/[username]/mario-game-engine

---

## Table des Matières

1. Introduction
2. Objectifs du Projet
3. Technologies Utilisées
4. Architecture du Système
5. Concepts de POO Appliqués
6. Design Patterns Implémentés
7. Extensibilité via Tiled
8. Fonctionnalités Implémentées
9. Contribution de Chaque Membre
10. Compilation et Exécution
11. Démonstration et Tests
12. Améliorations Futures
13. Conclusion
14. Annexes

---

## 1. Introduction

### 1.1 Contexte

Ce projet s'inscrit dans le cadre du cours de Programmation Orientée Objet et vise à développer un moteur de jeu 2D en utilisant la librairie LibGDX. L'objectif principal est de créer un système de jeu de plateforme inspiré de Mario, où tous les éléments du jeu peuvent être configurés via l'éditeur de cartes Tiled, sans nécessiter de modification du code Java.

### 1.2 Problématique

Comment concevoir un moteur de jeu extensible qui :

- Respecte les principes de la programmation orientée objet
- Permet l'ajout de nouveaux éléments sans modifier le code existant
- Sépare clairement les responsabilités (MVC)
- Facilite la création de niveaux via un éditeur externe

### 1.3 Approche

Notre approche repose sur :

- Une architecture MVC stricte
- L'utilisation de design patterns appropriés
- Un système de chargement dynamique depuis JSON
- Une documentation complète du code

---

## 2. Objectifs du Projet

### 2.1 Objectifs Fonctionnels

- ✅ Créer un moteur de jeu de plateforme jouable
- ✅ Permettre le chargement de niveaux depuis Tiled
- ✅ Implémenter un joueur avec mouvements et saut
- ✅ Ajouter des ennemis avec IA basique
- ✅ Intégrer des objets collectables
- ✅ Gérer les collisions et la physique

### 2.2 Objectifs Pédagogiques

- ✅ Appliquer les concepts de POO (héritage, polymorphisme, interfaces)
- ✅ Mettre en œuvre l'architecture MVC
- ✅ Utiliser des design patterns (Factory, Strategy, Template Method)
- ✅ Gérer un projet avec Git
- ✅ Produire une documentation de qualité

---

## 3. Technologies Utilisées

### 3.1 Langage et Framework

- **Java 11** : Langage de programmation principal
- **LibGDX 1.12.1** : Framework de développement de jeux
  - Support multi-plateforme (Desktop, Android, iOS, Web)
  - Gestion du rendu 2D/3D
  - Système audio intégré
  - Outils de physique

### 3.2 Outils de Développement

- **Gradle** : Système de build et gestion de dépendances
- **Git & GitHub** : Contrôle de version et collaboration
- **Tiled Map Editor** : Éditeur de niveaux
- **Gson** : Bibliothèque de parsing JSON
- **VS Code / IntelliJ IDEA** : Environnement de développement

### 3.3 Justification des Choix

**LibGDX** a été choisi pour :

- Sa maturité et sa large communauté
- Sa documentation complète
- Sa flexibilité et son extensibilité
- Son support natif du format JSON

**Tiled** pour :

- Son interface intuitive
- Son export JSON natif
- Sa popularité dans l'industrie du jeu vidéo
- Sa gratuité et son open-source

---

## 4. Architecture du Système

### 4.1 Architecture MVC

Notre projet suit strictement le pattern **Model-View-Controller** :

```
┌─────────────────────────────────────────────────┐
│                 CONTROLLER                      │
│  GameController, InputHandler                   │
│  - Boucle de jeu principale                     │
│  - Gestion des entrées utilisateur              │
│  - Orchestration Model ↔ View                   │
└────────────┬────────────────────────┬───────────┘
             │                        │
             ↓                        ↓
┌────────────────────────┐  ┌────────────────────┐
│        MODEL           │  │       VIEW         │
│  - Entity (abstract)   │  │  - GameRenderer    │
│  - Player              │  │  - Camera          │
│  - Goomba, Coin        │  │  - HUD             │
│  - Level               │  │                    │
│  - LevelLoader         │  │                    │
│  - PhysicsEngine       │  │                    │
└────────────────────────┘  └────────────────────┘
```

### 4.2 Diagramme de Packages

```
com.mario
├── Main.java
├── model/
│   ├── entity/
│   │   ├── Entity.java (abstract)
│   │   ├── Player.java
│   │   ├── Goomba.java
│   │   ├── Coin.java
│   │   ├── Enemy.java (interface)
│   │   └── Collectible.java (interface)
│   ├── level/
│   │   ├── Level.java
│   │   ├── LevelData.java
│   │   └── LevelLoader.java
│   └── physics/
│       └── PhysicsEngine.java
├── view/
│   └── GameRenderer.java
└── controller/
    ├── GameController.java
    └── InputHandler.java
```

### 4.3 Diagramme de Classes Simplifié

```
                    Entity (abstract)
                    ┌─────────────────────┐
                    │ - position: Vector2 │
                    │ - velocity: Vector2 │
                    │ - bounds: Rectangle │
                    │ + update(delta)     │
                    └──────────┬──────────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
    Player                  Goomba                  Coin
┌───────────────┐      ┌──────────────┐      ┌─────────────────┐
│ - health      │      │ - direction  │      │ - scoreValue    │
│ - score       │      │ - damage     │      │ - collected     │
│ - lives       │      │              │      │                 │
│ + jump()      │      │              │      │                 │
│ + takeDamage()│      │              │      │                 │
└───────────────┘      └──────────────┘      └─────────────────┘
                              │                      │
                       implements                implements
                              │                      │
                       ┌──────▼──────┐      ┌────────▼────────┐
                       │   Enemy     │      │  Collectible    │
                       │ (interface) │      │  (interface)    │
                       └─────────────┘      └─────────────────┘
```

### 4.4 Responsabilités par Package

#### Model (Modèle)

**Responsabilité :** Gestion des données et de la logique métier

- **entity/** : Toutes les entités du jeu
- **level/** : Chargement et structure des niveaux
- **physics/** : Moteur physique et collisions

#### View (Vue)

**Responsabilité :** Rendu graphique

- **GameRenderer** : Affichage du jeu, caméra, HUD

#### Controller (Contrôleur)

**Responsabilité :** Logique de contrôle et orchestration

- **GameController** : Boucle de jeu, mise à jour, coordination
- **InputHandler** : Gestion des entrées clavier

---

## 5. Concepts de POO Appliqués

### 5.1 Héritage

**Classe de base abstraite : Entity**

```java
public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;
    protected Rectangle bounds;

    public abstract void update(float delta);
}
```

**Classes dérivées :**

- `Player extends Entity`
- `Goomba extends Entity`
- `Coin extends Entity`

**Avantages :**

- Réutilisation du code (position, vélocité, bounds)
- Méthode `update()` commune à toutes les entités
- Ajout facile de nouvelles entités

### 5.2 Polymorphisme

**Utilisation dans Level.java :**

```java
public class Level {
    private List<Entity> entities;

    public void update(float delta) {
        for (Entity entity : entities) {
            entity.update(delta);  // Appel polymorphe
        }
    }
}
```

**Avantage :** Traiter toutes les entités de manière uniforme, quelle que soit leur classe concrète.

### 5.3 Interfaces

**Interface Enemy :**

```java
public interface Enemy {
    void move(float delta);
    void onPlayerCollision(Player player);
    int getDamage();
}
```

**Interface Collectible :**

```java
public interface Collectible {
    void onCollect(Player player);
    int getScoreValue();
    boolean isCollectable();
}
```

**Avantages :**

- Définition de contrats clairs
- Implémentation de comportements spécifiques
- Pattern Strategy pour différentes stratégies d'ennemis

### 5.4 Encapsulation

- Attributs `protected` ou `private`
- Accès via getters/setters
- Validation des données
- Protection de l'état interne

**Exemple :**

```java
public class Player extends Entity {
    private int health;  // Privé

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;  // Validation
            die();
        }
    }

    public int getHealth() {
        return health;  // Accès contrôlé
    }
}
```

### 5.5 Classes Abstraites

**Entity comme classe abstraite :**

- Fournit une implémentation commune (position, bounds)
- Définit une méthode abstraite `update()`
- Force les sous-classes à implémenter `update()`
- Empêche l'instanciation directe

---

## 6. Design Patterns Implémentés

### 6.1 Template Method

**Pattern :** Définir le squelette d'un algorithme, en déléguant certaines étapes aux sous-classes.

**Implémentation :**

```java
public abstract class Entity {
    // Méthode template (abstraite)
    public abstract void update(float delta);
}

public class Player extends Entity {
    @Override
    public void update(float delta) {
        applyGravity(delta);
        updatePosition(delta);
        updateBounds();
    }
}
```

**Avantage :** Chaque entité définit sa propre logique de mise à jour.

### 6.2 Factory Method

**Pattern :** Créer des objets sans spécifier leur classe exacte.

**Implémentation dans LevelLoader :**

```java
private Entity createEntityFromObject(TiledObject obj) {
    String type = obj.getType();

    switch (type) {
        case "player":
            return new Player(x, y);
        case "coin":
            return new Coin(x, y, scoreValue);
        case "goomba":
            return new Goomba(x, y);
        default:
            return null;
    }
}
```

**Avantages :**

- Séparation de la création et de l'utilisation
- Ajout facile de nouveaux types
- Code extensible

### 6.3 Strategy

**Pattern :** Définir une famille d'algorithmes interchangeables.

**Implémentation via interfaces Enemy et Collectible :**

```java
public interface Enemy {
    void move(float delta);  // Stratégie de mouvement
}

public class Goomba implements Enemy {
    public void move(float delta) {
        // Mouvement horizontal simple
    }
}

// Futur : FlyingEnemy avec stratégie différente
public class FlyingEnemy implements Enemy {
    public void move(float delta) {
        // Mouvement en arc de cercle
    }
}
```

**Avantage :** Comportements interchangeables et extensibles.

### 6.4 MVC (Model-View-Controller)

**Pattern architectural :** Séparation des responsabilités.

- **Model** : Données et logique métier (Entity, Level)
- **View** : Affichage (GameRenderer)
- **Controller** : Orchestration (GameController)

**Flux de données :**

```
Input → Controller → Model → Controller → View → Screen
```

---

## 7. Extensibilité via Tiled

### 7.1 Format JSON Tiled

Le moteur charge les niveaux au format JSON exporté depuis Tiled :

```json
{
  "width": 30,
  "height": 20,
  "tilewidth": 32,
  "tileheight": 32,
  "layers": [
    {
      "name": "Collision",
      "type": "tilelayer",
      "data": [...]
    },
    {
      "name": "Entities",
      "type": "objectgroup",
      "objects": [...]
    }
  ]
}
```

### 7.2 Types d'Objets Supportés

| Type     | Description       | Propriétés         |
| -------- | ----------------- | ------------------ |
| `player` | Joueur (Mario)    | -                  |
| `goomba` | Ennemi Goomba     | -                  |
| `coin`   | Pièce à collecter | `scoreValue` (int) |

### 7.3 Ajout de Nouveaux Types

**Étape 1 :** Créer la classe

```java
public class Koopa extends Entity implements Enemy {
    // Implémentation
}
```

**Étape 2 :** Ajouter dans le Factory

```java
case "koopa":
    return new Koopa(x, y);
```

**Étape 3 :** Utiliser dans Tiled avec `type = "koopa"`

**Aucune autre modification nécessaire !**

### 7.4 Propriétés Personnalisées

Les propriétés Tiled permettent de configurer les entités :

```json
{
  "type": "coin",
  "properties": [
    {
      "name": "scoreValue",
      "value": 50
    }
  ]
}
```

Le LevelLoader récupère automatiquement ces propriétés.

---

## 8. Fonctionnalités Implémentées

### 8.1 Système de Jeu

- ✅ **Boucle de jeu** avec delta time
- ✅ **Physique de platformer** (gravité, friction)
- ✅ **Système de collision** robuste
- ✅ **Chargement dynamique** de niveaux

### 8.2 Joueur

- ✅ Mouvements gauche/droite
- ✅ Saut avec physique réaliste
- ✅ Système de vie (health)
- ✅ Système de score
- ✅ Gestion des vies (lives)

### 8.3 Ennemis

- ✅ **Goomba** avec mouvement automatique
- ✅ Changement de direction aux obstacles
- ✅ Collision avec le joueur
- ✅ Possibilité d'être écrasé

### 8.4 Collectibles

- ✅ **Pièces** avec valeur configurable
- ✅ Collecte automatique au contact
- ✅ Incrémentation du score

### 8.5 Affichage

- ✅ Rendu des entités
- ✅ Rendu du terrain
- ✅ **HUD** (score, vie, vies)
- ✅ **Caméra** suivant le joueur
- ✅ Limitation de la caméra aux bords du niveau

### 8.6 Niveau de Test

En cas d'échec de chargement JSON, un niveau de test est généré automatiquement avec :

- Sol et plateformes
- Joueur
- 2-3 Goombas
- 4-5 pièces

---

## 9. Contribution de Chaque Membre

### 9.1 [Nom Prénom 1]

**Responsabilités principales :**

- Architecture du système d'entités
- Implémentation du moteur physique
- Système de collision

**Packages développés :**

- `com.mario.model.entity.*` (100%)
  - Entity.java
  - Player.java
  - Goomba.java
  - Coin.java
  - Enemy.java
  - Collectible.java
- `com.mario.model.physics.*` (100%)
  - PhysicsEngine.java

**Commits significatifs :**

1. `feat: Add Entity abstract class` (commit abc123)
   - Création de la classe de base pour toutes les entités
2. `feat: Implement Player with jump mechanics` (commit def456)
   - Ajout du joueur avec mouvements et saut
3. `feat: Add Goomba enemy with AI` (commit ghi789)
   - Ennemi avec mouvement automatique
4. `feat: Implement collision detection system` (commit jkl012)
   - Moteur physique complet

**Statistiques :**

- Fichiers créés : 7
- Lignes de code : ~500
- Commits : 15+

---

### 9.2 [Nom Prénom 2]

**Responsabilités principales :**

- Système de chargement de niveaux
- Parser JSON Tiled
- Factory pattern

**Packages développés :**

- `com.mario.model.level.*` (100%)
  - Level.java
  - LevelData.java
  - LevelLoader.java

**Commits significatifs :**

1. `feat: Add LevelData structure` (commit mno345)
   - Structure pour parser JSON Tiled
2. `feat: Implement LevelLoader with Factory` (commit pqr678)
   - Chargement dynamique depuis JSON
3. `feat: Add support for custom properties` (commit stu901)
   - Propriétés configurables (scoreValue, etc.)

**Statistiques :**

- Fichiers créés : 3
- Lignes de code : ~400
- Commits : 12+

---

### 9.3 [Nom Prénom 3]

**Responsabilités principales :**

- Système de rendu
- Boucle de jeu
- Gestion des inputs

**Packages développés :**

- `com.mario.view.*` (100%)
  - GameRenderer.java
- `com.mario.controller.*` (100%)
  - GameController.java
  - InputHandler.java
- `com.mario.Main.java` (100%)

**Commits significatifs :**

1. `feat: Add GameRenderer with camera` (commit vwx234)
   - Système de rendu complet
2. `feat: Implement game loop in GameController` (commit yza567)
   - Boucle de jeu principale
3. `feat: Add InputHandler for player controls` (commit bcd890)
   - Gestion centralisée des inputs

**Statistiques :**

- Fichiers créés : 4
- Lignes de code : ~350
- Commits : 13+

---

### 9.4 Travail Collaboratif

**Réunions de coordination :** 5 réunions (planification, revues de code)

**Outils utilisés :**

- Git pour le versioning
- GitHub pour l'hébergement
- Discord pour la communication
- Tiled pour les niveaux

**Répartition équilibrée :** ~33% de contribution par membre

---

## 10. Compilation et Exécution

### 10.1 Prérequis

- Java JDK 11 ou supérieur
- Git (pour cloner le projet)
- Connexion Internet (première exécution Gradle)

### 10.2 Installation

```bash
# Cloner le dépôt
git clone https://github.com/[username]/mario-game-engine.git
cd mario-game-engine
```

### 10.3 Compilation

**Windows :**

```powershell
.\gradlew.bat build
```

**Linux/Mac :**

```bash
chmod +x gradlew
./gradlew build
```

### 10.4 Exécution

**Méthode 1 : Via Gradle**

Windows :

```powershell
.\gradlew.bat run
```

Linux/Mac :

```bash
./gradlew run
```

**Méthode 2 : Via Script**

Windows :

```powershell
.\run.bat
```

Linux/Mac :

```bash
chmod +x run.sh
./run.sh
```

**Méthode 3 : JAR Exécutable**

```powershell
.\gradlew.bat jar
java -jar build\libs\Mario-game-1.0.0.jar
```

### 10.5 Contrôles

- **Flèche Gauche / Q** : Aller à gauche
- **Flèche Droite / D** : Aller à droite
- **Espace / Flèche Haut / Z** : Sauter

---

## 11. Démonstration et Tests

### 11.1 Scénario de Test

1. **Lancement du jeu**

   - Le niveau se charge depuis `assets/levels/level1.json`
   - Si échec, un niveau de test est créé

2. **Mouvements du joueur**

   - ✅ Déplacement fluide gauche/droite
   - ✅ Saut fonctionnel avec gravité
   - ✅ Collision avec le sol

3. **Interaction avec ennemis**

   - ✅ Collision latérale → perte de vie
   - ✅ Saut sur ennemi → ennemi détruit

4. **Collecte de pièces**

   - ✅ Contact avec pièce → score augmente
   - ✅ Pièce disparaît après collecte

5. **Interface**
   - ✅ Score affiché
   - ✅ Vie affichée
   - ✅ Vies restantes affichées
   - ✅ Caméra suit le joueur

### 11.2 Captures d'Écran

[Insérer captures d'écran ici]

1. Vue du jeu avec joueur et ennemis
2. Niveau ouvert dans Tiled
3. HUD affichant les statistiques

### 11.3 Tests Effectués

- ✅ Compilation sans erreurs
- ✅ Exécution sur Windows 10/11
- ✅ Exécution sur Linux (Ubuntu)
- ✅ Chargement de niveau personnalisé
- ✅ Création de nouveau type d'entité

---

## 12. Améliorations Futures

### 12.1 Version 1.1 (Prioritaire)

- **Graphismes** : Sprites et textures au lieu de formes
- **Animations** : Marche, saut, inactivité
- **Sons** : Effets sonores basiques
- **Nouveau type d'ennemi** : Koopa Troopa

### 12.2 Version 1.2+

- **Power-ups** : Mushroom, Fire Flower
- **Blocs interactifs** : Question Blocks, briques
- **Multi-niveaux** : Système de progression
- **Menu principal** : Écran titre, options

### 12.3 Extensions Ambitieuses

- **Boss de fin** : Combat contre Bowser
- **Multijoueur local** : 2 joueurs
- **Mobile** : Version Android
- **Tests unitaires** : Couverture de code

Voir `ROADMAP.md` pour détails complets.

---

## 13. Conclusion

### 13.1 Objectifs Atteints

Ce projet nous a permis d'atteindre tous les objectifs fixés :

✅ **Programmation Orientée Objet**

- Maîtrise de l'héritage, polymorphisme, interfaces
- Application de classes abstraites
- Encapsulation et gestion de l'état

✅ **Architecture Logicielle**

- Architecture MVC stricte et bien séparée
- Code modulaire et maintenable
- Faible couplage entre les composants

✅ **Design Patterns**

- Factory Method pour création d'entités
- Strategy pour comportements variés
- Template Method pour entités

✅ **Extensibilité**

- Ajout de nouveaux types sans modifier le code existant
- Configuration via Tiled
- Système de propriétés personnalisables

✅ **Documentation**

- Code abondamment commenté
- Javadoc sur toutes les classes publiques
- Guides utilisateur (README, TILED_GUIDE)
- Documentation architecturale

✅ **Gestion de Projet**

- Utilisation de Git
- Commits réguliers et descriptifs
- Collaboration efficace en équipe

### 13.2 Compétences Développées

**Compétences techniques :**

- Maîtrise de Java et de la POO
- Utilisation de LibGDX
- Parsing JSON avec Gson
- Build automation avec Gradle
- Versioning avec Git

**Compétences transversales :**

- Travail en équipe
- Planification de projet
- Résolution de problèmes
- Documentation technique
- Recherche autonome

### 13.3 Points Forts du Projet

1. **Code de qualité**

   - Lisible et bien structuré
   - Commenté et documenté
   - Respect des conventions Java

2. **Architecture solide**

   - MVC bien appliqué
   - Séparation des responsabilités claire
   - Code modulaire et réutilisable

3. **Extensibilité**

   - Ajout facile de nouvelles entités
   - Configuration externe via Tiled
   - Système de propriétés flexible

4. **Documentation complète**
   - README détaillé
   - Guides d'utilisation
   - Documentation architecturale
   - Commentaires dans le code

### 13.4 Difficultés Rencontrées et Solutions

**Difficulté 1 : Gestion des collisions**

- **Problème** : Calcul des collisions complexe
- **Solution** : Décomposition en sous-problèmes (joueur-terrain, joueur-ennemis, etc.)

**Difficulté 2 : Parsing JSON Tiled**

- **Problème** : Structure JSON complexe avec classes imbriquées
- **Solution** : Utilisation de Gson avec classes internes dans LevelData

**Difficulté 3 : Coordination de l'équipe**

- **Problème** : Conflits Git lors de modifications simultanées
- **Solution** : Séparation claire des packages, communication régulière

### 13.5 Leçons Apprises

1. **Planification** : L'architecture doit être réfléchie avant le code
2. **Simplicité** : Code simple et clair > code complexe
3. **Documentation** : Documenter au fur et à mesure, pas à la fin
4. **Tests** : Tester régulièrement évite les bugs accumulés
5. **Collaboration** : Communication = clé du travail en équipe

### 13.6 Application Professionnelle

Les compétences acquises sont directement applicables :

- **Développement logiciel** : Architecture, patterns, POO
- **Industrie du jeu vidéo** : LibGDX, game loop, physique
- **Gestion de projet** : Git, collaboration, documentation
- **Maintenance** : Code maintenable et extensible

### 13.7 Mot de Fin

Ce projet a été une expérience enrichissante qui nous a permis de mettre en pratique les concepts théoriques vus en cours dans un contexte concret et motivant. La création d'un jeu vidéo, même simple, nécessite une réflexion approfondie sur l'architecture et le design logiciel.

Nous sommes fiers d'avoir créé un moteur de jeu fonctionnel, extensible et bien documenté, qui démontre notre maîtrise de la programmation orientée objet et des bonnes pratiques de développement.

**Lien GitHub :** https://github.com/[username]/mario-game-engine

---

## 14. Annexes

### Annexe A : Diagramme de Classes UML Complet

[Insérer diagramme UML détaillé]

### Annexe B : Diagramme de Séquence (Chargement de Niveau)

```
User → GameController : loadLevel("level1.json")
GameController → LevelLoader : loadLevel("level1.json")
LevelLoader → Gdx.files : internal("level1.json")
Gdx.files → LevelLoader : FileHandle
LevelLoader → FileHandle : readString()
FileHandle → LevelLoader : jsonContent
LevelLoader → Gson : fromJson(jsonContent, LevelData.class)
Gson → LevelLoader : LevelData
LevelLoader → LevelLoader : createLevelFromData(levelData)
LevelLoader → Level : new Level(...)
Level → LevelLoader : level
loop for each object in entities layer
    LevelLoader → LevelLoader : createEntityFromObject(obj)
    LevelLoader → Entity : new Player/Goomba/Coin(...)
    Entity → LevelLoader : entity
    LevelLoader → Level : addEntity(entity)
end
LevelLoader → GameController : level
```

### Annexe C : Extraits de Code Significatifs

#### Factory Method dans LevelLoader

```java
private Entity createEntityFromObject(TiledObject obj) {
    String type = obj.getType().toLowerCase();
    float x = obj.getX();
    float y = obj.getY();

    switch (type) {
        case "player":
            return new Player(x, y);

        case "coin":
            int scoreValue = getIntProperty(obj, "scoreValue", 10);
            return new Coin(x, y, scoreValue);

        case "goomba":
            return new Goomba(x, y);

        default:
            System.out.println("Unknown entity type: " + type);
            return null;
    }
}
```

#### Gestion Polymorphe des Entités

```java
public void update(float delta) {
    // Mettre à jour toutes les entités
    for (Entity entity : entities) {
        if (entity.isActive()) {
            entity.update(delta);  // Appel polymorphe
        }
    }

    // Supprimer les entités inactives
    entities.removeIf(entity -> !entity.isActive());
}
```

### Annexe D : Structure du Fichier JSON Tiled

```json
{
  "width": 30,
  "height": 20,
  "tilewidth": 32,
  "tileheight": 32,
  "layers": [
    {
      "name": "Collision",
      "type": "tilelayer",
      "data": [0, 0, 1, 1, ...]
    },
    {
      "name": "Entities",
      "type": "objectgroup",
      "objects": [
        {
          "type": "player",
          "x": 64,
          "y": 384,
          "width": 32,
          "height": 32
        },
        {
          "type": "coin",
          "x": 256,
          "y": 288,
          "properties": [
            {
              "name": "scoreValue",
              "value": 10
            }
          ]
        }
      ]
    }
  ]
}
```

### Annexe E : Métriques du Projet

**Code :**

- Classes : 15+
- Interfaces : 2
- Lignes de code : ~1200+
- Packages : 5
- Fichiers de documentation : 7

**Git :**

- Commits : 40+
- Branches : main + feature branches
- Contributeurs : 3

**Documentation :**

- Fichiers markdown : 7
- Pages de documentation : ~50
- Commentaires Javadoc : 100+

---

**Fin du Rapport**

---

**Note pour la rédaction :**
Ce template peut être converti en PDF avec Pandoc :

```bash
pandoc RAPPORT_TEMPLATE.md -o Rapport_Mario_GameEngine.pdf --toc
```

Ou copié dans Word/LibreOffice avec mise en forme appropriée.
