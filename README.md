# javaHeadacheTracker

This project is a desktop application, providing migraine sufferers with the ability to track trigger factors.
This project is associated with a relational database used to store information from session to session.
The code for the relational database is:
CREATE database finalProject;
USE finalProject;
CREATE TABLE headaches (hdate DATE, hseverity INTEGER, alcohol BOOLEAN, pms BOOLEAN, 
chocolate BOOLEAN, weather BOOLEAN, stress BOOLEAN, lowsleep BOOLEAN, usertrig1 VARCHAR(30), 
usertrig2 VARCHAR(30), usertrig3 VARCHAR(30));

Without use of the relational database, the user will be unable to store their changes when a session is closed.
This project was created at Humber College as a student assignment.
