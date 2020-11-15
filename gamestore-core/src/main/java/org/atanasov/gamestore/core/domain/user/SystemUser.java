package org.atanasov.gamestore.core.domain.user;

import lombok.Getter;
import lombok.Setter;
import org.atanasov.gamestore.core.domain.Authority;
import org.atanasov.gamestore.core.domain.LifecycleEntity;
import org.atanasov.gamestore.core.domain.customer.Customer;
import org.atanasov.gamestore.core.utils.UIDGenerator;

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

  @Column(unique = true, nullable = false, updatable = false, columnDefinition = "BINARY(16)")
  private UUID uid;

  @Email
  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "first_name")
  private String lastName;

  @Column(name = "last_name")
  private String firstName;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Customer customer;

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

  protected SystemUser() {}

  public static SystemUser makeAdminEmpty() {
    SystemUser user = new SystemUser();
    user.uid = UIDGenerator.generateId();
    user.assignAsAdmin();
    return user;
  }

  public static SystemUser makeEmpty() {
    SystemUser user = new SystemUser();
    user.uid = UIDGenerator.generateId();
    user.assignAsNormalUser();
    return user;
  }

  public String getFullName() {
    return String.format("%s %s", getFirstName(), getLastName());
  }

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
