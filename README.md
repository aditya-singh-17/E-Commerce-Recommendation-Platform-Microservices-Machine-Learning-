# E-Commerce-Recommendation-Platform-Microservices-Machine-Learning-
Complete Backend project
# 🚀 E-Commerce Recommendation Platform (Microservices + Machine Learning)

## 📌 Project Overview

This project is a **production-inspired E-Commerce Recommendation Platform** built using a **Microservices Architecture** and a **Machine Learning Recommendation Engine**.

The system demonstrates modern backend engineering concepts including:

* Microservices Architecture
* Event Driven Communication
* Apache Kafka Messaging
* JWT Authentication
* Service-to-Service Communication
* Machine Learning Based Recommendations
* REST APIs
* Database Per Service Pattern

The goal of the project is to simulate how large-scale e-commerce platforms such as Amazon, Flipkart, and Myntra manage users, products, orders, payments, and personalized recommendations.

---

# 🏗 System Architecture

```text
                    +----------------+
                    |     Client     |
                    +--------+-------+
                             |
                             v

                    +----------------+
                    | User Service   |
                    | Authentication |
                    +--------+-------+
                             |
                             |
                             v

+-------------+     +----------------+     +----------------+
| Product     |<--->| Order Service  |<--->| Recommendation |
| Service     |     | Business Logic |     | Service (ML)   |
+------+------+     +--------+-------+     +----------------+
       |                     |
       |                     |
       v                     v
   MySQL DB             Apache Kafka
                              |
                              |
                              v
                    +----------------+
                    | Payment Service|
                    +----------------+
```

---

# 🛠 Technology Stack

## Backend

* Java 21
* Spring Boot
* Spring Data JPA
* Spring Web
* WebClient
* Maven

## Database

* MySQL

## Messaging

* Apache Kafka
* Kafka Topics
* Event Driven Architecture

## Machine Learning

* Python
* Pandas
* NumPy
* Scikit-Learn
* TF-IDF Vectorization

## Tools

* Postman
* Docker
* IntelliJ IDEA
* VS Code

---

# 📂 Microservices

The system consists of four major microservices:

---

# 1️⃣ User Service

## Purpose

Responsible for:

* User Registration
* User Login
* Authentication
* JWT Token Generation
* Role Management

---

## Database

```text
users
```

### User Entity

```java
id
name
email
password
role
```

---

## APIs

### Register User

```http
POST /api/users/register
```

Request

```json
{
  "name":"Aditya",
  "email":"adi@gmail.com",
  "password":"1234"
}
```

---

### Login User

```http
POST /api/users/login
```

Request

```json
{
  "email":"adi@gmail.com",
  "password":"1234"
}
```

Response

```text
JWT Token
```

---

# 2️⃣ Product Service

## Purpose

Manages the product catalog.

---

## Responsibilities

* Add Product
* Search Product
* Get Product Details
* Category Based Search

---

## Database

```text
product_db
```

### Product Entity

```java
id
name
category
price
stock
```

---

## APIs

### Add Product

```http
POST /api/products
```

### Get Product

```http
GET /api/products/{id}
```

### Get All Products

```http
GET /api/products
```

### Search Product

```http
GET /api/products/search
```

---

# 3️⃣ Order Service

## Purpose

Core business logic service.

Acts as the central coordinator of the system.

---

## Responsibilities

* Create Orders
* Validate Products
* Calculate Total Cost
* Publish Kafka Events
* Update Order Status

---

## Database

```text
order_db
```

---

## Order Entity

```java
id
userId
totalAmount
status
```

---

## Order Item Entity

```java
id
productId
quantity
price
orderId
```

---

## Service-to-Service Communication

Uses Spring WebClient.

### Flow

```text
Order Service
        |
        |
        v
Product Service
```

The Order Service fetches:

* Product Price
* Product Availability
* Stock Information

before creating an order.

---

## Order Flow

```text
Create Order
      |
      |
      v
Fetch Product Details
      |
      |
      v
Calculate Amount
      |
      |
      v
Save Order
      |
      |
      v
Publish Kafka Event
```

---

# 4️⃣ Payment Service

## Purpose

Handles asynchronous payment processing.

---

## Responsibilities

* Consume Order Events
* Process Payments
* Publish Payment Events
* Notify Order Service

---

## Kafka Consumer

Consumes:

```text
order-topic
```

---

## Kafka Producer

Publishes:

```text
payment-topic
```

---

# 📡 Apache Kafka Integration

Kafka is used to achieve asynchronous communication between services.

---

## Topics

### order-topic

Used when:

```text
Order Created
```

Message:

```json
{
  "orderId": 1,
  "status": "CREATED"
}
```

---

### payment-topic

Used when:

```text
Payment Processed
```

Message:

```json
{
  "orderId": 1,
  "status": "PAID"
}
```

or

```json
{
  "orderId": 1,
  "status": "FAILED"
}
```

---

# 🔄 Event Driven Flow

```text
User Creates Order
         |
         v

Order Saved
         |
         v

Order Event Published
         |
         v

Kafka (order-topic)
         |
         v

Payment Service Consumes Event
         |
         v

Payment Processing
         |
         v

Kafka (payment-topic)
         |
         v

Order Service Consumes Result
         |
         v

Order Status Updated
```

---

# 🤖 Machine Learning Recommendation Service

The recommendation engine was developed in Python using Amazon product data. The implementation uses TF-IDF based content filtering and product similarity scoring. 

---

## Dataset

The system uses:

```text
amazon_products.csv
amazon_categories.csv
```

The product dataset is merged with category information to enrich product metadata. 

---

## Data Preparation

A subset of:

```text
250,000 products
```

is sampled for model training. 

Feature engineering combines:

* Product Title
* Product Category

into a single textual feature column. 

---

## TF-IDF Vectorization

```python
TfidfVectorizer(
    stop_words='english',
    max_features=10000
)
```

The model generates a sparse TF-IDF matrix representing product content. 

---

## Similarity Computation

Recommendations are generated using:

```python
linear_kernel()
```

which computes cosine similarity between products. 

---

## Ranking Strategy

Products are ranked using a hybrid score:

```python
score =
(stars * 0.4)
+
(log(boughtInLastMonth) * 0.6)
```

This balances:

* Product Quality
* Product Popularity

for more relevant recommendations. 

---

## Model Persistence

The trained artifacts are serialized using Joblib:

```text
tfidf_vectorizer.pkl
tfidf_matrix.pkl
products_df.pkl
```

allowing fast loading during deployment. 

---

## Recommendation Workflow

```text
User Searches Product
           |
           v

Find Matching Product
           |
           v

Generate TF-IDF Vector
           |
           v

Calculate Cosine Similarity
           |
           v

Retrieve Similar Products
           |
           v

Rank Products
           |
           v

Return Top Recommendations
```

---

# 🔐 Security Features

Implemented:

* Password Based Authentication
* JWT Token Generation
* Role Based Design Foundation

Future Improvements:

* Refresh Tokens
* OAuth2
* Spring Security Filters
* API Gateway Authentication

---

# 📊 Design Patterns Used

* Microservices Pattern
* Repository Pattern
* DTO Pattern
* Event Driven Architecture
* Producer Consumer Pattern
* Database Per Service Pattern
* Dependency Injection

---

# 🚀 Future Enhancements

### Infrastructure

* API Gateway
* Service Discovery (Eureka)
* Config Server

### Observability

* ELK Stack
* Prometheus
* Grafana

### Recommendation System

* Collaborative Filtering
* Matrix Factorization
* Deep Learning Recommendations
* User Behavior Tracking
* Real-Time Recommendations

### Cloud Deployment

* Docker
* Kubernetes
* AWS ECS
* AWS RDS
* AWS MSK (Kafka)

---

# 🎯 Key Learning Outcomes

This project demonstrates practical experience with:

* Spring Boot Microservices
* REST API Design
* Kafka Messaging
* Event Driven Systems
* JWT Authentication
* MySQL Integration
* Service-to-Service Communication
* Machine Learning Recommendation Systems
* TF-IDF Based Similarity Search
* Production-Oriented Backend Design

---

## Author

**Aditya Singh**

Built as a comprehensive full-stack backend and machine learning project to explore modern distributed system architecture, scalable microservices, event-driven communication, and intelligent recommendation systems.
