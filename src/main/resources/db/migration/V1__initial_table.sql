CREATE TABLE user
(
    id         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    update_at  TIMESTAMP
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
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
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