package dk.zealandcs.gilbert.domain.post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Post {

    private int id;
    private int ownerId;
    private String name;
    private Brand brand;
    private ProductType typeOfClothing;
    private String description;
    private double price;
    private Condition condition;
    private String size;
    private String location;
    private PostStatus status = PostStatus.PendingApproval;
    private String imageId;
    private Date datePostedAt;
    private String ownerDisplayName;


    public Post() {
    }

    public Post(int id, int ownerId, String name, Brand brand, ProductType typeOfClothing, String description, double price, Condition condition, String size, String location, PostStatus status, String imageId, Date datePostedAt, String ownerDisplayName) {
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
        this.ownerDisplayName = ownerDisplayName;
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

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public ProductType getTypeOfClothing() {
        return typeOfClothing;
    }

    public void setTypeOfClothing(ProductType typeOfClothing) {
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

    public Date getDatePostedAt() {
        return datePostedAt;
    }

    public void setDatePostedAt(Date datePostedAt) {
        this.datePostedAt = datePostedAt;
    }

    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    public void setOwnerDisplayName(String ownerDisplayName) {
        this.ownerDisplayName = ownerDisplayName;
    }
}


