# Connect 4 in Java

Welcome to the Connect 4 project in Java! This project is a Connect 4 game that can be played against a computer or a
friend online.

## Table of contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Technologies used](#technologies-used)
- [Contributors](#contributors)

## Features

- **Play solo** : Challenge the computer in a single-player mode.
- **Play with a friend** : Connect and play against your friend online.
- **Game configuration** : Customize game settings such as board size, total game time per player, time per round, and the
  first player to start.
- **Error handling and edge case management** : Robust handling of errors and edge cases to ensure a smooth gameplay
  experience.

## Installation

### Prerequisites

- **Java JDK 21** must be installed and configured on your machine.
- **Apache Maven** must be installed and configured.

### Installation steps

1. **Clone the repository**

   Clone the repository to your local machine using the following command:
   ```sh
   git clone <repository-url>
   cd <project-directory-name>
   ```

2. **Build the project with Maven**

   Navigate to the project directory and use Maven to build the project:
   ```sh
   mvn clean install
   ```

3. **Run the project**

   To run the project, use the following command:
   ```sh
   mvn javafx:run
   ```

## Usage

1. Launch the game.
2. Enter your player name.
3. Choose one of the following three options:
    - **Play against computer**
    - **Play with a friend**
    - **Join a game**

### Play against computer

1. Configure the game:
    - Board size
    - Total game time per player (in minutes)
    - Time per round (in seconds)
    - First player to start
2. The game will start immediately after the configuration.

### Play with a friend

1. Configure the game:
    - Board size
    - Total game time per player (in minutes)
    - Time per round (in seconds)
    - First player to start
2. The application will provide an IP address and a port number.
3. Share this information with your friend so they can connect to the same game server.

### Join a game

1. Enter the IP address and port number provided by your friend.
2. Join the game and start playing!

## Technologies used

- **Java** : Main programming language.
- **JavaFX** : For the graphical user interface.
- **Java Sockets** : For network communication in multiplayer mode.
- **JUnit + Mockito** : For unit testing.

## Contributors

- xerox0213 (myself)