-- Drop existing tables if needed (optional)
DROP TABLE IF EXISTS trips;
DROP TABLE IF EXISTS cavers;
--tables for cavers and trips
CREATE TABLE cavers(
  caver_id SERIAL PRIMARY KEY,
  name VARCHAR(50),
  status VARCHAR(50),
  phone VARCHAR(50));

  
CREATE TABLE trips(
  trip_id SERIAL PRIMARY KEY,
  caver_id INT,
  cave_name VARCHAR(50),
  start_time timestamptz,
  end_time timestamptz,
  group_size INT,
  max_trip_length REAL,
  FOREIGN KEY (caver_id) REFERENCES cavers(caver_id)
  ON DELETE CASCADE);