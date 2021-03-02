package org.example.authserver.repo.redis;

import authserver.acl.Acl;
import authserver.acl.AclRelationConfig;
import lombok.extern.slf4j.Slf4j;
import org.example.authserver.repo.SubscriptionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

@Slf4j
@Repository
@ConditionalOnProperty(value = "app.subscription", havingValue = "REDIS")
public class RedisSubscriptionRepositoryImpl implements SubscriptionRepository {

    private static final String PUBSUB_ACL = "pubsub_acl";
    private static final String PUBSUB_CONFIG = "pubsub_config";

    private final JedisPool jedis;

    public RedisSubscriptionRepositoryImpl(JedisPool jedis) {
        this.jedis = jedis;
    }

    @Override
    public void publish(Acl acl) {
        Jedis conn = jedis.getResource();
        conn.publish("pubsub_acl", acl.getId().toString());
        conn.close();
    }

    @Override
    public void publish(AclRelationConfig config) {
        Jedis conn = jedis.getResource();
        conn.publish("pubsub_config", config.getId().toString());
        conn.close();
    }

    @Override
    public Flux<String> subscribeAcl() {
        return Flux.create(sink -> {
            Jedis conn = jedis.getResource();
            conn.subscribe(new AclListener(sink), PUBSUB_ACL);
        });
    }

    @Override
    public Flux<String> subscribeConfig() {
        return Flux.create(sink -> {
            Jedis conn = jedis.getResource();
            conn.subscribe(new AclListener(sink), PUBSUB_CONFIG);
        });
    }

    private static class AclListener extends JedisPubSub {

        private final FluxSink<String> fluxSink;

        public AclListener(FluxSink<String> fluxSink) {
            this.fluxSink = fluxSink;
        }

        @Override
        public void onMessage(String channel, String message) {
            super.onMessage(channel, message);
            fluxSink.next(message);
        }
    }
}
