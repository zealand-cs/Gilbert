package dk.zealandcs.gilbert.domain.post;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post {

    private int id;
    private int ownerId;
    private String name;
    private String brand;
    private String typeOfClothing;
    private String description;
    private double price;
    private Condition condition;
    private String size;
    private String location;
    private PostStatus status;
    private String imageId;
    private LocalDate datePostedAt;

    public Post() {
    }

    public Post(int id, int ownerId, String name, String brand, String typeOfClothing, String description, double price, Condition condition, String size, String location, PostStatus status, String imageId, LocalDate datePostedAt) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.brand = brand;
        this.typeOfClothing = typeOfClothing;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.size = size;
        this.location = location;
        this.status = PostStatus.PendingApproval;
        this.imageId = imageId;
        this.datePostedAt = datePostedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTypeOfClothing() {
        return typeOfClothing;
    }

    public void setTypeOfClothing(String typeOfClothing) {
        this.typeOfClothing = typeOfClothing;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public LocalDate getDatePostedAt() {
        return datePostedAt;
    }

    public void setDatePostedAt(LocalDate datePostedAt) {
        this.datePostedAt = datePostedAt;
    }
}


