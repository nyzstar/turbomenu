# --- !Ups
CREATE TABLE menu(
	id 			bigint AUTO_INCREMENT PRIMARY KEY,
	name 		varchar(100),
	category_id	long,
	price		float,
	restaurantId	long
);


CREATE TABLE restaurant(
	id 			bigint AUTO_INCREMENT PRIMARY KEY,
	name 		varchar(100),
	address		varchar(200),
	open		int,
	close		int,
	contact		varchar(100)
);

CREATE TABLE category(
	id 			int AUTO_INCREMENT PRIMARY KEY,
	name 		varchar(100)
);

# --- !Downs
DROP TABLE IF EXISTS menu;

DROP TABLE IF EXISTS restaurant;

DROP TABLE IF EXISTS category;
