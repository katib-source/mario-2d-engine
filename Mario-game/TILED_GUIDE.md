# Guide d'Utilisation de Tiled pour Mario Game Engine

## 📚 Introduction

Ce guide explique comment créer et configurer des niveaux pour le Mario Game Engine en utilisant l'éditeur de cartes **Tiled Map Editor**.

## 🔽 Installation de Tiled

1. Télécharger Tiled depuis : https://www.mapeditor.org/
2. Installer la version correspondant à votre système d'exploitation
3. Lancer Tiled

## 🗺️ Créer un Nouveau Niveau

### Étape 1 : Nouvelle Carte

1. **Fichier** → **Nouveau** → **Nouvelle Carte...**
2. Configurer les paramètres :
   - **Orientation** : Orthogonal
   - **Ordre des couches** : Droite vers le bas
   - **Largeur** : 30 tiles (ajustable)
   - **Hauteur** : 20 tiles (ajustable)
   - **Largeur de tile** : 32 pixels
   - **Hauteur de tile** : 32 pixels
3. Cliquer sur **OK**

### Étape 2 : Créer les Couches

Le moteur nécessite au minimum **2 couches** :

#### Couche 1 : Collision (Tile Layer)

Cette couche contient les tiles solides (sol, murs, plateformes).

1. Cliquer sur **Calque** → **Nouveau** → **Couche de Tiles**
2. Nom : `Collision`
3. Cliquer sur **OK**

**Important** : Tout tile avec une valeur ≠ 0 sera considéré comme solide !

#### Couche 2 : Entities (Object Layer)

Cette couche contient tous les objets du jeu (joueur, ennemis, pièces).

1. Cliquer sur **Calque** → **Nouveau** → **Couche d'Objets**
2. Nom : `Entities`
3. Cliquer sur **OK**

### Étape 3 : Dessiner le Terrain

1. Sélectionner la couche **Collision**
2. Utiliser l'outil **Pinceau** (B) ou **Remplissage** (G)
3. Utiliser la valeur `1` pour les tiles solides
4. Dessiner :
   - Le sol (lignes du bas)
   - Les plateformes
   - Les murs (si nécessaire)

**Astuce** : Vous pouvez créer des motifs intéressants avec différentes valeurs (2, 3, 4...), mais seules les tiles ≠ 0 seront solides.

### Étape 4 : Placer les Entités

1. Sélectionner la couche **Entities**
2. Cliquer sur l'outil **Insérer Rectangle** (R)
3. Dessiner un rectangle où vous voulez placer une entité
4. Dans le panneau **Propriétés**, définir le **Type** :

#### Types d'Entités Disponibles

| Type     | Taille recommandée | Description       |
| -------- | ------------------ | ----------------- |
| `player` | 32x32              | Le joueur (Mario) |
| `goomba` | 32x32              | Ennemi Goomba     |
| `coin`   | 16x16              | Pièce à collecter |

### Étape 5 : Ajouter des Propriétés Personnalisées

Pour les objets qui nécessitent des propriétés (comme la valeur d'une pièce) :

1. Sélectionner l'objet (ex: une pièce)
2. Dans le panneau **Propriétés**, cliquer sur **+** (Ajouter une propriété)
3. Nom : `scoreValue`
4. Type : `int`
5. Valeur : `10` (ou la valeur souhaitée)

**Exemple** : Créer une pièce super précieuse de 100 points !

### Étape 6 : Exporter le Niveau

1. **Fichier** → **Exporter sous...**
2. Format : **JSON map files (\*.json)**
3. Nom du fichier : `level1.json` (ou autre)
4. Enregistrer dans le dossier : `Mario-game/assets/levels/`

## 🎨 Conseils de Design de Niveau

### Placement du Joueur

- Toujours placer **UN SEUL** objet de type `player`
- Position recommandée : Début du niveau, au-dessus du sol
- Exemple : x=64, y=384

### Placement des Ennemis

- Les Goombas se déplacent automatiquement de gauche à droite
- Ils changent de direction quand ils heurtent un mur
- Placer des ennemis sur des plateformes pour plus de défi

### Placement des Pièces

- Créer des lignes de pièces pour guider le joueur
- Placer des pièces en hauteur pour encourager les sauts
- Varier la valeur des pièces (`scoreValue`) pour récompenser l'exploration

### Création de Plateformes

Exemples de patterns de plateformes :

```
Escaliers :
    [1]
  [1][1]
[1][1][1]

Saut long :
[1][1]              [1][1]

Plateforme mobile (conceptuellement) :
         [1][1][1]
```

## 🔧 Exemples de Configuration

### Exemple 1 : Pièce Standard

```json
{
  "type": "coin",
  "x": 256,
  "y": 288,
  "width": 16,
  "height": 16
}
```

### Exemple 2 : Pièce Bonus

```json
{
  "type": "coin",
  "x": 448,
  "y": 352,
  "width": 16,
  "height": 16,
  "properties": [
    {
      "name": "scoreValue",
      "type": "int",
      "value": 50
    }
  ]
}
```

### Exemple 3 : Goomba

```json
{
  "type": "goomba",
  "x": 320,
  "y": 512,
  "width": 32,
  "height": 32
}
```

## 🚀 Charger votre Niveau dans le Jeu

1. Exporter le niveau en JSON dans `assets/levels/`
2. Ouvrir `src/com/mario/controller/GameController.java`
3. Modifier la ligne 35 :

```java
loadLevel("levels/votre_niveau.json");
```

4. Compiler et exécuter le jeu

## ⚠️ Erreurs Courantes

### Le niveau ne se charge pas

- Vérifier que le fichier JSON est dans `assets/levels/`
- Vérifier le chemin dans `GameController.java`
- Vérifier la console pour les messages d'erreur

### Les collisions ne fonctionnent pas

- La couche doit s'appeler **exactement** `Collision` (sensible à la casse)
- Les tiles solides doivent avoir une valeur ≠ 0

### Les entités n'apparaissent pas

- Vérifier que la couche s'appelle `Entities`
- Vérifier que le **type** de chaque objet est correct (`player`, `goomba`, `coin`)
- Vérifier les coordonnées (Y inversé dans Tiled)

## 🎯 Idées de Niveaux

### Niveau Débutant

- Sol plat
- 2-3 Goombas espacés
- Quelques pièces faciles à collecter
- 1-2 plateformes basses

### Niveau Intermédiaire

- Plateformes de différentes hauteurs
- 5-6 ennemis
- Pièces en hauteur
- Petits sauts requis

### Niveau Difficile

- Plateformes espacées (sauts longs)
- 8-10 ennemis
- Pièces dans des endroits dangereux
- Timing requis pour éviter les ennemis

## 📖 Ressources Supplémentaires

- Documentation Tiled : https://doc.mapeditor.org/
- Tutoriels Tiled : https://doc.mapeditor.org/en/stable/manual/introduction/

## 🔮 Fonctionnalités Futures (Extensions Possibles)

- Ajout de nouveaux types d'ennemis (Koopa, etc.)
- Power-ups (champignon, fleur de feu)
- Blocs destructibles
- Portes vers d'autres niveaux
- Zones d'eau ou de lave
- Pièges mobiles

---

**Bon level design !** 🎮
