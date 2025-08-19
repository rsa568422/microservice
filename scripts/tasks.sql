CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS tasks (
    code UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    description VARCHAR(255),
    priority INT DEFAULT 4 CHECK (priority BETWEEN 1 AND 4),
    duration DOUBLE PRECISION CHECK (duration >= 0),
    status CHAR(1) CHECK (status ~ '^[PW]$')
);
