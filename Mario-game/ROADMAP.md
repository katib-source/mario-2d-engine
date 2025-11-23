# Roadmap et Fonctionnalités Futures

## 🎯 Version 1.0 (Actuelle) ✅

- [x] Architecture MVC complète
- [x] Système d'entités avec héritage
- [x] Chargement de niveaux depuis Tiled (JSON)
- [x] Physique de base (gravité, collisions)
- [x] Joueur avec mouvements et saut
- [x] Ennemi Goomba avec mouvement automatique
- [x] Pièces collectables avec système de score
- [x] Rendu simple avec formes géométriques
- [x] HUD (score, vie, vies restantes)
- [x] Caméra suivant le joueur
- [x] Documentation complète

## 🚀 Version 1.1 - Améliorations Visuelles

### Graphismes et Animations

- [ ] **Chargement de sprites depuis des images**

  - Textures pour le joueur
  - Textures pour les ennemis
  - Textures pour les pièces et power-ups
  - Tileset graphique pour le terrain

- [ ] **Système d'animations**

  - Animation de marche du joueur
  - Animation de saut
  - Animation d'inactivité (idle)
  - Animations des ennemis
  - Animation de collecte des pièces

- [ ] **Effets visuels**
  - Particules lors de la collecte
  - Animation de mort des ennemis
  - Effet de dégâts pour le joueur
  - Background parallax scrolling

### Amélioration du Code

```java
// Exemple : Système d'animation
public class AnimationComponent {
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;

    public void update(float delta) {
        stateTime += delta;
    }

    public TextureRegion getCurrentFrame() {
        return currentAnimation.getKeyFrame(stateTime, true);
    }
}
```

## 🎮 Version 1.2 - Gameplay Enrichi

### Nouveaux Ennemis

- [ ] **Koopa Troopa**
  - Marche horizontalement
  - Entre dans sa carapace quand sauté dessus
  - Carapace peut être utilisée comme projectile

```java
public class Koopa extends Entity implements Enemy {
    private boolean inShell;

    public void stompedByPlayer() {
        if (!inShell) {
            enterShell();
        } else {
            kickShell();
        }
    }
}
```

- [ ] **Piranha Plant**

  - Sort d'un tuyau périodiquement
  - Inflige des dégâts au contact
  - Ne peut pas être sauté dessus

- [ ] **Flying Enemy (Paratroopa)**
  - Déplacement en arc de cercle
  - Plus difficile à toucher

### Power-ups

- [ ] **Super Mushroom**
  - Augmente la taille du joueur
  - Donne un point de vie supplémentaire

```java
public class Mushroom extends Entity implements PowerUp {
    @Override
    public void applyEffect(Player player) {
        player.grow();
        player.addMaxHealth(50);
    }
}
```

- [ ] **Fire Flower**

  - Permet de lancer des boules de feu
  - Change la couleur du joueur

- [ ] **Star (Invincibilité)**
  - Rend invincible temporairement
  - Effet visuel spécial

### Objets Interactifs

- [ ] **Blocs ? (Question Blocks)**
  - Contiennent des pièces ou power-ups
  - Animation de frappe
  - Deviennent inactifs après utilisation

```java
public class QuestionBlock extends Entity implements Interactive {
    private boolean used = false;
    private String contains;

    public void onHitFromBelow(Player player) {
        if (!used) {
            spawnContent();
            used = true;
        }
    }
}
```

- [ ] **Briques Destructibles**

  - Peuvent être détruites par le joueur grand
  - Animation de destruction
  - Particules de débris

- [ ] **Tuyaux**
  - Entrées vers des zones secrètes
  - Téléportation entre niveaux

## 🌍 Version 1.3 - Multi-niveaux

### Système de Niveaux

- [ ] **Gestion de plusieurs niveaux**
  - Liste de niveaux configurables
  - Transition entre niveaux

```java
public class LevelManager {
    private List<String> levelPaths;
    private int currentLevelIndex;

    public void loadNextLevel() {
        currentLevelIndex++;
        return levelLoader.loadLevel(levelPaths.get(currentLevelIndex));
    }
}
```

- [ ] **Portes et Drapeaux**

  - Flag de fin de niveau
  - Animation de victoire
  - Calcul de bonus de temps

- [ ] **Zones secrètes**
  - Pièces d'une couche cachée dans Tiled
  - Accès via tuyaux

### Sauvegarde

- [ ] **Système de checkpoint**

  - Drapeaux de checkpoint
  - Respawn au dernier checkpoint

- [ ] **Sauvegarde de progression**
  - Score total
  - Niveaux débloqués
  - Meilleur temps par niveau

## 🎵 Version 1.4 - Audio

### Sons et Musique

- [ ] **Effets sonores**
  - Son de saut
  - Son de collecte de pièce
  - Son de mort
  - Son de power-up
  - Son d'ennemi écrasé

```java
public class AudioManager {
    private Map<String, Sound> sounds;
    private Music backgroundMusic;

    public void playSound(String soundName) {
        sounds.get(soundName).play();
    }
}
```

- [ ] **Musique de fond**
  - Musique du niveau
  - Musique du boss (si implémenté)
  - Musique de game over

## 🏆 Version 1.5 - Fonctionnalités Avancées

### Boss de Fin de Niveau

- [ ] **Bowser (exemple)**
  - Patterns d'attaque complexes
  - Plusieurs points de vie
  - Plusieurs phases

```java
public class Bowser extends Entity implements Enemy {
    private int phase = 1;
    private BossStrategy currentStrategy;

    public void takeDamage(int damage) {
        health -= damage;
        if (health < maxHealth / 2) {
            enterPhase2();
        }
    }
}
```

### Système de Vies et Game Over

- [ ] **Écran de Game Over**

  - Affichage du score final
  - Option de recommencer
  - Retour au menu principal

- [ ] **Système de vies extra**
  - 1UP tous les 100 pièces
  - 1UP cachés dans le niveau

### Menu Principal

- [ ] **Écran titre**
  - Logo du jeu
  - Options : Jouer, Options, Quitter
  - Animation de fond

```java
public class MenuScreen implements Screen {
    private Stage stage;
    private TextButton playButton;
    private TextButton optionsButton;

    public void show() {
        // Setup menu
    }
}
```

- [ ] **Écran d'options**
  - Volume son/musique
  - Contrôles personnalisables
  - Plein écran

### Statistiques

- [ ] **Écran de fin de niveau**
  - Pièces collectées
  - Ennemis vaincus
  - Temps écoulé
  - Score total
  - Bonus

## 🧪 Version 1.6 - Tests et Optimisation

### Tests Unitaires

- [ ] **Tests pour les entités**

  ```java
  @Test
  public void testPlayerJump() {
      Player player = new Player(0, 0);
      player.setOnGround(true);
      player.jump();
      assertTrue(player.getVelocity().y > 0);
  }
  ```

- [ ] **Tests pour les collisions**
- [ ] **Tests pour le chargement de niveaux**

### Optimisation

- [ ] **Pooling d'objets**
  - Éviter de créer/détruire constamment des objets
- [ ] **Culling de rendu**

  - Ne rendre que ce qui est visible à l'écran

- [ ] **Optimisation des collisions**
  - Spatial hashing pour réduire les vérifications

## 🌟 Extensions Créatives

### Mode Multijoueur Local

- [ ] **Deux joueurs**
  - Joueur 2 contrôlé au clavier
  - Caméra suivant les deux joueurs
  - Score partagé ou compétitif

### Éditeur de Niveaux Intégré

- [ ] **Éditeur in-game**
  - Placer des tiles
  - Placer des entités
  - Tester directement
  - Exporter en JSON

### Modes de Jeu Alternatifs

- [ ] **Mode Time Attack**

  - Finir le niveau le plus vite possible
  - Chronomètre affiché

- [ ] **Mode Survival**

  - Vagues d'ennemis
  - Score basé sur la survie

- [ ] **Mode Puzzle**
  - Niveaux nécessitant de la réflexion
  - Mécaniques spéciales

## 📱 Version 2.0 - Multi-plateforme

### Support Android

- [ ] Adaptation des contrôles tactiles
- [ ] Optimisation pour mobile
- [ ] Support de différentes résolutions

### Support Web (HTML5)

- [ ] Compilation en WebGL avec GWT
- [ ] Jouable dans le navigateur

## 🎓 Améliorations Pédagogiques

### Design Patterns Supplémentaires

- [ ] **Observer Pattern** pour les événements

  ```java
  public interface GameEventListener {
      void onCoinCollected(Coin coin);
      void onEnemyDefeated(Enemy enemy);
  }
  ```

- [ ] **Command Pattern** pour les inputs
- [ ] **State Pattern** pour l'état du joueur
- [ ] **Object Pool** pour les particules

### Architecture Avancée

- [ ] **Entity Component System (ECS)**

  - Séparation données/comportement
  - Plus flexible pour les extensions

- [ ] **Event Bus**
  - Communication découplée entre systèmes

## 📊 Priorités Recommandées

### Pour le Projet Académique (Version 1.1)

1. ✨ Ajout de sprites/textures basiques
2. 🎮 Un nouveau type d'ennemi (Koopa)
3. 💪 Un power-up (Mushroom)
4. 🎵 Sons basiques (saut, collecte)
5. 📦 Blocs interactifs (Question Block)

### Extensions Ambitieuses (Post-Projet)

1. 🏆 Boss de fin de niveau
2. 🌍 Système multi-niveaux complet
3. 🎮 Multijoueur local
4. 📱 Version mobile

---

**Note** : Cette roadmap est fournie à titre indicatif. Pour le projet académique, concentrez-vous sur la qualité du code existant plutôt que sur la quantité de fonctionnalités !

L'important est de démontrer :

- ✅ Maîtrise de la POO
- ✅ Architecture propre et maintenable
- ✅ Code bien documenté
- ✅ Extensibilité du moteur

**Conseil** : Mieux vaut un moteur simple et parfaitement implémenté qu'un moteur complexe et mal organisé !
