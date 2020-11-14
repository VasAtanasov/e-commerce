package org.atanasov.gamestore.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class EntityAuditFields implements Serializable {
  @Column(name = "created_by_user_id", updatable = false)
  private Long createdByUserId;

  @Column(name = "modified_by_user_id")
  private Long modifiedByUserId;

  @Column(name = "deleted_by_user_id")
  private Long deletedByUserId;
}
