CREATE TABLE IF NOT EXISTS prosjekt.users (
  userId INT AUTO_INCREMENT PRIMARY KEY,
  firstName VARCHAR(50) NOT NULL,
  lastName VARCHAR(50) NOT NULL,
  phoneNumber VARCHAR(20) UNIQUE,
  email VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS prosjekt.ticketTypes (
	ticketTypeId INT AUTO_INCREMENT PRIMARY KEY,
	ticketType VARCHAR(50) NOT NULL
);

INSERT INTO prosjekt.ticketTypes (ticketType) VALUES ("Normal");
INSERT INTO prosjekt.ticketTypes (ticketType) VALUES ("Student");
INSERT INTO prosjekt.ticketTypes (ticketType) VALUES ("Senior");

CREATE TABLE IF NOT EXISTS prosjekt.tickets (
	ticketId INT AUTO_INCREMENT PRIMARY KEY,
	ticketHash VARCHAR(50) NOT NULL,
	ticketType INT NOT NULL,
	ticketRouteOrigin VARCHAR(50) NOT NULL,
	ticketRouteDestination VARCHAR(50) NOT NULL,
	ticketOwnerId INT,
	FOREIGN KEY (ticketType) REFERENCES prosjekt.ticketTypes(ticketTypeId),
	FOREIGN KEY (ticketOwnerId) REFERENCES prosjekt.users(userId)
);

