package com.example.whiteboard2.controllers;

import com.example.whiteboard2.models.Widget;
import com.example.whiteboard2.services.WidgetService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



// controller is an interface between http world and java (object) world

// has to fetch data from the server
// rest controller so we can receive http requests
@RestController
@CrossOrigin(origins = "*")
public class WidgetController {

  // we're going to use some static code for rn placed in Widget Service
  // controller only deals with interface of HTTP
  WidgetService service = new WidgetService();

  // provide CRUD operations
  // http://localhost:8080/find/all/widgets => returns the jsons of the items in the array above
  // to be called a RESTful controller, you have to follow certain naming convention

  /*
   * 1. should end with a plural noun (widgets)
   * 2. use the path to establish a hierarchy
   * 3. if you want a specific object in a collection, encode the primary key at the end of the noun
   */
  @GetMapping("/api/widgets")
  public List<Widget> findAllWidgets() {
    return service.findAllWidgets();
  }
  @GetMapping("/api/topics/{tid}/widgets")
  public List<Widget> findWidgetForTopic(
          @PathVariable("tid") String topicId) {
    return service.findWidgetsForTopic(topicId);
  }

  @GetMapping("/api/widgets/{wid}")
  public Widget findWidgetById(
          @PathVariable("wid") String widgetId) {
    return service.findWidgetById(widgetId);
  }

  @PostMapping("/api/topics/{topicId}/widgets")
  public Widget createWidgetForTopic(
          @PathVariable("topicId") String topicId,
          @RequestBody Widget widget) {
    widget.setTopicId(topicId);
    return service.createWidget(widget);
  }

  @PostMapping("/api/widgets")
  public Widget createWidget(
          @RequestBody Widget widget) {
    // making the date the id for now until the DB can do it for us
    return service.createWidget(widget);
  }

  @DeleteMapping("/api/widgets/{wid}")
  public Integer deleteWidget(String widgetId) {
    return null;
  }

  @PutMapping("/api/widgets/{wid}")
  public Integer updateWidget(String widgetId, Widget newWidget) {
    return null;
  }

}
