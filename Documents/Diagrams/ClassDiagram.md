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

```mermaid
classDiagram
direction BT
class AdminController {
  + getUsersPage(HttpSession, Model) String
  + updateUserRole(int, UserRole, HttpSession) String
  + getAdminPanel(HttpSession, Model) String
}
class AuthController {
  + loginRequest(String, String, Optional~String~, HttpSession, Model) String
  + registerRequest(RegisterUser, Optional~String~, HttpSession, Model) String
  + logout(HttpSession) String
  + authPage(Optional~String~, Model) String
  + authPageRedirect(String, Optional~String~, Model) String
}
class AuthFilter {
  + doFilter(ServletRequest, ServletResponse, FilterChain) void
}
class Brand {
  - Integer id
  - String name
  + toString() String
   String name
   Integer id
}
class Condition {
<<enumeration>>
  + isAtLeast(Condition) boolean
  + values() Condition[]
  + valueOf(String) Condition
}
class DatabaseConfig {
   Connection connection
}
class DisplayNameValidator {
  + isValid(String) boolean
}
class DisplayNameValidatorTest {
  + isValid_tooLong() void
  + isValid_validDisplayName() void
  + isValid_tooShort() void
  + isValid_maxLength() void
  + isValid_minLength() void
}
class EmailInUseException
class EmailNotFoundException
class EmailValidator {
  + isValid(String) boolean
}
class EmailValidatorTest {
  + isValid_missingAt() void
  + isValid_edgeCaseMail() void
  + isValid_zeroDotThrows() void
  + isValid_validEmail() void
  + isValid_multipleAtSeperators() void
}
class FavoriteRepository {
  + remove(int, int) void
  + insert(int, int) void
  + getNumberOfFavoritesByPost(int) int
}
class FilterConfig {
  + authenticateFilter() FilterRegistrationBean~AuthFilter~
  + loggedInFilter() FilterRegistrationBean~LoggedInFilter~
}
class GeneralNode~T~ {
  - T value
   Integer id
   Optional~Integer~ parentId
   T value
}
class GilbertApplication {
  + main(String[]) void
}
class GilbertApplicationTests {
  ~ contextLoads() void
}
class GlobalControllerAdvice {
  + categoryTree() Tree
  + getCurrentUser(HttpSession) Optional~User~
}
class HomeController {
  + home() String
}
class IFavoriteRepository {
<<Interface>>
  + insert(int, int) void
  + remove(User, int) void
  + getNumberOfFavoritesByPost(Post) int
  + insert(int, Post) void
  + getNumberOfFavoritesByPost(int) int
  + insert(User, Post) void
  + insert(User, int) void
  + remove(int, Post) void
  + remove(User, Post) void
  + remove(int, int) void
}
class IPostRepository {
<<Interface>>
  + search(String, String[], String[]) List~Post~
  + write(Post) Post
  + getUserFavorites(User) List~Post~
  + findByOwnerId(int) List~Post~
  + findAll() List~Post~
  + update(Post) void
  + findById(int) Optional~Post~
  + delete(int) void
  + getUserFavorites(int) List~Post~
   List~ProductType~ allProductTypes
   List~Brand~ allBrands
}
class IPostService {
<<Interface>>
  + allPosts() List~Post~
  + findById(int) Optional~Post~
  + createPost(Post) Optional~Post~
  + getPost(int) Optional~Post~
  + editPost(User, Post) boolean
  + deletePost(User, Post) boolean
  + getPostsByOwner(int) List~Post~
  + search(String) List~Post~
   List~ProductType~ allProductTypes
   List~Brand~ allBrands
}
class IStorageRepository {
<<Interface>>
  + store(InputStream) String
  + objectUrl(String) String
  + store(String, InputStream) void
  + delete(String) void
}
class IUserRepository {
<<Interface>>
  + findByField(String, String) Optional~User~
  + findByEmail(String) Optional~User~
  + findByUsername(String) Optional~User~
  + update(User) void
  + findById(int) Optional~User~
  + delete(int) void
  + write(User) User
  + findAll() List~User~
}
class IUserService {
<<Interface>>
  + register(RegisterUser) User
  + getUserByUsername(String) Optional~User~
  + updateUser(User) boolean
  + updatePassword(User, String, String) boolean
  + updateRole(int, UserRole) void
  + removeFavorite(User, int) void
  + deleteUser(User) boolean
  + getProfilePictureUrl(String) String
  + allUsers() List~User~
  + login(String, String) User
  + getFavorites(User) List~Post~
  + getUser(int) Optional~User~
  + getUserByEmail(String) Optional~User~
  + updateProfilePicture(User, MultipartFile) boolean
  + addFavorite(User, int) void
}
class IdAccessorFunction~T~ {
<<Interface>>
  + getId(T) Integer
}
class InvalidDisplayNameException
class InvalidEmailFormatException
class InvalidPasswordException
class InvalidPasswordFormatException
class LoggedInFilter {
  + doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) void
  # shouldNotFilter(HttpServletRequest) boolean
}
class MinioS3Client {
  + objectUrl(String) String
  + delete(String) void
  + store(String, InputStream) void
}
class ParentIdAccessorFunction~T~ {
<<Interface>>
  + getParentId(T) Optional~Integer~
}
class PasswordError {
<<enumeration>>
  + values() PasswordError[]
  + valueOf(String) PasswordError
}
class PasswordValidator {
  + isValid(String) boolean
}
class PasswordValidatorTest {
  + isValid_tooShort() void
  + isValid_allErrors() void
  + isValid_missingUpperCase() void
  + isValid_minimumViable() void
  + isValid_missingLowerCase() void
  + isValid_validPassword() void
  + isValid_missingNumber() void
}
class PersonalProfileController {
  + profilePage(HttpSession, Model) String
  + accountSettingsPage(HttpSession, Model) String
  + addFavorite(int, HttpSession) String
  + updateDetails(String, String, HttpSession) String
  + favorite(HttpSession, Model) String
  + settingsPage(HttpSession, Model) String
  + updateProfilePicture(MultipartFile, HttpSession) String
  + updatePasswordSetting(String, String, HttpSession) String
  + deleteFavorite(int, HttpSession) String
  + deleteAccountSetting(HttpSession) String
}
class Post {
  - String imageId
  - String location
  - String size
  - Condition condition
  - String ownerDisplayName
  - String name
  - ProductType typeOfClothing
  - int id
  - int ownerId
  - Brand brand
  - PostStatus status
  - String description
  - Date datePostedAt
  - double price
   String description
   String location
   int ownerId
   Brand brand
   ProductType typeOfClothing
   double price
   Optional~String~ imageId
   String size
   String name
   int id
   Date datePostedAt
   PostStatus status
   String ownerDisplayName
   Condition condition
}
class PostController {
  + getAllPosts(Model) String
  + getPost(int, Model) String
  + deletePost(int, HttpSession, Model) String
  + updatePost(int, Post, HttpSession, Model) String
  + createPostPage(HttpSession, Model) String
  + editPostPage(int, HttpSession, Model) String
  + createPost(Post, HttpSession, Model) String
}
class PostRepository {
  + getUserFavorites(int) List~Post~
  - productTypeFromResultSet(ResultSet) Optional~ProductType~
  + update(Post) void
  - brandFromResultSet(ResultSet) Optional~Brand~
  + findByOwnerId(int) List~Post~
  + delete(int) void
  + search(String, String[], String[]) List~Post~
  + write(Post) Post
  + findById(int) Optional~Post~
  + findAll() List~Post~
  ~ postFromResultSet(ResultSet) Optional~Post~
   List~ProductType~ allProductTypes
   List~Brand~ allBrands
}
class PostService {
  + getPostsByOwner(int) List~Post~
  + deletePost(User, Post) boolean
  + findById(int) Optional~Post~
  + createPost(Post) Optional~Post~
  + allPosts() List~Post~
  + getPost(int) Optional~Post~
  + editPost(User, Post) boolean
  + search(String) List~Post~
   List~ProductType~ allProductTypes
   List~Brand~ allBrands
}
class PostStatus {
<<enumeration>>
  + valueOf(String) PostStatus
  + isAtLeast(PostStatus) boolean
  + values() PostStatus[]
   PostStatus default
}
class PostWriteException
class ProductType {
  - String name
  - Integer id
  - Integer parentId
   String name
   Integer id
   Optional~Integer~ parentId
}
class ProfileController {
  + ordersPage(String, HttpServletResponse, HttpServletRequest, HttpSession, Model) String
  + profilePicture(HttpServletRequest) String
  + postsPage(String, HttpServletResponse, HttpServletRequest, HttpSession, Model) String
  + profilePage(HttpServletResponse, HttpServletRequest, HttpSession, Model) String
  + favoritesPage(String, HttpServletRequest, HttpSession, Model) String
  + salesPage(String, HttpServletResponse, HttpServletRequest, HttpSession, Model) String
}
class ProfileInterceptor {
  + preHandle(HttpServletRequest, HttpServletResponse, Object) boolean
}
class RegisterUser {
  - String email
  - String password
  - String displayName
   String password
   boolean acceptedTerms
   String email
   String displayName
}
class SearchController {
  + mainSearchPage(Optional~String~, Optional~String[]~, Model) String
}
class SecurityConfig {
  + filterChain(HttpSecurity) SecurityFilterChain
}
class StorageConfig {
  + s3Client() MinioClient
}
class ThymeleafConfig {
  + localeResolver() LocaleResolver
  + localeChangeInterceptor() LocaleChangeInterceptor
  + messageSource() MessageSource
  + layoutDialect() LayoutDialect
}
class Tree~T~ {
  - SequencedSet~Integer~ roots
  + getNode(Integer) Optional~T~
  + getChildrenNodes(Integer) List~T~
  - getChildren(T) Set~Integer~
  + getChildrenNodes(T) List~T~
  - getChildren(Integer) Set~Integer~
   List~T~ roots
}
class TreeNode {
<<Interface>>
   Integer id
   Optional~Integer~ parentId
}
class User {
  - String username
  - String displayName
  - String passwordHash
  - Date termsAcceptedDate
  - String email
  - int id
  - String profilePictureId
  - UserRole role
  + generateUsernameSuffix(int) String
  + generateUsername(String) String
  + checkPassword(String) boolean
  + hashPassword() void
   String passwordHash
   String password
   Date termsAcceptedDate
   int id
   String email
   UserRole role
   String username
   String displayName
   Optional~String~ profilePictureId
}
class UserNotFoundException
class UserRepository {
  + update(User) void
  + write(User) User
  + findByField(String, String) Optional~User~
  + delete(int) void
  ~ userFromResultSet(ResultSet) Optional~User~
  + findAll() List~User~
}
class UserRole {
<<enumeration>>
  + isAtLeast(UserRole) boolean
  + valueOf(String) UserRole
  + values() UserRole[]
}
class UserService {
  + removeFavorite(User, int) void
  + getUserByUsername(String) Optional~User~
  + allUsers() List~User~
  + addFavorite(User, int) void
  + updateProfilePicture(User, MultipartFile) boolean
  + getUser(int) Optional~User~
  + updatePassword(User, String, String) boolean
  + updateRole(int, UserRole) void
  + updateUser(User) boolean
  + getFavorites(User) List~Post~
  + register(RegisterUser) User
  + getProfilePictureUrl(String) String
  + getUserByEmail(String) Optional~User~
  + login(String, String) User
  + deleteUser(User) boolean
}
class UserServiceTest {
  + beforeEach() void
  + register_validUser() void
  + register_throwsEmailAlreadyInUser() void
}
class UserWriteException
class WebConfig {
  + addInterceptors(InterceptorRegistry) void
}

AdminController "1" *--> "userService 1" IUserService 
AuthController "1" *--> "userService 1" IUserService 
FavoriteRepository "1" *--> "databaseConfig 1" DatabaseConfig 
FavoriteRepository  ..>  IFavoriteRepository 
GeneralNode~T~ "1" *--> "idAccessor 1" IdAccessorFunction~T~ 
GeneralNode~T~ "1" *--> "parentIdAccessor 1" ParentIdAccessorFunction~T~ 
GeneralNode~T~  ..>  TreeNode 
GlobalControllerAdvice "1" *--> "postService 1" IPostService 
GlobalControllerAdvice  ..>  Tree~T~ : «create»
InvalidPasswordFormatException "1" *--> "errors *" PasswordError 
MinioS3Client  ..>  IStorageRepository 
PersonalProfileController "1" *--> "userService 1" IUserService 
Post "1" *--> "brand 1" Brand 
Post "1" *--> "condition 1" Condition 
Post "1" *--> "status 1" PostStatus 
Post "1" *--> "typeOfClothing 1" ProductType 
PostController "1" *--> "postService 1" IPostService 
PostRepository "1" *--> "databaseConfig 1" DatabaseConfig 
PostRepository  ..>  IPostRepository 
PostService "1" *--> "postRepository 1" IPostRepository 
PostService  ..>  IPostService 
ProfileController "1" *--> "postService 1" IPostService 
ProfileController "1" *--> "userService 1" IUserService 
ProfileInterceptor "1" *--> "userService 1" IUserService 
SearchController "1" *--> "postService 1" IPostService 
Tree~T~  ..>  GeneralNode~T~ : «create»
Tree~T~  ..>  TreeNode 
User "1" *--> "role 1" UserRole 
UserRepository "1" *--> "databaseConfig 1" DatabaseConfig 
UserRepository  ..>  IUserRepository 
UserService "1" *--> "favoriteRepository 1" IFavoriteRepository 
UserService "1" *--> "postRepository 1" IPostRepository 
UserService "1" *--> "storageRepository 1" IStorageRepository 
UserService "1" *--> "userRepository 1" IUserRepository 
UserService  ..>  IUserService 
UserServiceTest "1" *--> "mockFavoriteRepo 1" IFavoriteRepository 
UserServiceTest "1" *--> "mockPostRepo 1" IPostRepository 
UserServiceTest "1" *--> "mockStorRepo 1" IStorageRepository 
UserServiceTest "1" *--> "mockRepo 1" IUserRepository 
UserServiceTest "1" *--> "userService 1" UserService 
WebConfig "1" *--> "profileInterceptor 1" ProfileInterceptor 
```
