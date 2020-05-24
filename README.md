# Revenue and Expense Ledger v2

## Menu:
* [Description](#description)
* [Preview](#preview)
* [Features](#features)

## Description
+ Service for accountants and business owners to manage company accounting. 
+ You can add/edit/delete invoices, clients, addresses (only these added by your company). 
+ You can log in in two ways: 
    + use login and password 
    + generate JWT and then log in by Bearer Token. 
+ After first log in, you will get 7 days valid account. 
+ You can extend access to site by 1 month when you enter subscription code. 
+ Summary of tax year and individual months, includes various tax rates. 
+ Separate login page for users and administrator. 
+ All activities are monitored and sent to the LogService-JarekIT microservice. 

## Preview
+ [www.RaELv2.herokuapp.com](http://RaELv2.herokuapp.com/) ---> working app
+ [www.JarekIT.pl](http://RaELv2.herokuapp.com/) ---> screens from site

## Features
+ 2.6.5
    + obtained IP address from client
    + added IP address and username info to LogService when client log in
    + migrated MySQL to Hekko
+ 2.6.4
    + separated login page to user login page and admin login page
+ 2.6.3
    + added separate thread to the LogViewer
    + sent Log to LogViewer on init that the program has started
+ 2.6.2
     + implemented JWT authentication
     + fixed infinitive recursion in JSON getAddresses API
     + corrected few typos
+ 2.6.1
     + hidden subscription page only for anonymous users
+ 2.6.0
    + created Subscription system
    + added new MySQL (ClearDB)
    + added favicon
    + added descriptions above the tables
    + corrected few typos
+ 2.5.4
    + added change password function
+ 2.5.3
    + visually improved frontend
    + added admin panel
+ 2.5.2
    + visually improved frontend
+ 2.5.1
    + added description in Home page
    + deleted EventListener in runDemoRepo()
    + added functions to set client data to Logged User
    + handled null case in Iterable getAddresses()
    + handled null case in UserDetails loadUserByUsername(String s)
    + added new MySQL (freemysqlhosting.net)
    + small fixes in html files
    + deleted List of clients in User
    + deleted account number from Client
