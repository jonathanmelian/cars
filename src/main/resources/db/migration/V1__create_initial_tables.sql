CREATE TABLE cars (
  cars_id INT AUTO_INCREMENT PRIMARY KEY,
  brand VARCHAR(255) NOT NULL,
  model VARCHAR(255) NOT NULL,
  color VARCHAR(255) NOT NULL
);
INSERT INTO cars (`cars_id`, brand, model, color)
VALUES (1, 'kia', 'sorento', 'yellow');
