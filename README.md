# Car Rental Management System
A Java desktop application for managing a car rental agency with a graphical user interface built using JFrame (Swing) and MySQL database.

## Features
### Core Functionality
- Car Categories Management: Add and manage car categories (Small, Large, Economic, Jeep)

- Car Management: Register cars with details (category, model, daily cost, engine size, seats)

- Customer Management: Store customer information (name, surname, gender, address, email, phone)

- Rental Management: Process car rentals linking customers with cars for specified durations

### Query & Display Features
- Available Cars: View all cars currently available for rent

- Customer Rentals: Display all rentals for a specific customer

- Car Rental History: Show rental history for a specific vehicle

- Search Functionality: Find cars or customers through search interface

### Technologies Used
- Java - Core programming language

- Swing (JFrame) - GUI framework

- MySQL - Database

- JDBC - Database connectivity

- IntelliJ IDEA - Development environment

### Prerequisites
- Java Development Kit (JDK) 8 or higher

- MySQL Server 5.7 or higher

- MySQL Connector/J (JDBC driver)

<details>
<summary>Database Setup</summary>
The database schema is included in the car_rental_db.sql file. The *main tables* are:

- categories - Car categories

- cars - Car details with foreign key to categories

- customers - Customer information

- rentals - Rental records linking customers and cars

#### To set up the database:

1. Create the database

```sql
CREATE DATABASE car_rental_db;
```
2. Import the schema

```bash
mysql -u root -p car_rental_db < car_rental_db.sql
```
</details>
<details>
<summary>Installation</summary>
1. Clone the repository

```bash
git clone https://github.com/yourusername/car-rental-app-Java.git
```
2. Configure database connection

3.Update src/db.properties with your MySQL credentials:

**_Properties_**
```
db.url=jdbc:mysql://localhost:3306/car_rental_db?useSSL=false&serverTimezone=UTC
db.username=your_username
db.password=your_password
```
4. Add MySQL Connector

5. Download MySQL Connector/J and add the JAR file to your project classpath

6. Run the application

7. Open the project in IntelliJ IDEA

8. Build and run Main.java
   
</details>

### Usage

1. Launch the application by running Main.java

2. Use the tabs to navigate between different sections:

  - Categories: Add/edit car categories

  - Cars: Register new cars

  - Customers: Manage customer information

  - Rentals: Process new rentals

  - Reports: View rental history and search

### Contact
Alexandros Vlandos - alexvla@windowslive.com

[Project Link](https://github.com/Alex-Vlandos/car-rental-app-Java)

