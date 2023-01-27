package me.infinity.arris;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ArrisCredentials {

  @Builder.Default
  private boolean ssl = false;

  private String address;
  private Integer port;
  private String password;

  public boolean authenticationEnabled() {
    return password != null;
  }

}
