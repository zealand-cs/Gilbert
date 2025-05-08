# Class diagram

```mermaid
---
title: Class diagram for Gilbert
---

classDiagram
direction LR
class User {
    - Integer Id
    - String Name
    - String DisplayName
    - String UserName
    - String Email
    - String Password
    - UserRole role
    - Integer numberOfSales
    + User(Integer id, String name, String displayName, String userName, String email, String password, UserRole role, Integer numberOfSales)
    + Integer getId()
    + void setId(Integer Id)
    + String getName()
    + void setName(String Name)
    + String getDisplayName()
    + void setDisplayName(String DisplayName)
    + String getUserName()
    + void setUserName(String UserName)
    + String getEmail()
    + void setEmail(String Email)
    + String getPassword()
    + void setPassword(String Password)
    + UserRole getRole()
    + void setRole(UserRole role)   
    + Integer getNumberOfSales()
    + void setNumberOfSales(Integer numberOfSales)
    
}

class Post {
    - Integer Id
    - Integer ownerId
    - String Name
    - String Brand
    - String TypeOfClothing
    - String Description
    - double Price
    - String condition
    - String size
    - String location
    - PostStatus status
    - String ImageId
    - LocalDate datePostedAt
    + ClothingItem(Integer Id, Integer ownerId, String name, String brand, String typeOfClothing, String description, double price, String condition, String size, String location, PostStatus status, Image image, LocalDate dateOfPost)
    + Integer getId()
    + void setId(Integer Id)
    + Integer getownerId()
    + void setownerId(Integer ownerId)
    + String getName()
    + void setName(String Name)
    + String getBrand()
    + void setBrand(String Brand)
    + String getTypeOfClothing()
    + void setTypeOfClothing(String TypeOfClothing)
    + String getDescription()
    + void setDescription(String Description)
    + double getPrice()
    + void setPrice(Double Price)
    + String getCondition()
    + void setCondition(String condition)
    + String getSize()
    + void setSize(String size)
    + String getLocation()
    + void setLocation(String location)
    + PostStatus getStatus()
    + void setStatus(PostStatus status)
    + Image getImage()
    + void setImage(Image Image)    
    + LocalDate getDateOfPost()
    + void setDateOfPost(LocalDate dateOfPost)
}

class UserRepository {
    + User write(User user)
    + Optional<User> findById(int id)
    + Optional<User> findByEmail(String email)
    + List<User> findAll()
    + void update(User user)
    + void delete(int id)
}

class IUserRepository {
    + User write(User user)
    + Optional<User> findById(int id)
    + Optional<User> findByEmail(String email)
    + List<User> findAll()
    + void update(User user)
    + void delete(int id)
}

class IUserService {
    + List<User> allUsers()
    + Optional<User> getUser(int id)
    + Optional<User> login(String email, String password)
    + Optional<User> register(User user)
    + bool deleteUser(User executingUser, User targetUser)
    + bool updateUser(User executingUser, User targetUser)
    + bool updatePassword(User executingUser, User targetUser, String password)
    + bool updateRole(User executingUser, User targetUser, UserRole role)
}

class UserService {
    + List<User> allUsers()
    + Optional<User> getUser(int id)
    + Optional<User> login(String email, String password)
    + Optional<User> register(User user)
    + bool deleteUser(User executingUser, User targetUser)
    + bool updateUser(User executingUser, User targetUser)
    + bool updatePassword(User executingUser, User targetUser, String password)
    + bool updateRole(User executingUser, User targetUser, UserRole role)
}

class UserController {
    + getUser(@PathVariable int id)
    + getAllUsers()
    + updateUser(@PathVariable int id, @RequestBody User user)
    + deleteUser(@PathVariable int id)
    + updatePassword(@PathVariable int id, @RequestBody String password)
    + updateRole(@PathVariable int id, @RequestBody UserRole role)
}

class IPostRepository {
    + Post write(Post post)
    + Optional<Post> findById(int id)
    + Optional<Post> findByOwnerId(String ownerId)
    + List<Post> findAll()
    + List<Post> findAllByOwnerId()
    + void update(Post post)
    + void delete(int id)
}

class PostRepository {
    + Post write(Post post)
    + Optional<Post> findById(int id)
    + List<Post> findAll()
    + List<Post> findAllByOwnerId(String ownerId)
    + void update(Post post)
    + void delete(int id)
}

class IPostService {
    + Post createPost(Post post);
    + Optional<Post> getPostById(int id);
    + List<Post> getPostsByOwnerId(String ownerId);
    + List<Post> getAllPosts();
    + void updatePost(Post post);
    + void deletePost(int id);
    + void markAsSold(int postId);
    + void markAsReserved(int postId)
}

class PostService {
    + Post createPost(Post post);
    + Optional<Post> getPostById(int id);
    + List<Post> getPostsByOwnerId(String ownerId);
    + List<Post> getAllPosts();
    + void updatePost(Post post);
    + void deletePost(int id);
    + void markAsSold(int postId);
    + void markAsReserved(int postId)
}

class PostController {
    + createPost(@RequestBody Post post)
    + getAllPosts()
    + getPostById(@PathVariable int id)
    + updatePost(@PathVariable int id, @RequestBody Post post)
    + deletePost(@PathVariable int id)
    + markAsSold(@PathVariable int postId)
    + markAsReserved(@PathVariable int postId)
}



class DatabaseConfig {
    - String dbUrl
    - String dbUsername
    - String dbPassword
    + Connection getConnection()
}



class UserRole {
    <<enumeration>>
    + User
    + Seller
    + Employee
    + Admin
    
    - Integer rank
    + Role(Integer rank)
    + bool isAtleast(UseRole role)
}

class PostStatus {
    <<enumeration>>
    + Available
    + Reserved
    + Sold
}


%% Interface implementations
    UserService ..|> IUserService : Implements
    UserRepository ..|> IUserRepository : Implements
    PostService ..|> IPostService : Implements
    PostRepository ..|> IPostRepository :Implements

%% Dependency injections
    UserController --> UserService
    UserService --> IUserRepository
    PostController --> PostService
    PostService --> IPostRepository

%% Relationships
    User "1" --> "many" Post : owns
    Post --> User : ownerId (via id)
    Post "1" -- "1" PostStatus : "has a status"
    User "1" -- "1" UserRole : "has a role"
    

```
