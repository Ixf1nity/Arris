package me.infinity.arris.test;

import java.util.Scanner;
import me.infinity.arris.Arris;
import me.infinity.arris.ArrisCredentials;
import me.infinity.arris.adapter.ArrisAdapter;

public class Main {

  public static void main(String[] args) {
    Arris arris = new Arris(ArrisCredentials.builder()
        .address("127.0.0.1")
        .port(6379)
        .build());

    ArrisAdapter<Data> adapter = new MyAdapter(arris, "TESTING:TEST");
    arris.register(adapter);

    while (true) {
      Scanner scanner = new Scanner(System.in);
      adapter.publish(new Data(scanner.nextLine()), true);
    }

  }

}
