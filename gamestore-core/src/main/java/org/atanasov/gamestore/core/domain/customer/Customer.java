package org.atanasov.gamestore.core.domain.customer;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.LifecycleEntity;
import org.atanasov.gamestore.core.domain.order.Order;
import org.atanasov.gamestore.core.domain.user.SystemUser;
import org.atanasov.gamestore.core.utils.UIDGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends LifecycleEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
  private UUID uid;

  @OneToOne(targetEntity = SystemUser.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_customer_user_id"))
  private SystemUser user;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "last_order_id", foreignKey = @ForeignKey(name = "fk_customer_last_order_id"))
  private Order lastOrder;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(
      name = "shopping_cart_id",
      foreignKey = @ForeignKey(name = "fk_customer_shopping_cart_id"))
  private Order shoppingCart = Order.createEmptyShoppingCart(this);

  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "first_name")
  private String lastName;

  @Column(name = "last_name")
  private String firstName;

  @Column(name = "orders_count")
  private int ordersCount;

  @Column(name = "total_spent")
  private long totalSpent;

  @OneToMany(
      mappedBy = "customer",
      targetEntity = Order.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  protected Customer() {}

  public static Customer of(String email, String firstName, String lastName) {
    Customer customer = new Customer();
    customer.uid = UIDGenerator.generateId();
    customer.email = email;
    customer.firstName = firstName;
    customer.lastName = lastName;
    return customer;
  }
}
