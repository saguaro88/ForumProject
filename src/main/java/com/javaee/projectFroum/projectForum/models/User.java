package com.javaee.projectFroum.projectForum.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    private Date createdAt;
    private long postCounter = 0;
    @ManyToMany
    private Set<Role> roles;
    @ManyToMany
    private Set<Topic> followedTopics;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
