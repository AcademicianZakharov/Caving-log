--tables for cavers and trips
CREATE TABLE cavers(
  caver_id INT PRIMARY KEY,
  name VARCHAR(50),
  status VARCHAR(50),
  phone VARCHAR(50));

  
CREATE TABLE trips(
  trip_id INT PRIMARY KEY,
  caver_id INT,
  cave_name VARCHAR(50),
  start_time timestamptz,
  end_time timestamptz,
  group_size INT,
  max_trip_length INT,
  FOREIGN KEY (caver_id) REFERENCES cavers(caver_id));