package vttp.PAFWS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    // @Value("${spring.redis.password}") //TODO change when working LOCAL
    private String redisPassword = "weishunlim";

    @Value("${spring.redis.database}")
    private int redisDB;

    @Bean(name = "redisconfig")
    @Scope("singleton")
    public RedisTemplate<String, String> redisConfig(){
        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName(redisHost);
        config.setPassword(redisPassword);
        config.setPort(redisPort);
        config.setDatabase(redisDB);

        final JedisClientConfiguration jedisClient = JedisClientConfiguration.builder().build();
        final JedisConnectionFactory jedisfac = new JedisConnectionFactory(config, jedisClient);
        jedisfac.afterPropertiesSet();

        RedisTemplate<String, String> template = new RedisTemplate<String, String>();
        template.setConnectionFactory(jedisfac);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());  
        return template;
    }
}
