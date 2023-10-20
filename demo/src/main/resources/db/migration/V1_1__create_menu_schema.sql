
CREATE TABLE users (
  user_id BIGINT AUTO_INCREMENT NOT NULL,
   user_name VARCHAR(255) NULL,
   password VARCHAR(255) NULL,
   `role` VARCHAR(255) NULL,
   CONSTRAINT pk_users PRIMARY KEY (user_id)
);

CREATE TABLE menu (
  id BIGINT AUTO_INCREMENT NOT NULL,
   title VARCHAR(255) NULL,
   CONSTRAINT pk_menu PRIMARY KEY (id)
);

CREATE TABLE meal (
  id BIGINT AUTO_INCREMENT NOT NULL,
   title VARCHAR(255) NULL,
   `description` VARCHAR(255) NULL,
   menu_id BIGINT NULL,
   CONSTRAINT pk_meal PRIMARY KEY (id)
);

ALTER TABLE meal ADD CONSTRAINT FK_MEAL_ON_MENU FOREIGN KEY (menu_id) REFERENCES menu (id);

CREATE TABLE food_order (
  id BIGINT AUTO_INCREMENT NOT NULL,
   created_date datetime NULL,
   total_amount DOUBLE NULL,
   is_confirmed BOOLEAN DEFAULT false,
   user_id BIGINT NULL,
   CONSTRAINT pk_foodorder PRIMARY KEY (id)
);

ALTER TABLE food_order ADD CONSTRAINT FK_FOODORDER_ON_USER FOREIGN KEY (user_id) REFERENCES users (user_id);

CREATE TABLE order_item (
  id BIGINT AUTO_INCREMENT NOT NULL,
   meal_id BIGINT NULL,
   quantity INT NOT NULL,
   food_order_id BIGINT NULL,
   CONSTRAINT pk_orderitem PRIMARY KEY (id)
);

ALTER TABLE order_item ADD CONSTRAINT FK_ORDERITEM_ON_FOODORDER FOREIGN KEY (food_order_id) REFERENCES food_order (id);

ALTER TABLE order_item ADD CONSTRAINT FK_ORDERITEM_ON_MEAL FOREIGN KEY (meal_id) REFERENCES meal (id);

