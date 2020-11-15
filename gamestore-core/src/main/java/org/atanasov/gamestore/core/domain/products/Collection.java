package org.atanasov.gamestore.core.domain.products;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.LifecycleEntity;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity(name = "collection")
public class Collection extends LifecycleEntity<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
  private UUID uid;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
      })
  @JoinTable(
      name = "collection_product",
      joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
  private List<Product> products = new ArrayList<>();

}
