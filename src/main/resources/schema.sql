CREATE TABLE attendees (
    attendee_id SERIAL PRIMARY KEY,
    attendee_name VARCHAR(100) NOT NULL,
    email VARCHAR(50)
);

CREATE TABLE venues (
    venue_id SERIAL PRIMARY KEY,
    venue_name VARCHAR(100) NOT NULL,
    location VARCHAR(100) NOT NULL
);

ALTER TABLE venues ADD CONSTRAINT unique_venue_name UNIQUE (venue_name);

CREATE TABLE events (
    event_id SERIAL PRIMARY KEY,
    event_name VARCHAR(100),
    event_date DATE,
    venue_id INT,
    CONSTRAINT fk_venue FOREIGN KEY (venue_id) REFERENCES venues(venue_id) ON DELETE RESTRICT
);

CREATE TABLE event_attendee (
    attendee_id INT NOT NULL,
    event_id INT NOT NULL,
    PRIMARY KEY (attendee_id, event_id),
    CONSTRAINT fk_attendee FOREIGN KEY (attendee_id) REFERENCES attendees (attendee_id) ON DELETE RESTRICT,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE RESTRICT
)