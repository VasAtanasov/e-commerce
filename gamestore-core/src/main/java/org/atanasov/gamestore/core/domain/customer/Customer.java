package org.atanasov.gamestore.core.domain.customer;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.LifecycleEntity;
import org.atanasov.gamestore.core.domain.order.Order;
import org.atanasov.gamestore.core.domain.user.SystemUser;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Positive;
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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "shopping_cart_id",
      foreignKey = @ForeignKey(name = "fk_customer_shopping_cart_id"))
  private Order shoppingCart;

  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "first_name")
  private String lastName;

  @Column(name = "last_name")
  private String firstName;

  @Positive
  @Column(name = "orders_count")
  private int ordersCount;

  @Positive
  @Column(name = "total_spent")
  private long totalSpent;

  @OneToMany(
      mappedBy = "customer",
      targetEntity = Order.class,
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  public static Customer.CustomerBuilder builder() {
    return new Customer.CustomerBuilder();
  }

  public static class CustomerBuilder {
    private Long id;
    private UUID uid;
    private SystemUser user;
    private Order lastOrder;
    private Order shoppingCart;
    private String email;
    private String lastName;
    private String firstName;
    private int ordersCount;
    private long totalSpent;
    private List<Order> orders;

    CustomerBuilder() {}

    public Customer.CustomerBuilder id(final Long id) {
      this.id = id;
      return this;
    }

    public Customer.CustomerBuilder uid(final UUID uid) {
      this.uid = uid;
      return this;
    }

    public Customer.CustomerBuilder user(final SystemUser user) {
      this.user = user;
      return this;
    }

    public Customer.CustomerBuilder lastOrder(final Order lastOrder) {
      this.lastOrder = lastOrder;
      return this;
    }

    public Customer.CustomerBuilder shoppingCart(final Order shoppingCart) {
      this.shoppingCart = shoppingCart;
      return this;
    }

    public Customer.CustomerBuilder email(final String email) {
      this.email = email;
      return this;
    }

    public Customer.CustomerBuilder lastName(final String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Customer.CustomerBuilder firstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Customer.CustomerBuilder ordersCount(final int ordersCount) {
      this.ordersCount = ordersCount;
      return this;
    }

    public Customer.CustomerBuilder totalSpent(final long totalSpent) {
      this.totalSpent = totalSpent;
      return this;
    }

    public Customer.CustomerBuilder orders(final List<Order> orders) {
      this.orders = orders;
      return this;
    }

    public Customer build() {
      Customer customer = new Customer();
      customer.id = this.id;
      customer.uid = this.uid;
      customer.user = this.user;
      customer.lastOrder = this.lastOrder;
      customer.shoppingCart = this.shoppingCart;
      customer.email = this.email;
      customer.lastName = this.lastName;
      customer.firstName = this.firstName;
      customer.ordersCount = this.ordersCount;
      customer.totalSpent = this.totalSpent;
      customer.orders = this.orders;
      return customer;
    }
  }

  protected Customer() {}
}
