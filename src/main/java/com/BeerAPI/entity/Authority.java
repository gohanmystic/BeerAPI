package com.BeerAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.BeerAPI.common.enums.Roles;

@Entity(name="Authority")
@Table(name="Authority")
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 6606923760842320797L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated( EnumType.STRING)
    @Column(name="name")
    private Roles role;

    @Override
    public String getAuthority() {
        return role.getValue();
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
