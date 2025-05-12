package dk.zealandcs.gilbert.infrastruture.storage;

import java.io.InputStream;
import java.util.UUID;

public interface IStorageRepository {
    void store(String id, InputStream stream);
    default String store(InputStream stream) {
        String objectId = UUID.randomUUID().toString();
        store(objectId, stream);
        return objectId;
    }
    String objectUrl(String objectId);
    void delete(String objectId);
}
