package com.example.whiteboard2.services;

// service class is agnostic (unaware) that we are using the service through the web

import com.example.whiteboard2.models.Widget;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WidgetService {

  // we're going to use some static code for rn
  List<Widget> widgets = new ArrayList<Widget>();
  {
    widgets.add(new Widget("123", "HEADING", "Widget 1", "topic123"));
    widgets.add(new Widget("234", "PARAGRAPH", "Widget 2", "topic123"));
    widgets.add(new Widget("345", "HEADING", "Widget 3", "topic123"));
    widgets.add(new Widget("456", "PARAGRAPH", "Widget 4", "topic123"));
    widgets.add(new Widget("567", "HEADING", "Widget A", "topic234"));
    widgets.add(new Widget("678", "PARAGRAPH", "Widget B", "topic234"));
  }

  // provide CRUD operations
  // http://localhost:8080/find/all/widgets => returns the jsons of the items in the array above
  // to be called a RESTful controller, you have to follow certain naming convention

  /*
   * 1. should end with a plural noun (widgets)
   * 2. use the path to establish a hierarchy
   * 3. if you want a specific object in a collection, encode the primary key at the end of the noun
   */
  // kinda stupid when would u evr need ALL widgets -- u only need all widgets FOR A SEPCIFIC TOPIC
  public List<Widget> findAllWidgets() {
    return widgets;
  }

  public List<Widget> findWidgetsForTopic(String tid) {
    List<Widget> widgetsForTopic = new ArrayList<Widget>();
    for (Widget w : widgets) {
      if (w.getTopicId().equals(tid)) {
        widgetsForTopic.add(w);
      }
    }
    return widgetsForTopic;
  }

  public Widget findWidgetById(
          @PathVariable("wid") String widgetId) {
    for (Widget w: widgets) {
      if (w.getId().equals(widgetId)) {
        return w;
      }
    }
    return null;
  }

  public Widget createWidget(
          @RequestBody Widget widget) {
    // making the date the id for now until the DB can do it for us
    String newId = String.valueOf(new Date().getTime());
    widget.setId(newId);
    widgets.add(widget);
    return widget;
  }

  public Integer deleteWidget(
          @PathVariable("wid") String widgetId) {
    Widget widgetToRemove = findWidgetById(widgetId);
    // remove is a boolean method, if successful return 1, else return 0
    if (widgets.remove(widgetToRemove)) {
      return 1;
    }
    return 0;
  }

  public Integer updateWidget(
          @PathVariable("wid") String widgetId,
          @RequestBody Widget newWidget) {
    // if there is a widget found in the widgets
    if (findWidgetById(widgetId) != null) {
      findWidgetById(widgetId).setClassName(newWidget.getClassName());
      findWidgetById(widgetId).setName(newWidget.getName());
      findWidgetById(widgetId).setHtml(newWidget.getHtml());
      findWidgetById(widgetId).setId(newWidget.getId());
      findWidgetById(widgetId).setSrc(newWidget.getSrc());
      findWidgetById(widgetId).setType(newWidget.getType());
      findWidgetById(widgetId).setHeight(newWidget.getHeight());
      findWidgetById(widgetId).setWidth(newWidget.getWidth());
      return 1;
    }
    return 0;
  }
}
