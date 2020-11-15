package org.atanasov.gamestore;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ContextConfiguration(classes = TestContextConfiguration.class)
@Transactional(readOnly = true)
public abstract class BaseDataJpaTest {}
