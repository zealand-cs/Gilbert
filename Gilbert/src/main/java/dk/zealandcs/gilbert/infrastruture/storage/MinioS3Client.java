package dk.zealandcs.gilbert.infrastruture.storage;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Repository
public class MinioS3Client implements IStorageRepository<UUID> {
    private final MinioClient client;

    private static final String S3_BUCKET = "gilbert";

    public MinioS3Client(MinioClient client) {
        this.client = client;
    }

    @Override
    public void store(UUID id, byte[] file) {
        try {
            var found = client.bucketExists(BucketExistsArgs.builder().bucket(S3_BUCKET).build());
            if (!found) {
                client.makeBucket(MakeBucketArgs.builder().bucket(S3_BUCKET).build());
            }

            UUID objectId = UUID.randomUUID();

            Path tempFile = Files.createTempFile("test", ".jpg");

            client.uploadObject(UploadObjectArgs.builder().bucket(S3_BUCKET).object(objectId.toString()).build());

        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {

        }
    }

    @Override
    public byte[] getById(UUID fileId) {
        return new byte[0];
    }

    @Override
    public void delete(UUID fileId) {

    }
}
