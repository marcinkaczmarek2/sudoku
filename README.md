# Sudoku game application (Java)

This is a javafx application for playing sudoku, created as a university project to practice object-oriented programming in Java.

Players can play the game in 3 difficulty modes (easy, normal, hard). The available languages are English and Polish. There is an option to save and load games both locally and from/to database (PostgreSQL).

>  Created as part of the Component Programming course at Lodz University of Technology (2025).

---

## 📸 Screenshots

Below are screenshots showcasing key functionalities of the application:

<p align="center">
  <img src="https://github.com/user-attachments/assets/266caacb-d224-4696-aff9-93787e84d87a" width="400"/>
</p>


---

## ⚙️ Installation

To run this application, follow these steps:

1. Install \([Java-Development-Kit](https://www.oracle.com/java/technologies/downloads/)) - version at least 23.02v.

2. Install \([Apache Maven](https://maven.apache.org/download.cgi/)) - version at least 3.9.9v.

3. Install \([Docker Desktop](https://www.docker.com/products/docker-desktop/)) and run it.

4. Using git bash or windows terminal clone the project:
   git clone https://github.com/marcinkaczmarek2/sudoku.git
5. Enter the project and run the database server:
   cd sudoku
   docker-compose up -d --build
6. Install dependencies:
   mvn clean install
7. Enter the view sub-project:
   cd View
8. Build and run the application:
   mvn javafx:run
9. (OPTIONAL) Create the project report:
	mvn test site
	mvn site:stage
10. (OPTIONAL) Open the project report (sudoku/target/site/index.html)

✅ Done!
You can now test the application and explore its functionality.


## 👷 Things to change

-Window name during game is "Sudoku - Difficulty Menu/Sudoku - Wybór poziomu trudnośći" instead of "Sudoku - Game/Gra

-Add the button to check the board correctness

-Add mode in which you have instant feedback about field correctnes

-Fix the screen shot correctness
