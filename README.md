# java-filmorate

![Image alt](https://github.com/EvgenyaOgoreltseva/java-filmorate/blob/main/diagram.jpg)

### Примеры запросов для основных операций приложения:

#### Получение логина пользователя по id

```
SELECT login
FROM users
WHERE user_id = 1;
```

#### Получение описания фильма по id

```
SELECT description
FROM films
WHERE film_id = 1;
```

#### Получение количества друзей пользователя по id

```
SELECT COUNT(friend_id)
FROM friends 
WHERE user_id = 1;
```