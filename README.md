# 🧩 Sudoku Game Application (Java / JavaFX)

A modern **JavaFX** application for playing Sudoku, created as a university project to practice **object-oriented programming** and component-based design.

The game supports **three difficulty levels**: Easy, Normal, and Hard.  
Available in **two languages**: English 🇬🇧 and Polish 🇵🇱.  
Players can **save and load games** both **locally** and from a **PostgreSQL** database.

> 🎓 Created as part of the *Component Programming* course at **Lodz University of Technology** (2025)

---

## 🖼️ Screenshots

Here are some screenshots showcasing the core features, interface and site plugin report:

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
  <img src="https://github.com/user-attachments/assets/d20d829c-2d8f-4532-a500-44bd51a0077b" width="400"/>
  <img src="https://github.com/user-attachments/assets/36af567f-2eb6-49fa-89fb-f9a3d62596b5" width="400"/>
</p>

---

## ⚙️ Installation

To run this application locally, follow these steps:

1. Install [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/) – version **17+** recommended (at least 23.02v).
2. Install [Apache Maven](https://maven.apache.org/download.cgi) – version **3.9.9+**.
3. Install [Docker Desktop](https://www.docker.com/products/docker-desktop/) and **ensure it is running**.
4. Clone the repository using Git:

   ```bash
   git clone https://github.com/marcinkaczmarek2/sudoku.git
   cd sudoku
   ```

5. Start the database server via Docker:

   ```bash
   docker-compose up -d --build
   ```

6. Install all dependencies:

   ```bash
   mvn clean install
   ```

7. Navigate to the JavaFX frontend project:

   ```bash
   cd View
   ```

8. Run the application:

   ```bash
   mvn javafx:run
   ```

9. *(Optional)* Generate the project report:

   ```bash
   mvn test site
   mvn site:stage
   ```

10. *(Optional)* View the generated report:

    Open `sudoku/target/site/index.html` in your browser.

✅ **Done!** You can now play Sudoku and explore the full functionality.

---

## 💡 Features

- 🎮 3 Difficulty levels: Easy, Normal, Hard  
- 🌐 Multilingual support: English & Polish  
- 💾 Save/load games locally and via PostgreSQL  
- 🧠 Smart input with real-time number checking *(planned)*  
- 🧱 Clear and intuitive JavaFX-based UI  
- 🐘 Docker-powered PostgreSQL backend  

---

## 🔧 Planned Improvements

- ✅ Add mode with instant feedback on field correctness   
- ✅ Add "go back to difficulty menu" button
- ✅ Add time counter
- ✅ Add score system
- ✅ Add leaderboards stored in database

---

## 🛠️ Technologies Used

- Java 17+  
- JavaFX  
- Apache Maven  
- PostgreSQL  
- Docker + Docker Compose  
- JUnit, Surefire, Site Plugin

