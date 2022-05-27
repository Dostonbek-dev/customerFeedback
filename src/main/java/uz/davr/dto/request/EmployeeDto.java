package uz.davr.dto.request;

import lombok.Data;
import uz.davr.entity.Positions;

/**
 * Created by Oybek Karimjanov
 * Date : 5.27.2022
 * Project Name : customerFeedback
 */
@Data
public class EmployeeDto {
    private String firstname;
    private String lastname;
    private Long positionId;
}
