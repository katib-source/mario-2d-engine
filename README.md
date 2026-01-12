# Mario Game Engine

Mario Game Engine est un moteur de jeu 2D de type platformer où le joueur contrôle un personnage à travers différents niveaux, collectant des pièces et évitant des ennemis. Le projet est développé en utilisant la bibliothèque LibGDX et l'outil Tiled pour la création de cartes.

## Prérequis

- Java 17 ou version supérieure (JDK)
- LibGDX : Version compatible avec le projet
- Tiled : Utilisé pour la création des cartes
- IDE recommandé : IntelliJ IDEA ou vs code
## Installation

Clonez ce dépôt sur votre machine locale en utilisant la commande suivante :

```bash
git clone https://github.com/katib-source/mario-2d-engine.git
```


## Compilation et Exécution

Après avoir ouvert le projet, exécutez les commandes suivantes :

```bash
# Windows
.\gradlew.bat run

# Linux/Mac
./gradlew run
```

## Fonctionnalités

- Naviguer dans les niveaux avec les touches du clavier.
- Collecter des pièces pour augmenter le score.
- Éviter ou éliminer les ennemis (Goombas).
- Terminer le niveau en atteignant la fin de la carte.
- Système audio intégré.
- Caméra suivant le joueur.

## Architecture du Jeu

Le moteur du jeu est basé sur une architecture MVC avec plusieurs composants :

- **Entity** : Classe de base pour tous les objets du jeu.
- **LevelLoader** : Gère le chargement des niveaux TMX.
- **CollisionSystem** : Gère les collisions entre objets.
- **Factory Method** : Pattern utilisé pour la création des entités.
- **HUD** : Gère l'affichage du score et des informations.

## Création de Niveaux

1. Créez une carte dans Tiled Map Editor.
2. Ajoutez les calques : `Collision` (tuiles), `Entities` (objets).
3. Types d'entités supportés : `player`, `goomba`, `coin`.
4. Sauvegardez en `.tmx` dans `assets/levels/`.

## Ajout de Nouvelles Entités

1. Créez une classe étendant `Entity`.
2. Enregistrez dans `LevelLoader.createEntityFromObject()`.
3. Utilisez dans Tiled avec le `type` correspondant.

## Contributions

Tous les membres ont contribué activement au code Java et ont travaillé ensemble pour intégrer de manière fluide les différents modules du jeu.

## Perspectives

Quelques améliorations possibles :

- Ajouter des niveaux avec des difficultés croissantes.
- Implémenter des power-ups (champignons, étoiles).
- Ajouter des boss de fin de niveau.
- Supporter les appareils mobiles Android.
