CREATE SCHEMA stolpersteine;
CREATE USER 'stolpersteineUsr'@'localhost' IDENTIFIED BY 'stolpersteineUsr';
GRANT CREATE, SELECT, UPDATE, INSERT, DROP, DELETE, ALTER, REFERENCES on stolpersteine.* to 'stolpersteineUsr'@'localhost'
