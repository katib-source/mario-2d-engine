# 📋 Checklist de Rendu - Mario Game Engine

## ✅ Fichiers à Inclure

### Code Source

- [x] `src/` - Tout le code Java organisé en packages MVC
- [x] `assets/levels/level1.json` - Niveau exemple fonctionnel
- [x] `build.gradle` - Configuration Gradle avec dépendances
- [x] `settings.gradle` - Paramètres Gradle

### Scripts d'Exécution

- [x] `run.bat` - Script Windows pour lancer le jeu
- [x] `run.sh` - Script Linux/Mac pour lancer le jeu
- [x] `build.bat` - Script Windows pour compiler
- [x] `gradlew.bat` - Gradle wrapper Windows

### Documentation

- [x] `README.md` - Documentation principale
- [x] `ARCHITECTURE.md` - Explication de l'architecture et patterns
- [x] `TILED_GUIDE.md` - Guide d'utilisation de Tiled
- [x] `CONTRIBUTING.md` - Guide Git et collaboration
- [x] `ROADMAP.md` - Fonctionnalités futures
- [x] `LICENSE` - Licence du projet

### Configuration

- [x] `.gitignore` - Fichiers à exclure du versioning

## 📝 Rapport PDF (À Créer)

### Structure Recommandée du Rapport

#### Page de Garde

- Titre du projet
- Noms des membres
- Date de rendu
- Logo de l'université (si applicable)

#### 1. Introduction (1 page)

```markdown
## Introduction

Ce projet consiste en la réalisation d'un moteur de jeu 2D de type
platformer utilisant la librairie LibGDX. L'objectif est de créer un
système extensible permettant de configurer entièrement les niveaux
via l'éditeur Tiled, sans modification du code Java.

**Lien GitHub :** [votre-lien-ici]

**Technologies utilisées :**

- Java 11
- LibGDX 1.12.1
- Gradle
- Tiled Map Editor
- Gson (parsing JSON)
```

#### 2. Architecture du Projet (2-3 pages)

```markdown
## Architecture MVC

### Modèle (Model)

[Décrire les packages model.entity, model.level, model.physics]
[Inclure le diagramme de classes]

### Vue (View)

[Décrire GameRenderer]

### Contrôleur (Controller)

[Décrire GameController et InputHandler]

## Diagramme de Classes

[Insérer le diagramme depuis ARCHITECTURE.md]

## Design Patterns Utilisés

1. Template Method (Entity)
2. Factory Method (LevelLoader)
3. Strategy (Enemy, Collectible)
4. MVC (Architecture globale)
```

#### 3. Concepts POO Appliqués (1-2 pages)

```markdown
## Héritage

- Classe de base Entity
- Player, Goomba, Coin héritent de Entity

## Polymorphisme

- Manipulation des entités via List<Entity>
- Appel polymorphe de update()

## Interfaces

- Enemy : définit le comportement des ennemis
- Collectible : définit le comportement des objets collectables

## Encapsulation

- Attributs protected/private
- Accès via getters/setters
```

#### 4. Extensibilité via Tiled (1-2 pages)

```markdown
## Chargement depuis JSON

Le système de chargement permet d'ajouter de nouveaux éléments
sans toucher au code :

1. Créer le niveau dans Tiled
2. Exporter en JSON
3. Placer dans assets/levels/

## Ajout de Nouveaux Types

Pour ajouter un nouveau type d'entité :
[Expliquer la procédure avec exemple de code]
```

#### 5. Fonctionnalités Implémentées (1 page)

```markdown
## Fonctionnalités Actuelles

✅ Chargement de niveaux depuis Tiled (JSON)
✅ Système d'entités avec héritage et polymorphisme
✅ Physique de platformer (gravité, collisions)
✅ Joueur avec mouvements, saut, vie, score
✅ Ennemi Goomba avec IA basique
✅ Pièces collectables avec valeurs configurables
✅ HUD affichant les statistiques
✅ Caméra suivant le joueur
✅ Architecture MVC propre et documentée
```

#### 6. Contribution de Chaque Membre (IMPORTANT - 1-2 pages)

```markdown
## Répartition du Travail

### [Nom Prénom 1]

**Packages développés :**

- com.mario.model.entity (100%)

  - Entity.java (classe abstraite de base)
  - Player.java (gestion du joueur)
  - Goomba.java (ennemi avec IA)
  - Coin.java (objet collectable)
  - Enemy.java (interface)
  - Collectible.java (interface)

- com.mario.model.physics (100%)
  - PhysicsEngine.java (collisions et gravité)

**Responsabilités :**

- Design de l'architecture des entités
- Implémentation du système de collision
- Tests et débogage du système physique

**Commits principaux :**

- feat: Add Entity base class (abc123)
- feat: Implement Player mechanics (def456)
- feat: Add collision detection (ghi789)
- fix: Correct gravity calculation (jkl012)

**Lignes de code :** ~400 lignes

---

### [Nom Prénom 2]

**Packages développés :**

- com.mario.model.level (100%)
  - Level.java
  - LevelData.java
  - LevelLoader.java

**Responsabilités :**

- Système de chargement JSON
- Parser Tiled
- Factory pattern pour création d'entités

**Commits principaux :**

- feat: Add JSON level loading (mno345)
- feat: Implement LevelLoader factory (pqr678)

**Lignes de code :** ~350 lignes

---

### [Nom Prénom 3]

**Packages développés :**

- com.mario.view (100%)
  - GameRenderer.java
- com.mario.controller (100%)
  - GameController.java
  - InputHandler.java

**Responsabilités :**

- Système de rendu
- Boucle de jeu
- Gestion des inputs

**Commits principaux :**

- feat: Add GameRenderer (stu901)
- feat: Implement game loop (vwx234)

**Lignes de code :** ~300 lignes
```

#### 7. Compilation et Exécution (1 page)

````markdown
## Instructions de Compilation

### Prérequis

- Java JDK 11+
- Git

### Compilation

Windows :

```powershell
.\gradlew.bat build
```
````

Linux/Mac :

```bash
./gradlew build
```

### Exécution

Windows :

```powershell
.\run.bat
```

Linux/Mac :

```bash
chmod +x run.sh
./run.sh
```

## Test depuis un Clone Frais

```bash
git clone [votre-lien-github]
cd mario-game-engine
.\gradlew.bat run
```

````

#### 8. Améliorations Futures (1 page)

```markdown
## Roadmap

Version 1.1 (prioritaire) :
- Ajout de sprites/textures
- Nouveau type d'ennemi (Koopa)
- Power-up (Mushroom)
- Blocs interactifs

Version 1.2+ :
- Multi-niveaux
- Sons et musique
- Boss de fin
- Menu principal

[Voir ROADMAP.md pour détails]
````

#### 9. Conclusion (1 page)

```markdown
## Conclusion

Ce projet nous a permis de :

✅ Approfondir nos connaissances en POO
✅ Appliquer les design patterns de manière concrète
✅ Structurer un projet avec architecture MVC
✅ Travailler en équipe avec Git
✅ Créer un système extensible et maintenable

**Points forts :**

- Code propre et bien documenté
- Architecture claire et extensible
- Séparation des responsabilités
- Documentation complète

**Points d'amélioration possibles :**

- Ajout de graphismes
- Plus de types d'entités
- Système de sons
- Tests unitaires

**Ressources utilisées :**

- Documentation LibGDX
- Documentation Tiled
- Cours de POO
- GitHub Copilot pour la génération de code de base
```

#### 10. Annexes

- Captures d'écran du jeu
- Diagrammes UML
- Extrait de code significatif
- Structure du fichier JSON Tiled

## 🖼️ Éléments Visuels à Inclure

### Captures d'Écran

- [ ] Écran de jeu avec joueur et ennemis
- [ ] Niveau dans Tiled
- [ ] Structure des fichiers dans l'explorateur
- [ ] Exécution du jeu dans le terminal

### Diagrammes

- [ ] Diagramme de classes UML
- [ ] Diagramme de séquence (chargement de niveau)
- [ ] Architecture MVC schématique

## 📊 Statistiques du Projet

```markdown
## Métriques

- **Lignes de code Java :** ~1000+
- **Nombre de classes :** 15+
- **Packages :** 5
- **Fichiers de documentation :** 6
- **Commits Git :** [À compter]
- **Durée du projet :** [X semaines]
```

## 🔗 Lien GitHub

Dans le rapport ET le README, inclure :

```markdown
## Lien GitHub

🔗 **Repository :** https://github.com/[username]/mario-game-engine

Le dépôt contient :

- ✅ Tout le code source
- ✅ Documentation complète
- ✅ Historique de commits
- ✅ Scripts d'exécution
- ✅ Niveau exemple
```

## ✅ Checklist Finale Avant Rendu

### Code

- [ ] Le projet compile sans erreurs
- [ ] Le jeu s'exécute correctement
- [ ] Tous les fichiers sources sont présents
- [ ] Le code est commenté et documenté

### Git & GitHub

- [ ] Dépôt GitHub créé et public
- [ ] Tous les commits sont poussés
- [ ] README complet avec lien GitHub
- [ ] .gitignore correctement configuré
- [ ] Chaque membre a des commits visibles

### Documentation

- [ ] README.md complet
- [ ] ARCHITECTURE.md explique les patterns
- [ ] TILED_GUIDE.md fonctionnel
- [ ] Commentaires Javadoc sur toutes les classes publiques

### Rapport PDF

- [ ] Page de garde complète
- [ ] Lien GitHub présent
- [ ] Section "Qui a fait quoi" détaillée
- [ ] Instructions de compilation claires
- [ ] Captures d'écran incluses
- [ ] Diagrammes de classes
- [ ] Conclusion rédigée
- [ ] PDF exporté et nommé correctement

### Scripts d'Exécution

- [ ] `run.bat` fonctionne sous Windows
- [ ] `run.sh` fonctionne sous Linux/Mac
- [ ] Instructions dans README testées

### Test Final

- [ ] Cloner le projet dans un nouveau dossier
- [ ] Compiler depuis zéro
- [ ] Exécuter le jeu
- [ ] Vérifier que tout fonctionne

```bash
# Test complet
cd ..
git clone [votre-url-github] test-final
cd test-final
.\gradlew.bat clean build run
```

## 📦 Livrable Final

### Structure du Rendu

```
📁 Rendu/
├── 📄 Rapport_Mario_GameEngine.pdf
├── 📄 Lien_GitHub.txt (contenant l'URL)
└── 📁 Code/ (optionnel si déjà sur GitHub)
    ├── src/
    ├── assets/
    ├── build.gradle
    ├── README.md
    └── ...
```

### Contenu du Fichier Lien_GitHub.txt

```
Projet : Mario Game Engine
Membres : [Noms des membres]

Lien GitHub : https://github.com/[username]/mario-game-engine

Instructions :
1. Cloner le dépôt
2. Exécuter : .\gradlew.bat run
3. Documentation complète dans README.md

Date de rendu : [Date]
```

## 🎯 Critères d'Évaluation (Rappel)

### Points Importants

1. ✅ Respect des consignes (MVC, Tiled, POO)
2. ✅ Extensibilité du moteur
3. ✅ Qualité et clarté du code
4. ✅ Documentation (README, commentaires)
5. ✅ Contribution individuelle claire
6. ✅ Utilisation de Git

### Bonus

- ✅ Architecture MVC stricte
- ✅ Design Patterns appliqués
- ✅ Code simple et maintenable

---

**Bon courage pour le rendu !** 🚀

**Rappel :** Un code simple et bien organisé vaut mieux qu'un code complexe et confus !
