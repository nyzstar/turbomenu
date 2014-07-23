# --- !Ups
CREATE TABLE menu(
	id 			bigint AUTO_INCREMENT PRIMARY KEY,
	name 		varchar(100),
	categoryId	long,
	price		float,
	restaurantId	long,
	imageUrl	varchar(500)
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


CREATE TABLE rating(
	id 				int AUTO_INCREMENT PRIMARY KEY,
	value			int,
	timeModified		long,
	userId			long,
	menuItemId		long
);

CREATE TABLE user(
	id 				int AUTO_INCREMENT PRIMARY KEY,
	email			varchar(100),
	password		varchar(50)
);


# --- !Downs
DROP TABLE IF EXISTS menu;

DROP TABLE IF EXISTS restaurant;

DROP TABLE IF EXISTS category;

DROP TABLE IF EXISTS rating;

DROP TABLE IF EXISTS user;
