package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.davr.entity.Positions;

@Repository
public interface PositionRepository extends JpaRepository<Positions, Long> {
}
