package me.infinity.arris.test;

import me.infinity.arris.Arris;
import me.infinity.arris.adapter.ArrisAdapter;

public class MyAdapter extends ArrisAdapter<Data> {

  public MyAdapter(Arris arris, String channel) {
    super(arris, channel);
  }

  @Override
  public void onPacketReceive(Data packet) {
    System.out.println("Data received < > " + packet.toString());
  }
}
