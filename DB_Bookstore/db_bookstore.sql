CREATE DATABASE  IF NOT EXISTS `db_bookstore`;
USE `db_bookstore`;

CREATE TABLE `users` (
  `id` int primary key auto_increment,
  `username` varchar(20) NOT NULL unique,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `categories` (
  `id` int primary key auto_increment,
  `name` varchar(50) NOT NULL unique
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `publishers` (
  `id` int primary key auto_increment,
  `name` varchar(50) NOT NULL unique
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `authors` (
  `id` int primary key auto_increment,
  `name` varchar(50) NOT NULL,
  `age` int not null
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `customers` (
  `id` int primary key auto_increment,
  `name` varchar(50) NOT NULL,
  `phone` varchar(10) NOT NULL unique,
  `email` varchar(50) NOT NULL unique,
  `address` varchar(50) NOT NULL,
  `books_purchased` int not null default 0,
  `rank` enum('vip', 'normal') default 'normal',
  `birth` date not null,
  CONSTRAINT phone_number_format CHECK (`phone` REGEXP '^[0-9]{10}$')
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `books` (
  `id` int primary key auto_increment,
  `title` varchar(50) NOT NULL unique,
  `author_id` int NOT NULL,
  `category_id` int NOT NULL,
  `publisher_id` int NOT NULL,
  `import_day` date NOT NULL,
  `price` double NOT NULL,
  `quantity` int NOT NULL,
  FOREIGN KEY (`author_id`) REFERENCES authors(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`publisher_id`) REFERENCES publishers(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`category_id`) REFERENCES categories(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `bills` (
  `id` int primary key auto_increment,
  `customer_id` int NULL,
  `date` date NOT NULL,
  `discount` double NOT NULL default 0,
  `total` double NULL,
  FOREIGN KEY (`customer_id`) REFERENCES customers(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `bill_details` (
  `id` int primary key auto_increment,
  `bill_id` int not null,
  `book_id` int NOT NULL,
  `quantity` int NOT NULL,
  FOREIGN KEY (`bill_id`) REFERENCES bills(`id`) ON DELETE CASCADE,
  FOREIGN KEY (`book_id`) REFERENCES books(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DELIMITER //
CREATE TRIGGER update_customer
BEFORE UPDATE ON customers
FOR EACH ROW
BEGIN
  IF NEW.books_purchased >= 200 THEN
    SET NEW.rank = 'vip';
  END IF;
END;

CREATE TRIGGER update_customer_books_purchased_delete
AFTER DELETE ON bill_details
FOR EACH ROW
BEGIN
  UPDATE customers
  SET books_purchased = books_purchased - OLD.quantity
  WHERE id = (SELECT customer_id FROM bills WHERE bills.id = OLD.bill_id);
END; 

CREATE TRIGGER update_customer_books_purchased_update
AFTER UPDATE ON bill_details
FOR EACH ROW
BEGIN
  UPDATE customers
  SET books_purchased = books_purchased - OLD.quantity + NEW.quantity
  WHERE id = (SELECT customer_id FROM bills WHERE bills.id = OLD.bill_id);
END; 

CREATE TRIGGER delete_bill AFTER DELETE ON bill_details FOR EACH ROW
BEGIN
  DECLARE remaining INT;
  SELECT COUNT(*) INTO remaining FROM bill_details WHERE bill_id = OLD.bill_id;
  IF remaining = 0 THEN
    DELETE FROM bills WHERE id = OLD.bill_id;
  END IF;
END; 

CREATE TRIGGER update_total AFTER INSERT ON bill_details
FOR EACH ROW
BEGIN
  DECLARE total DOUBLE;
  DECLARE totalDis DOUBLE;
  
  SET total = (
	SELECT SUM(bd.quantity * b.price)
	FROM bill_details bd
	JOIN books b ON bd.book_id = b.id
	WHERE bd.bill_id = NEW.bill_id
  );
  
  SET totalDis = total - (total * (SELECT discount from bills where bills.id = NEW.bill_id)/100);
  
  UPDATE bills
  SET total = totalDis
  WHERE id = NEW.bill_id;
  
  UPDATE customers SET books_purchased = books_purchased + NEW.quantity WHERE 
  customers.id = (SELECT customer_id from bills where bills.id = NEW.bill_id);
END; 

CREATE TRIGGER update_book_quantity_delete
AFTER DELETE ON bill_details
FOR EACH ROW
BEGIN
  UPDATE books SET quantity = quantity + OLD.quantity
  WHERE id = OLD.book_id;
END;

CREATE TRIGGER update_book_quantity_update
AFTER UPDATE ON bill_details
FOR EACH ROW
BEGIN
  UPDATE books SET quantity = quantity + OLD.quantity
  WHERE id = OLD.book_id;
  
  UPDATE books SET quantity = quantity - NEW.quantity
  WHERE id = NEW.book_id;
END; //
DELIMITER ;

CREATE OR REPLACE VIEW viewBooks AS SELECT books.id, books.title, 
books.import_day, books.price, books.quantity, categories.name as category_name, 
authors.name as author_name, publishers.name as publisher_name, 
(SELECT SUM(quantity) FROM bill_details where bill_details.book_id = books.id) as book_sold
FROM books LEFT JOIN categories ON books.category_id = categories.id 
LEFT JOIN authors ON books.author_id = authors.id
LEFT JOIN publishers ON books.publisher_id = publishers.id;

CREATE OR REPLACE VIEW viewBooksStatistic AS SELECT bill_details.book_id AS id, books.title, 
bills.date, books.quantity, SUM(bill_details.quantity) AS book_sold
FROM bill_details LEFT JOIN bills ON bill_details.bill_id = bills.id 
LEFT JOIN books ON bill_details.book_id = books.id
GROUP BY bill_details.book_id, books.title, bills.date, books.quantity;

CREATE OR REPLACE VIEW viewBillDetails AS SELECT bill_details.*, books.title, 
books.price, bills.date, (bill_details.quantity * books.price) as total FROM bill_details 
LEFT JOIN books ON bill_details.book_id = books.id 
LEFT JOIN bills ON bill_details.bill_id = bills.id;

CREATE OR REPLACE VIEW viewBills AS SELECT bills.id, bills.customer_id, customers.name, bills.date, 
(SELECT SUM(quantity) FROM bill_details WHERE bill_details.id = bills.id) as amount, bills.discount, bills.total
FROM bills LEFT JOIN customers ON bills.customer_id = customers.id;

INSERT INTO `users`(`username`, `password`) VALUES ('admin','admin');
INSERT INTO `categories`(`name`) VALUES ('Detective'), ('Romance'),('Comedy');
INSERT INTO `publishers`(`name`) VALUES ('Kim Dong'), ('Tuoi Tre'),('Thanh Xuan');
INSERT INTO `authors`(`name`, `age`) VALUES ('Agatha Christie', 47), ('Arthur Conan Doyle', 35),('Fujiko', 38);
INSERT INTO `customers`(`name`, `phone`, `email`, `address`, `birth`) VALUES ('Thomas','0337223434','tuananh@gmail.com','VN', '2002-06-28'),('Tom','0989828930','tom@gmail.com','America', '2000-03-13'),('Cleverly','0167496868','tuananhdao28@gmail.com','HN, Canada', '2001-11-28');
INSERT INTO `books`(`title`, `author_id`, `category_id`, `publisher_id`, `import_day`, `price`, `quantity`) 
VALUES ('Adventures of Sherlock Homles E.1',1,1,1,'2020-10-12',200,10),('Hercules Poirot',2,1,2,'2010-07-28',190,4),('Doraemon E.1', 3, 3, 1,'2012-06-28',150,6);
INSERT INTO `bills`(`customer_id`, `date`, `discount`) VALUES (2,'2023-05-25', 10), (null, '2023-05-15', 0);
INSERT INTO `bill_details`(`bill_id`,`book_id`,`quantity`) VALUES (1,1,3), (2, 1, 1);