package org.atanasov.gamestore.core.repositories;

import org.atanasov.gamestore.BaseDataJpaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class OrderRepositoryTest extends BaseDataJpaTest {

  @Autowired OrderRepository orderRepository;

  @Test
  void should_saveOrder_when_validData() {

  }
}
