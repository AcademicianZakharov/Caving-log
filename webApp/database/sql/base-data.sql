--caver data
INSERT INTO cavers (caver_id, name, status, phone)
VALUES
  (1, 'John', 'safe', 604123456),
  (2, 'Jane', 'safe', 604123456),
  (3, 'Alice', 'safe', 604123456),
  (4, 'Bob', 'safe', 604123456),
  (5, 'Emily', 'safe', 604123456);

--trips data
INSERT INTO trips (trip_id, caver_id, cave_name, start_time, end_time, group_size, max_trip_length)
VALUES
  (1, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (2, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (3, 2, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (4, 3, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5),
  (5, 1, 'Othello Tunnels', '1000-01-01 00:00:00', '9999-12-31 23:59:59', 1, 5);
  