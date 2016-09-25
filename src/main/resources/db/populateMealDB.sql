DELETE FROM meals;

ALTER SEQUENCE global_meal_seq RESTART WITH 1000;

INSERT INTO meals (
user_id,
dateTime,
description,
calories )
VALUES ('100000', '2016-09-20 10:00:00' , 'Завтрак', '500'),
  ('100000','2016-09-20 19:00:00' , 'Ужин', '500'),
  ('100000','2016-09-20 14:00:00' , 'Обед', '1000'),
  ('100001', '2016-09-20 10:00:00' , 'Завтрак', '500'),
  ('100001','2016-09-20 19:00:00' , 'Ужин', '500'),
  ('100001','2016-09-20 14:00:00' , 'Обед', '1010');



