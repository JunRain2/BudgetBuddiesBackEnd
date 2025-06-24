CREATE TABLE user
(
    id         BINARY(16) NOT NULL PRIMARY KEY,
    created_at TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP           DEFAULT NULL
);

CREATE TABLE category
(
    id         BINARY(16)  NOT NULL PRIMARY KEY,
    user_id    BINARY(16),
    type       VARCHAR(10) NOT NULL,
    name       VARCHAR(20) NOT NULL CHECK (TRIM(name) <> ''),
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP            DEFAULT NULL,
    CONSTRAINT ux_category_user_name UNIQUE (user_id, name)
);

CREATE TABLE consumption_goal
(
    id          BINARY(16) NOT NULL PRIMARY KEY,
    user_id     BINARY(16) NOT NULL,
    category_id BINARY(16) NOT NULL,
    cap         INT        NOT NULL,
    goal_month  CHAR(7)    NOT NULL, -- 형식: YYYY-MM
    created_at  TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP           DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP           DEFAULT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT ux_goal_user_category_month UNIQUE (user_id, category_id, goal_month)
);

CREATE TABLE expense
(
    id          BINARY(16)   NOT NULL PRIMARY KEY,
    user_id     BINARY(16)   NOT NULL,
    category_id BINARY(16)   NULL,
    amount      INT          NOT NULL,
    description VARCHAR(100) NOT NULL,
    expense_at  DATE         NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP             DEFAULT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
);


-- 기본 카테고리 초기 데이터
INSERT INTO category (id, user_id, type, name, created_at)
VALUES (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '카테고리 없음', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리2', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리3', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리4', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리5', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리6', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리7', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리8', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리9', NOW()),
       (UUID_TO_BIN(UUID(), 1), NULL, 'DEFAULT', '기본 카테고리10', NOW());