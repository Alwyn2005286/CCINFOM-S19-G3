-- Create the database
CREATE DATABASE IF NOT EXISTS DataBaseSystem;

-- Use the newly created database
USE DataBaseSystem;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS `Inspections`;
DROP TABLE IF EXISTS `Violations`;
DROP TABLE IF EXISTS `Assigned_Inspectors`;
DROP TABLE IF EXISTS `Establishments`;
DROP TABLE IF EXISTS `Establishment_Owners`;
DROP TABLE IF EXISTS `REF_Cities`;
DROP TABLE IF EXISTS `Inspectors`;
DROP TABLE IF EXISTS `REF_Inspection_Requirements`;


-- Create Tables based on the provided UML Diagram

CREATE TABLE `REF_Cities` (
    `city_id` INT AUTO_INCREMENT PRIMARY KEY,
    `city_name` VARCHAR(45) NOT NULL UNIQUE
);

CREATE TABLE `Establishment_Owners` (
    `owner_id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50),
    `last_name` VARCHAR(50)
);

CREATE TABLE `Establishments` (
    `establishment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `owner_id` INT,
    `establishment_name` VARCHAR(45),
    `city_id` INT,
    FOREIGN KEY (`owner_id`) REFERENCES `Establishment_Owners`(`owner_id`),
    FOREIGN KEY (`city_id`) REFERENCES `REF_Cities`(`city_id`)
);

CREATE TABLE `Inspectors` (
    `inspector_id` INT AUTO_INCREMENT PRIMARY KEY,
    `first_name` VARCHAR(50),
    `last_name` VARCHAR(50)
);

CREATE TABLE `REF_Inspection_Requirements` (
    `req_id` INT AUTO_INCREMENT PRIMARY KEY,
    `req_name` VARCHAR(100) NOT NULL
);

CREATE TABLE `Inspections` (
    `inspection_id` INT AUTO_INCREMENT PRIMARY KEY,
    `establishment_id` INT,
    `inspection_date` DATE,
    `score` DECIMAL(5,2),
    `grade` ENUM('PASS', 'FAIL'),
    `remarks` VARCHAR(255),
    FOREIGN KEY (`establishment_id`) REFERENCES `Establishments`(`establishment_id`)
);

CREATE TABLE `Assigned_Inspectors` (
    `assignment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `inspection_id` INT,
    `inspector_id` INT,
    FOREIGN KEY (`inspection_id`) REFERENCES `Inspections`(`inspection_id`),
    FOREIGN KEY (`inspector_id`) REFERENCES `Inspectors`(`inspector_id`)
);

CREATE TABLE `Violations` (
    `violation_id` INT AUTO_INCREMENT PRIMARY KEY,
    `inspection_id` INT,
    `req_id` INT,
    `remarks` VARCHAR(255),
    `status` ENUM('PASS', 'FAIL'),
    FOREIGN KEY (`inspection_id`) REFERENCES `Inspections`(`inspection_id`),
    FOREIGN KEY (`req_id`) REFERENCES `REF_Inspection_Requirements`(`req_id`)
);


-- Sample Data Insertion
-- 1. Reference Cities
INSERT INTO `REF_Cities` (`city_id`, `city_name`) VALUES
(1, 'Manila'), 
(2, 'Quezon City'),
(3, 'Makati'),
(4, 'Pasig'),
(5, 'Taguig'),
(6, 'Mandaluyong'),
(7, 'San Juan'),
(8, 'Pasay'),
(9, 'Parañaque'),
(10, 'Las Piñas'),
(11, 'Muntinlupa'),
(12, 'Marikina'),
(13, 'Valenzuela'),
(14, 'Caloocan'),
(15, 'Malabon'),
(16, 'Navotas'),
(17, 'Antipolo'),
(18, 'Cainta'),
(19, 'Taytay'),
(20, 'Cebu City'),
(21, 'Davao City'),
(22, 'Iloilo City'),
(23, 'Baguio City');

-- 2. Establishment Owners
INSERT INTO `Establishment_Owners` (`owner_id`, `first_name`, `last_name`) VALUES
(1, 'Tony', 'Tan Caktiong'), 
(2, 'George', 'Yang'), 
(3, 'Edgar', 'Sia'),
(4, 'Henry', 'Sy'),
(5, 'Lucio', 'Tan'),
(6, 'Ramon', 'Ang'),
(7, 'Lance', 'Gokongwei'),
(8, 'Jaime', 'Zobel'),
(9, 'Enrique', 'Razon'),
(10, 'Andrew', 'Tan'),
(11, 'Manny', 'Villar'),
(12, 'Dennis', 'Uy'),
(13, 'Erwan', 'Heussaff'),
(14, 'Marvin', 'Agustin'),
(15, 'Judy Ann', 'Santos'),
(16, 'Sharon', 'Cuneta'),
(17, 'Piolo', 'Pascual'),
(18, 'Dingdong', 'Dantes'),
(19, 'Marian', 'Rivera'),
(20, 'Anne', 'Curtis'),
(21, 'Vice', 'Ganda'),
(22, 'Coco', 'Martin'),
(23, 'Kathryn', 'Bernardo');

-- 3. Establishments
INSERT INTO `Establishments` (`establishment_id`, `owner_id`, `establishment_name`, `city_id`) VALUES
(1, 1, 'Jollibee', 1), 
(2, 2, 'McDonald''s', 2), 
(3, 3, 'Mang Inasal', 3),
(4, 4, 'Greenwich', 4),
(5, 5, 'Chowking', 5),
(6, 6, 'Red Ribbon', 6),
(7, 7, 'Goldilocks', 7),
(8, 8, 'Maxs Restaurant', 8),
(9, 9, 'Shakeys', 9),
(10, 10, 'Yellow Cab', 10),
(11, 11, 'Kenny Rogers', 11),
(12, 12, 'BonChon', 12),
(13, 13, 'Burger King', 13),
(14, 14, 'KFC', 14),
(15, 15, 'Wendys', 15),
(16, 16, 'Tokyo Tokyo', 16),
(17, 17, 'Teriyaki Boy', 17),
(18, 18, 'Pancake House', 18),
(19, 19, 'Dencios', 19),
(20, 20, 'Gerrys Grill', 20),
(21, 21, 'Kuya J', 21),
(22, 22, 'Aristocrat', 22),
(23, 23, 'Andoks', 23);

-- 4. Inspectors
INSERT INTO `Inspectors` (`inspector_id`, `first_name`, `last_name`) VALUES
(1, 'Juan', 'Dela Cruz'), 
(2, 'Maria', 'Santos'), 
(3, 'Pedro', 'Reyes'),
(4, 'Jose', 'Rizal'),
(5, 'Andres', 'Bonifacio'),
(6, 'Emilio', 'Aguinaldo'),
(7, 'Apolinario', 'Mabini'),
(8, 'Marcelo', 'Del Pilar'),
(9, 'Antonio', 'Luna'),
(10, 'Juan', 'Luna'),
(11, 'Melchora', 'Aquino'),
(12, 'Gabriela', 'Silang'),
(13, 'Lapu', 'Lapu'),
(14, 'Diego', 'Silang'),
(15, 'Emilio', 'Jacinto'),
(16, 'Gregorio', 'Del Pilar'),
(17, 'Miguel', 'Malvar'),
(18, 'Macario', 'Sakay'),
(19, 'Lope', 'Santos'),
(20, 'Carlos', 'Romulo'),
(21, 'Manuel', 'Quezon'),
(22, 'Sergio', 'Osmena'),
(23, 'Ramon', 'Magsaysay');

-- 5. Reference Inspection Requirements
INSERT INTO `REF_Inspection_Requirements` (`Requirement_Code`, `Title`, `Standard_Fine`, `Description`, `Weight`) VALUES
(1, 'Food Safety Compliance', 100.00, 'Food preparation and processing', 50),
(2, 'Sanitation and Cleanliness', 100.00, 'Environment is free from microbes', 50),
(3, 'Pest Control Measures', 100.00, 'Infestation is kept to a minimal', 50),
(4, 'Fire Exits Accessibility', 10000.00, 'Clear and accessible fire exits at all times', 20),
(5, 'Structural Integrity', 20000.00, 'No visible cracks or compromised foundations', 25),
(6, 'Electrical Wiring Safety', 15000.00, 'All wires insulated and properly routed', 15),
(7, 'Ventilation Systems', 5000.00, 'Adequate airflow and exhaust systems', 10),
(8, 'Waste Disposal Management', 8000.00, 'Proper segregation and scheduled collection', 10),
(9, 'Water Quality', 12000.00, 'Potable water supply compliant with health standards', 15),
(10, 'Employee Health Certificates', 5000.00, 'All staff must have updated health cards', 10),
(11, 'Emergency Lighting', 7500.00, 'Functional backup lights in corridors', 10),
(12, 'First Aid Availability', 3000.00, 'Fully stocked first aid kits on site', 5),
(13, 'Hazard Communication', 4000.00, 'Chemicals properly labeled and stored', 5),
(14, 'Noise Level Control', 5000.00, 'Acoustic treatment to prevent noise pollution', 5),
(15, 'Parking Space Adequacy', 10000.00, 'Sufficient parking for max capacity', 10),
(16, 'Signage Visibility', 2000.00, 'Clear entrance, exit, and warning signs', 5),
(17, 'Security Personnel', 8000.00, 'Licensed guards on duty during hours', 10),
(18, 'CCTV Operations', 10000.00, 'Cameras covering all public areas', 10),
(19, 'Restroom Accessibility', 5000.00, 'Clean and PWD accessible washrooms', 10),
(20, 'Smoking Area Designation', 5000.00, 'Properly enclosed smoking zones', 5),
(21, 'Grease Trap Maintenance', 7000.00, 'Cleaned out to prevent sewer blocks', 10),
(22, 'Fire Extinguisher Checks', 5000.00, 'Updated tags and proper pressure', 15),
(23, 'Business Permit Display', 2000.00, 'Permits displayed at the main counter', 5);

-- 6. Inspections
INSERT INTO `Inspections` (`inspection_id`, `establishment_id`, `inspection_date`, `score`, `grade`, `remarks`) VALUES
(1, '2024-01-15', 75.5, 'FAIL', 'Needs improvement.'),
(2, '2024-01-16', 60.0, 'FAIL', 'Critical violation.'),
(3, '2024-01-17', 45.0, 'FAIL', 'Serious health risk.'),
(4, 4, '2025-01-10', 95.5, 'PASS', 'Excellent overall compliance'),
(5, 5, '2025-02-14', 88.0, 'PASS', 'Minor issues with signage'),
(6, 6, '2025-03-20', 75.0, 'PASS', 'Barely passed, needs immediate minor fixes'),
(7, 7, '2025-04-11', 60.0, 'FAIL', 'Failed fire exit checks'),
(8, 8, '2025-05-05', 92.0, 'PASS', 'Well maintained facility'),
(9, 9, '2025-06-15', 55.0, 'FAIL', 'Structural issues found'),
(10, 10, '2025-07-22', 89.5, 'PASS', 'Good record keeping'),
(11, 11, '2025-08-30', 98.0, 'PASS', 'Outstanding standard'),
(12, 12, '2025-09-12', 45.0, 'FAIL', 'Severe electrical hazards'),
(13, 13, '2025-10-01', 81.0, 'PASS', 'Acceptable conditions'),
(14, 14, '2025-11-18', 70.0, 'FAIL', 'Employee health cards expired'),
(15, 15, '2025-12-05', 90.0, 'PASS', 'Cleared all previous warnings'),
(16, 16, '2026-01-08', 96.5, 'PASS', 'No issues found'),
(17, 17, '2026-02-14', 68.0, 'FAIL', 'CCTV system inactive'),
(18, 18, '2026-03-03', 85.0, 'PASS', 'Minor plumbing issues noted'),
(19, 19, '2026-04-20', 100.0, 'PASS', 'Perfect score'),
(20, 20, '2026-05-15', 58.0, 'FAIL', 'Grease traps overflowing'),
(21, 21, '2026-06-10', 79.0, 'PASS', 'Average compliance'),
(22, 22, '2026-07-25', 91.0, 'PASS', 'Highly cooperative staff'),
(23, 23, '2026-08-08', 62.0, 'FAIL', 'Missing fire extinguishers');

-- 7. Assigned Inspectors
INSERT INTO `Assigned_Inspectors` (`assignment_id`, `inspection_id`, `inspector_id`) VALUES
(1, 1, 1), 
(2, 2, 2), 
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 11, 11),
(12, 12, 12),
(13, 13, 13),
(14, 14, 14),
(15, 15, 15),
(16, 16, 16),
(17, 17, 17),
(18, 18, 18),
(19, 19, 19),
(20, 20, 20),
(21, 21, 21),
(22, 22, 22),
(23, 23, 23);

-- 8. Violations
INSERT INTO `Violations` (`violation_id`, `inspection_id`, `Requirement_Code`, `remarks`, `status`) 
VALUES 
(1, 1, 2, 'Floors are dirty.', 'FAIL'), 
(2, 2, 1, 'Food not stored at correct temperature.', 'FAIL'), 
(3, 3, 3, 'Evidence of rodents found.', 'FAIL'),
(4, 7, 4, 'Boxes blocking the rear fire exit', 'FAIL'),
(5, 9, 5, 'Deep cracks observed on the load-bearing wall', 'FAIL'),
(6, 12, 6, 'Exposed wiring near water sources', 'FAIL'),
(7, 14, 10, 'Five employees missing updated health certificates', 'PASS'),
(8, 17, 18, 'Main entrance camera is broken', 'PASS'),
(9, 20, 21, 'Kitchen grease trap has not been cleaned in 6 months', 'FAIL'),
(10, 23, 22, 'Fire extinguishers expired last year', 'FAIL'),
(11, 6, 16, 'Exit signs not illuminated', 'PASS'),
(12, 8, 23, 'Permit was kept in the office, not displayed', 'PASS'),
(13, 13, 12, 'First aid kit missing bandages and antiseptics', 'PASS'),
(14, 15, 8, 'Waste bins missing lids', 'PASS'),
(15, 18, 19, 'PWD ramp to restroom slightly obstructed', 'PASS'),
(16, 21, 11, 'One emergency light battery dead', 'PASS'),
(17, 4, 13, 'Cleaning chemicals next to dry goods (fixed immediately)', 'PASS'),
(18, 5, 20, 'Smoking area sign too small', 'PASS'),
(19, 10, 15, 'Customer parked in delivery zone', 'PASS'),
(20, 11, 9, 'Water filter needs scheduled replacement', 'PASS'),
(21, 19, 7, 'Minor dust buildup on exhaust fan', 'PASS'),
(22, 22, 14, 'Background music slightly above limit', 'PASS'),
(23, 16, 17, 'Guard was out of uniform', 'PASS');
