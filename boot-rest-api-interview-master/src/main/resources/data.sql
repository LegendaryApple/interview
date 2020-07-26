INSERT INTO book (id, status, name) VALUES 
(1, 'A', 'Book1'),
(2, 'A', 'Book2'),
(3, 'B', 'Book3');

ALTER TABLE user ADD UNIQUE KEY unique_login (login);

ALTER TABLE user_role ADD FOREIGN KEY (user_id) REFERENCES public.user(id);
ALTER TABLE user_role ADD FOREIGN KEY (role_name) REFERENCES public.role(name);
ALTER TABLE user_role ADD UNIQUE KEY unique_user_role (user_id, role_name);

INSERT INTO user (id, login, password, status) VALUES (1, 'member', 'member', 'A');
INSERT INTO role (name) VALUES ('MEMBER');
INSERT INTO user_role (user_id, role_name) VALUES (1, 'MEMBER');

INSERT INTO user (id, login, password, status) VALUES (2, 'librarian', 'librarian', 'A');
INSERT INTO role (name) VALUES ('LIBRARIAN');
INSERT INTO user_role (user_id, role_name) VALUES (2, 'LIBRARIAN');

INSERT INTO user (id, login, password, status) VALUES (3, 'asd', 'asd', 'A');
INSERT INTO user_role (user_id, role_name) VALUES (3, 'MEMBER');

INSERT INTO user (id, login, password, status) VALUES (4, 'del1', 'asd', 'A');
INSERT INTO user_role (user_id, role_name) VALUES (4, 'MEMBER');

INSERT INTO user (id, login, password, status) VALUES (5, 'del2', 'asd', 'A');
INSERT INTO user_role (user_id, role_name) VALUES (5, 'MEMBER');

INSERT INTO borrow_event (id, book_id, user_id, borrow_time, return_time) VALUES (1, 3, 1, '2020-07-01 00:00:00', null);