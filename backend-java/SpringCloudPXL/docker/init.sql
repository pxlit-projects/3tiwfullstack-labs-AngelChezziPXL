-- Create the databases
CREATE DATABASE IF NOT EXISTS employeeservice_db;
CREATE DATABASE IF NOT EXISTS departmentservice_db;
CREATE DATABASE IF NOT EXISTS organizationservice_db;

-- Grant all privileges on the databases to the specified user
GRANT ALL PRIVILEGES ON employeeservice_db.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON departmentservice_db.* TO 'user'@'%';
GRANT ALL PRIVILEGES ON organizationservice_db.* TO 'user'@'%';

-- Apply the changes
FLUSH PRIVILEGES;
