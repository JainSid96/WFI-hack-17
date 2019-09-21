package com.example.dcube.foodo;

/**
 * Created by Siddhant Jain on 10/26/2017.
 */

public class Item {
    int _id;
    String _name;
    byte[] _Image;
    int _days;
    // Empty constructor
    public Item(){

    }
    // constructor
    public Item(int id, String name, byte[] _Image,int _days){
        this._id = id;
        this._name = name;
        this._Image = _Image;
        this._days = _days;
    }

    // constructor
    public Item(String name, byte[] _Image,int _days){
        this._name = name;
        this._Image = _Image;
        this._days = _days;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    public byte[] getImage()
    {
        return this._Image;
    }
    public void setImage(byte[] image)
    {
        this._Image = image;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }
    // getting phone number
    public int getdays(){
        return this._days;
    }

    // setting phone number
    public void setdays(int days){
        this._days = days;
    }



}

