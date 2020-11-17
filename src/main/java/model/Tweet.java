package model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 300)
    private String message;

    private String translation;

    @Column(name = "published_at")
    @CreationTimestamp
    private Date publishedAt;

    @ManyToOne
    private AppUser author;


    public Tweet() {
    }

    public Tweet(AppUser author, String message) {
        this.author = author;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAuthor() {
        return author;
    }

    public void setAuthor(AppUser author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String messageTranslation) {
        this.translation = messageTranslation;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", publishedAt=" + publishedAt +
                '}';
    }
}

//id: Long
//        author: AppUser
//        message: String
//        publishedAt: Date