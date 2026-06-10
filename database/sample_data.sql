-- Sample data (mirrors backend/src/main/resources/data.sql)
INSERT INTO investor (first_name, last_name, email, age) VALUES
  ('Thabo',  'Mokoena',  'thabo@example.com', 70),
  ('Aisha',  'Patel',    'aisha@example.com', 45),
  ('Sipho',  'Dlamini',  'sipho@example.com', 30),
  ('Siseko', 'Makomazi', 'siseko@example.com', 25),
  ('Nomsa',  'Ngubane',  'nomsa@example.com', 35);

INSERT INTO portfolio (investor_id, name) VALUES
  (1, 'Thabo Retirement Portfolio'),
  (2, 'Aisha Growth Portfolio'),
  (3, 'Sipho Savings Portfolio'),
  (4, 'Siseko Savings Portfolio'),
  (5, 'Nomsa Investment Portfolio');

INSERT INTO product (portfolio_id, type, name, balance) VALUES
  (1, 'RETIREMENT', 'Pension Fund A',   500000.00),
  (1, 'SAVINGS',    'Money Market',      80000.00),
  (2, 'RETIREMENT', 'Pension Fund B',   250000.00),
  (2, 'SAVINGS',    'Unit Trust',       120000.00),
  (3, 'SAVINGS',    'Tax-Free Savings',  50000.00),
  (4, 'SAVINGS',    'Emergency Fund',    25000.00),
  (5, 'SAVINGS',    'Stock Portfolio',   75000.00);