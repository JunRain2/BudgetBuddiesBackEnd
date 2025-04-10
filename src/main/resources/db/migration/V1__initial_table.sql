CREATE TABLE user
(
    id         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE category
(
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT,
    is_default BOOLEAN     NOT NULL,
    created_at TIMESTAMP   NOT NULL,
    updated_at TIMESTAMP,
    name       VARCHAR(20) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE consumption_goal
(
    id          BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    category_id BIGINT    NOT NULL,
    cap         INT       NOT NULL,
    goal_at     DATE      NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE,
    CONSTRAINT ux_consumption_goal_user_category_month
        UNIQUE (user_id, category_id, goal_at)
);

CREATE TABLE expense
(
    id          BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    category_id BIGINT,
    amount      INT         NOT NULL,
    description VARCHAR(30) NOT NULL,
    expense_at  DATE        NOT NULL,
    created_at  TIMESTAMP   NOT NULL,
    updated_at  TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE SET NULL
);

INSERT INTO category
VALUES (1, null, true, NOW(), null, '카테고리 없음'),
       (2, null, true, NOW(), null, '기본 카테고리1'),
       (3, null, true, NOW(), null, '기본 카테고리2'),
       (4, null, true, NOW(), null, '기본 카테고리3'),
       (5, null, true, NOW(), null, '기본 카테고리4'),
       (6, null, true, NOW(), null, '기본 카테고리5'),
       (7, null, true, NOW(), null, '기본 카테고리6'),
       (8, null, true, NOW(), null, '기본 카테고리7'),
       (9, null, true, NOW(), null, '기본 카테고리8'),
       (10, null, true, NOW(), null, '기본 카테고리9');
