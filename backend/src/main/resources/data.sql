-- Sample investors
INSERT INTO investor (id, first_name, last_name, email, age) VALUES (1, 'Thabo',   'Mokoena',  'thabo@example.com',   70);
INSERT INTO investor (id, first_name, last_name, email, age) VALUES (2, 'Aisha',   'Patel',    'aisha@example.com',   45);
INSERT INTO investor (id, first_name, last_name, email, age) VALUES (3, 'Sipho',   'Dlamini',  'sipho@example.com',   30);
INSERT INTO investor (id, first_name, last_name, email, age) VALUES (4, 'Siseko',  'Makomazi', 'siseko@example.com',  25);
INSERT INTO investor (id, first_name, last_name, email, age) VALUES (5, 'Nomsa',   'Ngubane',  'nomsa@example.com',   35);

-- Portfolios (one per investor)
INSERT INTO portfolio (id, investor_id, name) VALUES (1, 1, 'Thabo Retirement Portfolio');
INSERT INTO portfolio (id, investor_id, name) VALUES (2, 2, 'Aisha Growth Portfolio');
INSERT INTO portfolio (id, investor_id, name) VALUES (3, 3, 'Sipho Savings Portfolio');
INSERT INTO portfolio (id, investor_id, name) VALUES (4, 4, 'Siseko Savings Portfolio');
INSERT INTO portfolio (id, investor_id, name) VALUES (5, 5, 'Nomsa Investment Portfolio');

-- Products
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (1, 1, 'RETIREMENT', 'Pension Fund A',     500000.00);
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (2, 1, 'SAVINGS',    'Money Market',        80000.00);
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (3, 2, 'RETIREMENT', 'Pension Fund B',     250000.00);
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (4, 2, 'SAVINGS',    'Unit Trust',         120000.00);
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (5, 3, 'SAVINGS',    'Tax-Free Savings',    50000.00);
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (6, 4, 'SAVINGS',    'Emergency Fund',     25000.00);
INSERT INTO product (id, portfolio_id, type, name, balance) VALUES (7, 5, 'SAVINGS',    'Stock Portfolio',    75000.00);
