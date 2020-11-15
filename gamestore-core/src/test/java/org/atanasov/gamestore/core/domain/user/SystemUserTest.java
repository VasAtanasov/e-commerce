package org.atanasov.gamestore.core.domain.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SystemUserTest {

  @Test
  void should_returnTrue_when_SystemUser_makeAdminEmpty() {
    SystemUser user = SystemUser.makeAdminEmpty();
    assertThat(user.isAdmin()).isTrue();
  }

  @Test
  void should_returnFalse_when_SystemUser_makeEmpty() {
    SystemUser user = SystemUser.makeEmpty();
    assertThat(user.isAdmin()).isFalse();
  }
}
