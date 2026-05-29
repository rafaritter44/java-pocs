package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

class App {
    private static final String BOOTSTRAP_SERVERS = "broker:9092";
    private static final String TEXT_LINES_TOPIC = "TextLinesTopic";
    private static final String WORDS_WITH_COUNTS_TOPIC = "WordsWithCountsTopic";

    void main() {
        createTopics();
        startStreams();
        produceMessages();
        consumeMessages();
    }

    private void startStreams() {
        IO.println("Starting streams");
        var props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        var builder = new StreamsBuilder();
        KStream<String, String> textLines = builder.stream(TEXT_LINES_TOPIC);
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(textLine -> Arrays.asList(textLine.toLowerCase().split("\\W+")))
                .groupBy((_, word) -> word)
                .count(Materialized.as("counts-store"));
        wordCounts.toStream().to(WORDS_WITH_COUNTS_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));

        var streams = new KafkaStreams(builder.build(), props);
        streams.start();
        IO.println("Streams started");
    }

    private void produceMessages() {
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
    }

    private void consumeMessages() {
        var props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "print-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(List.of(WORDS_WITH_COUNTS_TOPIC));

            while (true) {
                consumer.poll(Duration.ofMillis(100))
                        .forEach(record -> IO.println("Consumed message: " + record.value()));
            }
        }
    }

    private void createTopics() {
        IO.println("Creating topics");
        var props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        try (var adminClient = AdminClient.create(props)) {
            var numPartitions = 3;
            short replicationFactor = 1;
            var textLinesTopic = new NewTopic(TEXT_LINES_TOPIC, numPartitions, replicationFactor);
            var wordsWithCountsTopic = new NewTopic(WORDS_WITH_COUNTS_TOPIC, numPartitions, replicationFactor);
            adminClient.createTopics(List.of(textLinesTopic, wordsWithCountsTopic)).all().get();
            IO.println("Topics created");
        } catch (Exception e) {
            IO.println("Failed to create topics: " + e.getMessage());
        }
    }
}
