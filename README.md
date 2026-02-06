# JTime - Project Management Application

**Author**: Saverio [Cognome]
**Matricola**: 119159
**Course**: Metodologie di Programmazione

## Overview
JTime is a JavaFX application designed to help users manage projects and tasks efficiently. It features a persistent database (H2), a planning view, and a reporting system.

## Features
- **Project Management**: Create, list, filtering, and close projects.
- **Task Management**: Add tasks with estimated time, complete them with actual time, and track status.
- **Planning**: Visualize and assign tasks to specific dates.
- **Reporting**: Generate text-based reports for project status.

## Requirements
- Java 21 (or higher)
- Gradle 8.x (wrapper included)

## How to Run

### Command Line
Windows:

aprire la cartella del progetto in terminale
```powershell
./gradlew run
```

### IntelliJ IDEA
1. Open the project in IntelliJ.
2. Uses the Gradle tool window to run `Tasks -> application -> run`.
3. Or run `src/main/java/it/unicam/cs/mpgc/jtime119159/Main.java`.

## Persistence
The application uses an embedded H2 database.
The database files are automatically created in the `./data/` directory.
**Note**: The `data` directory is ignored by Git to prevent committing local data.

## Documentation
See `Relazione.md` for a detailed report on architecture and design decisions.
