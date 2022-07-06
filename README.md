# zadanieBolt

After running a program you can choose from 2 optioins of registrations, also you can register as Customer, person who want to order a taxi, or Provider, person who provides taxi service due to order, in each option you must post some informations about user, here they are:

Customer:
POST http://localhost:8080/api/v1/registration
{
    "firstName": "firstName",
    "lastName": "lastName",
    "email":"email@email.com",
    "password": "password",
    "cardNumber": "cardnumber", 
    "cardValidity": "xx/yy",
    "cv": "cv number",
    "phoneNumber": "phone number"
}

after sending this post request you will receive an confirmation email, that will expires in 15 minutes, if you don't confirm your account, your account will be deleted from database

Provider:
POST http://localhost:8080/api/v1/registration

{
    "firstName": "firstName",
    "lastName": "lastName",
    "email": "email",
    "password": "password",
    "car" : "car",
    "IBAN" : "iban",
    "description": "description"
}

after that, you will also receive a confirmation email...

-> if you are already registered you can upgrade your account to CUSTOMER_PROVIDER, but you have to be logged in

Login:
POST http://localhost:8080/api/v1/registration/login
{
    "email": "email,
    "password": "password"
}

Logout:
POST http://localhost:8080/api/v1/registration/logout
{
    "email": "email,
    "password": "password"
}

-> if you want to show online providers you should choose this request:
GET http://localhost:8080/api/v1/registration/online


-> if you want to send an order to provider
Send:
POST http://localhost:8080/api/v1/registration/send

{
    "customer": "email",
    "provider": "email",
    "aDestination": "actualDestination",
    "fDestination" : "FinalDestination"
}


-> if you want show proivders orders
Show providers orders:
GET http://localhost:8080/api/v1/registration/orders?provider=email

-> if you want delete order:
Delete order:
POST http://localhost:8080/api/v1/registration/deleteOrder
{
    "provider":"email",
    "id":"order id"
}

-> if you want delete user:
Delete user:
POST http://localhost:8080/api/v1/registration/deleteUser
{
    "email":"email,
    "password":"password"
}

