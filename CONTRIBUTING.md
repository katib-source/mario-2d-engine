# Guide de Contribution et Bonnes Pratiques Git

git init

git add .

git commit -m "Initial commit: Mario Game Engine with LibGDX"


git remote add origin https://github.com/katib-source/mario-2d-engine.git
git branch -M main
git push -u origin main




### Pour un Projet Solo


git add .
git commit -m "feat: Add new feature"
git push


### Pour un Projet en Équipe


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

