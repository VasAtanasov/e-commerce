package org.atanasov.gamestore.core.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.BaseEntity;
import org.atanasov.gamestore.core.domain.products.Product;

import javax.persistence.*;
import java.util.UUID;

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

  @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
  private UUID uid;

  @Column(name = "price")
  private long price;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "name")
  private String name;

  @ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "order_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_line_item_order_id"))
  private Order order;

  @ManyToOne(targetEntity = Product.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "product_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_line_item_product_id"))
  private Product product;

  protected LineItem() {}

  public static LineItem of(Order order, Product product, long price, int quantity) {
    LineItem lineItem = new LineItem();
    lineItem.order = order;
    lineItem.product = product;
    lineItem.price = price;
    lineItem.quantity = quantity;
    return lineItem;
  }
}
