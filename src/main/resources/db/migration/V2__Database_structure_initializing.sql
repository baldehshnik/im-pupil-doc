CREATE TABLE educational_institution (
                                         id integer PRIMARY KEY AUTO_INCREMENT,
                                         name text UNIQUE NOT null,
                                         abbreviation varchar(10) NOT null,
                                         type integer not null,
                                         address text null,
                                         phone varchar(20) null
);




CREATE TABLE user (
                      id INTEGER PRIMARY KEY AUTO_INCREMENT,
                      email VARCHAR(255) unique NOT NULL,
                      password VARCHAR(128) NOT NULL
);




CREATE TABLE role (
                      id INTEGER PRIMARY KEY AUTO_INCREMENT,
                      role_name VARCHAR(255) UNIQUE NOT NULL
);




CREATE TABLE user_role (
                           user_id INTEGER NOT NULL,
                           role_id INTEGER NOT NULL,

                           FOREIGN KEY (user_id) REFERENCES user(id)
                               ON DELETE CASCADE,
                           FOREIGN KEY (role_id) references role(id)
                               ON DELETE CASCADE,

                           UNIQUE (user_id, role_id)
);




CREATE TABLE admin (
                       id integer PRIMARY KEY AUTO_INCREMENT,
                       firstname varchar(32) NOT null,
                       lastname varchar(32) NOT null,
                       patronymic varchar(32) null,
                       access_mode integer NOT null,
                       icon text null,
                       institution_id integer NOT null,
                       user_id integer unique not null,

                       FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                           ON UPDATE CASCADE ON DELETE CASCADE,

                       FOREIGN KEY (user_id) REFERENCES user(id)
                           on UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE institution_event (
                                   id integer PRIMARY KEY AUTO_INCREMENT,
                                   title varchar(32) NOT null,
                                   description text NOT null,
                                   image text NOT null,
                                   duration integer NOT null,
                                   type integer NOT null,
                                   institution_id integer NOT null,

                                   FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                                       ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE event_service_info (
                                    id integer PRIMARY KEY AUTO_INCREMENT,
                                    date timestamp DEFAULT CURRENT_TIMESTAMP NOT null,
                                    event_id integer UNIQUE NOT null,
                                    admin_id integer null,

                                    FOREIGN KEY (admin_id) REFERENCES admin(id)
                                        ON UPDATE CASCADE ON DELETE CASCADE,

                                    FOREIGN KEY (event_id) REFERENCES institution_event(id)
                                        ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE event_reward (
                              id integer PRIMARY KEY AUTO_INCREMENT,
                              public_hours double null,
                              work_hours double null,
                              event_id integer UNIQUE NOT null,

                              FOREIGN KEY (event_id) REFERENCES institution_event(id)
                                  ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE event_date (
                            id integer PRIMARY KEY AUTO_INCREMENT,
                            event_date timestamp null,
                            start_time time null,
                            meeting_time time null,
                            event_id integer UNIQUE NOT null,

                            FOREIGN KEY (event_id) REFERENCES institution_event(id)
                                ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE event_place (
                             id integer PRIMARY KEY AUTO_INCREMENT,
                             address varchar(256) null,
                             place varchar(256) null,
                             event_id integer UNIQUE NOT null,

                             FOREIGN KEY (event_id) REFERENCES institution_event(id)
                                 ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE event_sorting (
                               id integer PRIMARY KEY AUTO_INCREMENT,
                               gender integer null,
                               maximum_amount integer null,
                               age_from integer null,
                               age_to integer null,
                               event_id integer UNIQUE NOT null,

                               FOREIGN KEY (event_id) REFERENCES institution_event(id)
                                   ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE practice (
                          id integer PRIMARY KEY AUTO_INCREMENT,
                          icon text null,
                          pay_ability boolean NOT null,
                          description text NOT null,
                          work_type integer NOT null,
                          title varchar(32) NOT null,
                          website text null,
                          institution_id integer NOT null,

                          FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                              ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE relocation (
                            id integer PRIMARY KEY AUTO_INCREMENT,
                            name varchar(32) NOT null,
                            practice_id integer NOT null,

                            FOREIGN KEY (practice_id) REFERENCES practice(id)
                                ON UPDATE CASCADE ON DELETE CASCADE,

                            UNIQUE(name, practice_id)
);




CREATE TABLE information_block (
                                   id integer PRIMARY KEY AUTO_INCREMENT,
                                   title varchar(32) NOT null,
                                   content text NOT null,
                                   practice_id integer NOT null,

                                   FOREIGN KEY (practice_id) REFERENCES practice(id)
                                       ON UPDATE CASCADE ON DELETE CASCADE,

                                   UNIQUE(title, practice_id)
);




CREATE TABLE section (
                         id integer PRIMARY KEY AUTO_INCREMENT,
                         title varchar(32) NOT null,
                         trainer varchar(128) NOT null,
                         price boolean NOT null,
                         gender integer NOT null,
                         description text null,
                         icon text null,
                         from_course integer null,
                         to_course integer null,
                         institution_id integer NOT null,

                         FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                             ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE pupil (
                       id integer PRIMARY KEY AUTO_INCREMENT,
                       icon text null,
                       code varchar(16) NOT null,
                       institution_id integer NOT null,
                       user_id integer unique not null,

                       FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                           ON UPDATE CASCADE ON DELETE CASCADE,

                       FOREIGN KEY (user_id) REFERENCES user(id)
                           ON UPDATE CASCADE ON DELETE CASCADE,

                       UNIQUE(code, institution_id)
);




CREATE TABLE notification (
                              id integer PRIMARY KEY AUTO_INCREMENT,
                              icon text NOT null,
                              title varchar(32) NOT null,
                              description text null,
                              date_time timestamp DEFAULT CURRENT_TIMESTAMP NOT null,
                              status boolean DEFAULT false NOT null,
                              pupil_id integer null,
                              admin_id integer null,
                              institution_id integer not null,

                              FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE,

                              FOREIGN KEY (pupil_id) REFERENCES pupil(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE,

                              FOREIGN KEY (admin_id) REFERENCES admin(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE section_question (
                                  id integer PRIMARY KEY AUTO_INCREMENT,
                                  message text NOT null,
                                  date timestamp DEFAULT CURRENT_TIMESTAMP NOT null,
                                  status integer DEFAULT 1 NOT null,
                                  pupil_id integer NOT null,
                                  institution_id integer NOT null,

                                  FOREIGN KEY (pupil_id) REFERENCES pupil(id)
                                      ON DELETE CASCADE ON UPDATE CASCADE,

                                  FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                                      ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE section_answer (
                                id integer PRIMARY KEY AUTO_INCREMENT,
                                message text NOT null,
                                date timestamp DEFAULT CURRENT_TIMESTAMP NOT null,
                                status integer DEFAULT 1 NOT null,
                                question_id integer NOT null,

                                FOREIGN KEY (question_id) REFERENCES section_question(id)
                                    ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE faculty (
                         id integer PRIMARY KEY AUTO_INCREMENT,
                         name varchar(256) NOT null,
                         abbreviation varchar(10) NOT null,
                         institution_id integer NOT null,

                         FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                             ON DELETE CASCADE ON UPDATE CASCADE,

                         UNIQUE(name, institution_id)
);




CREATE TABLE speciality (
                            id integer PRIMARY KEY AUTO_INCREMENT,
                            name varchar(256) NOT null,
                            abbreviation varchar(10) NOT null,
                            faculty_id integer NOT null,

                            FOREIGN KEY (faculty_id) REFERENCES faculty(id)
                                ON DELETE CASCADE ON UPDATE CASCADE,

                            UNIQUE(name, faculty_id)
);




CREATE TABLE unconfirmed_pupil (
                                   id integer PRIMARY KEY AUTO_INCREMENT,
                                   code varchar(16) NOT null,
                                   email varchar(256) UNIQUE NOT null,
                                   password varchar(16) NOT null,
                                   group_name varchar(32) NOT null,
                                   institution_id integer NOT null,
                                   speciality_id integer NOT null,
                                   faculty_id integer NOT null,

                                   FOREIGN KEY (institution_id) REFERENCES educational_institution(id)
                                       ON DELETE CASCADE ON UPDATE CASCADE,

                                   FOREIGN KEY (faculty_id) REFERENCES faculty(id)
                                       ON DELETE CASCADE ON UPDATE CASCADE,

                                   FOREIGN KEY (speciality_id) REFERENCES speciality(id)
                                       ON DELETE CASCADE ON UPDATE CASCADE,

                                   UNIQUE(code, institution_id)
);




CREATE TABLE institution_group (
                                   id integer PRIMARY KEY AUTO_INCREMENT,
                                   name varchar(32) NOT null,
                                   course integer NOT null,
                                   speciality_id integer NOT null,

                                   FOREIGN KEY (speciality_id) REFERENCES speciality(id)
                                       ON DELETE CASCADE ON UPDATE CASCADE,

                                   UNIQUE(name, speciality_id)
);




CREATE TABLE group_member (
                              id integer PRIMARY KEY AUTO_INCREMENT,
                              firstname varchar(32) NOT null,
                              lastname varchar(32) NOT null,
                              patronymic varchar(32) null,
                              code varchar(16) NOT null,
                              pupil_id integer null,
                              group_id integer NOT null,

                              FOREIGN KEY (pupil_id) REFERENCES pupil(id)
                                  ON UPDATE CASCADE,

                              FOREIGN KEY (group_id) REFERENCES institution_group(id)
                                  ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE exam (
                      id integer PRIMARY KEY AUTO_INCREMENT,
                      type integer NOT null,
                      name varchar(32) NOT null,
                      audience varchar(10) NOT null,
                      date_time timestamp NOT null,
                      status integer DEFAULT 0 NOT null,
                      group_id integer NOT null,

                      FOREIGN KEY (group_id) REFERENCES institution_group(id)
                          ON DELETE CASCADE ON UPDATE CASCADE,

                      UNIQUE(name, group_id)
);




CREATE TABLE schedule (
                          id integer PRIMARY KEY AUTO_INCREMENT,
                          name varchar(32) NOT null,
                          finish_date timestamp NOT null,
                          start_type integer NOT null,
                          start_date timestamp DEFAULT CURRENT_TIMESTAMP NOT null,
                          type integer DEFAULT 0 NOT null,
                          group_id integer NOT null,

                          FOREIGN KEY (group_id) REFERENCES institution_group(id)
                              ON DELETE CASCADE ON UPDATE CASCADE,

                          UNIQUE(name, group_id)
);




CREATE TABLE lesson (
                        id integer PRIMARY KEY AUTO_INCREMENT,
                        name varchar(64) NOT null,
                        start time NOT null,
                        end time NOT null,
                        teacher varchar(128) NOT null,
                        audience varchar(10) NOT null,
                        type integer NOT null,
                        dayofweek integer NOT null,
                        week_type integer NOT null,
                        schedule_id integer NOT null,

                        FOREIGN KEY (schedule_id) REFERENCES schedule(id)
                            ON DELETE CASCADE ON UPDATE CASCADE
);




CREATE TABLE pass (
                      id integer PRIMARY KEY AUTO_INCREMENT,
                      date timestamp NOT null,
                      status integer DEFAULT -1 NOT null,
                      pupil_id integer NOT null,
                      lesson_id integer NOT null,

                      FOREIGN KEY (pupil_id) REFERENCES group_member(id)
                          ON DELETE CASCADE ON UPDATE CASCADE,

                      FOREIGN KEY (lesson_id) REFERENCES lesson(id)
                          ON DELETE CASCADE ON UPDATE CASCADE,

                      UNIQUE(date, lesson_id, pupil_id)
);




CREATE TABLE news (
                      id integer PRIMARY KEY AUTO_INCREMENT,
                      title varchar(128) NOT null,
                      image text null,
                      description text null
);




CREATE TABLE guide (
                       id integer PRIMARY KEY AUTO_INCREMENT,
                       title varchar(128) NOT null,
                       description text null,
                       image text null,
                       news_id integer NOT null,

                       FOREIGN KEY (news_id) REFERENCES news(id)
                           ON DELETE CASCADE ON UPDATE CASCADE
);
