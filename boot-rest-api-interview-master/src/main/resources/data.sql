--CREATE TABLE book (
--	id INT AUTO_INCREMENT  PRIMARY KEY,
--	status VARCHAR(1) NOT NULL,
--	name VARCHAR(255) NOT NULL
--);

INSERT INTO book (id, status, name) VALUES 
(1, 'A', 'Book1'),
(2, 'A', 'Book2'),
(3, 'A', 'Book3');



CREATE TABLE user (
	id INT AUTO_INCREMENT PRIMARY KEY,
	login varchar(255) NOT NULL,
	password varchar(255) NOT NULL
);

CREATE TABLE role (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name varchar(255) NOT NULL
);

CREATE TABLE user_role (
	user_id INT NOT NULL,
	role_id INT NOT NULL
);

ALTER TABLE user_role ADD FOREIGN KEY (user_id) REFERENCES public.user(id);
ALTER TABLE user_role ADD FOREIGN KEY (role_id) REFERENCES public.role(id);
ALTER TABLE user_role ADD UNIQUE KEY unique_user_role (user_id, role_id);

INSERT INTO user (id, login, password) VALUES (1, 'member', 'member');
INSERT INTO role (id, name) VALUES (1, 'ROLE_MEMBER');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO user (id, login, password) VALUES (2, 'librarian', 'librarian');
INSERT INTO role (id, name) VALUES (2, 'ROLE_LIBRARIAN');
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);