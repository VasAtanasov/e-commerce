package org.atanasov.gamestore.core.repositories;

import org.atanasov.gamestore.BaseDataJpaTest;
import org.atanasov.gamestore.core.domain.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepositoryTest extends BaseDataJpaTest {

  @Autowired CustomerRepository customerRepository;

  @Test
  @Transactional
  void should_saveCustomer_when_ValidData() {
    Customer customer = Customer.of("sadas@mail.com", "User", "User");
    customerRepository.save(customer);
    assertThat(customerRepository.count()).isGreaterThan(0);
  }
}
