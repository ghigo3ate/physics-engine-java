# 2D Physics Engine
## Table of Contents
1. [Introduction](#introduction)
2. [Features](#features)
3. [Prerequisites](#prerequisites)
4. [Installation](#installation)
5. [Configuration](#configuration)
6. [Usage](#usage)
7. [Project Structure](#project-structure)
8. [Running the Simulation](#running-the-simulation)
---

## Introduction
The **2D Physics Engine** is a lightweight simulation framework implemented in Java using core libraries and Swing. It demonstrates rigid body dynamics, collision detection (circle vs. circle, AABB vs. AABB, circle vs. AABB), restitution-based resolution, and real-time rendering. Ideal for learning physics integration and game prototyping.

## Features
- **RigidBody Integration**: Simple Euler integration of position and velocity under applied forces.
- **Collision Detection**:
    - Circle–Circle
    - AABB–AABB (axis-aligned bounding box)
    - Circle–AABB
- **Collision Resolution**: Impulse-based response with configurable restitution (bounciness).
- **Visualization**:
    - Swing `PhysicsPanel` for real-time 2D rendering.
    - Coordinate transformation for simulation-to-screen mapping.
- **Console Logging**: `ConsoleRenderer` optionally logs collider positions and collision events.
- **Modular Design**: Easily extendable classes for new shapes or integration methods.

## Prerequisites
- **Java Development Kit** (JDK) 17 or higher installed and `JAVA_HOME` set.
- **Apache Maven** 3.6+ for build and dependency management.

## Installation
1. **Clone the repository**:
    ```bash
    git clone https://github.com/ghigo3ate/physics-engine-java.git
    cd physics-engine-java
    ```
2. **Build the project**:
    ```bash
    mvn clean package
    ```
   This compiles sources, runs any tests, and packages `target/physics-engine-1.0.0.jar`.

## Configuration
Edit restitution or simulation parameters in code (`PhysicsWorld` or `PhysicsPanel`).

## Usage
After building the JAR, run the simulation:
```bash
java -jar target/physics-engine-1.0.0.jar
```
- A Swing window titled **"2D Physics Engine Visualization"** opens.
- Two circles move and collide on a red ground plane.
- Collision events are printed:
  ```text
  Collision detected and resolved between objects!
  ```

## Project Structure
```
src/main/java/com/myproject/physics/
├── Collider.java        # Collision shapes and detection logic
├── PhysicsWorld.java    # Simulation loop and collision resolution
├── Rigidbody.java       # Mass, forces, Euler integration
├── Vector2D.java        # 2D vector math utilities
├── PhysicsPanel.java    # Swing-based renderer
├── PhysicsUI.java       # Main application entry point
└── ConsoleRenderer.java # Optional console logging of colliders
```

## Running the Simulation
1. **Launch** by running the JAR (see [Usage](#usage)).
2. **Observe** the graphical window: colored circles bounce with restitution.
3. **Monitor** the console for collision log messages.

