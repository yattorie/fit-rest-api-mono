package com.orlovandrei.fit_rest.config;

import com.orlovandrei.fit_rest.util.Messages;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.url}")
    private String url;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket}")
    private String bucket;

    @Bean
    public MinioClient minioClient() {
        MinioClient client = MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();

        try {
            boolean exists = client.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());

            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
                System.out.println(Messages.MINIO_BUCKET.getMessage() + bucket + Messages.CREATED.getMessage());
            } else {
                System.out.println(Messages.MINIO_BUCKET.getMessage() + bucket + Messages.EXISTS.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(Messages.MINIO_BUCKET_ERROR_INIT.getMessage(), e);
        }

        return client;
    }
}


