package org.atanasov.gamestore.core.utils;

import java.util.UUID;

public final class UIDGenerator {

  public static UUID generateId() {
    return UUID.randomUUID();
  }

  private UIDGenerator() {
    throw new AssertionError();
  }
}
