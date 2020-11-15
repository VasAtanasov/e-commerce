package org.atanasov.gamestore.core.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.PersistableEnumId;
import org.atanasov.gamestore.core.domain.PersistableEnumIdConverter;
import org.atanasov.gamestore.core.domain.HasID;
import org.atanasov.gamestore.core.domain.LifecycleEntity;
import org.atanasov.gamestore.core.domain.customer.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends LifecycleEntity<Long> {

  public enum OrderStatus implements PersistableEnumId {
    SHOPPING_CART(101),
    COMPLETED(102);

    private final Integer id;

    OrderStatus(Integer id) {
      this.id = id;
    }

    @Override
    public Integer getId() {
      return id;
    }
  }

  public static class OrderStatusConverter implements PersistableEnumIdConverter<OrderStatus> {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Column(name = "closed_at")
  private LocalDateTime closedAt;

  @Column(name = "closed")
  private boolean closed;

  @Column(name = "order_status_id")
  @Convert(converter = OrderStatusConverter.class)
  private OrderStatus orderStatusId = OrderStatus.SHOPPING_CART;

  @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, optional = false)
  @JoinColumn(
      name = "customer_id",
      referencedColumnName = "id",
      foreignKey = @ForeignKey(name = "fk_order_customer_id"))
  private Customer customer;

  @Column(name = "current_subtotal_line_items_quantity")
  private int currentSubtotalLineItemsQuantity;

  @Column(name = "current_subtotal_line_items_price")
  private long currentSubtotalLineItemsPrice;

  @OneToMany(
      mappedBy = "product",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<LineItem> lineItems = new ArrayList<>();

  protected Order() {}

  public static Order createEmptyShoppingCart(Customer customer) {
    Order order = new Order();
    order.assignAsShoppingCart();
    order.customer = customer;
    return order;
  }

  public void assignAsShoppingCart() {
    orderStatusId = OrderStatus.SHOPPING_CART;
  }
}
