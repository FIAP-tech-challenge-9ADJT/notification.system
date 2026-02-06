-- V3__create_lessons_table.sql

CREATE TABLE IF NOT EXISTS lessons (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(200),
  score DECIMAL(2,1) NOT NULL DEFAULT 0.0
);