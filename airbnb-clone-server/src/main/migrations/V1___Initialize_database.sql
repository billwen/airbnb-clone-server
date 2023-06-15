CREATE TABLE account
(
    id                BIGINT AUTO_INCREMENT,
    no_expired        BOOLEAN      NOT NULL,
    no_locked         BOOLEAN      NOT NULL,
    created_at        TIMESTAMP(6) NOT NULL,
    no_passwd_expired BOOLEAN      NOT NULL,
    email             VARCHAR(128),
    enabled           BOOLEAN      NOT NULL,
    jwt_key           VARCHAR(128),
    hashed_passwd     VARCHAR(128),
    updated_at        TIMESTAMP(6) NOT NULL,
    username          VARCHAR(64)  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE oauth_profile
(
    id             BIGINT AUTO_INCREMENT,
    access_key     VARCHAR(1024),
    avatar         VARCHAR(256),
    created_at     TIMESTAMP(6) NOT NULL,
    oauth_sub      VARCHAR(128),
    oauth_username VARCHAR(128) NOT NULL,
    person_name    VARCHAR(128),
    provider       VARCHAR(32)  NOT NULL,
    refresh_key    VARCHAR(1024),
    scope          VARCHAR(512),
    updated_at     TIMESTAMP(6) NOT NULL,
    account_id     BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE role
(
    id         BIGINT AUTO_INCREMENT,
    role_name  VARCHAR(64)  NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE int_accounts_roles
(
    account_id BIGINT NOT NULL,
    role_id    BIGINT NOT NULL,
    PRIMARY KEY (account_id, role_id)
);

ALTER TABLE account
    ADD CONSTRAINT uq_account_username UNIQUE (username);

ALTER TABLE role
    ADD CONSTRAINT uq_role_role_name UNIQUE (role_name);

ALTER TABLE int_accounts_roles
    ADD CONSTRAINT fk_accounts_roles_role_id FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE int_accounts_roles
    ADD CONSTRAINT fk_accounts_roles_account_id FOREIGN KEY (account_id) REFERENCES account (id);

ALTER TABLE oauth_profile
    ADD CONSTRAINT fk_oauth_profile_account_id FOREIGN KEY (account_id) REFERENCES account (id);
