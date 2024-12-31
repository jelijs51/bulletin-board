# Bulletin Board API

A simple Spring Boot-based RESTful API for managing a bulletin board system. The API allows users to create, read, update, delete, and interact with posts.

## Features

- **Create Posts**: Add a new post to the bulletin board.
- **Read Posts**: Fetch individual or all posts.
- **Update Posts**: Modify existing posts.
- **Delete Posts**: Mark posts as deleted.
- **Increment Views**: Increase the view count for a post.

# How to run
- ./mvnw install
- Start postgresql
- Run DB creation schema
- Setup environtment variable (DB_USERNAME, DB_PASSWORD, DB_URL)
- ./mvnw spring-boot:run