package dk.zealandcs.gilbert.infrastruture.storage;

public interface IStorageRepository<ID> {
    void store(ID id, byte[] file);
    byte[] getById(ID fileId);
    void delete(ID fileId);
}
