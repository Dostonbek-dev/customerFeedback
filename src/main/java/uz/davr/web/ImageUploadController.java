package uz.davr.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.dto.response.MessageResponse;
import uz.davr.service.ImageService;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageUploadController {

    private final ImageService imageService;

    @PostMapping("/upload/{employeeId}")
    public ResponseEntity<MessageResponse>
    uploadImageByEmployeeId(@RequestParam("file") MultipartFile file, @PathVariable Long employeeId,
                            Principal principal) throws IOException {
        imageService.uploadImageByEmployeePhotos(file, principal, employeeId);
        return ResponseEntity.ok(new MessageResponse("Successfully saved image"));

    }

}
