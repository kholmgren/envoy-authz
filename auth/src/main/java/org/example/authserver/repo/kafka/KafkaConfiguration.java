package org.example.authserver.repo.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@ConditionalOnProperty(value = "app.subscription", havingValue = "KAFKA")
@RequiredArgsConstructor
public class KafkaConfiguration {
    private static final String PUBSUB_ACL = "pubsub_acl";
    private static final String PUBSUB_CONFIG = "pubsub_config";

//    @Bean
//    public KafkaAdmin admin() {
//        Map<String, Object> configs = new HashMap<>();
////        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, ...);
//        return new KafkaAdmin(configs);
//    }

//    @Value("${spring.kafka.bootstrap-servers}")
//    private String bootstrapAddress;
//
//    @Value("${spring.kafka.consumer.group-id}")
//    private String groupId;
//
//    @Bean
//    public ProducerFactory<String, String> producerFactory() {
//        Map<String, Object> configProps = new HashMap<>();
//        configProps.put(
//            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            bootstrapAddress);
//        configProps.put(
//            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//            StringSerializer.class);
//        configProps.put(
//            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//            StringSerializer.class);
//        return new DefaultKafkaProducerFactory<>(configProps);
//    }
//
//    @Bean
//    public KafkaTemplate<String, String> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ConsumerFactory<String, String> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(
//            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//            bootstrapAddress);
//        props.put(
//            ConsumerConfig.GROUP_ID_CONFIG,
//            groupId);
//        props.put(
//            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//            StringDeserializer.class);
//        props.put(
//            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//            StringDeserializer.class);
//
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }


//    @Bean
//    public NewTopic pubsubAclTopic() {
//        return TopicBuilder.name(PUBSUB_ACL)
////            .partitions(10)
////            .replicas(3)
////            .compact()
//            .build();
//    }
//
//    @Bean
//    public NewTopic pubsubConfigTopic() {
//        return TopicBuilder.name(PUBSUB_CONFIG)
////            .partitions(10)
////            .replicas(3)
////            .compact()
//            .build();
//    }
}
