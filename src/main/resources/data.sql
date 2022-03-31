INSERT INTO roles(id, name) VALUES(1, 'ROLE_USER');
INSERT INTO roles(id, name) VALUES(2, 'ROLE_ADMIN');

INSERT INTO user ("USER_ID", "USER_NAME", "PASSWORD", "FIRST_NAME", "LAST_NAME", "RETRY_COUNT","IS_ACCOUNT_LOCKED") 
VALUES (1, 'salil.bansal@nagarro.com', '$2a$10$HiglxhNn8GPPa3nEAu3w2.T5gQzMRAbj/1w6TQaVpzOAaFnkZvaB2'
,'Salil', 'Bansal', 0, false);



INSERT INTO user ("USER_ID", "USER_NAME", "PASSWORD", "FIRST_NAME", "LAST_NAME", "RETRY_COUNT", "IS_ACCOUNT_LOCKED") 
VALUES (2, 'salil.bansal@mcb.com', '$2a$10$rpeJLiSs3Ze6r619ssvhR./QP97/cd9LBP6zzneCnI/ziYj7lwEDW'
,'Sameer', 'Sharma', 0, false);

INSERT into user_roles ("USER_ID", "ROLE_ID") VALUES(1, 2);
INSERT into user_roles ("USER_ID", "ROLE_ID") VALUES(2, 1);