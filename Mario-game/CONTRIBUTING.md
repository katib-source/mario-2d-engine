# Guide de Contribution et Bonnes Pratiques Git

## 🔄 Workflow Git Recommandé

### Initialisation du Projet

```bash
# Initialiser le dépôt Git
git init

# Ajouter tous les fichiers
git add .

# Premier commit
git commit -m "Initial commit: Mario Game Engine with LibGDX"

# Créer le dépôt sur GitHub et le lier
git remote add origin https://github.com/votre-username/mario-game-engine.git
git branch -M main
git push -u origin main
```

### Structure des Commits

Utilisez des messages de commit clairs et descriptifs :

```bash
# ✅ Bon
git commit -m "feat: Add Goomba enemy with horizontal movement"
git commit -m "fix: Correct collision detection for player-terrain"
git commit -m "docs: Update README with Tiled instructions"

# ❌ Mauvais
git commit -m "update"
git commit -m "fix bug"
git commit -m "changes"
```

### Préfixes de Commit Recommandés

- `feat:` - Nouvelle fonctionnalité
- `fix:` - Correction de bug
- `docs:` - Modification de documentation
- `style:` - Formatage, point-virgules manquants, etc.
- `refactor:` - Refactorisation du code
- `test:` - Ajout ou modification de tests
- `chore:` - Modifications diverses (build, configuration, etc.)

## 📋 Checklist Avant Chaque Commit

- [ ] Le code compile sans erreurs
- [ ] Les nouveaux fichiers sont ajoutés avec `git add`
- [ ] Les fichiers temporaires sont exclus (`.gitignore`)
- [ ] Le message de commit est descriptif
- [ ] La documentation est mise à jour si nécessaire

## 🌿 Stratégie de Branches

### Pour un Projet Solo

```bash
# Travailler directement sur main
git add .
git commit -m "feat: Add new feature"
git push
```

### Pour un Projet en Équipe

```bash
# Créer une branche pour chaque fonctionnalité
git checkout -b feature/add-koopa-enemy

# Développer sur la branche
git add .
git commit -m "feat: Add Koopa enemy class"

# Fusionner avec main
git checkout main
git merge feature/add-koopa-enemy
git push

# Supprimer la branche
git branch -d feature/add-koopa-enemy
```

## 👥 Contribution en Équipe

### Attribution des Tâches

Documentez clairement qui travaille sur quoi :

```markdown
## Répartition du Travail

### Membre 1 : [Nom]

- ✅ Système d'entités (Entity, Player, Enemy)
- ✅ Interface Collectible
- 🔄 Ajout de nouveaux ennemis

### Membre 2 : [Nom]

- ✅ Système de rendu (GameRenderer)
- ✅ Gestion de la caméra
- 🔄 Ajout d'animations

### Membre 3 : [Nom]

- ✅ LevelLoader et parsing JSON
- ✅ PhysicsEngine
- 🔄 Amélioration des collisions
```

### Gestion des Conflits

Si deux personnes modifient le même fichier :

```bash
# Récupérer les dernières modifications
git pull

# En cas de conflit, Git marquera les zones
<<<<<<< HEAD
Votre code
=======
Code de l'autre personne
>>>>>>> branch-name

# Résoudre manuellement, puis :
git add fichier_resolu.java
git commit -m "fix: Resolve merge conflict in Player.java"
git push
```

## 📝 Documentation des Contributions

### Dans le Rapport

Documentez clairement vos contributions :

```markdown
## Qui a fait quoi ?

### [Nom Prénom 1]

**Packages développés :**

- `com.mario.model.entity.*` (5 fichiers)
- `com.mario.model.physics.*` (1 fichier)

**Fonctionnalités implémentées :**

- Système de base des entités avec héritage
- Joueur avec mouvements et saut
- Ennemi Goomba avec IA simple
- Pièces collectables avec système de score
- Moteur physique et gestion des collisions

**Commits principaux :**

- feat: Add Entity base class (commit abc123)
- feat: Implement Player with jump mechanics (commit def456)
- feat: Add PhysicsEngine for collisions (commit ghi789)

### [Nom Prénom 2]

...
```

## 🔍 Vérification du Code

### Avant de Pousser

```bash
# Vérifier les fichiers modifiés
git status

# Vérifier les modifications
git diff

# Vérifier l'historique
git log --oneline

# Compiler et tester
.\gradlew.bat build
.\gradlew.bat run
```

## 📊 Suivi de Progression

### Utiliser les Issues GitHub

Créez des issues pour chaque tâche :

```markdown
**Titre :** Ajouter l'ennemi Koopa Troopa

**Description :**
Créer un nouvel ennemi de type Koopa avec les caractéristiques suivantes :

- Déplacement horizontal
- Change de couleur après être écrasé
- Peut être utilisé comme projectile

**Assigné à :** @membre1
**Labels :** enhancement, gameplay
**Milestone :** Version 1.1
```

### Utiliser les Pull Requests

Pour les projets en équipe :

```markdown
**Titre :** Add Koopa enemy implementation

**Description :**
This PR adds the Koopa enemy class with the following features:

- Horizontal movement pattern
- Shell mechanic when jumped on
- Collectible shell

**Files changed :**

- src/com/mario/model/entity/Koopa.java (new)
- src/com/mario/model/level/LevelLoader.java (modified)
- docs/ARCHITECTURE.md (updated)

**Testing :**

- ✅ Compilation successful
- ✅ Enemy spawns correctly from Tiled
- ✅ Collision detection works
- ✅ Shell mechanic functional

**Related issues :** #15
```

## 🏷️ Versioning

Utilisez des tags pour marquer les versions :

```bash
# Version initiale
git tag -a v1.0.0 -m "Initial release: Basic platformer engine"
git push origin v1.0.0

# Version avec nouvelles fonctionnalités
git tag -a v1.1.0 -m "Add Koopa enemy and power-ups"
git push origin v1.1.0

# Correction de bugs
git tag -a v1.0.1 -m "Fix collision bug with platforms"
git push origin v1.0.1
```

## 🚀 Workflow Complet Recommandé

```bash
# 1. Créer une branche pour votre fonctionnalité
git checkout -b feature/nouvelle-fonctionnalite

# 2. Développer et commiter régulièrement
git add fichier1.java fichier2.java
git commit -m "feat: Add partial implementation"

# 3. Tester
.\gradlew.bat build
.\gradlew.bat run

# 4. Commiter les tests
git add .
git commit -m "test: Add tests for new feature"

# 5. Mettre à jour la documentation
git add README.md ARCHITECTURE.md
git commit -m "docs: Update documentation for new feature"

# 6. Fusionner avec main
git checkout main
git merge feature/nouvelle-fonctionnalite

# 7. Pousser vers GitHub
git push origin main

# 8. Nettoyer
git branch -d feature/nouvelle-fonctionnalite
```

## 📚 Ressources Git

- [Git Documentation](https://git-scm.com/doc)
- [GitHub Guides](https://guides.github.com/)
- [Conventional Commits](https://www.conventionalcommits.org/)

## ✅ Checklist Finale Avant Rendu

- [ ] Tous les fichiers sources sont committés
- [ ] Le `.gitignore` exclut les fichiers compilés
- [ ] Le README est complet et à jour
- [ ] Le rapport PDF mentionne le lien GitHub
- [ ] Tous les membres ont des commits visibles
- [ ] Le projet compile et s'exécute depuis un clone frais
- [ ] La documentation est complète

```bash
# Test final : cloner dans un nouveau dossier
cd ..
git clone https://github.com/votre-username/mario-game-engine.git test-clone
cd test-clone
.\gradlew.bat build
.\gradlew.bat run
```

---

**Bonne collaboration !** 🤝
