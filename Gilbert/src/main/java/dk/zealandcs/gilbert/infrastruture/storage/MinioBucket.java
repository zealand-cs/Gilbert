package dk.zealandcs.gilbert.infrastruture.storage;

public enum MinioBucket {
    ProfilePicture("profile");

    private final String bucketName;
    MinioBucket(String bucketName) { this.bucketName = bucketName; }

    public String getBucket() {
        return bucketName;
    }
}
