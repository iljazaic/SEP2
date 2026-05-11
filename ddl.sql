CREATE SCHEMA sep2;

CREATE TABLE sep2.guest(
  email VARCHAR(254) PRIMARY KEY,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sep2.registered_user (
  email VARCHAR(254) PRIMARY KEY REFERENCES sep2.guest(email) ON DELETE CASCADE,
  password_hash TEXT NOT NULL,
  full_name TEXT NOT NULL,
  passport_no VARCHAR(32) NOT NULL UNIQUE,
  phone VARCHAR(32)
);

CREATE TABLE sep2.passenger (
  email VARCHAR(254) PRIMARY KEY REFERENCES sep2.registered_user(email) ON DELETE CASCADE,
  passenger_status VARCHAR(32)
);

CREATE TABLE sep2.administrator (
  email VARCHAR(254) PRIMARY KEY REFERENCES sep2.registered_user(email) ON DELETE CASCADE,
  staff_no VARCHAR(32) NOT NULL UNIQUE,
  role_name VARCHAR(64) NOT NULL
);

CREATE TABLE sep2.discount_code (
  code VARCHAR(32) PRIMARY KEY,
  description TEXT,
  percentage NUMERIC(5,2) NOT NULL CHECK (percentage >= 0 AND percentage <= 100),
  expires_at TIMESTAMP
);

CREATE TABLE sep2.flight (
  flight_no VARCHAR(16) PRIMARY KEY,
  origin VARCHAR(64) NOT NULL,
  destination VARCHAR(64) NOT NULL,
  depart_time TIMESTAMP NOT NULL,
  arrive_time TIMESTAMP NOT NULL,
  status VARCHAR(32) NOT NULL,
  base_price NUMERIC(10,2) NOT NULL CHECK (base_price >= 0),
  capacity INTEGER NOT NULL CHECK (capacity >= 0),
  admin_email VARCHAR(254) NOT NULL REFERENCES sep2.administrator(email),
  CONSTRAINT chk_flight_time CHECK (depart_time < arrive_time)
);

CREATE TABLE sep2.seat (
  flight_no VARCHAR(16) NOT NULL REFERENCES sep2.flight(flight_no) ON DELETE CASCADE,
  seat_no VARCHAR(8) NOT NULL,
  seat_class VARCHAR(16) NOT NULL,
  is_window BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (flight_no, seat_no)
);

CREATE TABLE sep2.reservation (
  reservation_id BIGSERIAL PRIMARY KEY,
  booked_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status VARCHAR(32) NOT NULL,
  total_price NUMERIC(10,2) NOT NULL CHECK (total_price >= 0),
  passenger_email VARCHAR(254) NOT NULL REFERENCES sep2.passenger(email),
  flight_no VARCHAR(16) NOT NULL REFERENCES sep2.flight(flight_no),
  discount_code VARCHAR(32) REFERENCES sep2.discount_code(code),
  override_admin_email VARCHAR(254) REFERENCES sep2.administrator(email)
);

CREATE TABLE sep2.ticket (
  ticket_id BIGSERIAL PRIMARY KEY,
  reservation_id BIGINT NOT NULL REFERENCES sep2.reservation(reservation_id) ON DELETE CASCADE,
  flight_no VARCHAR(16) NOT NULL,
  seat_no VARCHAR(8) NOT NULL,
  traveler_name TEXT NOT NULL,
  passport_no VARCHAR(32),
  CONSTRAINT fk_ticket_seat FOREIGN KEY (flight_no, seat_no) REFERENCES sep2.seat(flight_no, seat_no),
  CONSTRAINT uq_ticket_seat UNIQUE (flight_no, seat_no)
);

CREATE TABLE sep2.luggage (
  luggage_id BIGSERIAL PRIMARY KEY,
  ticket_id BIGINT NOT NULL REFERENCES sep2.ticket(ticket_id) ON DELETE CASCADE,
  size VARCHAR(16),
  weight NUMERIC(8,2) CHECK (weight >= 0),
  price NUMERIC(10,2) CHECK (price >= 0)
);

CREATE TABLE sep2.service (
  service_id BIGSERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  price NUMERIC(10,2) NOT NULL CHECK (price >= 0)
);

CREATE TABLE sep2.reservation_service (
  reservation_id BIGINT NOT NULL REFERENCES sep2.reservation(reservation_id) ON DELETE CASCADE,
  service_id BIGINT NOT NULL REFERENCES sep2.service(service_id),
  quantity INTEGER NOT NULL CHECK (quantity > 0),
  PRIMARY KEY (reservation_id, service_id)
);