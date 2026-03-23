-- NOTE: Uncomment the line below if running this script for the first time to create the database
CREATE DATABASE IF NOT EXISTS establishmentDB;
USE establishmentDB;

-- 1. Create inspection requirements table
CREATE TABLE inspection_requirements (
    Requirement_Code INT PRIMARY KEY,
    Title VARCHAR(100) NOT NULL,
    Standard_Fine INT NOT NULL DEFAULT 1000
);

-- 2. Create inspector management table
CREATE TABLE inspector_management (
    Inspector_Id INT PRIMARY KEY AUTO_INCREMENT,
    Full_Name VARCHAR(100) NOT NULL,
    District VARCHAR(50) NOT NULL,
    Active_Status ENUM('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE'
);

-- 3. Create establishment table (NO FK to inspection)
CREATE TABLE establishment (
    Establishment_Id INT PRIMARY KEY AUTO_INCREMENT,
    Establishment_Name VARCHAR(100) NOT NULL,
    Owner_Name VARCHAR(100) NOT NULL,
    Address VARCHAR(255) NOT NULL,
    Contact_Info VARCHAR(20),
    Operating_Status ENUM('OPEN','CLOSED','SUSPENDED') NOT NULL DEFAULT 'OPEN'
);

-- 4. Create assigned inspector table
CREATE TABLE assigned_inspector (
    Assignment_Id INT PRIMARY KEY AUTO_INCREMENT,
    Inspector_Id INT NOT NULL,
    Establishment_Id INT NOT NULL,
    Assignment_Date DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (Inspector_Id) REFERENCES inspector_management(Inspector_Id),
    FOREIGN KEY (Establishment_Id) REFERENCES establishment(Establishment_Id)
);

-- 5. Create inspection table with COMPOSITE PRIMARY KEY
--    This enforces: Only ONE inspection per establishment per day
CREATE TABLE inspection (
    Establishment_Id INT NOT NULL,
    Inspection_Date DATE NOT NULL,
    Assignment_Id INT NOT NULL,
    Score FLOAT,
    Grade VARCHAR(4),
    Remarks VARCHAR(500),
    
    -- COMPOSITE PRIMARY KEY: No duplicates for (Establishment_Id, Inspection_Date)
    PRIMARY KEY (Establishment_Id, Inspection_Date),
    
    FOREIGN KEY (Establishment_Id) REFERENCES establishment(Establishment_Id),
    FOREIGN KEY (Assignment_Id) REFERENCES assigned_inspector(Assignment_Id)
);

-- 6. Create violations table with COMPOSITE FOREIGN KEY
CREATE TABLE violations (
    Violation_Id INT PRIMARY KEY AUTO_INCREMENT,
    Establishment_Id INT NOT NULL,
    Inspection_Date DATE NOT NULL,
    Requirement_Code INT NOT NULL,
    
    -- COMPOSITE FOREIGN KEY: Links to (Establishment_Id, Inspection_Date) in inspection table
    FOREIGN KEY (Establishment_Id, Inspection_Date) 
        REFERENCES inspection(Establishment_Id, Inspection_Date),
    FOREIGN KEY (Requirement_Code) 
        REFERENCES inspection_requirements(Requirement_Code)
);

-- Add indexes for better query performance
CREATE INDEX idx_inspection_establishment ON inspection(Establishment_Id);
CREATE INDEX idx_violations_inspection ON violations(Establishment_Id, Inspection_Date);
CREATE INDEX idx_assigned_inspector ON assigned_inspector(Inspector_Id);

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