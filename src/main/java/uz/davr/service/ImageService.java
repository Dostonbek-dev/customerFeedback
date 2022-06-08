package uz.davr.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.davr.entity.Employees;
import uz.davr.entity.ImageModel;
import uz.davr.entity.User;
import uz.davr.exeptions.ImageNotFoundException;
import uz.davr.repository.EmployeeRepository;
import uz.davr.repository.ImageRepository;
import uz.davr.repository.PositionRepository;
import uz.davr.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@RequiredArgsConstructor
public class ImageService {
    public static final Logger LOG = LoggerFactory.getLogger(ImageService.class);

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final ImageRepository imageRepository;


    public ImageModel uploadImageByEmployeePhotos(MultipartFile file,
                                                  Principal principal,
                                                  Long employeeId) throws IOException {
        User user = getUserByPrincipal(principal);
        ImageModel imageModel = new ImageModel();
        imageModel.setName(file.getOriginalFilename());
        imageModel.setImageBytes(compressBytes(file.getBytes()));
        imageModel.setEmployeeId(employeeId);
        LOG.info("Uploading image to Employee id {}", employeeId);
        return imageRepository.save(imageModel);

    }

    public ImageModel getImageByEmployeeId(Long employeeId) {
        ImageModel imageModel = imageRepository.findByEmployeeId(employeeId)
                .orElseThrow(() -> new ImageNotFoundException("Cannot find image to Post : " + employeeId));
        if (!ObjectUtils.isEmpty(imageModel)) {
            imageModel.setImageBytes(decompressBytes(imageModel.getImageBytes()));
        }
        return imageModel;
    }

    public boolean saveImageByPosition(MultipartFile file,
                                       Principal principal,
                                       Long positionId) throws IOException {
        ImageModel imageModel = new ImageModel();
        imageModel.setName(file.getOriginalFilename());
        imageModel.setImageBytes(file.getBytes());
        imageModel.setPositionId(positionId);
        LOG.info("Uploading image to Employee id {}", positionId);
        imageRepository.save(imageModel);
        return true;
    }


    public User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

    public byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size -" + outputStream.toByteArray());
        return outputStream.toByteArray();
    }

    private static byte[] decompressBytes(byte[] date) {
        Inflater inflater = new Inflater();
        inflater.setInput(date);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(date.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            LOG.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    public ImageModel getImageByPositionId(Long id) {
        Optional<ImageModel> byPositionId = imageRepository.findByPositionId(id);
        return byPositionId.orElse(null);
    }

    public boolean deleteImageByPosition(Long id) {
        Optional<ImageModel> byPositionId = imageRepository.findByPositionId(id);
        if (byPositionId.isPresent()) {
            imageRepository.deleteById(byPositionId.get().getId());
            return true;
        } else {
            return false;
        }
    }

    public boolean updateImageByPositionId(Long id, ImageModel file) throws IOException {
        Optional<ImageModel> byPositionId = imageRepository.findByPositionId(id);
        if (byPositionId.isPresent()) {
            ImageModel imageModel = byPositionId.get();
            imageModel.setName(file.getName());
            imageModel.setImageBytes(compressBytes(file.getImageBytes()));
            LOG.info("Uploading image to Employee id {}", id);
            imageRepository.save(imageModel);
            return true;
        } else {
            return false;
        }
    }
}
