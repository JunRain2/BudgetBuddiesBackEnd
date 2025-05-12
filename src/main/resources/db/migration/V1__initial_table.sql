CREATE TABLE user
(
    id         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP          DEFAULT NULL
);

CREATE TABLE category
(
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT,
    type       VARCHAR(10) NOT NULL,
    name       VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP            DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT ux_category_user_name UNIQUE (user_id, name)
);

CREATE TABLE consumption_goal
(
    id          BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    category_id BIGINT    NOT NULL,
    cap         INT       NOT NULL,
    goal_month  CHAR(7)   NOT NULL, -- 형식: YYYY-MM
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP          DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT ux_goal_user_category_month UNIQUE (user_id, category_id, goal_month)
);

CREATE TABLE expense
(
    id          BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT       NOT NULL,
    category_id BIGINT       NULL,
    amount      INT          NOT NULL,
    description VARCHAR(100) NOT NULL,
    expense_at  DATE         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP             DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (category_id) REFERENCES category (id)
);


-- 기본 카테고리 초기 데이터
INSERT INTO category (id, user_id, type, name, created_at)
VALUES (1, NULL, 'DEFAULT', '기본 카테고리1', NOW()),
       (2, NULL, 'DEFAULT', '기본 카테고리2', NOW()),
       (3, NULL, 'DEFAULT', '기본 카테고리3', NOW()),
       (4, NULL, 'DEFAULT', '기본 카테고리4', NOW()),
       (5, NULL, 'DEFAULT', '기본 카테고리5', NOW()),
       (6, NULL, 'DEFAULT', '기본 카테고리6', NOW()),
       (7, NULL, 'DEFAULT', '기본 카테고리7', NOW()),
       (8, NULL, 'DEFAULT', '기본 카테고리8', NOW()),
       (9, NULL, 'DEFAULT', '기본 카테고리9', NOW()),
       (10, NULL, ' DEFAULT', '기본 카테고리10', NOW());