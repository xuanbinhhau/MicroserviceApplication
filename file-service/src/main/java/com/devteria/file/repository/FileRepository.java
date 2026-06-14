package com.devteria.file.repository;

import com.devteria.file.dto.FileInfor;
import com.devteria.file.entity.FileMgmt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Repository
public class FileRepository {

    @Value("${app.file.storage-dir}")
    String fileStorage;

    @Value("${app.file.download-Prefi}")
    String urlPrefix;


    public FileInfor store(MultipartFile file) throws IOException {

        Path folder = Paths.get(fileStorage);

        String fileNameExtenstion = StringUtils
                .getFilenameExtension(file.getOriginalFilename());

        String filedName = Objects.isNull(fileNameExtenstion)
                ? UUID.randomUUID().toString()
                : UUID.randomUUID().toString() +"."+ fileNameExtenstion;

        Path filePath = folder.resolve(filedName).normalize().toAbsolutePath();

        Files.copy(file.getInputStream(),filePath, StandardCopyOption.REPLACE_EXISTING);
        return FileInfor.builder()
                .name(filedName)
                .size(file.getSize())
                .contentType(file.getContentType())
                .md5Checksum(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .path(filePath.toString())
                .url(urlPrefix + filedName)
                .build();
    }

    public Resource read(FileMgmt fileMgmt) throws IOException {
        var data = Files.readAllBytes(Path.of(fileMgmt.getPath()));
        return new ByteArrayResource(data);
    }
}
