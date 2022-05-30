package uz.davr.entity;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;

@Entity
@Data
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    private byte[] imageBytes;
    @JsonIgnore
    private Long positionId;
    @JsonIgnore
    private Long employeeId;

    public ImageModel() {
    }

}
