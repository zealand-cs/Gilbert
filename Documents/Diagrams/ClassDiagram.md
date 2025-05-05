# Class diagram

```mermaid
---
title: Class diagram for Gilbert
---

classDiagram
direction LR
class User {
    - Integer Id
}

class Role {
    <<enumeration>>
    User
    Employee
    Admin
}
```
