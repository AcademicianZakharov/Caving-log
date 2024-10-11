--CREATE  DATABASE Caving;
--USE caving;
CREATE TABLE cavers(
  caver_id INT PRIMARY KEY,
  name VARCHAR(50),
  status VARCHAR(50),
  phone VARCHAR(50));

 INSERT INTO cavers (caver_id, name, status, phone)
VALUES
  (1, 'John', 'safe', 604123456),
  (2, 'Jane', 'safe', 604123456),
  (3, 'Alice', 'safe', 604123456),
  (4, 'Bob', 'safe', 604123456),
  (5, 'Emily', 'safe', 604123456);
  
  
CREATE TABLE trips(
  trip_id INT PRIMARY KEY,
  caver_id INT,
  cave_name VARCHAR(50),
  start_time timestamptz,
  end_time timestamptz,
  group_size INT,
  max_trip_length INT,
  FOREIGN KEY (caver_id) REFERENCES cavers(caver_id));

 INSERT INTO trips (trip_id, caver_id, cave_name, start_time, end_time, group_size, max_trip_length)
VALUES
  (1, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (2, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (3, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (4, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (5, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5);
--SELECT * FROM trips where trip_id = 1;  

SELECT * FROM cavers
INNER JOIN trips ON trips.caver_id = cavers.caver_id;