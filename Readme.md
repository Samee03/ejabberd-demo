# Ejabberd Chat Application

## Ejabberd Server
* host: localhost
* port: 5222

## Dependencies
* smack-tcp
* smack-im
* smack-extensions
* smack-java7
* smack-xmlparser-xpp3
* smack-xmlparser-stax

## Configuration
* Create beans for xmppConnection and chat instance
* To inject as a service in controller and service class

## Api Endpoints
### Connect to Server
```
method: post
url: /api/v1/chat/connect
params: {
    username,
    password
}
```
Initialize a connection between application instance and server.
Then it will initiate a listener to listen for incoming messages.
### Send Message
```
method: post
url: /api/v1/chat/send
params: {
    recipient,
    message
}
```
Sends message to the recipient simply by invoking the send method of chatManager.
