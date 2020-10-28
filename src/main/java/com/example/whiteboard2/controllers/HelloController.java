package com.example.whiteboard2.controllers;

import com.example.whiteboard2.models.Message;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @GetMapping("/message")
  public Message getMessage() {
    Message m = new Message();
    m.setMessage("Life is Good!!!");
    return m;
  }
  @GetMapping("/hello")
  public String sayHello() {
    return "Hello World";
  }

  // shows how you can pass parameters
  // localhost:8080/add/2/3 => returns a page with 5 on it
  @GetMapping("/add/{paramA}/{paramB}")
  public Integer addIntegers(
          @PathVariable("paramA") Integer a,
          @PathVariable("paramB") Integer b
  ) {
    return a + b;
  }

  // you can pass parameters either as paths or as query strings
  // localhost:8080/add?paramA=2&paramB=3 => returns a page with 5 on it
  @GetMapping("/add")
  public Integer addIntegers2(
          // requestparam parses the url
          @RequestParam("paramA") Integer a,
          @RequestParam("paramB") Integer b) {
    return a + b;
  }
}


