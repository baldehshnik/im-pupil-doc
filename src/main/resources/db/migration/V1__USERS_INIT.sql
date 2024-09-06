-- Create the user `local_admin` with the password '1111'
CREATE USER IF NOT EXISTS 'local_admin'@'localhost' IDENTIFIED BY '1111';

-- Grant ALL PRIVILEGES on the `im_pupil` database to `local_admin`
GRANT ALL PRIVILEGES ON `im_pupil`.* TO 'local_admin'@'localhost';

-- Create the user `local_user` with the password '1111'
CREATE USER IF NOT EXISTS 'local_user'@'localhost' IDENTIFIED BY '1111';

-- Grant specific privileges (SELECT, INSERT, UPDATE, DELETE, CREATE) on the `im_pupil` database to `local_user`
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE ON `im_pupil`.* TO 'local_user'@'localhost';

-- Apply privilege changes
FLUSH PRIVILEGES;