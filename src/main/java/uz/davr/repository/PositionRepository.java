package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import uz.davr.dto.response.PositionByCountNumber;
import uz.davr.dto.response.PositionByImage;
import uz.davr.entity.Positions;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Positions, Long> {


    @Query(value = "select pos.id, pos.name as positionname, sum(emp.good + emp.bad + emp.excellent) as counter\n" +
            "from employees emp,\n" +
            "     position pos\n" +
            "where emp.positions_id = pos.id\n" +
            "group by pos.name, pos.id\n" +
            "order by counter DESC limit 3 ", nativeQuery = true)
    List<PositionByCountNumber> getPositionByCountFeedBack();


    @Query(value = "select pos.id, pos.name as positionname, sum(emp.good + emp.bad + emp.excellent) as counter\n" +
            "            from employees emp,\n" +
            "                 position pos\n" +
            "            where  emp.positions_id = pos.id  and emp.user_id=:userId\n" +
            "            group by pos.name, pos.id\n" +
            "            order by counter DESC ", nativeQuery = true)
    List<PositionByCountNumber> getPositionByCountFeedBackBranch(@Param(value = "userId") Long user_id);

    @Query(value = "select pos.id,pos.name  ,img.name as imagename,img.image_bytes as imagebytes ,img.id as imageid   from position pos , image_model img where  img.position_id=pos.id", nativeQuery = true)
    List<PositionByImage> getAllPositionByImage();
}
