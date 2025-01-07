CREATE TABLE refresh_token(
                                             id integer PRIMARY KEY AUTO_INCREMENT,
                                             user_id integer UNIQUE NOT null,
                                             token varchar(256) NOT null,
                                             expiration_date TIMESTAMP not null
);