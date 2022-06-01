package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.PositionByCountNumber;
import uz.davr.dto.response.PositionDto;
import uz.davr.entity.ImageModel;
import uz.davr.entity.Positions;
import uz.davr.entity.User;
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
    private final UserService userService;


    public String createPosition(String positions, MultipartFile file, Principal principal) throws IOException {
        Positions newPosition = new Positions();
        newPosition.setName(positions);
        Positions save = repository.save(newPosition);
        Long id = save.getId();
        boolean b = imageService.saveImageByPosition(file, principal, id);
        if (b) {
            LOG.info("Position is created successfully!");
            return new String("\"Position is created successfully!\"");
        } else {
            LOG.info("Position is not created!");
            return new String("Position is not created!");
        }

    }

    public Positions updatePosition(Long id, String positionName) {
        Positions positionById = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Position is not found by Id "));
        positionById.setName(positionName);
        Positions save = repository.save(positionById);
        LOG.info("Position is updated by Id! " + id);
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

    public boolean updatePositionById(Long id, PositionDto positionDto) throws IOException {
        Optional<Positions> positionId = repository.findById(id);
        if (positionId.isPresent()) {
            Positions positions = positionId.get();
            positions.setName(positionDto.getName());
            imageService.updateImageByPositionId(positions.getId(), positionDto.getImageModel());
            return true;
        } else {
            return false;
        }
    }
    public List<PositionByCountNumber> getPositionByCount(){
       return repository.getPositionByCountFeedBack();
    }

    public List<PositionByCountNumber> getPositionByCountBranch(Principal principal) {
        User currentUser = userService.getCurrentUser(principal);
        return repository.getPositionByCountFeedBackBranch(currentUser.getId());
    }
}
