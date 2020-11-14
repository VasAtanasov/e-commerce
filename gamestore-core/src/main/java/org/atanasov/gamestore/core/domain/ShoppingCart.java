package org.atanasov.gamestore.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity<Long> {

  @Id
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @OneToOne(targetEntity = SystemUser.class, fetch = FetchType.LAZY)
  @MapsId
  private SystemUser user;

}
