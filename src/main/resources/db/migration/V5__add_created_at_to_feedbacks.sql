-- V5__add_created_at_to_feedbacks.sql

ALTER TABLE feedbacks ADD COLUMN created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP;
