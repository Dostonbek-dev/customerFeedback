package uz.davr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    public ResponseEntity<?> getPositionById(@PathVariable Long positionId){
        return ResponseEntity.ok(positionService.getPositionById(positionId));
    }


}
