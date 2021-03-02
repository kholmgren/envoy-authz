#!/bin/bash
docker-compose down
mvn clean install

docker-compose build

docker-compose up -d -V --no-deps cassandra
sleep 30
docker-compose run cassandra cqlsh cassandra -u cassandra -p cassandra -e "CREATE KEYSPACE IF NOT EXISTS authz WITH replication= {'class':'SimpleStrategy', 'replication_factor':1};"

docker-compose up -d -V --no-deps zookeeper kafka
sleep 30
docker-compose run kafka /opt/bitnami/kafka/bin/kafka-topics.sh \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic pubsub_acl
docker-compose run kafka /opt/bitnami/kafka/bin/kafka-topics.sh \
  --create \
  --bootstrap-server localhost:9092 \
  --replication-factor 1 \
  --partitions 1 \
  --topic pubsub_config

docker-compose up -d
sleep 15

python3 test.py

echo "NOTE: if all tests fail or do not proceed , it is likely that cassandra is not ready / slow - check the cassandra container logs and then try once it looks stable"
