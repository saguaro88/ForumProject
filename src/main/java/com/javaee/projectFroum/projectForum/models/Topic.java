package com.javaee.projectFroum.projectForum.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;
    private String title;
    private Date creationDate;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Post> posts;
    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }


}
