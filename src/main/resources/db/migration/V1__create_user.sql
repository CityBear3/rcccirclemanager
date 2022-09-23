CREATE TABLE IF NOT EXISTS users
(
    id         UUID                     DEFAULT gen_random_uuid() NOT NULL
        PRIMARY KEY,
    email      VARCHAR(255)                                       NOT NULL
        UNIQUE,
    first_name VARCHAR(255)                                       NOT NULL,
    last_name  VARCHAR(255)                                       NOT NULL,
    password   VARCHAR(255)                                       NOT NULL,
    is_admin   BOOLEAN                  DEFAULT FALSE             NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

