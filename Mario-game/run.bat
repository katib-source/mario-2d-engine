@echo off
REM Script de lancement rapide du jeu Mario
REM Ce script compile et execute le jeu en une seule commande

echo ========================================
echo   Mario Game Engine - LibGDX
echo ========================================
echo.
echo Compilation et execution du jeu...
echo.

REM Executer le jeu avec Gradle
gradlew.bat run

echo.
echo Appuyez sur une touche pour quitter...
pause > nul
