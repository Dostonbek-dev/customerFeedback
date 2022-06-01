package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import uz.davr.dto.response.PositionByCountNumber;
import uz.davr.entity.Positions;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Positions, Long> {


    @Query(value ="select pos.id, pos.name as positionname, sum(emp.good + emp.bad + emp.excellent) as counter\n" +
            "from employees emp,\n" +
            "     position pos\n" +
            "where emp.positions_id = pos.id\n" +
            "group by pos.name, pos.id\n" +
            "order by counter DESC",nativeQuery = true)
    List<PositionByCountNumber> getPositionByCountFeedBack();
}
