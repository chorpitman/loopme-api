Prerequisites
==============
| **Tool** | **Required Version** | **How to check**  | **Comments** |
| ----- | ------ | ---- | ---- |
| Java | 1.8.x | java -version | |
| Maven | 3.2.3 or 3.2.5 | mvn -version | Newer versions should also work |
| Git | any (latest preferable) | git --version | |
| H2 in memory database |  |  | |

Setup
======

Java
----------
Download and install latest JDK from `http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html`

Oauth 2 endpoints
----------- 
  Attempt to access resources [REST API] without any authorization [will fail of-course].

**`Token Request:`**  
The client requests an access token from the authorization server’s token endpoint by including the credentials 
  received from the resource owner. When making the request, the client authenticates with the authorization 
  server by providing client_id & secret.
  
**`Token Response:`**
The authorization server authenticates the client and validates the resource owner credentials and, if valid, 
issues an access token.

  Ask for tokens[access+refresh] using HTTP POST on /oauth/token, with grant_type=password, and resource owners credentials as req-params. 
  Additionally, send client credentials in Authorization header (KEY:Authorization, VALUE:Basic bG9vcG1lOmxvb3BtZS1zZWNyZXQ=)
 > `POST http://localhost:8080/oauth/token?grant_type=password&username=bill&password=abc123`
  
  Ask for a new access token via valid refresh-token, using HTTP POST on /oauth/token, with grant_type=refresh_token,and sending actual refresh token. Additionally, send client credentials in Authorization header.
 > `POST http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=094b7d23-973f-4cc1-83ad-8ffd43de1845`
  
  Access the resource by providing an access token using access_token query param with request.
 > `GET http://localhost:8080/api/user/?access_token=3525d0e4-d881-49e7-9f91-bcfd18259109`
  
Enable Lombok
-----------