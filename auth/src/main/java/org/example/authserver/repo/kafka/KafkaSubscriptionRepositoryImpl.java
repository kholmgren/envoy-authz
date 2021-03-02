package org.example.authserver.repo.kafka;

import authserver.acl.Acl;
import authserver.acl.AclRelationConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.example.authserver.repo.SubscriptionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Slf4j
@Repository
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.subscription", havingValue = "KAFKA")
public class KafkaSubscriptionRepositoryImpl implements SubscriptionRepository {
    private static final String PUBSUB_ACL = "pubsub_acl";
    private static final String PUBSUB_CONFIG = "pubsub_config";

    private final ConsumerFactory<String, String> consumerFactory;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(Acl acl) {
        kafkaTemplate.send(
            PUBSUB_ACL,
            acl.getId().toString());
    }

    @Override
    public void publish(AclRelationConfig config) {
        kafkaTemplate.send(
            PUBSUB_CONFIG,
            config.getId().toString());
    }

    @Override
    public Flux<String> subscribeAcl() {
        return subscribe(PUBSUB_ACL);
    }

    @Override
    public Flux<String> subscribeConfig() {
        return subscribe(PUBSUB_CONFIG);
    }

    private Flux<String> subscribe(String topicName) {
        return Flux.empty();

//        return Flux.create(sink -> {
//            ContainerProperties containerProps = new ContainerProperties(topicName);
//            containerProps.setMessageListener((MessageListener<String, String>) data -> {
//                log.info("Received {}", data.value());
//                sink.next(data.value());
//            });
//
////        DefaultKafkaConsumerFactory<Integer, String> cf = new DefaultKafkaConsumerFactory<>(Collections.emptyMap());
//            KafkaMessageListenerContainer<String, String> container = new KafkaMessageListenerContainer<>(consumerFactory, containerProps);
//            sink.onDispose(() -> {
//                log.info("Stop {} container", topicName);
//                if (container.isRunning())
//                    container.stop();
//            });
//
//            log.info("Start {} container", topicName);
//            container.start();
//        });
    }

}
