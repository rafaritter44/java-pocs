package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

class App {
    record ClicksByRegion(String region, long clicks) {}

    private static final String BOOTSTRAP_SERVERS = "broker:9092";

    private static final String TEXT_LINES_TOPIC = "TextLinesTopic";
    private static final String WORD_COUNTS_TOPIC = "WordCountsTopic";

    private static final String USER_CLICKS_TOPIC = "UserClicksTopic";
    private static final String USER_REGIONS_TOPIC = "UserRegionsTopic";
    private static final String CLICKS_BY_REGION_TOPIC = "ClicksByRegionTopic";

    void main() {
        createTopics();
        startStreams();
        produceMessages();
        consumeMessages();
    }

    private void startStreams() {
        IO.println("Starting streams...");
        var props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        var builder = new StreamsBuilder();

        KStream<String, String> textLines = builder.stream(TEXT_LINES_TOPIC);
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((_, word) -> word)
                .count(Materialized.as("counts-store"));
        wordCounts.toStream().to(WORD_COUNTS_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        KStream<String, Long> userClicks = builder.stream(USER_CLICKS_TOPIC, Consumed.with(Serdes.String(), Serdes.Long()));
        KTable<String, String> userRegions = builder.table(USER_REGIONS_TOPIC);
        KTable<String, Long> clicksByRegion = userClicks
                .leftJoin(userRegions, (clicks, region) ->
                        new ClicksByRegion(region == null ? "UNKNOWN" : region, clicks))
                .map((_, cbr) -> KeyValue.pair(cbr.region, cbr.clicks))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                .reduce(Long::sum);
        clicksByRegion.toStream().to(CLICKS_BY_REGION_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        var streams = new KafkaStreams(builder.build(), props);
        streams.start();
        IO.println("Streams started");
    }

    private void produceMessages() {
        IO.println("Starting to produce messages...");
        var props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            Consumer<String> sendMessage = message -> {
                ProducerRecord<String, String> record = new ProducerRecord<>(TEXT_LINES_TOPIC, message);
                producer.send(record, (_, e) -> {
                    if (e == null) {
                        IO.println("Produced message: " + message);
                    } else {
                        IO.println("Failed to produce message: " + e.getMessage());
                    }
                });
            };
            sendMessage.accept("hello world");
            sendMessage.accept("hello kafka");
            sendMessage.accept("hello kafka streams");
            producer.flush();
        }

        try (KafkaProducer<String, Long> producer = new KafkaProducer<>(props)) {
            BiConsumer<String, Long> sendMessage = (key, val) -> {
                ProducerRecord<String, Long> record = new ProducerRecord<>(USER_CLICKS_TOPIC, key, val);
                producer.send(record, (_, e) -> {
                    if (e == null) {
                        IO.println("Produced message: " + key + "=" + val);
                    } else {
                        IO.println("Failed to produce message: " + e.getMessage());
                    }
                });
            };
            sendMessage.accept("alice", 13L);
            sendMessage.accept("bob", 4L);
            sendMessage.accept("charlie", 25L);
            sendMessage.accept("bob", 19L);
            sendMessage.accept("david", 56L);
            sendMessage.accept("eve", 78L);
            sendMessage.accept("alice", 40L);
            sendMessage.accept("frank", 99L);
            producer.flush();
        }

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            BiConsumer<String, String> sendMessage = (key, val) -> {
                ProducerRecord<String, String> record = new ProducerRecord<>(USER_REGIONS_TOPIC, key, val);
                producer.send(record, (_, e) -> {
                    if (e == null) {
                        IO.println("Produced message: " + key + "=" + val);
                    } else {
                        IO.println("Failed to produce message: " + e.getMessage());
                    }
                });
            };
            sendMessage.accept("alice", "asia");
            sendMessage.accept("bob", "americas");
            sendMessage.accept("charlie", "asia");
            sendMessage.accept("david", "europe");
            sendMessage.accept("alice", "europe");
            sendMessage.accept("eve", "americas");
            sendMessage.accept("frank", "asia");
            producer.flush();
        }

        IO.println("Finished producing messages");
    }

    private void consumeMessages() {
        IO.println("Starting to consume messages...");
        var props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "print-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());

        try (KafkaConsumer<String, Long> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(List.of(WORD_COUNTS_TOPIC, CLICKS_BY_REGION_TOPIC));

            while (true) {
                consumer.poll(Duration.ofMillis(100))
                        .forEach(record ->
                                IO.println("Consumed message: " + record.key() + "=" + record.value()));
            }
        }
    }

    private void createTopics() {
        IO.println("Creating topics...");
        var props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        try (var adminClient = AdminClient.create(props)) {
            var numPartitions = 3;
            short replicationFactor = 1;

            var textLinesTopic = new NewTopic(TEXT_LINES_TOPIC, numPartitions, replicationFactor);
            var wordCountsTopic = new NewTopic(WORD_COUNTS_TOPIC, numPartitions, replicationFactor);

            var userClicksTopic = new NewTopic(USER_CLICKS_TOPIC, numPartitions, replicationFactor);
            var userRegionsTopic = new NewTopic(USER_REGIONS_TOPIC, numPartitions, replicationFactor);
            var clicksByRegionTopic = new NewTopic(CLICKS_BY_REGION_TOPIC, numPartitions, replicationFactor);

            var topics = List.of(textLinesTopic, wordCountsTopic, userClicksTopic, userRegionsTopic, clicksByRegionTopic);

            adminClient.createTopics(topics).all().get();
            IO.println("Topics created");
        } catch (Exception e) {
            IO.println("Failed to create topics: " + e.getMessage());
        }
    }
}
