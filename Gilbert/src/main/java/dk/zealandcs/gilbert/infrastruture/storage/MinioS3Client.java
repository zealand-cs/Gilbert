package dk.zealandcs.gilbert.infrastruture.storage;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Repository
public class MinioS3Client implements IStorageRepository {
    private static final String S3_BUCKET = "gilbert";

    private final MinioClient client;
    private final Environment environment;

    public MinioS3Client(MinioClient client, Environment environment) {
        this.client = client;
        this.environment = environment;
    }

    @Override
    public void store(String objectId, InputStream stream) {
        try {
            var found = client.bucketExists(BucketExistsArgs.builder().bucket(S3_BUCKET).build());
            if (!found) {
                client.makeBucket(MakeBucketArgs.builder().bucket(S3_BUCKET).build());
            }

            var putArgs = PutObjectArgs.builder()
                    .bucket(S3_BUCKET)
                    .object(objectId)
                    .stream(stream, -1, 1024 * 1024 * 5)
                    .build();

            var resp = client.putObject(putArgs);

        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {

        }
    }

    @Override
    public String objectUrl(String objectId) {
        return environment.getProperty("S3_ENDPOINT") + "/" + S3_BUCKET + "/" + objectId;
    }

    @Override
    public void delete(String objectId) {
        try {
            var found = client.bucketExists(BucketExistsArgs.builder().bucket(S3_BUCKET).build());
            if (!found) {
                client.makeBucket(MakeBucketArgs.builder().bucket(S3_BUCKET).build());
            }

            client.removeObject(RemoveObjectArgs.builder().bucket(S3_BUCKET).object(objectId).build());

        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {

        }
    }
}
