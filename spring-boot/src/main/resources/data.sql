DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS teams;

CREATE TABLE teams
(
   id		serial PRIMARY KEY NOT NULL,
   name		text NOT NULL,
   stadium	text NOT NULL
);

CREATE TABLE players
(
   id			 serial PRIMARY KEY NOT NULL,
   team_id		 integer REFERENCES teams(id),
   name			 text NOT NULL,
   position		 text NOT NULL,
   jersey_number integer NOT NULL
);

INSERT INTO teams (name, stadium) VALUES
	('Crusaders', 'Christchurch Stadium'),
	('Blues', 'Eden Park'),
	('Hurricanes', 'Sky Stadium'),
	('Highlanders', 'Forsyth Barr Stadium'),
	('Chiefs', 'Waikato Stadium');

INSERT INTO players (name, position, jersey_number, team_id)
	select 'Codie Taylor', 'Hooker', 2, id from teams where name = 'Crusaders';
INSERT INTO players (name, position, jersey_number, team_id)
	select  'Richie Mo''unga', 'First five-eights', 10, id from teams where name = 'Crusaders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'George Bridge', 'Winger', 11, id from teams where name = 'Crusaders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Jack Goodhue', 'Centre', 12, id from teams where name = 'Crusaders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Patrick Tuipulotu', 'Lock', 4, id from teams where name = 'Blues';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Beauden Barrett', 'First five-eights', 10, id from teams where name = 'Blues';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Dalton Papalii', 'Loose forward', 8, id from teams where name = 'Blues';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Matt Duffie', 'Fullback', 15, id from teams where name = 'Blues';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Dane Coles', 'Hooker', 2, id from teams where name = 'Hurricanes';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'TJ Perenara', 'Halfback', 9, id from teams where name = 'Hurricanes';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Ardie Savea', 'Loose forward', 8, id from teams where name = 'Hurricanes';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Ngani Laumape', 'Centre', 12, id from teams where name = 'Hurricanes';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Ash Dixon', 'Hooker', 2, id from teams where name = 'Highlanders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Aaron Smith', 'Halfback', 9, id from teams where name = 'Highlanders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Pari Pari Parkinson', 'Lock', 4, id from teams where name = 'Highlanders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Bryn Gatland', 'First five-eights', 10, id from teams where name = 'Highlanders';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Sam Cane', 'Loose forward', 7, id from teams where name = 'Chiefs';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Aaron Cruden', 'First five-eights', 10, id from teams where name = 'Chiefs';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Damian McKenzie', 'Fullback', 15, id from teams where name = 'Chiefs';
INSERT INTO players (name, position, jersey_number, team_id)
	select 'Anton Lienert-Brown', 'Centre', 12, id from teams where name = 'Chiefs';
