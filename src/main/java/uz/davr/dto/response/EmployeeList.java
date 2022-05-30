package uz.davr.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface EmployeeList {

     Long getemployee_id();

     byte[] getimage_bytes();

     String getimage_name();

     String getlast_name();

     Long getimage_id();

     String getfirst_name();

     String getparent_name();

     String getposition_name();

     Long getposition_id();
}
