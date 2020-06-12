package com.javaee.projectFroum.projectForum.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @OneToOne
    private Topic reportedTopic;
    @OneToOne
    private Post reportedPost;
    @OneToOne
    private User reportedByUser;
    private String message;

}
