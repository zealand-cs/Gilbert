package dk.zealandcs.gilbert.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    @Value("#{environment.S3_ENDPOINT}")
    private String storageEndpoint;

    @Value("#{environment.S3_ACCESS_KEY}")
    private String accessKey;

    @Value("#{environment.S3_ACCESS_SECRET}")
    private String accessSecret;

    @Bean
    public MinioClient s3Client() {
        return MinioClient.builder().endpoint(storageEndpoint).credentials(accessKey, accessSecret).build();
    }
}
