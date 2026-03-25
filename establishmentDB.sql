-- CREATE DATABASE IF NOT EXISTS establishmentDB;
USE establishmentDB;


CREATE TABLE inspection_requirements (
    Requirement_Code INT PRIMARY KEY,
    Title VARCHAR(100),
    Standard_Fine INT
);

CREATE TABLE inspector_management (
    Inspector_Id INT PRIMARY KEY AUTO_INCREMENT,
    Full_Name VARCHAR(50),
    District VARCHAR(50),
    Active_Status ENUM('ACTIVE','INACTIVE') NOT NULL
);

CREATE TABLE assigned_inspector (
    Assignment_Id INT PRIMARY KEY AUTO_INCREMENT,
    Inspector_Id INT,
    Full_Name VARCHAR(50)
    FOREIGN KEY (Inspector_Id) REFERENCES inspector_management(Inspector_Id)
);

CREATE TABLE establishment (
   Establishment_Id INT PRIMARY KEY AUTO_INCREMENT,
   Establishment_Name VARCHAR(50),
   Owner_Name VARCHAR(50),
   Address VARCHAR(100),
   Contact_Info VARCHAR(15),
   Operating_Status ENUM('OPEN','CLOSED','SUSPENDED')
);

CREATE TABLE inspection (
    Inspection_Id INT PRIMARY KEY AUTO_INCREMENT,
    Inspection_Date DATE,
    Score FLOAT,
    Grade ENUM('PASS','FAIL'),
    Remarks VARCHAR(100),
    Establishment_Id INT,
    Assignment_Id INT,
    Violation_Id INT
    FOREIGN KEY (Establishment_Id) REFERENCES establishment(Establishment_Id),
    FOREIGN KEY (Assignment_Id) REFERENCES assigned_inspector(Assignment_Id),
    FOREIGN KEY (Violation_Id) REFERENCES violations(Violation_Id)
);

CREATE TABLE violations (
    Violation_Id INT PRIMARY KEY AUTO_INCREMENT,
    Requirement_Code INT,
    Inspection_ID INT
    FOREIGN KEY (Requirement_Code) REFERENCES inspection_requirements(Requirement_Code),
    FOREIGN KEY (Inspection_ID) REFERENCES inspection(Inspection_Id)
);


-- Inspection Requirements
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

-- Inspectors
INSERT INTO inspector_management (Full_Name, District, Active_Status) VALUES
('Juan Dela Cruz', 'Manila', 'ACTIVE'),
('Maria Santos', 'Quezon City', 'ACTIVE'),
('Pedro Reyes', 'Makati', 'ACTIVE');

-- Assigned Inspectors
INSERT INTO assigned_inspector (Inspector_Id, Full_Name) VALUES
(1, 'Juan Dela Cruz'),
(2, 'Maria Santos'),
(3, 'Pedro Reyes');

-- Establishments
INSERT INTO establishment (Establishment_Name, Owner_Name, Address, Contact_Info, Operating_Status) VALUES
('Jollibee Quiapo', 'Tony Tan Caktiong', 'Quiapo, Manila', '09171234567', 'OPEN'),
('McDonalds España', 'Golden Arches Dev Corp', 'España Blvd, Manila', '09181234567', 'OPEN'),
('Mang Inasal Cubao', 'Ibrahim Family', 'Cubao, Quezon City', '09191234567', 'OPEN');

-- Violations 
INSERT INTO violations (Requirement_Code, Inspection_ID) VALUES
(1, 1),
(2, 2),
(3, 2),
(5, 4);

-- Inspections
INSERT INTO inspection (Inspection_Date, Score, Grade, Remarks, Establishment_Id, Assignment_Id, Violation_Id) VALUES
('2026-03-10', 85, 'PASS', 'Good compliance', 1, 1, 1),
('2026-03-12', 60, 'FAIL', 'Multiple violations', 2, 2, 2),
('2026-03-15', 90, 'PASS', 'Minor issues only', 3, 3, 3),
('2026-03-18', 55, 'FAIL', 'Critical violations found', 1, 1, 4);
