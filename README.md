# Spawning Overhaul — Minecraft 1.20.1 NeoForge port

Port of the original Spawning Overhaul by Payangar: https://github.com/payangar-dev/spawning-overhaul

Target: Minecraft 1.20.1, NeoForge 1.20.1-47.1.106, Java 17.

The upstream design modifies acceptance probability for existing natural spawn attempts. It does not run an independent spawn scheduler or bypass vanilla mob caps. Spawners, commands and scripted/event spawns are outside its intended scope.

Build with JDK 17 and Gradle 8.8 using: gradle clean build

The JAR is generated in build/libs. The GitHub Actions workflow also compiles the project and uploads the JAR as an artifact.
