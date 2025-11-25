USE placement_management;
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE applications;
TRUNCATE TABLE jobs;
TRUNCATE TABLE companies;
TRUNCATE TABLE students;
TRUNCATE TABLE admins;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- SAMPLE DATA FOR ADMINS
-- ============================================
INSERT INTO admins (name, email, password)
VALUES 
('Admin User', 'admin@college.edu', 'admin123');


-- ============================================
-- SAMPLE DATA FOR STUDENTS
-- ============================================
INSERT INTO students 
(roll_no, name, email, password, dob, gender, department, cgpa, phone, resume_url, status)
VALUES 
('CS001', 'John Mathew', 'john.cs@college.edu', 'john123', '2003-02-15', 'Male', 'CSE', 8.2, '9876543210', NULL, 'unplaced'),
('CS002', 'Anita Joseph', 'anita.cs@college.edu', 'anita123', '2003-08-10', 'Female', 'CSE', 8.8, '9876501234', NULL, 'unplaced'),
('EC001', 'Rahul Nair', 'rahul.ec@college.edu', 'rahul123', '2002-11-25', 'Male', 'ECE', 7.9, '9988776655', NULL, 'unplaced'),
('ME001', 'Sneha Varma', 'sneha.me@college.edu', 'sneha123', '2003-04-05', 'Female', 'MECH', 8.0, '9776655443', NULL, 'unplaced');


-- ============================================
-- SAMPLE DATA FOR COMPANIES
-- ============================================
INSERT INTO companies (company_name, industry, website, location)
VALUES
('Google', 'IT', 'https://google.com', 'Bangalore'),
('TCS', 'IT Services', 'https://tcs.com', 'Mumbai'),
('Infosys', 'IT', 'https://infosys.com', 'Hyderabad'),
('Tesla', 'Automobile', 'https://tesla.com', 'California');


-- ============================================
-- SAMPLE JOB POSTINGS
-- ============================================
INSERT INTO jobs 
(company_id, job_role, description, salary, eligibility_cgpa, job_location, last_date)
VALUES
(1, 'Software Engineer', 'Work on scalable systems and AI products.', '20 LPA', 7.5, 'Bangalore', '2025-01-31'),
(1, 'Data Analyst', 'Data cleaning, visualization and pipelines.', '12 LPA', 7.0, 'Hyderabad', '2025-02-15'),
(2, 'Associate Engineer', 'Development and maintenance of enterprise apps.', '6 LPA', 6.5, 'Mumbai', '2025-03-10'),
(3, 'Full Stack Developer', 'Full-stack web development across teams.', '7.5 LPA', 7.2, 'Chennai', '2025-04-05'),
(4, 'MECH Designer', 'CAD design for EV components.', '8.5 LPA', 7.0, 'California', '2025-05-01');


-- ============================================
-- SAMPLE APPLICATIONS
-- ============================================
INSERT INTO applications (student_id, job_id, apply_date, status)
VALUES
(1, 1, '2025-01-10', 'applied'),
(2, 1, '2025-01-10', 'shortlisted'),
(2, 2, '2025-01-11', 'applied'),
(3, 3, '2025-01-12', 'applied'),
(4, 5, '2025-01-13', 'applied');

USE placement_management;

-- ============================================
-- 20+ STUDENTS
-- ============================================
INSERT INTO students 
(roll_no, name, email, password, dob, gender, department, cgpa, phone, resume_url, status)
VALUES
('CS003','Ajay Kumar','ajay.k@college.edu','ajay123','2003-01-10','Male','CSE',7.2,'9877000101',NULL,'unplaced'),
('CS004','Divya S','divya.s@college.edu','divya123','2003-02-11','Female','CSE',8.1,'9877000102',NULL,'unplaced'),
('CS005','Vishal Raj','vishal.raj@college.edu','vishal123','2003-03-02','Male','CSE',9.0,'9877000103',NULL,'unplaced'),
('CS006','Maria Joseph','maria.j@college.edu','maria123','2002-12-20','Female','CSE',7.6,'9877000104',NULL,'unplaced'),
('CS007','Nikhil Thomas','nikhil.t@college.edu','nikhil123','2003-04-14','Male','CSE',8.7,'9877000105',NULL,'unplaced'),
('CS008','Riya Nair','riya.n@college.edu','riya123','2003-05-16','Female','CSE',8.9,'9877000106',NULL,'unplaced'),
('CS009','Suresh B','suresh.b@college.edu','suresh123','2003-06-21','Male','CSE',7.8,'9877000107',NULL,'unplaced'),
('CS010','Lakshmi P','lakshmi.p@college.edu','lakshmi123','2003-07-13','Female','CSE',8.5,'9877000108',NULL,'unplaced'),
('EC002','Neeraj M','neeraj.m@college.edu','neeraj123','2003-01-17','Male','ECE',7.4,'9877000201',NULL,'unplaced'),
('EC003','Sneha KP','sneha.kp@college.edu','snehakp123','2003-03-11','Female','ECE',9.1,'9877000202',NULL,'unplaced'),
('EC004','Abhinav R','abhinav.r@college.edu','abhinav123','2003-08-09','Male','ECE',8.3,'9877000203',NULL,'unplaced'),
('EC005','Anjali Menon','anjali.m@college.edu','anjali123','2003-09-10','Female','ECE',7.9,'9877000204',NULL,'unplaced'),
('ME002','Ravi Shankar','ravi.s@college.edu','ravi123','2003-02-02','Male','MECH',7.0,'9877000301',NULL,'unplaced'),
('ME003','Sona Varghese','sona.v@college.edu','sona123','2003-11-25','Female','MECH',8.2,'9877000302',NULL,'unplaced'),
('ME004','Akshay M','akshay.m@college.edu','akshay123','2003-05-22','Male','MECH',7.8,'9877000303',NULL,'unplaced'),
('IT001','Joel Abraham','joel.a@college.edu','joel123','2003-04-19','Male','IT',8.4,'9877000401',NULL,'unplaced'),
('IT002','Beena Mathew','beena.m@college.edu','beena123','2003-07-05','Female','IT',8.9,'9877000402',NULL,'unplaced'),
('IT003','Stephen P','stephen.p@college.edu','stephen123','2003-10-10','Male','IT',9.2,'9877000403',NULL,'unplaced'),
('CE001','Rahul KP','rahul.kp@college.edu','rahulkp123','2003-06-15','Male','CIVIL',7.3,'9877000501',NULL,'unplaced'),
('CE002','Tina R','tina.r@college.edu','tina123','2003-03-27','Female','CIVIL',8.0,'9877000502',NULL,'unplaced');


-- ============================================
-- 10+ COMPANIES
-- ============================================
INSERT INTO companies (company_name, industry, website, location)
VALUES
('Wipro','IT Services','https://wipro.com','Bangalore'),
('Accenture','IT Consulting','https://accenture.com','Hyderabad'),
('Deloitte','Finance/IT','https://deloitte.com','Mumbai'),
('Capgemini','Consulting','https://capgemini.com','Chennai'),
('Amazon','E-Commerce','https://amazon.com','Bangalore'),
('Flipkart','E-Commerce','https://flipkart.com','Bangalore'),
('HCL','IT Services','https://hcl.com','Noida'),
('IBM','IT','https://ibm.com','Pune'),
('Mahindra','Automobile','https://mahindra.com','Chennai'),
('Zoho','Software','https://zoho.com','Chennai');


-- ============================================
-- 15+ JOBS
-- ============================================
INSERT INTO jobs (company_id, job_role, description, salary, eligibility_cgpa, job_location, last_date)
VALUES
(5,'SDE 1','Backend services and AWS infra','18 LPA',7.5,'Bangalore','2025-03-01'),
(5,'Support Engineer','Technical support for AWS systems','12 LPA',7.0,'Hyderabad','2025-02-15'),
(6,'SDE 1','Frontend + React + APIs','15 LPA',7.2,'Bangalore','2025-03-10'),
(6,'Data Engineer','ETL & Data pipelines','14 LPA',7.5,'Hyderabad','2025-04-01'),
(7,'Associate Engineer','Java, Spring Boot apps','6.5 LPA',6.8,'Noida','2025-03-20'),
(8,'Cloud Engineer','Cloud infra & automation','13 LPA',7.6,'Pune','2025-05-01'),
(9,'MECH Engineer','Design EV components','7 LPA',7.0,'Chennai','2025-04-10'),
(10,'Software Developer','Full-stack apps','8.5 LPA',7.4,'Chennai','2025-06-01'),
(1,'QA Tester','Automation scripts','6 LPA',6.5,'Mumbai','2025-04-05'),
(2,'Business Analyst','Client communication & analysis','7 LPA',7.0,'Hyderabad','2025-03-15'),
(3,'Cybersecurity Analyst','Security audits & SOC','10 LPA',7.8,'Mumbai','2025-05-20'),
(4,'System Analyst','ERP support & testing','5.5 LPA',6.2,'Chennai','2025-03-22'),
(3,'Financial Consultant','Advisory & analytics','9 LPA',7.5,'Pune','2025-05-12'),
(8,'AI/ML Engineer','AI model training + deployment','16 LPA',8.0,'Bangalore','2025-04-28'),
(7,'Tech Support','L1 support role','3.5 LPA',6.0,'Noida','2025-02-28');


-- ============================================
-- 20+ APPLICATIONS (Randomized)
-- ============================================
INSERT INTO applications (student_id, job_id, apply_date, status) VALUES
(1,1,'2025-01-21','applied'),
(2,1,'2025-01-22','shortlisted'),
(3,2,'2025-01-22','applied'),
(4,3,'2025-01-23','applied'),
(5,4,'2025-01-23','applied'),
(6,5,'2025-01-24','rejected'),
(7,6,'2025-01-24','applied'),
(8,7,'2025-01-25','applied'),
(9,8,'2025-01-26','applied'),
(10,9,'2025-01-27','applied'),
(11,10,'2025-01-28','selected'),
(12,11,'2025-01-28','applied'),
(13,12,'2025-01-29','applied'),
(14,13,'2025-01-29','shortlisted'),
(15,14,'2025-01-30','applied'),
(16,15,'2025-01-31','applied'),
(17,1,'2025-02-01','applied'),
(18,2,'2025-02-01','applied'),
(19,3,'2025-02-02','applied'),
(20,4,'2025-02-02','applied'),
(21,7,'2025-02-03','applied');

