package com.javaee.projectFroum.projectForum.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private User user;
    private String content;
    private Date creationDate;
    private Date updateDate;


    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
        this.updateDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = new Date();
    }
}
