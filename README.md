## Social Media Application – Facebook Page Integration
# Overview :
    This project is a Spring Boot backend application that supports:
    Google-based login (mocked via email & name)
    JWT-based authentication
    Facebook Page integration
    Fetching Facebook Pages
    Publishing posts to Facebook Pages using Facebook Graph API
    
The application stores users and their Facebook Pages in a MySQL database and allows posting content to linked pages.
# Tech Stack :
    Java 17
    Spring Boot
    Spring Security (JWT)
    Spring Data JPA (Hibernate)
    MySQL
    Facebook Graph API (v19.0)
    Maven
## 1.Authentication Flow (JWT)
    1.Google Login (Mock)
    Endpoint : POST /auth/google
    Request
    {
    "email": "user@gmail.com",
    "name": "John Doe"
    }
    Response
    {
    "token": "JWT_TOKEN"
    }
    Saves user if not present
    Generates JWT token
    JWT is used for protected APIs
    Facebook Token (IMPORTANT)
    This is NOT your JWT token
    You must manually generate a Facebook USER Access Token.
    Required Permissions
        pages_show_list
        pages_read_engagement
        pages_manage_metadata
        pages_manage_posts
        
    How to Generate Token
    Go to Facebook Developers
      Create an App → Choose Business
      Add Facebook Login product
      Go to Tools → Graph API Explorer
      Select your app
      Generate User Access Token
      Add required permissions
      Copy token

## 2.Link Facebook Pages
      Fetches pages user manages and stores them in DB.
      Endpoint- POST /facebook/pages/link
      Headers
      Authorization: Bearer <JWT_TOKEN>
      Content-Type: application/json
      Request
      {
      "facebookAccessToken": "EAAG..."
      }
      
      Internal API Call
      GET https://graph.facebook.com/v19.0/me/accounts
      Response : "Pages linked successfully"
## 3.Publish Post
    Endpoint - POST /facebook/pages/{pageId}/post
    Headers
    Authorization: Bearer <JWT_TOKEN>
    Content-Type: application/json
    Request -
    {
    "message": "Hello from Flintzy!"
    }
    Internal API Call
    POST https://graph.facebook.com/v19.0/{pageId}/feed
    
    Response - "Post published"
