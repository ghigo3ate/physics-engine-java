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
The **2D Physics Engine** is a lightweight simulation framework implemented in Java using core libraries and Swing. It demonstrates rigid body dynamics, collision detection (circle vs. circle, AABB vs. AABB, circle vs. AABB), impulse-based resolution, and real-time rendering. Ideal for learning physics integration and game prototyping.

## Features
- **RigidBody Integration**: Euler integration of position and velocity under applied forces.
- **Collision Detection**:
    - Circle–Circle
    - AABB–AABB (axis-aligned bounding box)
    - Circle–AABB
- **Collision Resolution**: Impulse-based response with configurable restitution (bounciness).
- **Visualization**:
    - Swing `PhysicsPanel` for real-time 2D rendering with customizable ball color.
    - Coordinate transformation for simulation-to-screen mapping.
- **UI Controls**:
    - **Number of Balls**: Text input for dynamic ball count.
    - **Start**: Launches the simulation with current settings (or press `Enter`).
    - **Pause/Resume**: Toggle simulation pause with a button or `Alt+S` shortcut.
    - **Color Picker**: Select ball color via a `JColorChooser` dialog.
- **Spawn Positioning**: Balls spawn centered around the panel midpoint for symmetric initial conditions.
- **Modular Design**: Easily extendable classes for adding shapes or integration methods.

## Prerequisites
- **Java Development Kit** (JDK) 17 or higher with `JAVA_HOME` set.
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
   This compiles sources, runs tests, and packages `target/physics-engine-1.0.0.jar`.

## Configuration
Customize restitution or simulation parameters in code (e.g., `PhysicsWorld` or `PhysicsPanel`).

## Usage
After building the JAR, run:
```bash
java -jar target/physics-engine-1.0.0.jar
```
- The Swing window **"2D Physics Engine Visualization"** opens.
- **Enter** the desired number of balls in the input field.
- Press **Start** or hit `Enter` to launch the simulation.
- To **pause/resume**, click the **Pause/Resume** button or use the `Alt+S` keyboard shortcut.
- Click **Color** to open the color chooser and set ball fill color before starting.

## Project Structure
```bash
src/main/java/com/myproject/physics/
├── Collider.java        # Collision shapes and detection logic
├── PhysicsWorld.java    # Simulation loop and collision resolution
├── Rigidbody.java       # Mass, forces, Euler integration
├── Vector2D.java        # 2D vector math utilities
├── PhysicsPanel.java    # Swing renderer with start/pause and color support
├── PhysicsUI.java       # Main entry point with UI and key bindings
└── ConsoleRenderer.java # Optional console logging of collider events
```

## Running the Simulation
1. **Launch** the JAR (see [Usage](#usage)).
2. **Observe** the graphical window: colored balls bounce with restitution.
3. **Interact** using UI controls and key shortcuts:
    - `Enter`: Start/restart simulation
    - `Alt+S`: Pause/resume
    - **Color** button: Choose ball color
