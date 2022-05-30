package uz.davr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.PositionDto;
import uz.davr.entity.ImageModel;
import uz.davr.entity.Positions;
import uz.davr.service.ImageService;
import uz.davr.service.PositionService;

import java.io.IOException;
import java.security.Principal;

/**
 * Created by Oybek Karimjanov
 * Date : 5.27.2022
 * Project Name : customerFeedback
 */
@RestController
@RequestMapping("/api/position")
@CrossOrigin
public class PositionController {

    private final PositionService positionService;


    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(positionService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> createPosition(@RequestParam String positions, @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        return ResponseEntity.ok(positionService.createPosition(positions, file, principal));
    }

    @GetMapping("/position{positionId}")
    public ResponseEntity<?> getPositionById(@PathVariable Long positionId) {
        return ResponseEntity.ok(positionService.getPositionById(positionId));
    }

    @DeleteMapping("delete/{positionId}")
    public ResponseEntity<?> deleteById(@PathVariable Long positionId) {
        if (positionService.deletePositionById(positionId)) {
            return ResponseEntity.ok("Delete by id successfully");
        } else {
            return ResponseEntity.ok("Delete failed");
        }
    }

    @PutMapping("update/{positionId}")
    public ResponseEntity<?> updateEmployeeByPositionId(@PathVariable Long positionId,
                                                        @RequestParam MultipartFile file,
                                                        @RequestParam String name) throws IOException {
        PositionDto positionDto = new PositionDto();
        positionDto.setName(name);
        ImageModel imageModel = new ImageModel();
        imageModel.setPositionId(positionId);
        imageModel.setImageBytes(file.getBytes());
        imageModel.setName(file.getOriginalFilename());
        positionDto.setImageModel(imageModel);
        boolean result = positionService.updatePositionById(positionId, positionDto);
        if (result) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }

    }


}
