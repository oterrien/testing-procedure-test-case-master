package com.test.infra.user.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

import static com.test.domain.user.spi.IUser.Role;

@Data
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
@Entity
@Table(name = "USER_ROLES")
@NoArgsConstructor
public class RoleEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private int id;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    public RoleEntity(Role role, UserEntity user) {
        this.role = role;
        this.user = user;
    }
}
