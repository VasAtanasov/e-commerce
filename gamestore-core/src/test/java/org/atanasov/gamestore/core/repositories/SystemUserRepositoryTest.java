package org.atanasov.gamestore.core.repositories;

import org.atanasov.gamestore.TestContextConfiguration;
import org.atanasov.gamestore.core.domain.user.SystemUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestContextConfiguration.class)
@Transactional(readOnly = true)
class SystemUserRepositoryTest {
  @Autowired SystemUserRepository systemUserRepository;

  @Test
  @Transactional
  void should_saveUser() {

    long beforeSave = systemUserRepository.count();

    SystemUser user = SystemUser.makeAdminEmpty();
    user.setEmail("user@email.com");
    user.setFirstName("User");
    user.setLastName("User");
    user.setPassword("passwd");

    systemUserRepository.save(user);
    systemUserRepository.flush();

    long afterSave = systemUserRepository.count();
    assertThat(beforeSave + 1).isEqualTo(afterSave);
  }

  @Test
  void noUsers() {

    long count = systemUserRepository.count();

    assertThat(count).isEqualTo(0);
  }
}
