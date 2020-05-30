package com.javaee.projectFroum.projectForum.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Topic topic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;
    private String content;
    private Date creationDate;
}
