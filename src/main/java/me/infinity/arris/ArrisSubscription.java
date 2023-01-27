package me.infinity.arris;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import me.infinity.arris.adapter.ArrisAdapter;
import redis.clients.jedis.JedisPubSub;

@RequiredArgsConstructor
public class ArrisSubscription extends JedisPubSub {

  private final Arris arris;

  @Override
  public void onMessage(String channel, String message) {
    arris.getExecutorService().submit(() -> {
      List<ArrisAdapter<?>> handlers = arris.getAdapters()
          .stream()
          .filter(adapter -> adapter.getChannel().equals(channel))
          .collect(Collectors.toList());

      handlers.forEach(adapter -> {
        Type superclass = adapter.getClass().getGenericSuperclass();
        Type tType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        adapter.onPacketReceive(arris.getGson().fromJson(message, tType));
      });

    });
  }
}