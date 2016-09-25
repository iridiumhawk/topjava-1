DROP TABLE IF EXISTS meals;
CREATE SEQUENCE global_meal_seq START 1000;

CREATE TABLE meals
(
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_meal_seq'),
  user_id     INTEGER NOT NULL,
  dateTime    TIMESTAMP DEFAULT now(),
  description VARCHAR NOT NULL,
  calories    INTEGER NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);


