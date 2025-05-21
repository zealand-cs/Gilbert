# PackageDiagram

```mermaid
graph TD

    subgraph templates [Templates GUI Layer]
        ThymeleafTemplates
    end

    subgraph presentation [Presentation Layer]
        Controllers
    end

    subgraph application [Application Layer]
        Services
    end

    subgraph domain [Domain Layer]
        Entities
    end

    subgraph infrastructure [Infrastructure Layer]
        Repositories
        MySQL
    end
+

  %% Afhængigheder
  ThymeleafTemplates --> Controllers
  Controllers --> Services
  Services --> Entities
  Services --> Repositories
  Repositories --> Entities
  Repositories --> MySQL
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
