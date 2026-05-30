# Kafka Streams

## What is Kafka Streams?

- A Java/Scala library.
- A stream processing engine.
- An API for transforming data stored in Kafka topics.

## POC

```
streams-app-1  | Creating topics...
streams-app-1  | SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
streams-app-1  | SLF4J: Defaulting to no-operation (NOP) logger implementation
streams-app-1  | SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
streams-app-1  | Topics created
streams-app-1  | Starting streams...
streams-app-1  | Streams started
streams-app-1  | Starting to produce messages...
streams-app-1  | Produced message: hello world
streams-app-1  | Produced message: hello kafka
streams-app-1  | Produced message: hello kafka streams
streams-app-1  | Produced message: frank=asia
streams-app-1  | Produced message: alice=asia
streams-app-1  | Produced message: bob=americas
streams-app-1  | Produced message: charlie=asia
streams-app-1  | Produced message: david=europe
streams-app-1  | Produced message: alice=europe
streams-app-1  | Produced message: eve=americas
streams-app-1  | Produced message: eve=78
streams-app-1  | Produced message: alice=13
streams-app-1  | Produced message: bob=4
streams-app-1  | Produced message: charlie=25
streams-app-1  | Produced message: bob=19
streams-app-1  | Produced message: david=56
streams-app-1  | Produced message: alice=40
streams-app-1  | Produced message: frank=99
streams-app-1  | Finished producing messages
streams-app-1  | Starting to consume messages...
streams-app-1  | WARNING: A restricted method in java.lang.System has been called
streams-app-1  | WARNING: java.lang.System::loadLibrary has been called by org.rocksdb.RocksDB in an unnamed module (file:/opt/app/app.jar)
streams-app-1  | WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
streams-app-1  | WARNING: Restricted methods will be blocked in a future release unless native access is enabled
streams-app-1  | 
streams-app-1  | Consumed message: hello=3
streams-app-1  | Consumed message: asia=124
streams-app-1  | Consumed message: europe=109
streams-app-1  | Consumed message: americas=101
streams-app-1  | Consumed message: world=1
streams-app-1  | Consumed message: kafka=2
streams-app-1  | Consumed message: streams=1
```

## Competitors

| Criterion | Kafka Streams | Spark and Flink      |
| --------- | ------------- | -------------------- |
|           | Java library  | Master-slave cluster |
|           |               |                      |
|           |               |                      |
|           |               |                      |
|           |               |                      |

Stream and batch processing vs. Stream processing only

Require an external file system like S3 or HDFS

Multiple sources and sinks vs. Limited to Kafka (requires Kafka Connect for integration with other systems)

Language support:
Spark: Java, Scala, Python, R, SQL
Flink: Java, Scala, Python, SQL
Kafka Streams: Java, Scala