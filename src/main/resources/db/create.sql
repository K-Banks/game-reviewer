SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS developers (
  id int PRIMARY KEY auto_increment,
  name VARCHAR
);

CREATE TABLE IF NOT EXISTS games (
  id int PRIMARY KEY auto_increment,
    name VARCHAR,
    genre VARCHAR,
    minPlayers INTEGER,
    maxPlayers INTEGER,
    timeToPlay INTEGER,
    developerId INTEGER,
    rating INTEGER
 );