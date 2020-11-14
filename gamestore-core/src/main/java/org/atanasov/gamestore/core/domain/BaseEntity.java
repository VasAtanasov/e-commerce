package org.atanasov.gamestore.core.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * This class is an abstract superclass for all Entity classes in the application. This class
 * defines variables which are common for all entity classes.
 */
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> implements HasID<PK>, Persistable<PK> {

  @Version
  @Column(nullable = false, name = "rev")
  @Getter
  @Setter
  protected Integer rev;

  /**
   * AccessType.PROPERTY allows safe getId() call on associated entities without unnecessary entity
   * lazy-fetch. NOTE: Annotation must be applied to sub-class property.
   *
   * <p>
   */
  @Override
  public abstract PK getId();

  @Override
  @Transient
  public boolean isNew() {
    return null == getId();
  }
}
