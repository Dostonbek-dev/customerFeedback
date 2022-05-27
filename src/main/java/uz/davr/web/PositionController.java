package uz.davr.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.entity.Positions;
import uz.davr.service.PositionService;

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
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(positionService.getAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> createPosition(@RequestBody Positions positions){
        return ResponseEntity.ok(positionService.createPost(positions));
    }





}
