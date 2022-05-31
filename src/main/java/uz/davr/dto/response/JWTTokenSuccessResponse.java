package uz.davr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by Oybek Karimjanov
 * Date : 5.20.2022
 * Project Name : instazoo
 */
@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {
    private boolean success;
    private String token;
    private String branchCode;
    private String role;
}
