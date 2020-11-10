package com.example.whiteboard2.controllers;

import com.example.whiteboard2.models.Widget;
import com.example.whiteboard2.services.WidgetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



// controller is an interface between http world and java (object) world

// has to fetch data from the server
// rest controller so we can receive http requests
@RestController
@CrossOrigin(origins = "*")
public class WidgetController {

  @Autowired
  WidgetService service;// = new WidgetService();


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
          @PathVariable("wid") Integer widgetId) {
    return service.findWidgetById(widgetId);
  }

  // request annotation allows us to extract things that are coming into the HTTP body as a JSON object
  // and parse it and try to instantiate a java object that is equivalent to the body
  @PostMapping("/api/topics/{topicId}/widgets")
  public Widget createWidget(
          @PathVariable("topicId") String tid,
          @RequestBody Widget widget) {
    widget.setTopicId(tid);
    return service.createWidget(tid, widget);
  }

////  @PostMapping("/api/topics/{topicId}/widgets")
//@PostMapping("/api/widgets")
//public Widget createWidget(
////          @PathVariable("topicId") String topicId,
//          @RequestBody Widget widget) {
//    // making the date the id for now until the DB can do it for us
////    widget.setTopicId(topicId);
//    return service.createWidget(widget);
//  }

  @DeleteMapping("/api/widgets/{wid}")
  public void deleteWidget(
          @PathVariable("wid") Integer widgetId) {
    // delegating the work to the service
    service.deleteWidget(widgetId);
  }

  @PutMapping("/api/widgets/{wid}")
  public Widget updateWidget(
          @PathVariable("wid") Integer widgetId,
          @RequestBody Widget newWidget) {
    return service.updateWidget(widgetId, newWidget);
  }


  @GetMapping("api/topics/{topicId}/widgets/{wid}/{direction}")
  public List<Widget> moveWidgetUp(
          @PathVariable("topicId") String topicId,
          @PathVariable("wid") Integer wid,
          @PathVariable("direction") String direction
  ) {
    return service.moveWidgetUp(wid, topicId, direction);
//    return 1;
  }

  // wrote somethig on th e sever that swpas element
  //if u call a certain url w a certain widget, then either up or down changes th order of thw ei widget
  // th ebutton is attach to calling that end point

}
