package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.PositionDto;
import uz.davr.entity.ImageModel;
import uz.davr.entity.Positions;
import uz.davr.repository.PositionRepository;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PositionService {

    public static final Logger LOG = LoggerFactory.getLogger(PositionService.class);
    private final PositionRepository repository;
    private final ImageService imageService;


    public String createPosition(String positions, MultipartFile file, Principal principal) throws IOException {
        Positions newPosition = new Positions();
        newPosition.setName(positions);
        Positions save = repository.save(newPosition);
        Long id = save.getId();
        boolean b = imageService.saveImageByPosition(file, principal, id);
        if (b) {
            LOG.info("Successfully created Position");
            return "Successfully create position";
        } else {
            LOG.info("Failed created Position");
            return "Failed to created position with photos ";
        }

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

    public List<Positions> getAll() {
        return repository.findAll();
    }

    public PositionDto getPositionById(Long positionId) {
        Optional<Positions> positionById = repository.findById(positionId);
        if (positionById.isPresent()) {
            Positions positions = positionById.get();
            ImageModel imageByPositionId = imageService.getImageByPositionId(positions.getId());
            PositionDto positionDto = new PositionDto();
            positionDto.setImageModel(imageByPositionId);
            positionDto.setName(positions.getName());
            return positionDto;
        } else {
            return null;
        }
    }

    public boolean deletePositionById(Long positionId) {
        Optional<Positions> byId = repository.findById(positionId);
        if (byId.isPresent()) {
            Positions positions = byId.get();
            if (imageService.deleteImageByPosition(positions.getId())) {
                repository.deleteById(positions.getId());
                return true;
            }
        } else {
            return false;
        }
        return false;
    }
}
