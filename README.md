## 🚀 Spring Boot + MongoDB + Redis Caching Example

This project demonstrates how to use Spring Boot, Redis Cache, and MongoDB together in a simple user lookup + update workflow.

Features included:

✅ MongoDB User Repository
✅ Redis Caching with TTL
✅ @Cacheable for caching user lookups
✅ @CacheEvict for removing stale cache on updates
✅ REST APIs to test the caching behavior

## 📌 Project Structure
src/main/java/org/main/cacheseries4
│
├── config
│   └── RedisConfig.java
│
├── controller
│   └── UserController.java
│
├── entity
│   └── User.java
│
├── repo
│   └── UserRepo.java
│
└── service
    └── UserService.java

## ⚙️ Technologies Used

Java 17+

Spring Boot 3+

Spring Data MongoDB

Spring Cache

Redis (Lettuce Client)

MongoDB

Docker (optional)

## 🧰 Redis Configuration

RedisConfig.java configures:

1 minute TTL for cache entries

JSON Serialization (GenericJackson2JsonRedisSerializer)

String serializer for keys

## 🗄️ MongoDB Repository

UserRepo.java includes:

User findByEmail(String email);


Used by the service layer for lookup and updates.

## 🧠 Service Layer Caching Logic
✔ Cache Read
@Cacheable(value = "users", key = "#email")
public User findByEmail(String email)


First call → fetches from MongoDB and stores in Redis

Next calls → returned directly from Redis

✔ Cache Evict on Update
@CacheEvict(value="users", key="#oldEmail")
public void updateEmail(String oldEmail, String newEmail)


Removes the old cached entry

Updates the email inside MongoDB

(Note): Cache for the new email is populated next time findByEmail(newEmail) is called

🌐 REST API Endpoints
1️⃣ Get User by Email (Cached)
GET http://localhost:8080/main/getuser/{email}

Example:
GET http://localhost:8080/main/getuser/john@gmail.com


Console Output:

First call →

*************FindByEmail Method Executed***************


Second call →
(No log → served from cache)

2️⃣ Update Email (Evicts Old Cache)
POST http://localhost:8080/main/update-email?oldEmail=a@gmail.com&newEmail=b@gmail.com

Example:
POST http://localhost:8080/main/update-email?oldEmail=john@gmail.com&newEmail=john123@gmail.com


After update:

Cache entry for oldEmail is removed

MongoDB document updated

New cache entry will be created on next findByEmail(newEmail) call

🧪 Testing Using Postman
Fetch User
GET /main/getuser/john@gmail.com

Update Email
POST /main/update-email?oldEmail=john@gmail.com&newEmail=john123@gmail.com

Recheck Cache Behavior

Call old email → should return null / no data

Call new email → DB hit once, then cached

## 🐳 Running Redis in Docker (Optional)

Run Redis with a single command:

docker run --name redis-dev -p 6379:6379 -d redis:7

## 📦 application.properties (Example)
spring.data.mongodb.uri=mongodb://localhost:27017/cache-demo
spring.cache.type=redis
spring.redis.host=localhost
spring.redis.port=6379

## 📝 User Document Structure

Example MongoDB document:

{
  "_id": {
    "$oid": "68c14583bcee683bdac61756"
  },
  "firstname": "venkatesh",
  "lastname": "pardeshi",
  "email": "vrp@gmail.com",
  "password": "$2a$10$d10qCWf4pipOhUb.TuMls.pSuSgMILRFMW4rUGdUuRv/SEi1mPZtm",
  "enabled": true,
  "roles": [
    "ROLE_USER"
  ],
  "_class": "org.main.cacheseries4.entity.User"
}
