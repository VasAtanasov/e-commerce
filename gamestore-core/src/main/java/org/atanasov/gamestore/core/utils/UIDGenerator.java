package org.atanasov.gamestore.core.utils;

import java.util.UUID;

public class UIDGenerator {
  private UIDGenerator() {}

  public static UUID generateId() {
    return UUID.randomUUID();
  }
}
