INSERT INTO usuarios (username, password, enabled)
VALUES ('emylle', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xdqD1RPHu5vGhzS6', true)
ON CONFLICT (username) DO NOTHING;