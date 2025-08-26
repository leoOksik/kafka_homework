Разработка приложения Kafka Streams
Цель:
Разработать приложение Kafka Streams.

Разработка приложения Kafka Streams:
Запустить Kafka
Создать топик events
Разработать приложение, которое подсчитывает количество событий с одинаковыми key в рамках сессии 5 минут
Для проверки отправлять сообщения, используя console producer.


docker-compose exec kafka1 kafka-console-producer \
--topic events \
--bootstrap-server localhost:9191

{"code":"test","message":"testMessage"}

{"code":"test2","message":"testMessage2"}