DELIMITER $$

CREATE TRIGGER notification_receiver_trigger
    BEFORE INSERT ON notification
    FOR EACH ROW
BEGIN
    IF (NEW.pupil_id IS NOT NULL AND NEW.admin_id IS NULL) OR
       (NEW.pupil_id IS NULL AND NEW.admin_id IS NOT NULL) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Only one field should be set: either pupil_id or admin_id';
END IF;
END$$

DELIMITER ;




DELIMITER $$

CREATE TRIGGER after_group_member_insert_trigger
    AFTER INSERT ON group_member
    FOR EACH ROW
BEGIN
    DECLARE pupil_id integer;
    DECLARE unconfirmed_pupil_id integer;
    DECLARE new_pupil_id integer;

    SELECT id INTO pupil_id FROM pupil WHERE pupil.code = NEW.code LIMIT 1;

    IF pupil_id IS NOT null THEN
    UPDATE group_member SET group_member.pupil_id = pupil_id WHERE id = NEW.id;
    ELSE
    SELECT id INTO unconfirmed_pupil_id FROM unconfirmed_pupil WHERE unconfirmed_pupil.code = NEW.code LIMIT 1;

    IF unconfirmed_pupil_id IS NOT null THEN
    		INSERT IGNORE INTO pupil(email, password, institution_id, code)
    SELECT email, password, institution_id, code FROM unconfirmed_pupil WHERE unconfirmed_pupil_id = id;

    SELECT id INTO new_pupil_id FROM pupil WHERE email = (SELECT email FROM unconfirmed_pupil WHERE unconfirmed_pupil_id = id);

    UPDATE group_member SET pupil_id = new_pupil_id WHERE id = NEW.id;
END IF;
END IF;
END$$

DELIMITER ;





DELIMITER $$

CREATE TRIGGER unconfirmed_pupil_deletion_after_registration
    AFTER INSERT ON pupil
    FOR EACH ROW
BEGIN
    DELETE FROM unconfirmed_pupil WHERE code = NEW.code;
    END$$

    DELIMITER ;






DELIMITER $$

    CREATE TRIGGER set_group_member_link_after_registration
        AFTER INSERT ON pupil
        FOR EACH ROW
    BEGIN
        DECLARE member_id integer;

        SELECT id INTO member_id FROM group_member WHERE code = NEW.code;

        IF member_id IS NOT null THEN
        UPDATE group_member SET pupil_id = NEW.id WHERE id = member_id;
    END IF;
    END$$

    DELIMITER ;





DELIMITER $$

    CREATE TRIGGER unconfirmed_pupil_registration_check
        BEFORE INSERT ON unconfirmed_pupil
        FOR EACH ROW
    BEGIN
        DECLARE pupil_id integer;

        SELECT id INTO pupil_id FROM pupil WHERE email = NEW.email;
        IF pupil_id IS NOT null THEN
    	SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A user with this email is already registered!';
        ELSE
        SELECT id INTO pupil_id FROM pupil WHERE code = NEW.code AND institution_id = NEW.institution_id;
        IF pupil_id IS NOT null THEN
        	SIGNAL SQLSTATE '45000'
        	SET MESSAGE_TEXT = 'The user in the selected educational institution with the specified code is already registered!';
    END IF;
END IF;
END$$

DELIMITER ;





DELIMITER $$

CREATE TRIGGER group_member_exists_check
    BEFORE INSERT ON group_member
    FOR EACH ROW
BEGIN
    DECLARE institution_id integer;
    DECLARE group_member_id integer;

    SELECT educational_intitution.id INTO institution_id FROM institution_group
                                                                  JOIN speciality ON institution_group.speciality_id = speciality.id
                                                                  JOIN faculty ON speciality.faculty_id = faculty.id
                                                                  JOIN educational_intitution ON faculty.institution_id = educational_intitution.id
    WHERE institution_group.id = NEW.group_id
        LIMIT 1;

    SELECT group_member.id INTO group_member_id FROM group_member
                                                         JOIN institution_group ON group_member.group_id = institution_group.id
                                                         JOIN speciality ON institution_group.speciality_id = speciality.id
                                                         JOIN faculty ON speciality.faculty_id = faculty.id
                                                         JOIN educational_intitution ON faculty.institution_id = educational_intitution.id
    WHERE group_member.code = NEW.code
      AND educational_intitution.id = institution_id
        LIMIT 1;

    IF group_member_id IS NOT null THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'A student with this code already exists in another group!';
END IF;
END$$

DELIMITER ;