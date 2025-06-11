# Sudoku game application (Java)

This is a javafx application for playing sudoku, created as a university project to practice object-oriented programming in Java.

Players can play the game in 3 difficulty modes (easy, normal, hard). The available languages are English and Polish. There is an option to save and load games both locally and from/to database (PostgreSQL).

>  Created as part of the Component Programming course at Lodz University of Technology (2025).

---

## 📸 Screenshots

Below are screenshots showcasing key functionalities of the application:

<p align="center">
  <img src="https://github.com/user-attachments/assets/266caacb-d224-4696-aff9-93787e84d87a" width="400"/>
  <img src="https://github.com/user-attachments/assets/c50f8042-56a9-4d06-a7e9-4f2e917ad491" width="400"/>
  <img src="https://github.com/user-attachments/assets/4c830064-5e65-44ca-847a-abadd2e10698" width="400"/>
  <img src="https://github.com/user-attachments/assets/242c67b0-4f1e-47b1-bac2-672fd71191f1" width="400"/>
  <img src="https://github.com/user-attachments/assets/fa60b2e4-2a0d-402a-9f2c-0a45a06ce3bf" width="400"/>
  <img src="https://github.com/user-attachments/assets/3fbd669b-baf7-4545-805f-715f7e39382b" width="400"/>
  <img src="https://github.com/user-attachments/assets/75bc27c2-efd3-4c63-8bf1-f1873f671083" width="400"/>
  <img src="https://github.com/user-attachments/assets/e5371f1d-7803-43b1-81a0-24d569751f98" width="400"/>
  <img src="https://github.com/user-attachments/assets/cd1d99d7-644c-48d3-9e5b-da071c797172" width="400"/>
  <img src="https://github.com/user-attachments/assets/8ed0b883-c94b-4d3d-8665-934caba8ff8d" width="400"/>
  <img src="https://github.com/user-attachments/assets/ea78e1be-ee44-4e61-bd72-6298b041706f" width="400"/>
  <img src="https://github.com/user-attachments/assets/3f9de46b-8bce-4851-adea-ddd160971d19" width="400"/>
  <img src="https://github.com/user-attachments/assets/17023540-6383-448e-ac57-0d297438131d" width="400"/>
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
