--caver data
INSERT INTO cavers (name, status, phone)
VALUES
  ('John', 'safe', '604123456'),
  ('Jane', 'safe', '604123456'),
  ('Alice', 'safe', '604123456'),
  ('Bob', 'safe', '604123456'),
  ('Emily', 'safe', '604123456');

--trips data
INSERT INTO trips (caver_id, cave_name, start_time, end_time, group_size, max_trip_length)
VALUES
  (1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (2, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (3, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (4, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5),
  (5, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5.5);
  