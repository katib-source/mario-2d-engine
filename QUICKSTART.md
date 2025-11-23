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



## 📦 Créer un JAR Exécutable

```powershell
.\gradlew.bat jar
```

Le JAR sera dans : `build/libs/Mario-game-1.0.0.jar`

Exécuter :

```powershell
java -jar build\libs\Mario-game-1.0.0.jar
```


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