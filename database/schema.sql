-- PostgreSQL schema for Enviro365 (optional alternative to H2)
DROP TABLE IF EXISTS withdrawal CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS portfolio CASCADE;
DROP TABLE IF EXISTS investor CASCADE;

CREATE TABLE investor (
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    age         INT NOT NULL
);

CREATE TABLE portfolio (
    id           BIGSERIAL PRIMARY KEY,
    investor_id  BIGINT NOT NULL REFERENCES investor(id) ON DELETE CASCADE,
    name         VARCHAR(255) NOT NULL
);

CREATE TABLE product (
    id            BIGSERIAL PRIMARY KEY,
    portfolio_id  BIGINT NOT NULL REFERENCES portfolio(id) ON DELETE CASCADE,
    type          VARCHAR(50)  NOT NULL,   -- RETIREMENT | SAVINGS
    name          VARCHAR(255) NOT NULL,
    balance       NUMERIC(18,2) NOT NULL CHECK (balance >= 0)
);

CREATE TABLE withdrawal (
    id           BIGSERIAL PRIMARY KEY,
    investor_id  BIGINT NOT NULL REFERENCES investor(id),
    product_id   BIGINT NOT NULL REFERENCES product(id),
    amount       NUMERIC(18,2) NOT NULL CHECK (amount > 0),
    created_at   TIMESTAMP NOT NULL DEFAULT NOW(),
    status       VARCHAR(50) NOT NULL DEFAULT 'APPROVED',
    rejection_reason TEXT
);

CREATE INDEX idx_withdrawal_investor ON withdrawal(investor_id);
