# Kafka Streams

## What is Kafka Streams?

- A Java/Scala library.
- A stream processing engine.
- An API for transforming data stored in Kafka topics.

## POCs

### Word count

Input:
```
app-1  | Produced message: hello world
app-1  | Produced message: hello kafka
app-1  | Produced message: hello kafka streams
```


Output:
```
app-1  | Consumed message: hello=3
app-1  | Consumed message: world=1
app-1  | Consumed message: kafka=2
app-1  | Consumed message: streams=1
```

### Clicks by region

Input:
```
app-1  | Produced message: alice=asia
app-1  | Produced message: bob=americas
app-1  | Produced message: charlie=asia
app-1  | Produced message: david=europe
app-1  | Produced message: alice=europe
app-1  | Produced message: eve=americas
app-1  | Produced message: frank=asia
```
```
app-1  | Produced message: alice=13
app-1  | Produced message: bob=4
app-1  | Produced message: charlie=25
app-1  | Produced message: bob=19
app-1  | Produced message: david=56
app-1  | Produced message: eve=78
app-1  | Produced message: alice=40
app-1  | Produced message: frank=99
```

Output:
```
app-1  | Consumed message: americas=101
app-1  | Consumed message: europe=109
app-1  | Consumed message: asia=124
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