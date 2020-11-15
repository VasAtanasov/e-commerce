package org.atanasov.gamestore.core.domain.products;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.LifecycleEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product extends LifecycleEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Size(min = 3, max = 100)
  @Column(name = "title", nullable = false, unique = true)
  private String title;

  @Min(1)
  @Column(name = "price")
  private long price;

  @Size(min = 20)
  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "image_rul")
  private String imageUrl;

  @Column(name = "total_inventory")
  private int totalInventory;
}
