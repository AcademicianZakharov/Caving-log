--CRUD operations in database
--CREATE  DATABASE Caving;
--USE caving;

-- Drop existing tables if needed 
DROP TABLE IF EXISTS trips;
DROP TABLE IF EXISTS cavers;
-- Create the cavers table with auto-incrementing caver_id
CREATE TABLE cavers(
  caver_id SERIAL PRIMARY KEY,  -- SERIAL will auto-increment the caver_id
  name VARCHAR(50),
  status VARCHAR(50),
  phone VARCHAR(50)
);
-- Insert into cavers without specifying caver_id (it will auto-increment)
INSERT INTO cavers (name, status, phone)
VALUES
  ('Gandalf', 'Entering mine', '250-742-6412'),
  ('Aragorn', 'Entering mine', '604-854-4562'),
  ('Legolas', 'Entering mine', '778-742-4532'),
  ('Gimli', 'Entering mine', '250-345-4652'),
  ('Boromir', 'Entering mine', '250-542-5431'),
  ('Merry', 'Entering mine', '250-769-2753'),
  ('Pippin', 'Entering mine', '250-542-5431'),
  ('Sam', 'Entering mine', '250-769-2753');
-- Create the trips table with auto-incrementing trip_id
CREATE TABLE trips(
  trip_id SERIAL PRIMARY KEY,   -- SERIAL will auto-increment the trip_id
  caver_id INT,
  cave_name VARCHAR(50),
  start_time timestamp,
  end_time timestamp,
  group_size INT,
  max_trip_length REAL,
  FOREIGN KEY (caver_id) REFERENCES cavers(caver_id)
  ON DELETE CASCADE
);
-- Insert into trips without specifying trip_id (it will auto-increment)
INSERT INTO trips (caver_id, cave_name, start_time, end_time, group_size, max_trip_length)
VALUES
  (1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (2, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (3, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (4, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (5, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5);
  
  SELECT * FROM cavers
left JOIN trips ON trips.caver_id = cavers.caver_id;