package me.infinity.arris;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.Getter;
import me.infinity.arris.adapter.ArrisAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Getter
public class Arris {

  private final ExecutorService executorService;
  private final Gson gson = new Gson();
  private final Logger logger = LoggerFactory.getLogger(Arris.class);
  private final List<ArrisAdapter<?>> adapters;

  private final JedisPool jedisPool;

  public Arris(ArrisCredentials credentials) {
    this.adapters = new ArrayList<>();
    this.executorService = Executors.newFixedThreadPool(2);

    // Configure pool config
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(100);
    poolConfig.setMaxIdle(50);
    poolConfig.setMinIdle(10);
    poolConfig.setTestOnBorrow(true);

    // Connect and Authenticate jedis pool
    this.jedisPool = new JedisPool(
        poolConfig,
        credentials.getAddress(),
        credentials.getPort(),
        credentials.isSsl()
    );
    // Try to authenticate with password
    if (credentials.authenticationEnabled()) jedisPool.getResource().auth(credentials.getPassword());

    // Check if the connection is alive and works.
    if (Objects.equals(jedisPool.getResource().ping(), "PONG") && jedisPool.getResource().isConnected()) {
      this.logger.info("Bingo! Established connection to redis server successfully.");
    } else {
      throw new RuntimeException(
          "Failed to connect redis server, please recheck credentials. " + credentials);
    }
  }

  public void register(ArrisAdapter<?> adapter) {
    this.adapters.add(adapter);
  }

}
