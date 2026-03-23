-- NOTE: Uncomment the line below if running this script for the first time to create the database
-- CREATE DATABASE IF NOT EXISTS establishmentDB;
USE establishmentDB;

-- Create inspection requirements table
CREATE TABLE inspection_requirements (
    Requirement_Code INT PRIMARY KEY,
    Title VARCHAR(100),
    Standard_Fine INT
);

-- Create inspection table 
CREATE TABLE Inspection (
    Inspection_Id INT PRIMARY KEY AUTO_INCREMENT,
    Inspection_Date DATE,
    Score FLOAT,
    Grade VARCHAR(4),
    Remarks VARCHAR(100)
);

-- Create violations table 
CREATE TABLE violations (
    Violation_Id INT PRIMARY KEY AUTO_INCREMENT,
    Requirement_Code INT,
    Inspection_Id INT,
    FOREIGN KEY (Requirement_Code) REFERENCES inspection_requirements(Requirement_Code),
    FOREIGN KEY (Inspection_Id) REFERENCES Inspection(Inspection_Id)
);

-- Insert inspection requirements with standard fine of 1000 for each violation
INSERT INTO inspection_requirements (Requirement_Code, Title, Standard_Fine) VALUES
(1, 'Cross-Contamination', 1000),
(2, 'Bare-Hand Contact with Ready-to-Eat Food', 1000),
(3, 'Improper Cooking Temperatures', 1000),
(4, 'Failure to Rapidly Cool or Reheat Foods', 1000),
(5, 'Poor Handwashing Practices', 1000),
(6, 'Improper Food Storage Temperatures', 1000),
(7, 'Pest Infestation', 1000),
(8, 'Expired Food Items', 1000),
(9, 'Dirty Kitchen Equipment', 1000),
(10, 'Improper Dishwashing Techniques', 1000),
(11, 'Cluttered or Dirty Floors', 1000),
(12, 'Inadequate Food Protection', 1000),
(13, 'Improper Employee Hygiene', 1000),
(14, 'Unapproved Food Sources', 1000),
(15, 'Unclean Restrooms', 1000),
(16, 'Grease Buildup in Exhaust Systems', 1000),
(17, 'Failure to Properly Label Allergens', 1000),
(18, 'Inadequate Training for Employees', 1000);