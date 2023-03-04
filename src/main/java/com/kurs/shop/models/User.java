package com.kurs.shop.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User implements UserDetails
{
    // в спринг нужно еще хранить роль на сайте юзер или админ ит.д. role, enabled (!!!!!!тоже обязательно, активен ли юзер, это булевое значение)
    // и загляни в файл Role, это перечисление

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")

    private Set<Item> items = new HashSet<>();

    public Set<Item> getItems() {return items;}

    public void setItems(Set<Item> items) {this.items = items;}

    private String username, password, email;

    private boolean enabled;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER) // fetch тип подругзки, лайзи подружает после прогрузки всех основных данных объекта
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles; // роль используя перечисление

    public User() {}

//    public User(String username, String password, String email, boolean enabled, Set<Role> roles)
//    {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.enabled = enabled;
//        this.roles = roles;
//    }

    public User(String username, String password, String email, boolean enabled, Set<Role> roles, Set<Item> items)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.roles = roles;
        this.items = items;
    }

    public long getId() {return id;}

    public void setId(long id) {this.id = id;}

    public String getUsername() {return username;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    public void setUsername(String username) {this.username = username;}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {

        return getRoles();
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public boolean isEnabled() {return enabled;}

    public void setEnabled(boolean enabled) {this.enabled = enabled;}

    public Set<Role> getRoles() {return roles;}

    public void setRoles(Set<Role> roles) {this.roles = roles;}
}
