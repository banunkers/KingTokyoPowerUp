#!/bin/bash
# Script that compiles and starts the game by compiling and running one server and two bot clients

javac -d bin src/*.java

# gnome-terminal working-directory='github/D7032E_Home_Exam/KingTokyoPowerUp' -- java bin.KingTokyoPowerUpServer
# gnome-terminal -- bash -c 'java KingTokyoPowerUpClient bot'
# gnome-terminal -- bash -c 'java KingTokyoPowerUpClient bot'
gnome-terminal working-directory='github/D7032E_Home_Exam/KingTokyoPowerUp/bin' -- 'java bin/KingTokyoPowerUpServer.class'