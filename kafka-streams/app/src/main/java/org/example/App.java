package org.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;

class App {
    void main() {
        IO.println("Creating topic");
        var props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "broker:9092");
        try (var adminClient = AdminClient.create(props)) {
            var numPartitions = 3;
            short replicationFactor = 1;
            var newTopic = new NewTopic("my-new-topic", numPartitions, replicationFactor);
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
            IO.println("Topic created");
        } catch (Exception e) {
            IO.println("Topic not created: " + e.getMessage());
        }
    }
}
