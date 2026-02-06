-- V4__create_feedbacks_table.sql

CREATE TABLE IF NOT EXISTS feedbacks (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(200) NOT NULL,
  score INT NOT NULL CHECK (score BETWEEN 0 AND 10),
  lesson_id BIGINT NOT NULL,

  CONSTRAINT fk_feedback_lesson
    FOREIGN KEY (lesson_id)
    REFERENCES lessons(id)
    ON DELETE CASCADE
);