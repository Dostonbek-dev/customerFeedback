package uz.davr.dto.response;


/**
 * Created by Oybek Karimjanov
 * Date : 6.1.2022
 * Project Name : customerFeedback
 */
public interface EmpFIOAndResult {
    String getfirstname();
    String getlastname();
    String getparentname();
    int getexcellent();
    int getgood();
    int getbad();
}
