# Chat-backend
 
This is a backend for chat application written in Java using Spring.

Url: [https://athever21-chat-backend.herokuapp.com/]()

---

```
Users paths:
  - /users path:
    - Methods:
      - get (return all users)
      - post (create user)
        - body : {"username": "value", "password": "value"}
  - /login:
    - Methods:
      - post (log user, returns token)
        - body : {"username": "value", "password": "value"}
  - /refresh_token path:
    - Methods:
      - post(need refresh cookie, return token)
  - /users/{id}:
    - Methods:
      - put(change user, require token auth)
        - body : {"username": "value", "password": "value", "img": "value"}
      - delete(deletes user, require token auth)
```
---
```
Channel paths:
  - /channels path:
    - Methods:
      - get (return all channels)
      - post (create channel, require token auth)
        - body : {"name": "value"}
  - /channels/{id}:
    - Methods:
      - get(return single channel)
      - put(changes channel, require token auth)
        - body : {"name": "value"}
      - delete(delete channel, requires token auth)
```
---
```
Message paths:
  - /messages path:
    - Methods:
      - get (return messages from channel)
        - path variables:
          - channel - channel id
          - page - determinate page, default = 0, page size = 20
      - post (create message, require token auth)
        - body : {"channelId": "value", "message": "value"}
  - /message/{id}:
    - Methods:
      - delete(delete channel, requires token auth)
```