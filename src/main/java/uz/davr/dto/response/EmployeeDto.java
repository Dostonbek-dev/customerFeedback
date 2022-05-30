package uz.davr.dto.response;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.entity.ImageModel;
import uz.davr.entity.Positions;
@Data
public class EmployeeDto {
    private Long id;
    private String firstname;
    private String lastname;
    private String parentName;
    private Long positionId;
    private String branch;
    private ImageModel imageModel;
}

