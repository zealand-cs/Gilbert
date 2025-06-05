# PackageDiagram

```mermaid
graph TD

    subgraph templates [Templates GUI Layer]
        ThymeleafTemplates
    end

    subgraph presentation [Presentation Layer]
        Controllers
        UserController
        PostController
        AuthController
        SearchController
        ProfileController
        HomeController
    end

    subgraph application [Application Layer]
        Services
        UserService
        PostService
        IPostService
        IUserService
    end

    subgraph domain [Domain Layer]
        Entities
        Post
        User
        RegisterUser
        Brand
        Condition
        PostStatus
        ProductType
    end

    subgraph infrastructure [Infrastructure Layer]
        Repositories
        IUserRepository
        UserRepository
        IPostRepository
        PostRepository
        IFavoriteRepository
        FavoriteRepository
        MySQL
    end
+

  %% Afhængigheder
  ThymeleafTemplates --> Controllers
  Controllers --> Services
  Services --> Entities
  Services --> Repositories
  Repositories --> Entities
```

## 🧠 Lag-forklaring

| Lag | Indhold | Funktion |
|-----|---------|----------|
| **Templates (GUI)** | Thymeleaf HTML-filer | Præsenterer data for brugeren |
| **Presentation Layer** | Controllers | Behandler HTTP-forespørgsler, videresender til services og returnerer views |
| **Application Layer** | Services | Indeholder forretningslogik og koordination |
| **Domain Layer** | Entities | Repræsenterer kerneobjekter og domænemodellen |
| **Infrastructure Layer** | Repositories, MySQL | Dataadgang og vedvarende lagring (database) |
```
