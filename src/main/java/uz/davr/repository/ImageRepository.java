package uz.davr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.davr.entity.ImageModel;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<ImageModel, Long> {
        Optional<ImageModel> findByPositionId(Long positionId);
        Optional<ImageModel> findByEmployeeId(Long id);
}
