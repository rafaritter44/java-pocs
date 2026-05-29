package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

class App {
    private static final String BOOTSTRAP_SERVERS = "broker:9092";
    private static final String TEXT_LINES_TOPIC = "TextLinesTopic";
    private static final String WORDS_WITH_COUNTS_TOPIC = "WordsWithCountsTopic";

    void main() {
        createTopics();
        startStreams();
    }

    void startStreams() {
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

    void createTopics() {
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
            IO.println("Topics not created: " + e.getMessage());
        }
    }
}
