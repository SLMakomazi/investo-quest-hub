-- Sample data (mirrors backend/src/main/resources/data.sql)
INSERT INTO investor (first_name, last_name, email, age) VALUES
  ('Thabo',  'Mokoena',  'thabo@example.com', 70),
  ('Aisha',  'Patel',    'aisha@example.com', 45),
  ('Sipho',  'Dlamini',  'sipho@example.com', 30);

INSERT INTO portfolio (investor_id, name) VALUES
  (1, 'Thabo Retirement Portfolio'),
  (2, 'Aisha Growth Portfolio'),
  (3, 'Sipho Savings Portfolio');

INSERT INTO product (portfolio_id, type, name, balance) VALUES
  (1, 'RETIREMENT', 'Pension Fund A',   500000.00),
  (1, 'SAVINGS',    'Money Market',      80000.00),
  (2, 'RETIREMENT', 'Pension Fund B',   250000.00),
  (2, 'SAVINGS',    'Unit Trust',       120000.00),
  (3, 'SAVINGS',    'Tax-Free Savings',  50000.00);
