Установка JDK не производилась в текущий момент, установлено ранее

Шаги по запуску Kafka на Windows:
1. Скачать Kafka с сайта https://kafka.apache.org/downloads версию 3.7.0  ->  kafka_2.13-3.7.0.tgz (asc, sha512) 
2. Перейти в папку  -> cd C:\kafka\kafka_2.13-3.7.0 
3. Запустить Zookeeper -> .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
4. Запустить Kafka Broker -> .\bin\windows\kafka-server-start.bat .\config\server.properties
5. Создание топика test -> .\bin\windows\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092
5.1 Посмотреть инфу о топике .\bin\windows\kafka-topics.bat --describe --topic test --bootstrap-server localhost:9092
6. Добавление сообщений в топик -> .\bin\windows\kafka-console-producer.bat --topic test --bootstrap-server localhost:9092
> message  -> жмем Enter
> message  -> Enter
Ctrl+C для выхода, но нужно перейти на новую строку Enter для сохранения последнего сообщения
7. Чтение сообщений из топика -> .\bin\windows\kafka-console-consumer.bat --topic test --from-beginning --bootstrap-server localhost:9092