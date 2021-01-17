package com.example.Rentup;

public class ProfileHelper {//it is class use to suport db process conatin constructors
    public String Firstname,Lastname,Email,Password,Confpassword,Ph;
    public ProfileHelper()
    {
//it is empty constructor
    }

    public ProfileHelper(String firstname, String lastname, String email, String password, String confpassword, String ph) {
        //constructor
        Firstname = firstname;//assigning veriable
        Lastname = lastname;
        Email = email;
        Password = password;
        Confpassword = confpassword;
        Ph = ph;
        //we call this class whem we use db in main class in my case it is profile class
    }
}
