# Sudoku game application (Java)

This is a javafx application for playing sudoku, created as a university project to practice object-oriented programming in Java.

Players can play the game in 3 difficulty modes (easy, normal, hard). The available languages are English and Polish. There is an option to save and load games both locally and from/to database (PostgreSQL).

>  Created as part of the Component Programming course at Lodz University of Technology (2025).

---

## 📸 Screenshots

Below are screenshots showcasing key functionalities of the application:

<p align="center">
  <img src="https://github.com/user-attachments/assets/df2f05a0-effa-4d71-86fc-43f69a4ada78" width="400"/>
  <img src="https://github.com/user-attachments/assets/1941047f-4df2-42b0-8955-d080fac09751" width="400"/>
  <img src="https://github.com/user-attachments/assets/72452bc3-db80-4d91-b70b-89d076e833f1" width="400"/>
  <img src="https://github.com/user-attachments/assets/14e268f6-231b-4d5c-b92d-1ceaec7ee1d9" width="400"/>
  <img src="https://github.com/user-attachments/assets/44ed4a03-b715-4ed3-bdf0-ff90233103c8" width="400"/>
  <img src="https://github.com/user-attachments/assets/e93050e3-0284-40c6-9b51-931a245a849e" width="400"/>
  <img src="https://github.com/user-attachments/assets/79162ba5-7d99-4d92-8c4f-7d2d98335828" width="400"/>
  <img src="https://github.com/user-attachments/assets/24a1d68b-b84b-45e7-877c-d2d716c1694e" width="400"/>
  <img src="https://github.com/user-attachments/assets/87b3bff1-6cd9-4314-8c33-fd57b04f4c19" width="400"/>
  <img src="https://github.com/user-attachments/assets/7f97a6c9-cff5-49bb-812c-f8cbd0179e8c" width="400"/>
  <img src="https://github.com/user-attachments/assets/81e57241-b201-4fb4-9d4e-deb188745efd" width="400"/>
  <img src="https://github.com/user-attachments/assets/2e68e4b8-efdb-4bb5-bde6-50bf99319b24" width="400"/>
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

-Add mode in which you have instant feedback about field correctnes

-Make the database load menu larger

-Make the check board button wider

-Transition into difficulty menu after finished game
