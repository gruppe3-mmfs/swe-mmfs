CREATE TABLE IF NOT EXISTS prosjekt.family (
	familyId INT AUTO_INCREMENT PRIMARY KEY,
	familyName VARCHAR(50) NOT NULL
);

INSERT IGNORE INTO prosjekt.family (familyId, familyName) VALUES (1, "Duck");

CREATE TABLE IF NOT EXISTS prosjekt.users (
  userId INT AUTO_INCREMENT PRIMARY KEY,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  phoneNumber VARCHAR(20) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
	userFamilyId INT,
	FOREIGN KEY (userFamilyId) REFERENCES prosjekt.family(familyId)
);

CREATE TABLE IF NOT EXISTS prosjekt.ticketTypes (
	ticketTypeId INT AUTO_INCREMENT PRIMARY KEY,
	ticketType VARCHAR(50) NOT NULL UNIQUE
);

INSERT IGNORE INTO prosjekt.ticketTypes (ticketTypeId, ticketType) VALUES (1, "Normal");
INSERT IGNORE INTO prosjekt.ticketTypes (ticketTypeId, ticketType) VALUES (2, "Student");
INSERT IGNORE INTO prosjekt.ticketTypes (ticketTypeId, ticketType) VALUES (3, "Senior");

CREATE TABLE IF NOT EXISTS prosjekt.tickets (
	ticketId INT AUTO_INCREMENT PRIMARY KEY,
	ticketHash VARCHAR(64) NOT NULL UNIQUE,
	ticketType INT NOT NULL,
	ticketRouteOrigin VARCHAR(50) NOT NULL,
	ticketRouteDestination VARCHAR(50) NOT NULL,
	ticketOwnerId INT,
	FOREIGN KEY (ticketType) REFERENCES prosjekt.ticketTypes(ticketTypeId),
	FOREIGN KEY (ticketOwnerId) REFERENCES prosjekt.users(userId)
);
