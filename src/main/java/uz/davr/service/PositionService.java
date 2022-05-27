package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.davr.entity.Positions;
import uz.davr.repository.PositionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    public static final Logger LOG = LoggerFactory.getLogger(PositionService.class);
    private final PositionRepository repository;


    public String createPost(Positions positions) {
        Positions newPosition = new Positions();
        positions.setName(positions.getName());
        repository.save(positions);
        LOG.info("Successfully created Position");
        return "Successfully create position";
    }

    public String delete(Long id) {
        repository.deleteById(id);
        LOG.info("Position deleted by id ");
        return "Position deleted by id {}";
    }

    public Positions updatePosition(Long id, String positionName) {
        Positions positionById = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Position not found by ID"));
        positionById.setName(positionName);
        Positions save = repository.save(positionById);
        LOG.info("Update Position By Id  " + id);
        return save;
    }

    public List<Positions> getALl() {
        return repository.findAll();
    }


}
