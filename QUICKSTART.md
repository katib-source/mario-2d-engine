# 🚀 Quick Start Guide - Mario Game Engine

Bienvenue ! Ce guide vous permettra de lancer le jeu en **moins de 5 minutes**.

## ⚡ Démarrage Ultra-Rapide

### Option 1 : Utiliser le Script (Recommandé)

**Windows :**

```powershell
.\run.bat
```

**Linux/Mac :**

```bash
chmod +x run.sh
./run.sh
```

C'est tout ! Le jeu se lance automatiquement. 🎮

---

### Option 2 : Via Gradle

**Windows :**

```powershell
.\gradlew.bat run
```

**Linux/Mac :**

```bash
./gradlew run
```

---

## 🎮 Contrôles

| Touche                       | Action         |
| ---------------------------- | -------------- |
| **←** ou **Q**               | Aller à gauche |
| **→** ou **D**               | Aller à droite |
| **ESPACE** ou **↑** ou **Z** | Sauter         |

---

## 🎯 Objectif du Jeu

- Collecter les **pièces** 🪙 pour augmenter votre score
- Éviter ou écraser les **Goombas** 👾
- Atteindre la fin du niveau

---

## 🏗️ Structure du Projet (En Bref)

```
Mario-game/
├── src/com/mario/         # Code source
│   ├── model/             # Entités, niveaux, physique
│   ├── view/              # Rendu graphique
│   └── controller/        # Logique de jeu
├── assets/levels/         # Niveaux (JSON Tiled)
├── build.gradle           # Configuration
└── README.md              # Documentation complète
```

---

## 📚 Documentation Complète

Pour en savoir plus, consultez :

- **README.md** - Documentation principale
- **TILED_GUIDE.md** - Créer vos propres niveaux
- **ARCHITECTURE.md** - Design patterns et architecture
- **CONTRIBUTING.md** - Travailler avec Git

---

## 🆘 Problèmes Courants

### Le jeu ne se lance pas

1. Vérifier Java :

   ```powershell
   java -version
   ```

   (Doit afficher Java 11+)

2. Si Java n'est pas installé :
   - Télécharger depuis https://adoptium.net/
   - Installer et redémarrer le terminal

### Erreur de compilation

```powershell
# Nettoyer et recompiler
.\gradlew.bat clean build run
```

### Le niveau ne se charge pas

- Vérifier que `assets/levels/level1.json` existe
- Si absent, un niveau de test est créé automatiquement

---

## 🎨 Créer Votre Propre Niveau

1. Télécharger **Tiled** : https://www.mapeditor.org/
2. Lire le guide : **TILED_GUIDE.md**
3. Créer votre niveau
4. Exporter en JSON dans `assets/levels/`
5. Modifier `GameController.java` ligne 35 pour charger votre niveau

---

## 🔧 Personnalisation Rapide

### Changer la taille de la fenêtre

Fichier : `src/com/mario/Main.java`

```java
config.setWindowedMode(800, 600);  // Modifier ici
```

### Ajouter des vies au joueur

Fichier : `src/com/mario/model/entity/Player.java`

```java
this.lives = 3;  // Modifier ici (ligne 34)
```

### Modifier la vitesse du joueur

Fichier : `src/com/mario/model/entity/Player.java`

```java
private static final float MOVE_SPEED = 150f;  // Modifier ici (ligne 13)
```

---

## 📦 Créer un JAR Exécutable

```powershell
.\gradlew.bat jar
```

Le JAR sera dans : `build/libs/Mario-game-1.0.0.jar`

Exécuter :

```powershell
java -jar build\libs\Mario-game-1.0.0.jar
```

---

## 🌟 Aller Plus Loin

### Ajouter un Nouveau Type d'Ennemi

1. Créer la classe dans `src/com/mario/model/entity/`
2. Ajouter dans `LevelLoader.createEntityFromObject()`
3. Utiliser dans Tiled avec le nouveau type

**Exemple complet dans ARCHITECTURE.md**

### Contribuer au Projet

1. Fork sur GitHub
2. Créer une branche : `git checkout -b feature/ma-feature`
3. Commiter : `git commit -m "feat: Add awesome feature"`
4. Pousser : `git push origin feature/ma-feature`
5. Créer une Pull Request

---

## 🔗 Liens Utiles

- **Documentation LibGDX** : https://libgdx.com/wiki/
- **Tiled Documentation** : https://doc.mapeditor.org/
- **Java Documentation** : https://docs.oracle.com/en/java/
- **GitHub du Projet** : [votre-lien-ici]

---

## ❓ Questions Fréquentes

### Puis-je modifier les graphismes ?

Oui ! Pour l'instant le rendu utilise des formes simples, mais vous pouvez ajouter des sprites en modifiant `GameRenderer.java`.

### Comment ajouter des sons ?

Voir la section "Audio" dans **ROADMAP.md** pour un guide complet.

### Le jeu fonctionne-t-il sur mobile ?

Pas encore, mais LibGDX le permet. Voir **ROADMAP.md** section "Multi-plateforme".

---

## 🎓 Pour les Étudiants

Ce projet est conçu pour :

- ✅ Apprendre la POO en pratique
- ✅ Comprendre l'architecture MVC
- ✅ Découvrir les design patterns
- ✅ Pratiquer Git et la collaboration

**Conseil** : Lisez d'abord **ARCHITECTURE.md** pour comprendre la structure avant de modifier le code !

---

## 🚀 Commandes Essentielles

```powershell
# Lancer le jeu
.\run.bat

# Compiler
.\gradlew.bat build

# Nettoyer et recompiler
.\gradlew.bat clean build

# Créer un JAR
.\gradlew.bat jar

# Voir les tâches disponibles
.\gradlew.bat tasks
```

---

## 🎉 C'est Tout !

Vous êtes prêt à jouer et à développer !

**Bon jeu et bon code !** 🎮👨‍💻

---

**Besoin d'aide ?** Consultez le **README.md** complet ou ouvrez une issue sur GitHub.
