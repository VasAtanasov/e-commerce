package org.atanasov.gamestore.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "line_item")
public class LineItem extends BaseEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Column(name = "price")
  private long price;

  @Column(name = "quantity")
  private long quantity;

  @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "product_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_line_item_product_id"))
  private Product product;
}
