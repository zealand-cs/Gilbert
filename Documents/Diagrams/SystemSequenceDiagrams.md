# System sequence diagrams for Gilbert


## Create User

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Register user
alt Invalid credentials/email already used
    System ->> User: Invalid registration. Try again.
else
    System ->> User: Registration complete
end
```

## Delete User

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Delete user
alt Invalid permissions
    System ->> User: You are not allowed to delete the user
else
    System ->> User: User gets deleted
end
```
 
## Edit User

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Edit user
alt Invalid permissions
    System ->> User: You are not allowed to edit the user
else
    System ->> User: User gets modified
end
```

## Create Post

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Create post
alt Error occured
    System ->> User: Something went wrong when making post
else
    System ->> User: Post created successfully
end
```

## Delete Post

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Delete post
alt Error occured
    System ->> User: Something went wrong when deleting post
else
    System ->> User: Post deleted successfully
end
```

## Edit Post

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Edit post
alt Error occured
    System ->> User: Something went wrong when editing post
else
    System ->> User: Post modified successfully
end
```
 
## Login

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Login
alt Error occured
    System ->> User: Something went wrong logging in, wrong credentials
else
    System ->> User: Login successful
end
```
 
## Logout

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Logout
alt Error occured
    System ->> User: Something went wrong logging out
else
    System ->> User: Logout successful
end
```
 
## Add favorites

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: Add favorite
alt Error occured
    System ->> User: Something went wrong when favoriting item
else
    System ->> User: Favorite successful
end
```
 
## Remove favorites

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: remove favorite
alt Error occured
    System ->> User: Something went wrong when removing item from favorites
else
    System ->> User: deletion of favorited item successful
end
```

## See favorites

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: see favorites
alt Error occured
System ->> User: Something went wrong when viewing favorites
else
System ->> User: Favorites shown successfully
end
```
 
## Searching/sorting

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: search/sort
alt Error occured
    System ->> User: Something went wrong when searching/sorting
else
    System ->> User: Searched/sorted item shown successfully
end
```
  
## Buy

```mermaid
sequenceDiagram
actor User
participant System

User ->> System: buying
alt Error occured
    System ->> User: Something went wrong when buying
else
    System ->> User: Bought item successfully
end
```


