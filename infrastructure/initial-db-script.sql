CREATE database stolpersteine;
CREATE USER stolpersteineusr WITH PASSWORD 'stolpersteineUsr';
GRANT USAGE ON SCHEMA public TO stolpersteineusr;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO stolpersteineusr;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO stolpersteineusr;
ALTER SCHEMA public OWNER TO stolpersteineusr;
create extension postgis;


