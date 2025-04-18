CREATE PROCEDURE insert_dummy_data()
BEGIN
    DECLARE
        i INT DEFAULT 1;
    DECLARE
        user_id BIGINT;

    WHILE
        i <= 100000
        DO
            INSERT INTO user (created_at, updated_at)
            VALUES (NOW(), NULL);

            SET
                user_id = LAST_INSERT_ID();

            INSERT INTO consumption_goal (user_id, category_id, cap, goal_at, created_at, updated_at)
            VALUES (user_id, 1, 10000, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 2, 10001, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 3, 10002, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 4, 10003, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 5, 10004, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 6, 10005, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 7, 10006, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 8, 10007, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 9, 10008, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL),
                   (user_id, 10, 10009, DATE_FORMAT(CURDATE() - INTERVAL 1 MONTH, '%Y-%m-01'), NOW(), NULL);

            SET
                i = i + 1;
        END WHILE;
END;