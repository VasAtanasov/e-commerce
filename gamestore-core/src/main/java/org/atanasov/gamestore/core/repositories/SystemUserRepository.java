package org.atanasov.gamestore.core.repositories;

import org.atanasov.gamestore.core.domain.user.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {}
