package uz.davr.entity;

import lombok.Data;

import javax.persistence.*;

@Table(name = "position")
@Data
@Entity
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    public Positions() {
    }

    public Positions(Long positionId, String positionName) {

    }
}
