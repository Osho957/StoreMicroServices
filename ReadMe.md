# StoreMicroservices

## Overview

This document provides a comprehensive overview of the functional requirements and high-level design (HLD) for an ecommerce website. The system leverages a microservices architecture to ensure scalability, flexibility, and maintainability. Key components include user management, product catalog, cart & checkout, order management, payment processing, and authentication.

## Functional Requirements

### 1. User Management
- **Registration**: Allow new users to create an account using their email or social media profiles.
- **Login**: Users should be able to securely log in using their credentials.
- **Profile Management**: Users should have the ability to view and modify their profile details.
- **Password Reset**: Users must have the option to reset their password through a secure link.

### 2. Product Catalog
- **Browsing**: Users should be able to browse products by different categories.
- **Product Details**: Detailed product pages with product images, descriptions, specifications, and other relevant information.
- **Search**: Users must be able to search for products using keywords.

### 3. Cart & Checkout
- **Add to Cart**: Users should be able to add products to their cart.
- **Cart Review**: View selected items in the cart with price, quantity, and total details.
- **Checkout**: Seamless process to finalize the purchase, including specifying delivery address and payment method.

### 4. Order Management
- **Order Confirmation**: After making a purchase, users should receive a confirmation with order details.
- **Order History**: Users should be able to view their past orders.
- **Order Tracking**: Provide users with a way to track their order's delivery status.

### 5. Payment
- **Multiple Payment Options**: Support for credit/debit cards, online banking, and other popular payment methods.
- **Secure Transactions**: Ensure user trust by facilitating secure payment transactions.
- **Payment Receipt**: Provide users with a receipt after a successful payment.

### 6. Authentication
- **Secure Authentication**: Ensure that user data remains private and secure during login and throughout their session.
- **Session Management**: Users should remain logged in for a specified duration or until they decide to log out.

## High-Level Design (HLD)

### Architecture Components
- **Load Balancers (LB)**
- **API Gateway**
- **Microservices**
- **Databases (Relational and NoSQL)**
- **Message Broker (Kafka)**
- **Caching (Redis)**
- **Search and Analytics (Elasticsearch)**

### 1. Load Balancers (LB)
- **Function**: Distribute incoming user requests across multiple server instances to balance load and ensure high availability.
- **Tool**: Amazon Elastic Load Balancing (ELB).

### 2. API Gateway
- **Function**: Entry point for clients. Routes requests to the right microservices, handles rate limiting, and manages authentication.
- **Tool**: Kong.

### 3. Microservices Architecture
#### 3.1 User Management Service
- **Function**: Handles user registration, login, profile management, and password reset.
- **Database**: MySQL for structured user data.
- **Communication**: Uses Kafka to communicate relevant user activities to other services.

#### 3.2 Product Catalog Service
- **Function**: Manages product listings, details, and categorization.
- **Database**: MySQL.
- **Search**: Incorporates Elasticsearch for fast product searches.

#### 3.3 Cart Service
- **Function**: Manages user's shopping cart.
- **Database**: MongoDB for flexible cart structures.
- **Caching**: Uses Redis for fast, in-memory data access.

#### 3.4 Order Management Service
- **Function**: Handles order processing, history, and tracking.
- **Database**: MySQL.
- **Communication**: Interacts with Payment Service and User Management Service through Kafka.

#### 3.5 Payment Service
- **Function**: Manages payment gateways and transaction logs.
- **Database**: MySQL.
- **Communication**: Produces messages to Kafka to notify the Order Management Service upon payment confirmation.

#### 3.6 Notification Service
- **Function**: Manages email and potentially other notifications (e.g., SMS).
- **Communication**: Consumes Kafka messages for events requiring user notifications.
- **Integration**: Integrates with third-party platforms like Amazon SES for email delivery.

### 4. Databases
- **MySQL**: For structured data.
- **MongoDB**: For flexible, unstructured data.

### 5. Kafka
- **Function**: Central message broker allowing asynchronous communication between microservices, ensuring data consistency, and acting as an event store for critical actions.

### 6. Caching with Redis
- **Usage**: Primarily by Cart Service for faster response times.

### 7. Elasticsearch
- **Usage**: Used by Product Catalog for fast and relevant product searches.

## Typical Flow with Kafka & Elasticsearch Integration

### Part 1: User Interaction
1. User logs in and searches for a product.
2. Request reaches LB, then passed to API Gateway.
3. API Gateway routes the search request to Product Catalog Service.
4. Product Catalog Service queries Elasticsearch for a fast product search.

### Part 2: Cart Interaction
1. User adds a product to the cart.
2. Cart Service produces a message to Kafka about this action.

### Part 3: Checkout Process
1. User checks out, triggering the Order Management Service.
2. After placing the order, a message is sent to Kafka.
3. Payment Service consumes the Kafka message to process payment.

## Conclusion

This PRD and HLD outline the essential functional requirements and high-level design for developing a scalable and efficient ecommerce website. By leveraging a microservices architecture and integrating tools like Kafka, Elasticsearch, and Redis, the system ensures high performance, reliability, and user satisfaction.
