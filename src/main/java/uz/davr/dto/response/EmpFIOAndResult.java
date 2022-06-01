package uz.davr.dto.response;


/**
 * Created by Oybek Karimjanov
 * Date : 6.1.2022
 * Project Name : customerFeedback
 */
public interface EmpFIOAndResult {
    String getfirst_name();
    String getlast_name();
    String getparent_name();
    int getexcellent();
    int getgood();
    int getbad();
}
