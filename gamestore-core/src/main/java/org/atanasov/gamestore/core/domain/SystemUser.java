package org.atanasov.gamestore.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "system_user")
public class SystemUser extends LifecycleEntity<Long> {

  public enum Role implements Authority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
      return name();
    }
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Access(value = AccessType.PROPERTY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "full_name")
  private String fullName;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
  private ShoppingCart shoppingCart;

  @ElementCollection(fetch = FetchType.LAZY, targetClass = Role.class)
  @JoinTable(
      name = "user_role",
      joinColumns =
          @JoinColumn(
              name = "user_id",
              referencedColumnName = "id",
              foreignKey = @ForeignKey(name = "fk_role_user_id")))
  @Column(name = "role")
  @Enumerated(value = EnumType.STRING)
  private Set<Role> roles = new HashSet<>();

  @OneToMany(
      mappedBy = "user",
      fetch = FetchType.LAZY,
      cascade = CascadeType.ALL,
      orphanRemoval = true)
  private List<Order> orders = new ArrayList<>();

  public boolean isAdmin() {
    if (roles == null || roles.isEmpty()) {
      throw new IllegalStateException("User role not defined");
    }
    return roles.contains(Role.ADMIN);
  }

  public void assignAsNormalUser() {
    initRoleCollection();
    roles.clear();
    roles.addAll(getInheritedRolesFromRole(Role.USER));
  }

  public void assignAsAdmin() {
    initRoleCollection();
    roles.clear();
    roles.addAll(getInheritedRolesFromRole(Role.ADMIN));
  }

  private void initRoleCollection() {
    if (roles == null) {
      roles = new HashSet<>();
    }
  }

  private Set<Role> getInheritedRolesFromRole(Role role) {
    List<Role> allRoles = Arrays.stream(Role.values()).collect(Collectors.toList());
    int index = allRoles.indexOf(role);
    return new HashSet<>(allRoles.subList(index, allRoles.size()));
  }
}
