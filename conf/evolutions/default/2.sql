# --- Sample dataset

# --- !Ups
insert into restaurant (name, address, open, close, contact) values ('Evvia Estiatorio', '420 Emerson St, Palo Alto, CA, 94301', 1050, 1320, '650-326-0983');
insert into restaurant (name, address, open, close, contact) values ('LYFE Kitchen', '167 Hamilton Ave, Palo Alto, CA', 1140, 1260, '650-325-5933');
insert into restaurant (name, address, open, close, contact) values ('Rangoon Ruby', '445 Emerson St, Palo Alto, CA 94301', 1020, 1320, '650-323-6543');

insert into category (name) values('entree');
insert into category (name) values('side');
insert into category (name) values('Dessert');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Psari Psito', 1, 26.50, 1, '/test_data/img/psari_psito.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Solomos', 1, 26.50, 1, '/test_data/img/solomos.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Kokinisto Me Manestra', 1, 30.00, 1, '/test_data/img/kokinisto_me_manestra.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Arnisia Paidakia', 1, 38.00, 1, '/test_data/img/arnisia_paidakia.jpg');


insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Horiatiki', 2, 10.00, 1, '/test_data/img/arnisia_paidakia.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Spanakotiropita', 2, 8.75, 1, '/test_data/img/spanakotiropita.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Avgolemono', 2, 9.00, 1, '/test_data/img/avgolemono.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Yiaourti Sorbet', 3, 8.25, 1, '/test_data/img/yiaourti_sorbet.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ('Sokolatina', 3, 8.00, 1, '/test_data/img/sokolatina.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Art's Unfried Chicken or Gardein Chicken", 1, 8.00, 2, '/test_data/img/Arts_Unfried_Chicken_or_Gardein_Chicken.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Gardein Sausage and Mozzarella Ravioli", 1, 11.99, 2, '/test_data/img/Gardein_Sausage_and_Mozzarella_Ravioli.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Grilled Barramundi", 1, 13.99, 2, '/test_data/img/Grilled_Barramundi.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Baked Sweet Potato Fries", 2, 2.99, 2, '/test_data/img/Baked_Sweet_Potato_Fries.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Roasted Brussels Sprouts and Butternut Squash", 2, 2.99, 2, '/test_data/img/Roasted_Brussels_Sprouts_and_Butternut_Squash.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Baby Kale Salad", 2, 2.99, 2, '/test_data/img/Baby_Kale_Salad.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Chocolate Budino", 3, 3.99, 2, '/test_data/img/Chocolate_Budino.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Kale-Banana Smoothie", 3, 4.99, 2, '/test_data/img/Kale-Banana_Smoothie.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Kebat", 1, 12.99, 3, '/test_data/img/Kebat.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Basil Chili Beef", 1, 15.99, 3, '/test_data/img/Basil_Chili_Beef.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Rangoon Chicken Curry", 1, 18.99, 3, '/test_data/img/Rangoon_Chicken_Curry.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Northern Fried Tofu", 2, 5.99, 3, '/test_data/img/Northern_Fried_Tofu.jpg');
insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Palata", 2, 4.99, 3, '/test_data/img/Palata.jpg');

insert into menu (name, categoryId, price, restaurantId, imageUrl) values ("Strawberry Shortcake", 3, 6.75, 3, '/test_data/img/Strawbery_Shortcake.jpg');

insert into user(email, password) values ('allen@turbomenu.com', 'allen');
insert into user(email, password) values ('chen@turbomenu.com', 'chen');

insert into rating(value, timeModified, userId, menuItemId) values (5, 1396482600, 1, 1);
insert into rating(value, timeModified, userId, menuItemId) values (4, 1396482600, 1, 2);
insert into rating(value, timeModified, userId, menuItemId) values (5, 1396482600, 1, 3);

insert into rating(value, timeModified, userId, menuItemId) values (3, 1397482600, 2, 1);
insert into rating(value, timeModified, userId, menuItemId) values (2, 1397482600, 2, 2);
insert into rating(value, timeModified, userId, menuItemId) values (1, 1397482600, 2, 3);

# --- !Downs
delete from restaurant;
delete from category;
delete from menu;
delete from user;
delete from rating;