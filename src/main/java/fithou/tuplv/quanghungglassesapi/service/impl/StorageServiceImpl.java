package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Override
    public String saveImageFile(String dir, MultipartFile file) {
        try {
            if (file.isEmpty())
                throw new RuntimeException("File không được để trống");

            // Tạo thư mục nếu nó không tồn tại
            Path filePath = Paths.get("./uploads/" + dir).toAbsolutePath().normalize(); // toAbsolutePath(): lấy đường dẫn tuyệt đối
            if (!Files.exists(filePath))
                Files.createDirectories(filePath);
            String newFileName = StringUtils.cleanPath(
                    UUID.randomUUID() + "." + FilenameUtils.getExtension(file.getOriginalFilename())
            );

            // Lưu file vào thư mục
            filePath = Paths.get("./uploads/" + dir, StringUtils.cleanPath(newFileName));
            Files.copy(file.getInputStream(), filePath);

            return dir + "/" + newFileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        Path filePath = Paths.get("./uploads/").resolve(fileName).normalize();
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
