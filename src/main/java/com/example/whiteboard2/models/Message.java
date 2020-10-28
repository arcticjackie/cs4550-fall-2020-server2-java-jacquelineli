package com.example.whiteboard2.models;

// this is a java bean
// for every single private field, we have setters and getters
public class Message {
  private String message;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
