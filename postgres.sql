CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activities (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    duration decimal NOT NULL,
    calories integer NOT NULL,
    stepcount integer NOT NULL,
    started timestamp NOT NULL,
    useremail VARCHAR(255) NOT NULL
);

CREATE TABLE hydrations (
    id SERIAL PRIMARY KEY,
    mltaken integer NOT NULL,
    liquidname VARCHAR(255) NOT NULL,
    datetime timestamp NOT NULL,
	useremail VARCHAR(255) NOT NULL
);

CREATE TABLE nutritions (
    id SERIAL PRIMARY KEY,
    noofcaloriestaken integer NOT NULL,
    foodname text NOT NULL,
    datetime timestamp NOT NULL,
	useremail VARCHAR(255) NOT NULL
);

CREATE TABLE weightgoals (
    id SERIAL PRIMARY KEY,
    goaltype VARCHAR(255) NOT NULL,
    targetweight integer NOT NULL,
	started timestamp NOT NULL,
    deadline timestamp NOT NULL,
	useremail TEXT NOT NULL
);

select * from users;