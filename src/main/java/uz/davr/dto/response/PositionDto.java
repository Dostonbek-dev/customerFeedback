package uz.davr.dto.response;

import lombok.Data;
import uz.davr.entity.ImageModel;

@Data
public class PositionDto {

    public String name;
    public ImageModel imageModel;
}
