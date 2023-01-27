package me.infinity.arris.adapter;

import lombok.Getter;
import me.infinity.arris.Arris;

public abstract class ArrisAdapter<T> {

  private final Arris arris;

  @Getter
  private final String channel;

  public ArrisAdapter(Arris arris, String channel) {
    this.arris = arris;
    this.channel = channel;
  }

  public abstract void onPacketReceive(T packet);

  public void publish(T data, boolean async) {
    String json = arris.getGson().toJson(data);
    if (async) {
      arris.getExecutorService()
          .submit(() -> arris.getJedisPool().getResource().publish(channel, json));
    } else {
      arris.getJedisPool().getResource().publish(channel, json);
    }
  }

  public void publish(String data, boolean async) {
    if (async) {
      arris.getExecutorService()
          .submit(() -> arris.getJedisPool().getResource().publish(channel, data));
    } else {
      arris.getJedisPool().getResource().publish(channel, data);
    }
  }

}
