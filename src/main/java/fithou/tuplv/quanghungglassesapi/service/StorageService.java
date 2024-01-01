package fithou.tuplv.quanghungglassesapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String saveImageFile(String dir, MultipartFile file);

    void deleteFile(String fileName);
}
