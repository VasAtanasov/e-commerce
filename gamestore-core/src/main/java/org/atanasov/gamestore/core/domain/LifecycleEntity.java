package org.atanasov.gamestore.core.domain;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class LifecycleEntity<T extends Serializable> extends BaseEntity<T> {

  @Valid @Embedded private EntityLifecycleFields lifecycleFields;

  public EntityLifecycleFields getLifecycleFields() {
    if (this.lifecycleFields == null) {
      this.lifecycleFields = new EntityLifecycleFields();
    }
    return lifecycleFields;
  }

  public LocalDateTime getCreationTime() {
    return getLifecycleFields().getCreationTime();
  }

  public LocalDateTime getModificationTime() {
    return getLifecycleFields().getModificationTime();
  }

  public LocalDateTime getDeletionTime() {
    return getLifecycleFields().getDeletionTime();
  }

  public boolean isDeleted() {
    return getDeletionTime() != null;
  }

  public void softDelete() {
    if (!isDeleted()) {
      getLifecycleFields().setDeletionTime(LocalDateTime.now());
    }
  }

  @PrePersist
  protected void prePersist() {
    final LocalDateTime now = LocalDateTime.now();
    getLifecycleFields().setCreationTime(now);
    getLifecycleFields().setModificationTime(now);
  }

  @PreUpdate
  void preUpdate() {
    setModificationTimeToCurrentTime();
  }

  public void forceRevisionUpdate() {
    setModificationTimeToCurrentTime();
  }

  public void setModificationTimeToCurrentTime() {
    getLifecycleFields().setModificationTime(LocalDateTime.now());
  }
}
