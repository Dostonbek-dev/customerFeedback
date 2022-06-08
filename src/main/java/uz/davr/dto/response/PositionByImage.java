package uz.davr.dto.response;

import lombok.Data;


public interface PositionByImage {
    Long getid();

    String getname();

    Long getimageid();

    byte[] getimagebytes();

    String getimagename();
}
