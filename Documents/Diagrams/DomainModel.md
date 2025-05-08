# Domain model

```mermaid
---
title: Domain model for Gilbert
---

classDiagram
class User {
    id
    display_name
    username
    email
    password
    role
    followers
    following
}

class SellerPost {
    id
    seller_id
    name
    brand
    condition
    sold
}

class BuyerOrders {
    id
    buyer_id

}

class Notification {
    type
    title_label
    description
    image_url
    link_to
}

class Follower {
    id
    name
    since
}

class Favorite {
    post_id
    added_time
}
```
