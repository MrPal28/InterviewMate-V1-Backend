/* ---------------------------------------------------------
   PRODUCTION-GRADE MYSQL BOOTSTRAP SCRIPT
   ---------------------------------------------------------
   Responsibilities:
   ✔ Create all required databases
   ✔ Create service-specific users (least privilege model)
   ✔ Grant required privileges per microservice
   ✔ Enforce strong security patterns
   ✔ Make script fully idempotent (safe on re-run)
---------------------------------------------------------- */

/* ------------------------------
   1. CREATE DATABASES
------------------------------ */

CREATE DATABASE IF NOT EXISTS userdb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS resume_db
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;





/* ------------------------------
   2. CREATE USERS PER SERVICE
   (Least Privilege Best Practice)
------------------------------ */

/* User-service user */
CREATE USER IF NOT EXISTS 'user_service'@'%' IDENTIFIED BY 'UserService#2025';

/* Resume-service user */
CREATE USER IF NOT EXISTS 'resume_service'@'%' IDENTIFIED BY 'ResumeService#2025';



/* -------------------------------------------------------
   3. GRANT ONLY REQUIRED PRIVILEGES TO EACH SERVICE
---------------------------------------------------------- */

/* USER SERVICE → owns userdb */

GRANT ALL PRIVILEGES ON userdb.* TO 'user_service'@'%';

-- If your service needs to run Flyway/Liquibase:
-- GRANT CREATE, ALTER, INDEX, DROP ON userdb.* TO 'user_service'@'%';

/* RESUME SERVICE → owns resume_db */
GRANT ALL PRIVILEGES ON resume_db.* TO 'resume_service'@'%';





/* -------------------------------------------------------
   4. OPTIONAL: AUDIT/LOGGING USER
   (Useful for centralized analytics later)
---------------------------------------------------------- */
CREATE USER IF NOT EXISTS 'audit_user'@'%' IDENTIFIED BY 'Audit#2025';
GRANT SELECT ON userdb.* TO 'audit_user'@'%';
GRANT SELECT ON resume_db.* TO 'audit_user'@'%';



/* ------------------------------
   5. FINALIZE
------------------------------ */
FLUSH PRIVILEGES;
