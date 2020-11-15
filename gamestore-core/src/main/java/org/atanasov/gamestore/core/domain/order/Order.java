package org.atanasov.gamestore.core.domain.order;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.HasID;
import org.atanasov.gamestore.core.domain.LifecycleEntity;
import org.atanasov.gamestore.core.domain.customer.Customer;
import org.atanasov.gamestore.core.domain.user.SystemUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends LifecycleEntity<Long> {

  public enum OrderStatus implements HasID<Integer> {
    SHOPPING_CART(1),
    COMPLETED(2);

    private final int id;

    OrderStatus(int id) {
      this.id = id;
    }

    @Override
    public Integer getId() {
      return id;
    }
  }

  public static class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus enumValue) {
      return enumValue == null ? null : enumValue.getId();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer dbData) {
      return Stream.of(OrderStatus.values())
          .filter(t -> t.getId().equals(dbData))
          .findAny()
          .orElseThrow();
    }
  }

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
  private Integer orderStatusId = OrderStatus.SHOPPING_CART.getId();

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

  public static Order createEmptyShoppingCart() {
    Order order = new Order();
    order.assignAsShoppingCart();
    return order;
  }

  public void assignAsShoppingCart() {
    orderStatusId = OrderStatus.SHOPPING_CART.getId();
  }
}