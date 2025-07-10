#  Remove-Item -Recurse -Force C:\tmp\kraft-combined-logs\

```shell
 cd C:\kafka\kafka_2.13-4.0.0
```

```shell
#Создаём CLUSTER ID (на одном узле):
 $env:KAFKA_CLUSTER_ID = & .\bin\windows\kafka-storage.bat random-uuid
```

```shell
#Форматируем log каталог
 .\bin\windows\kafka-storage.bat format -t $env:KAFKA_CLUSTER_ID -c config\server.properties
```

```shell
#Запускаем Kafka брокер без доп конфигураций
 .\bin\windows\kafka-server-start.bat config\server.properties
 # Остановка локально ^C
```

```shell
#Создаём CLUSTER ID (на одном узле):
 $env:KAFKA_CLUSTER_ID = & .\bin\windows\kafka-storage.bat random-uuid
```

```shell
#Форматируем log каталог
 .\bin\windows\kafka-storage.bat format -t $env:KAFKA_CLUSTER_ID -c C:\otus\kafka\kafka_homework\hw2\config\server.properties
```

```shell
# Формируем общий properties с sasl и acl (kafka_server_jaas.conf прежний)
$env:KAFKA_OPTS = "-Djava.security.auth.login.config=C:\otus\kafka\kafka_homework\hw2\config\kafka_server_jaas.conf"
.\bin\windows\kafka-server-start.bat C:\otus\kafka\kafka_homework\hw2\config\server.properties
# Остановка локально ^C
```

```shell
.\bin\windows\kafka-topics.bat --create --topic test --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1 --command-config C:\otus\kafka\kafka_homework\hw2\config\client-sasl-plain.properties
```

```shell
#Выдаем права записи для Alex
.\bin\windows\kafka-acls.bat --bootstrap-server localhost:9092 --command-config C:\otus\kafka\kafka_homework\hw2\config\client-sasl-plain.properties --add --allow-principal "User:Alex" --operation Write --topic test
```

```shell
#Выдаем права чтения для Sofi
.\bin\windows\kafka-acls.bat --bootstrap-server localhost:9092 --command-config C:\otus\kafka\kafka_homework\hw2\config\client-sasl-plain.properties --add --allow-principal "User:Sofi" --operation Read --topic test
```

```shell
#Запрещаем все для Lu
.\bin\windows\kafka-acls.bat --bootstrap-server localhost:9092 --command-config C:\otus\kafka\kafka_homework\hw2\config\client-sasl-plain.properties --add --deny-principal "User:Lu" --operation All --topic test
```

```shell
# Просмотр (+), запись (+), чтение (-) для Alex
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list --command-config C:\otus\kafka\kafka_homework\hw2\config\client-alex-plain.properties
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test --producer.config C:\otus\kafka\kafka_homework\hw2\config\client-alex-plain.properties
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning --consumer.config C:\otus\kafka\kafka_homework\hw2\config\client-alex-plain.properties
```
```shell
# Просмотр (+), запись (-), чтение (+) для Sofi
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list --command-config C:\otus\kafka\kafka_homework\hw2\config\client-sofi-plain.properties
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test --producer.config C:\otus\kafka\kafka_homework\hw2\config\client-sofi-plain.properties
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning --consumer.config C:\otus\kafka\kafka_homework\hw2\config\client-sofi-plain.properties
```
```shell
# Просмотр (-), запись (-), чтение (-) для Lu
.\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --list --command-config C:\otus\kafka\kafka_homework\hw2\config\client-lu-plain.properties
.\bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic test --producer.config C:\otus\kafka\kafka_homework\hw2\config\client-lu-plain.properties
.\bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning --consumer.config C:\otus\kafka\kafka_homework\hw2\config\client-lu-plain.properties
```




```shell
#Посмотреть содержимое meta.properties (содержит в т.ч. node.id, directory.id, version, cluster.id)
 cd C:\tmp
 type .\kraft-combined-logs\meta.properties 
```
```shell
#Проверка Cluster ID
bin/windows/kafka-cluster.bat cluster-id --bootstrap-server kafka:9092
```