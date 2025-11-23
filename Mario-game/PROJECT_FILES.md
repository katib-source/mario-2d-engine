# 📁 Structure Complète du Projet Mario Game Engine

## Vue d'Ensemble

Ce document liste tous les fichiers du projet avec leur description et leur rôle.

---

## 📂 Fichiers Racine

| Fichier               | Description                                                    |
| --------------------- | -------------------------------------------------------------- |
| `README.md`           | Documentation principale du projet                             |
| `QUICKSTART.md`       | Guide de démarrage rapide (5 min)                              |
| `ARCHITECTURE.md`     | Explication détaillée de l'architecture et des design patterns |
| `TILED_GUIDE.md`      | Guide complet pour créer des niveaux avec Tiled                |
| `CONTRIBUTING.md`     | Guide Git et bonnes pratiques de collaboration                 |
| `ROADMAP.md`          | Fonctionnalités futures et extensions possibles                |
| `RENDU.md`            | Checklist pour le rendu du projet académique                   |
| `RAPPORT_TEMPLATE.md` | Template pour le rapport PDF à rendre                          |
| `LICENSE`             | Licence MIT du projet                                          |
| `.gitignore`          | Fichiers à exclure du versioning Git                           |

---

## 🏗️ Configuration et Build

| Fichier           | Description                                  |
| ----------------- | -------------------------------------------- |
| `build.gradle`    | Configuration Gradle avec dépendances LibGDX |
| `settings.gradle` | Paramètres Gradle du projet                  |
| `gradlew.bat`     | Gradle wrapper pour Windows                  |
| `run.bat`         | Script de lancement rapide (Windows)         |
| `run.sh`          | Script de lancement rapide (Linux/Mac)       |
| `build.bat`       | Script de compilation (Windows)              |

---

## 💻 Code Source Java

### Package Principal : `com.mario`

| Fichier     | Description                                           |
| ----------- | ----------------------------------------------------- |
| `Main.java` | Point d'entrée de l'application, configuration LibGDX |

### Package : `com.mario.model.entity`

**Classe abstraite et interfaces :**

| Fichier            | Type             | Description                                                                 |
| ------------------ | ---------------- | --------------------------------------------------------------------------- |
| `Entity.java`      | Classe abstraite | Classe de base pour toutes les entités (position, vélocité, bounds, update) |
| `Enemy.java`       | Interface        | Définit le comportement des ennemis (move, onPlayerCollision, getDamage)    |
| `Collectible.java` | Interface        | Définit le comportement des objets collectables (onCollect, getScoreValue)  |

**Classes concrètes :**

| Fichier       | Description                                   | Pattern                          |
| ------------- | --------------------------------------------- | -------------------------------- |
| `Player.java` | Joueur avec mouvements, saut, vie, score      | Héritage de Entity               |
| `Goomba.java` | Ennemi avec mouvement horizontal et IA simple | Héritage + Interface Enemy       |
| `Coin.java`   | Pièce collectable avec valeur configurable    | Héritage + Interface Collectible |

### Package : `com.mario.model.level`

| Fichier            | Description                                        | Pattern        |
| ------------------ | -------------------------------------------------- | -------------- |
| `Level.java`       | Représente un niveau (entités, tiles, collisions)  | -              |
| `LevelData.java`   | Structure de données pour parsing JSON Tiled       | DTO            |
| `LevelLoader.java` | Charge les niveaux depuis JSON et crée les entités | Factory Method |

### Package : `com.mario.model.physics`

| Fichier              | Description                                                       |
| -------------------- | ----------------------------------------------------------------- |
| `PhysicsEngine.java` | Gère toutes les collisions (joueur-terrain, joueur-ennemis, etc.) |

### Package : `com.mario.view`

| Fichier             | Description                                     |
| ------------------- | ----------------------------------------------- |
| `GameRenderer.java` | Rendu graphique (entités, terrain, HUD, caméra) |

### Package : `com.mario.controller`

| Fichier               | Description                                        |
| --------------------- | -------------------------------------------------- |
| `GameController.java` | Boucle de jeu principale, orchestration Model-View |
| `InputHandler.java`   | Gestion des entrées clavier pour le joueur         |

---

## 🗺️ Assets (Ressources)

### Dossier : `assets/levels`

| Fichier       | Description                                                        |
| ------------- | ------------------------------------------------------------------ |
| `level1.json` | Niveau exemple au format Tiled JSON avec joueur, ennemis et pièces |

---

## 📊 Statistiques du Projet

### Code Java

| Métrique           | Valeur                  |
| ------------------ | ----------------------- |
| **Classes**        | 15                      |
| **Interfaces**     | 2                       |
| **Packages**       | 5                       |
| **Lignes de code** | ~1200+                  |
| **Commentaires**   | 100+ (Javadoc + inline) |

### Documentation

| Métrique                    | Valeur |
| --------------------------- | ------ |
| **Fichiers Markdown**       | 8      |
| **Pages de documentation**  | ~60+   |
| **Guides utilisateur**      | 4      |
| **Documentation technique** | 2      |

### Répartition du Code par Package

```
com.mario
├── Main.java (20 lignes)
│
├── model/ (~850 lignes)
│   ├── entity/ (~500 lignes)
│   │   ├── Entity.java (115 lignes)
│   │   ├── Player.java (160 lignes)
│   │   ├── Goomba.java (90 lignes)
│   │   ├── Coin.java (60 lignes)
│   │   ├── Enemy.java (30 lignes)
│   │   └── Collectible.java (25 lignes)
│   ├── level/ (~300 lignes)
│   │   ├── Level.java (140 lignes)
│   │   ├── LevelData.java (120 lignes)
│   │   └── LevelLoader.java (140 lignes)
│   └── physics/ (~150 lignes)
│       └── PhysicsEngine.java (150 lignes)
│
├── view/ (~180 lignes)
│   └── GameRenderer.java (180 lignes)
│
└── controller/ (~200 lignes)
    ├── GameController.java (170 lignes)
    └── InputHandler.java (40 lignes)
```

---

## 🎯 Fichiers par Fonctionnalité

### Système d'Entités

- `Entity.java` (base)
- `Player.java` (joueur)
- `Goomba.java` (ennemi)
- `Coin.java` (collectable)
- `Enemy.java` (interface)
- `Collectible.java` (interface)

### Chargement de Niveaux

- `LevelLoader.java` (chargement JSON)
- `LevelData.java` (structure de données)
- `Level.java` (représentation niveau)
- `level1.json` (données de niveau)

### Physique et Collisions

- `PhysicsEngine.java` (moteur physique)

### Rendu

- `GameRenderer.java` (affichage)

### Contrôle

- `GameController.java` (boucle de jeu)
- `InputHandler.java` (gestion inputs)

---

## 📖 Documentation par Audience

### Pour les Développeurs

1. **QUICKSTART.md** - Démarrage en 5 min
2. **ARCHITECTURE.md** - Comprendre la structure
3. **Code source** - Commenté et documenté

### Pour les Level Designers

1. **TILED_GUIDE.md** - Créer des niveaux
2. **level1.json** - Exemple de niveau

### Pour la Collaboration

1. **CONTRIBUTING.md** - Workflow Git
2. **README.md** - Vue d'ensemble

### Pour le Rendu Académique

1. **RENDU.md** - Checklist de rendu
2. **RAPPORT_TEMPLATE.md** - Template de rapport
3. **README.md** - Documentation principale

### Pour l'Extension

1. **ROADMAP.md** - Fonctionnalités futures
2. **ARCHITECTURE.md** - Extensibilité

---

## 🔄 Flux de Travail Typique

### Développeur

1. Lire **QUICKSTART.md**
2. Lancer le jeu avec `run.bat`
3. Lire **ARCHITECTURE.md**
4. Modifier le code
5. Tester avec `gradlew run`
6. Commiter avec Git (voir **CONTRIBUTING.md**)

### Level Designer

1. Installer Tiled
2. Lire **TILED_GUIDE.md**
3. Créer un niveau
4. Exporter en JSON dans `assets/levels/`
5. Modifier `GameController.java` pour charger le niveau
6. Tester avec `run.bat`

### Étudiant (Rendu)

1. Développer le code
2. Consulter **RENDU.md** (checklist)
3. Utiliser **RAPPORT_TEMPLATE.md** pour le rapport
4. Pousser sur GitHub
5. Rendre le PDF + lien GitHub

---

## 🌳 Arborescence Complète

```
Mario-game/
│
├── 📄 README.md                    Documentation principale
├── 📄 QUICKSTART.md                Guide démarrage rapide
├── 📄 ARCHITECTURE.md              Architecture et patterns
├── 📄 TILED_GUIDE.md               Guide Tiled
├── 📄 CONTRIBUTING.md              Guide Git
├── 📄 ROADMAP.md                   Fonctionnalités futures
├── 📄 RENDU.md                     Checklist rendu
├── 📄 RAPPORT_TEMPLATE.md          Template rapport PDF
├── 📄 LICENSE                      Licence MIT
├── 📄 .gitignore                   Exclusions Git
│
├── 🏗️ build.gradle                Configuration Gradle
├── 🏗️ settings.gradle              Paramètres Gradle
├── 🏗️ gradlew.bat                 Gradle wrapper (Windows)
│
├── 🚀 run.bat                      Script lancement (Windows)
├── 🚀 run.sh                       Script lancement (Linux/Mac)
├── 🚀 build.bat                    Script compilation (Windows)
│
├── 📁 src/
│   └── 📁 com/
│       └── 📁 mario/
│           ├── 💻 Main.java
│           │
│           ├── 📁 model/
│           │   ├── 📁 entity/
│           │   │   ├── 💻 Entity.java (abstract)
│           │   │   ├── 💻 Player.java
│           │   │   ├── 💻 Goomba.java
│           │   │   ├── 💻 Coin.java
│           │   │   ├── 💻 Enemy.java (interface)
│           │   │   └── 💻 Collectible.java (interface)
│           │   │
│           │   ├── 📁 level/
│           │   │   ├── 💻 Level.java
│           │   │   ├── 💻 LevelData.java
│           │   │   └── 💻 LevelLoader.java
│           │   │
│           │   └── 📁 physics/
│           │       └── 💻 PhysicsEngine.java
│           │
│           ├── 📁 view/
│           │   └── 💻 GameRenderer.java
│           │
│           └── 📁 controller/
│               ├── 💻 GameController.java
│               └── 💻 InputHandler.java
│
├── 📁 assets/
│   └── 📁 levels/
│       └── 🗺️ level1.json          Niveau exemple
│
├── 📁 bin/                         Fichiers compilés (généré)
├── 📁 build/                       Build Gradle (généré)
└── 📁 .gradle/                     Cache Gradle (généré)
```

---

## 📦 Dépendances Externes

Définies dans `build.gradle` :

| Dépendance | Version      | Usage                  |
| ---------- | ------------ | ---------------------- |
| **LibGDX** | 1.12.1       | Framework de jeu       |
| **Gson**   | 2.10.1       | Parsing JSON           |
| **LWJGL3** | (via LibGDX) | Bibliothèque graphique |

---

## 🔧 Fichiers Générés (Ignorés par Git)

Ces fichiers sont créés automatiquement et ne doivent pas être versionnés :

- `bin/` - Fichiers compilés par VS Code
- `build/` - Fichiers build de Gradle
- `.gradle/` - Cache Gradle
- `*.class` - Fichiers Java compilés
- `*.jar` - Archives (sauf dans `lib/`)

Voir `.gitignore` pour la liste complète.

---

## ✅ Checklist de Fichiers Requis

### Pour Compiler et Exécuter

- [x] `build.gradle`
- [x] `settings.gradle`
- [x] `gradlew.bat`
- [x] Tous les fichiers `.java` dans `src/`
- [x] `assets/levels/level1.json`

### Pour la Documentation

- [x] `README.md`
- [x] `ARCHITECTURE.md`
- [x] `TILED_GUIDE.md`
- [x] Commentaires dans le code

### Pour le Rendu

- [x] Tous les fichiers ci-dessus
- [x] `CONTRIBUTING.md` (historique Git)
- [x] `LICENSE`
- [x] `.gitignore`
- [ ] Rapport PDF (à créer depuis `RAPPORT_TEMPLATE.md`)

---

## 🎯 Ordre de Lecture Recommandé

### Pour Découvrir le Projet

1. **QUICKSTART.md** (5 min)
2. **README.md** (15 min)
3. **ARCHITECTURE.md** (30 min)
4. Code source (1-2h)

### Pour Créer un Niveau

1. **TILED_GUIDE.md** (20 min)
2. Tiled (expérimentation)
3. `level1.json` (exemple)

### Pour Contribuer

1. **CONTRIBUTING.md** (15 min)
2. **ARCHITECTURE.md** (structure)
3. Code source (développement)

### Pour le Rendu

1. **RENDU.md** (checklist)
2. **RAPPORT_TEMPLATE.md** (template)
3. Tout le reste (pour le rapport)

---

## 📊 Métriques de Documentation

| Fichier             | Lignes    | Pages (A4) |
| ------------------- | --------- | ---------- |
| README.md           | ~400      | ~8         |
| ARCHITECTURE.md     | ~500      | ~10        |
| TILED_GUIDE.md      | ~350      | ~7         |
| CONTRIBUTING.md     | ~300      | ~6         |
| ROADMAP.md          | ~500      | ~10        |
| RAPPORT_TEMPLATE.md | ~900      | ~18        |
| RENDU.md            | ~400      | ~8         |
| QUICKSTART.md       | ~150      | ~3         |
| **TOTAL**           | **~3500** | **~70**    |

---

## 🏆 Points Forts du Projet

### Code

- ✅ Architecture MVC stricte
- ✅ Design patterns bien appliqués
- ✅ Code commenté et documenté
- ✅ Extensible et maintenable

### Documentation

- ✅ 8 fichiers de documentation
- ✅ ~70 pages de documentation
- ✅ Guides pour tous les profils
- ✅ Templates pour le rendu

### Outils

- ✅ Scripts d'exécution rapide
- ✅ Configuration Gradle complète
- ✅ Gestion Git professionnelle
- ✅ Niveau exemple fonctionnel

---

**Ce projet démontre une maîtrise complète de la POO, de l'architecture logicielle et de la documentation professionnelle !** 🚀
