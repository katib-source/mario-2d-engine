# Architecture et Design Patterns - Mario Game Engine

## 📐 Architecture MVC (Modèle-Vue-Contrôleur)

Le projet suit strictement le pattern **MVC** pour une séparation claire des responsabilités :

### 🧩 MODÈLE (Model)

**Responsabilité** : Gestion des données et de la logique métier

#### 📁 Package `model.entity`

Contient toutes les entités du jeu :

- **`Entity.java`** (Classe abstraite)

  - Classe de base pour toutes les entités
  - Gère position, vélocité, dimensions, et boîte de collision
  - Méthode abstraite `update(float delta)` à implémenter par les sous-classes
  - **Pattern : Template Method**

- **`Player.java`**

  - Hérite de `Entity`
  - Gère les mouvements, le saut, la vie, et le score
  - Constantes pour vitesse, gravité, vélocité de saut
  - **Pattern : Héritage**

- **`Goomba.java`**

  - Hérite de `Entity` et implémente `Enemy`
  - Déplacement automatique avec changement de direction
  - **Pattern : Héritage + Interface**

- **`Coin.java`**
  - Hérite de `Entity` et implémente `Collectible`
  - Gère la collecte et la valeur en points
  - **Pattern : Héritage + Interface**

#### 📁 Package `model.level`

Gestion du chargement et de la structure des niveaux :

- **`Level.java`**

  - Contient toutes les entités et tiles d'un niveau
  - Gère la mise à jour de toutes les entités
  - Stocke les rectangles de collision

- **`LevelData.java`**

  - Structure de données pour le parsing JSON
  - Classes internes pour représenter les couches, objets, propriétés
  - **Pattern : Data Transfer Object (DTO)**

- **`LevelLoader.java`**
  - Charge les niveaux depuis JSON (format Tiled)
  - Crée dynamiquement les entités selon leur type
  - **Pattern : Factory Method**

#### 📁 Package `model.physics`

Moteur physique du jeu :

- **`PhysicsEngine.java`**
  - Gère les collisions joueur-terrain
  - Gère les collisions joueur-ennemis
  - Gère les collisions joueur-collectibles
  - Gère les collisions ennemis-terrain
  - **Pattern : Service**

### 👁️ VUE (View)

**Responsabilité** : Rendu graphique et affichage

#### 📁 Package `view`

- **`GameRenderer.java`**
  - Gère tout le rendu graphique du jeu
  - Caméra suivant le joueur
  - Rendu des tiles et des entités
  - Affichage du HUD (score, vie, vies restantes)
  - **Pattern : Renderer**

### 🎮 CONTRÔLEUR (Controller)

**Responsabilité** : Orchestration et gestion des entrées

#### 📁 Package `controller`

- **`GameController.java`**

  - Boucle de jeu principale (hérite de `ApplicationAdapter` de LibGDX)
  - Orchestration entre Model et View
  - Appel des mises à jour et du rendu
  - **Pattern : Mediator**

- **`InputHandler.java`**
  - Gestion centralisée des entrées clavier
  - Traduction des inputs en actions du joueur
  - **Pattern : Command (simplifié)**

## 🔧 Design Patterns Utilisés

### 1. **Template Method** (Méthode Template)

**Où** : Classe `Entity`

**Pourquoi** : Définit le squelette de l'algorithme de mise à jour

```java
public abstract class Entity {
    // Méthode template (abstraite)
    public abstract void update(float delta);
}

public class Player extends Entity {
    @Override
    public void update(float delta) {
        // Implémentation spécifique au joueur
        applyGravity(delta);
        updatePosition(delta);
        updateBounds();
    }
}
```

### 2. **Strategy** (Stratégie)

**Où** : Interfaces `Enemy` et `Collectible`

**Pourquoi** : Permet de définir différents comportements pour les entités

```java
public interface Enemy {
    void move(float delta);
    void onPlayerCollision(Player player);
    int getDamage();
}

// Différentes stratégies de mouvement
public class Goomba implements Enemy {
    public void move(float delta) {
        // Déplacement horizontal simple
    }
}

// On peut facilement ajouter :
public class FlyingEnemy implements Enemy {
    public void move(float delta) {
        // Déplacement volant
    }
}
```

### 3. **Factory Method** (Méthode Fabrique)

**Où** : `LevelLoader.createEntityFromObject()`

**Pourquoi** : Crée des objets sans spécifier leur classe exacte

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
        // Extensible : ajouter de nouveaux types ici
        default:
            return null;
    }
}
```

**Avantage** : Pour ajouter un nouveau type d'entité, il suffit :

1. De créer la classe
2. D'ajouter un `case` dans le switch
3. Pas besoin de modifier le reste du code !

### 4. **MVC (Model-View-Controller)**

**Où** : Architecture globale du projet

**Pourquoi** : Séparation des responsabilités

```
MODÈLE          VUE             CONTRÔLEUR
(données)       (affichage)     (logique)
   ↓               ↑                ↓
  Level ←—— GameRenderer ←—— GameController
   ↑                                ↓
Entities                       InputHandler
```

### 5. **Singleton** (Potentiel)

**Où** : Pourrait être appliqué à `PhysicsEngine`

**Pourquoi** : Une seule instance du moteur physique suffit

```java
// Non implémenté actuellement, mais possible :
public class PhysicsEngine {
    private static PhysicsEngine instance;

    private PhysicsEngine() {}

    public static PhysicsEngine getInstance() {
        if (instance == null) {
            instance = new PhysicsEngine();
        }
        return instance;
    }
}
```

## 🏛️ Principes SOLID Appliqués

### S - Single Responsibility Principle

Chaque classe a une seule responsabilité :

- `Player` : Gère uniquement le joueur
- `GameRenderer` : Gère uniquement le rendu
- `LevelLoader` : Gère uniquement le chargement
- `PhysicsEngine` : Gère uniquement la physique

### O - Open/Closed Principle

Ouvert à l'extension, fermé à la modification :

```java
// ✅ Ajouter un nouvel ennemi sans modifier le code existant
public class Koopa extends Entity implements Enemy {
    // Nouveau comportement
}

// Puis dans LevelLoader :
case "koopa":
    return new Koopa(x, y);
```

### L - Liskov Substitution Principle

Les objets des classes dérivées peuvent remplacer les objets de la classe de base :

```java
List<Entity> entities = new ArrayList<>();
entities.add(new Player(x, y));    // ✅
entities.add(new Goomba(x, y));    // ✅
entities.add(new Coin(x, y));      // ✅

// Tous peuvent être utilisés de manière polymorphe
for (Entity e : entities) {
    e.update(delta);  // Fonctionne pour tous
}
```

### I - Interface Segregation Principle

Interfaces spécifiques plutôt qu'une interface générique :

```java
// ✅ Deux interfaces spécialisées
interface Enemy { ... }
interface Collectible { ... }

// ❌ Au lieu d'une interface fourre-tout
interface GameObject {
    void onPlayerCollision(Player p);
    void onCollect(Player p);
    int getDamage();
    int getScoreValue();
}
```

### D - Dependency Inversion Principle

Dépendre des abstractions, pas des implémentations :

```java
// PhysicsEngine dépend de l'interface Enemy, pas d'une classe concrète
public void handlePlayerEnemyCollision(Player player, Level level) {
    for (Entity entity : level.getEntities()) {
        if (entity instanceof Enemy) {  // Interface
            Enemy enemy = (Enemy) entity;
            enemy.onPlayerCollision(player);
        }
    }
}
```

## 📊 Diagramme de Classes (Simplifié)

```
                    Entity (abstract)
                    ├─ position: Vector2
                    ├─ velocity: Vector2
                    ├─ bounds: Rectangle
                    └─ update(delta): void
                         ↑
        ┌────────────────┼────────────────┐
        │                │                │
     Player           Goomba            Coin
  ├─ health          implements      implements
  ├─ score            Enemy          Collectible
  ├─ lives
  ├─ jump()
  └─ takeDamage()


  Enemy (interface)          Collectible (interface)
  ├─ move()                  ├─ onCollect()
  ├─ onPlayerCollision()     ├─ getScoreValue()
  └─ getDamage()             └─ isCollectable()
```

## 🔄 Flux de Données

```
1. CHARGEMENT
   LevelLoader → LevelData (JSON) → Level + Entities

2. BOUCLE DE JEU
   Input → InputHandler → Player
   Player → PhysicsEngine → Collision checks
   Level.update() → All entities update
   GameRenderer → Render all

3. COLLISION
   PhysicsEngine checks:
   - Player ↔ Terrain
   - Player ↔ Enemies
   - Player ↔ Collectibles
   - Enemies ↔ Terrain
```

## 🎯 Extensibilité du Moteur

### Ajouter un Nouveau Type d'Ennemi

1. Créer `Koopa.java` :

```java
public class Koopa extends Entity implements Enemy {
    @Override
    public void move(float delta) {
        // Logique spécifique
    }
    // ...
}
```

2. Modifier `LevelLoader.createEntityFromObject()` :

```java
case "koopa":
    return new Koopa(x, y);
```

3. Utiliser dans Tiled avec `type = "koopa"`

**Aucune autre modification nécessaire !**

### Ajouter un Power-Up

1. Créer une interface `PowerUp` :

```java
public interface PowerUp {
    void applyEffect(Player player);
    int getDuration();
}
```

2. Créer `Mushroom.java` :

```java
public class Mushroom extends Entity implements PowerUp {
    @Override
    public void applyEffect(Player player) {
        player.heal(25);
    }
}
```

3. Ajouter dans le Factory

## 📚 Conclusion

Ce moteur de jeu démontre :

- ✅ Architecture **MVC** bien structurée
- ✅ **POO** : Héritage, polymorphisme, interfaces, classes abstraites
- ✅ **Design Patterns** : Factory, Strategy, Template Method, MVC
- ✅ **Principes SOLID** appliqués
- ✅ Code **extensible** et **maintenable**
- ✅ Séparation claire des responsabilités
- ✅ Documentation complète

**L'accent est mis sur la clarté et la maintenabilité plutôt que sur la complexité !**
