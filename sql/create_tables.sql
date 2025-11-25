-- ============================================
--  USE YOUR DATABASE
-- ============================================
USE placement_management;

-- ============================================
--  1. ADMINS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- ============================================
--  2. STUDENTS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    roll_no VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    dob DATE,
    gender VARCHAR(10),
    department VARCHAR(100),
    cgpa DECIMAL(4,2),
    phone VARCHAR(15),
    resume_url VARCHAR(255),
    status ENUM('unplaced','placed') DEFAULT 'unplaced'
);

-- ============================================
--  3. COMPANIES TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(100) NOT NULL,
    industry VARCHAR(100),
    website VARCHAR(255),
    location VARCHAR(100)
);

-- ============================================
--  4. JOBS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS jobs (
    job_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT NOT NULL,
    job_role VARCHAR(100) NOT NULL,
    description TEXT,
    salary VARCHAR(100),
    eligibility_cgpa DECIMAL(4,2),
    job_location VARCHAR(100),
    last_date DATE,
    
    FOREIGN KEY (company_id)
        REFERENCES companies(company_id)
        ON DELETE CASCADE
);

-- ============================================
--  5. APPLICATIONS TABLE
-- ============================================
CREATE TABLE IF NOT EXISTS applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    job_id INT NOT NULL,
    apply_date DATE DEFAULT (CURRENT_DATE),
    status ENUM('applied','shortlisted','rejected','selected') DEFAULT 'applied',
    
    FOREIGN KEY (student_id)
        REFERENCES students(student_id)
        ON DELETE CASCADE,
        
    FOREIGN KEY (job_id)
        REFERENCES jobs(job_id)
        ON DELETE CASCADE
);
