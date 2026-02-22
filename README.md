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

### Database Setup
The database schema is included in the car_rental_db.sql file. The *main tables* are:

- categories - Car categories

- cars - Car details with foreign key to categories

- customers - Customer information

- rentals - Rental records linking customers and cars

#### To set up the database:

1.Create the database

sql
CREATE DATABASE car_rental_db;
Import the schema

bash
mysql -u root -p car_rental_db < database/schema.sql
Installation
Clone the repository

bash
git clone https://github.com/yourusername/car-rental-management.git
Configure database connection

Update src/db.properties with your MySQL credentials:

properties
db.url=jdbc:mysql://localhost:3306/car_rental_db?useSSL=false&serverTimezone=UTC
db.username=your_username
db.password=your_password
Add MySQL Connector

Download MySQL Connector/J and add the JAR file to your project classpath

Run the application

Open the project in IntelliJ IDEA

Build and run Main.java

Project Structure
text
car-rental-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   └── carrental/
│   │   │   │       ├── model/         # Entity classes
│   │   │   │       ├── dao/            # Data Access Objects
│   │   │   │       ├── gui/            # JFrame forms
│   │   │   │       ├── db/             # Database connection
│   │   │   │       └── Main.java       # Application entry point
│   │   │   └── resources/
│   │   │       └── db.properties       # Database configuration
│   └── test/                            # Unit tests
├── database/
│   └── schema.sql                       # Database schema
├── lib/
│   └── mysql-connector-java-x.x.xx.jar  # MySQL JDBC driver
├── README.md
└── .gitignore
Usage
Launch the application by running Main.java

Use the tabs to navigate between different sections:

Categories: Add/edit car categories

Cars: Register new cars

Customers: Manage customer information

Rentals: Process new rentals

Reports: View rental history and search

Contributing
Fork the repository

Create a feature branch (git checkout -b feature/NewFeature)

Commit your changes (git commit -m 'Add some NewFeature')

Push to the branch (git push origin feature/NewFeature)

Open a Pull Request

License
This project is licensed under the MIT License - see the LICENSE file for details.

Contact
Your Name - your.email@example.com

Project Link: https://github.com/yourusername/car-rental-management

