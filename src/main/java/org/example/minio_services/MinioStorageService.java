package org.example.minio_services;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MinioStorageService {
    private final MinioClient minioClient;

    public void save(MultipartFile file, String path) throws Exception {
        minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(MinioConfig.COMMON_BUCKET_NAME)
                .object(path)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build());
    }

    public String getPresignedLink(String path) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(MinioConfig.COMMON_BUCKET_NAME)
                        .object(path)
                        .expiry(60 * 60 * 24)
                        .build());
    }

    public void delete(String path) throws Exception {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(MinioConfig.COMMON_BUCKET_NAME)
                .object(path)
                .build()
        );
    }
}
