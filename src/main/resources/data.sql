INSERT INTO attendees VALUES (default, 'Sok Davin', 'davin@gmail.com'),
                             (default, 'Many Many', 'many@gmail.com'),
                             (default, 'Central Cee', 'cee@gmail.com');

INSERT INTO venues VALUES (default, 'Leaf Village', 'Konoha Village'),
                          (default, 'Jujutsu High', 'Outskirt Tokyo'),
                          (default, 'Shiganshina District', 'Wall Maria, Paradis');

INSERT INTO events VALUES (default, 'Homecoming Party', '2026-03-29', 1),
                          (default, 'Graduation Party', '2026-04-19', 2),
                          (default, 'Wedding', '2026-04-09', 3);

INSERT INTO event_attendee (attendee_id, event_id) VALUES (2, 1),
                                                          (2, 3),
                                                          (1, 3),
                                                          (2, 2),
                                                          (3,3);
