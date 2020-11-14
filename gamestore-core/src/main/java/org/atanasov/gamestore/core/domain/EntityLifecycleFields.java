package org.atanasov.gamestore.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
public class EntityLifecycleFields implements Serializable {

  @NotNull
  @Column(name = "creation_time", nullable = false, updatable = false)
  private LocalDateTime creationTime;

  @NotNull
  @Column(name = "modification_time", nullable = false)
  private LocalDateTime modificationTime;

  @Column(name = "deletion_time")
  private LocalDateTime deletionTime;
}
