--  CREATE DATABASE IF NOT EXISTS establishmentDB;
USE establishmentDB;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS Violations, Inspections, Assigned_Inspectors, Establishments, Establishment_Owners, REF_Cities, Inspectors, REF_Inspection_Requirements;

-- Create Tables based on the provided UML Diagram

CREATE TABLE REF_Cities (
    City_name VARCHAR(45) PRIMARY KEY
);

CREATE TABLE Establishment_Owners (
    Owner_ID INT(3) PRIMARY KEY,
    First_Name VARCHAR(50),
    Last_Name VARCHAR(50)
);

CREATE TABLE Establishments (
    Establishment_ID INT(5) PRIMARY KEY,
    Owner_ID INT(3),
    Establishment_Name VARCHAR(45),
    City_name VARCHAR(45),
    FOREIGN KEY (Owner_ID) REFERENCES Establishment_Owners(Owner_ID),
    FOREIGN KEY (City_name) REFERENCES REF_Cities(City_name)
);

CREATE TABLE Inspectors (
    Inspector_ID INT(3) PRIMARY KEY,
    First_Name VARCHAR(50),
    Last_Name VARCHAR(50)
);

CREATE TABLE Assigned_Inspectors (
    Assignment_ID INT(5) PRIMARY KEY,
    Inspector_ID INT(3),
    Establishment_ID INT(5),
    FOREIGN KEY (Inspector_ID) REFERENCES Inspectors(Inspector_ID),
    FOREIGN KEY (Establishment_ID) REFERENCES Establishments(Establishment_ID)
);

CREATE TABLE REF_Inspection_Requirements (
    Requirement_Code INT(2) PRIMARY KEY,
    Title VARCHAR(50),
    Standard_Fine DECIMAL(7,2),
    Description VARCHAR(100),
    Weight INT(3)
);

CREATE TABLE Violations (
    Violation_Code INT(5) PRIMARY KEY,
    Requirement_Code INT(2),
    Inspector_Remarks VARCHAR(100),
    Requirement_Status ENUM('PASS', 'FAIL'),
    FOREIGN KEY (Requirement_Code) REFERENCES REF_Inspection_Requirements(Requirement_Code)
);

CREATE TABLE Inspections (
    Inspection_ID INT(5) PRIMARY KEY,
    Violation_Code INT(5),
    Assignment_ID INT(10),
    Inspection_Date DATE,
    Remarks VARCHAR(50),
    Score DECIMAL(5,2),
    Grade ENUM('PASS', 'FAIL'),
    FOREIGN KEY (Violation_Code) REFERENCES Violations(Violation_Code)
);

-- Sample Data Insertion

INSERT INTO REF_Cities (City_name) VALUES
('Manila'),
('Quezon City'),
('Makati');

INSERT INTO Establishment_Owners (Owner_ID, First_Name, Last_Name) VALUES
(1, 'Tony', 'Tan Caktiong'),
(2, 'George', 'Yang'),
(3, 'Edgar', 'Sia');

INSERT INTO Establishments (Establishment_ID, Owner_ID, Establishment_Name, City_name) VALUES
(1, 1, 'Jollibee', 'Manila'),
(2, 2, 'McDonald''s', 'Quezon City'),
(3, 3, 'Mang Inasal', 'Makati');

INSERT INTO Inspectors (Inspector_ID, First_Name, Last_Name) VALUES
(1, 'Juan', 'Dela Cruz'),
(2, 'Maria', 'Santos'),
(3, 'Pedro', 'Reyes');

INSERT INTO Assigned_Inspectors (Assignment_ID, Inspector_ID, Establishment_ID) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

INSERT INTO REF_Inspection_Requirements (Requirement_Code, Title, Standard_Fine, Description, Weight) VALUES
(1, 'Food Safety', 500.00, 'Ensure all food is handled properly.', 10),
(2, 'Cleanliness', 300.00, 'Maintain a clean environment.', 8),
(3, 'Pest Control', 1000.00, 'No pests allowed.', 12);

INSERT INTO Violations (Violation_Code, Requirement_Code, Inspector_Remarks, Requirement_Status) VALUES
(1, 2, 'Floors are dirty.', 'FAIL'),
(2, 1, 'Food not stored at correct temperature.', 'FAIL'),
(3, 3, 'Evidence of rodents found.', 'FAIL');

INSERT INTO Inspections (Inspection_ID, Violation_Code, Assignment_ID, Inspection_Date, Remarks, Score, Grade) VALUES
(1, 1, 1, '2024-01-15', 'Needs improvement.', 75.5, 'FAIL'),
(2, 2, 2, '2024-01-16', 'Critical violation.', 60.0, 'FAIL'),
(3, 3, 3, '2024-01-17', 'Serious health risk.', 45.0, 'FAIL'),
(4, 3, 3, '2026-03-15', 'Minor issues only', 90, 'PASS'),
(5, 1, 1, '2026-03-18', 'Critical violations found', 55, 'FAIL');
