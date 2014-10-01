package com.milot.databaseexample;

public class Document {
        
    int id;
    String name;
    String path;
     
    public Document(){
         
    }
    
    public Document(int id, String name, String path){
        this.id = id;
        this.name = name;
        this.path = path;
    }
     
    public Document(String name, String path) {
        this.name = name;
        this.path = path;
    }
    
    
    public int getID(){
        return this.id;
    }
     
    public void setID(int id){
        this.id = id;
    }
     
    public String getName(){
        return this.name;
    }
     
    public void setName(String name){
        this.name = name;
    }
     
    public String getPath(){
        return this.path;
    }
     
    public void setPath(String path){
        this.path = path;
    }
}
