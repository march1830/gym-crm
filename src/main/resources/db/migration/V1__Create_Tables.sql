-- V1__Create_Tables.sql

-- Create Users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active BOOLEAN NOT NULL
);

-- Create Training Types table
CREATE TABLE training_types (
    id BIGSERIAL PRIMARY KEY,
    training_type_name VARCHAR(255) NOT NULL UNIQUE
);

-- Create Trainers table
CREATE TABLE trainers (
    id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    -- ADDED THIS LINE to link to the training_types table
    specialization_id BIGINT REFERENCES training_types(id)
);

-- Create Trainees table
CREATE TABLE trainees (
    id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
    date_of_birth DATE,
    address VARCHAR(255)
);

-- Create Trainings table
CREATE TABLE trainings (
    id BIGSERIAL PRIMARY KEY,
    trainee_id BIGINT NOT NULL REFERENCES trainees(id) ON DELETE CASCADE,
    trainer_id BIGINT NOT NULL REFERENCES trainers(id),
    training_type_id BIGINT NOT NULL REFERENCES training_types(id),
    training_name VARCHAR(255) NOT NULL,
    training_date DATE NOT NULL,
    training_duration INT NOT NULL
);

-- Create join table for Trainee-Trainer Many-to-Many relationship
CREATE TABLE trainee_trainer (
    trainee_id BIGINT NOT NULL REFERENCES trainees(id) ON DELETE CASCADE,
    trainer_id BIGINT NOT NULL REFERENCES trainers(id) ON DELETE CASCADE,
    PRIMARY KEY (trainee_id, trainer_id)
);