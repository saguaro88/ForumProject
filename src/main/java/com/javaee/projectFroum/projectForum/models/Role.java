package com.javaee.projectFroum.projectForum.models;

import com.javaee.projectFroum.projectForum.models.enums.AccountRole;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

}