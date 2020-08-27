package cn.hitek.cabinetserver.product.common.file;

import cn.hitek.cabinetserver.product.common.property.LocationProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * @author chenlm
 */
@RestController
@RequestMapping("file")
public class Uploader {

    private final LocationProperty locationProperty;

    public Uploader(LocationProperty locationProperty) {
        this.locationProperty = locationProperty;
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        Optional.ofNullable(originalFilename).orElseThrow(NullPointerException::new);
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String unitPath = locationProperty.getUnitPath() + UUID.randomUUID().toString() + suffix;
        try {
            File file = new File(locationProperty.getLocation() + unitPath);
            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();
            }
            multipartFile.transferTo(file);
            return ResponseEntity.ok(locationProperty.getDeploy() + unitPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
