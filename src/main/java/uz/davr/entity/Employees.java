package uz.davr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "employees")
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "parent_name")
    private String parentName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Positions positions;

    @Column(updatable = false)
    private LocalDateTime createdDate;
    @Column(name = "excellent")
    private int excellent=0;
    @Column(name = "good")
    private int good=0;
    @Column(name = "bad")
    private int bad=0;
    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    public Employees() {
    }

    @PrePersist
    protected void onCreated() {
        this.createdDate = LocalDateTime.now();
    }
}
