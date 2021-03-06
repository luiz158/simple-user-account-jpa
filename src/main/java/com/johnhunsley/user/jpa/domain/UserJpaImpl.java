package com.johnhunsley.user.jpa.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.johnhunsley.user.domain.Role;
import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.domain.YNEnum;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  __________        _________        __________        _________
 * |          |      |         |      |          |      |         |
 * | Account  |-----<|  User   |-----<| UserRole |>-----|  Role   |
 * |__________|      |_________|      |__________|      |_________|
 *
 *  Spring Security {@link org.springframework.security.core.userdetails.UserDetails} which are associated
 *  to an Account and granted {@link GrantedAuthority} which are implemented as Role instances.
 *
 * </p>
 * <p>
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 * </p>
 *
 * catalog = "amfrv5ox1agftf0t
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 19:51
 */
@Entity
@Table(name = "USER", catalog = "simpleuseraccount", schema = "")
public class UserJpaImpl implements User, Serializable {
    private static final long serialVersionUID = 555L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "USERNAME", unique=true)
    private String username;

    @Basic
    @Column(name = "PASSWORD")
    private String password;

    @Basic
    @Column(name = "EMAIL")
    private String email;

    @Basic
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Basic
    @Column(name = "LASTNAME")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column
    private YNEnum active;

    @Column(name = "ACCOUNT_ID")
    private Integer accountId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", catalog = "simpleuseraccount", schema = "",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID", nullable = false))
    private Set<RoleJpaImpl> roles;

    public UserJpaImpl() {}

    public UserJpaImpl(String username, String email, String firstName,
                       String lastName, YNEnum active, byte[] passwordHash) {
        this.username = username;
        setPasswordHash(passwordHash);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * <p>
     *     Set the password as a {@link Base64} encoded String of the
     *     given byte[] hash.
     * </p>
     * @param passwordHash
     */
    @Override
    public void setPasswordHash(byte[] passwordHash) {
        byte[] base64Hash = Base64.getEncoder().encode(passwordHash);
        password = new String(base64Hash);
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean isActive() {
        return active.equals(YNEnum.Y);
    }

    @Override
    public void setActive(boolean active) {
        if(active) this.active = YNEnum.Y;
        else this.active = YNEnum.N;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public Collection<? extends Role> getRoles() {
        return roles;
    }

    @Override
    public void addRole(Role role) {
        if(roles == null) roles = new HashSet<>();
        roles.add((RoleJpaImpl)role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserJpaImpl userJpa = (UserJpaImpl) o;

        if (getId() != null ? !getId().equals(userJpa.getId()) : userJpa.getId() != null) return false;
        return !(getUsername() != null ? !getUsername().equals(userJpa.getUsername()) : userJpa.getUsername() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsername() != null ? getUsername().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserJpaImpl{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", active=" + active +
//                ", account=" + account +
                ", roles=" + roles +
                '}';
    }
}
