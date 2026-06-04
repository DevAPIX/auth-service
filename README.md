# DevAPIX - Authentication Service

## Overview
The Authentication Service provides robust, centralized security for the entire DevAPIX platform. It handles user registration, login, token generation, and validation, ensuring that all API requests are properly authenticated and authorized.

## Key Features
- **User Identity Management**: Secure registration and management of developers, API providers, and consumers.
- **JWT (JSON Web Token)**: Issues and validates stateless JWTs for secure, scalable authentication across all microservices.
- **Role-Based Access Control (RBAC)**: Enforces access policies to restrict administrative actions, API publishing, and consumption based on user roles.
- **Secure Password Hashing**: Implements industry-standard cryptographic algorithms for storing user credentials.

## Technology Stack
- **Framework**: Spring Boot 3
- **Security**: Spring Security
- **Database**: PostgreSQL
- **Service Discovery**: Netflix Eureka Client
