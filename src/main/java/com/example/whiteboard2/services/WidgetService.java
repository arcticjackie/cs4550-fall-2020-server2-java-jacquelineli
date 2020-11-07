package com.example.whiteboard2.services;

// service class is agnostic (unaware) that we are using the service through the web

import com.example.whiteboard2.models.Widget;
import com.example.whiteboard2.repositories.WidgetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WidgetService {
  // the widgetRepository is going to replace the array we had
  @Autowired
  WidgetRepository widgetRepository;

  // we're going to use some static code for rn
  List<Widget> widgets = new ArrayList<>();

  {
    widgets.add(new Widget(123, "HEADING", "Widget 1", "topic123"));
    widgets.add(new Widget(234, "PARAGRAPH", "Widget 2", "topic123"));
    widgets.add(new Widget(345, "HEADING", "Widget 3", "topic123"));
    widgets.add(new Widget(456, "PARAGRAPH", "Widget 4", "topic123"));
    widgets.add(new Widget(567, "HEADING", "Widget A", "topic234"));
    widgets.add(new Widget(678, "PARAGRAPH", "Widget B", "topic234"));
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
//    return widgets;
    return (List<Widget>) widgetRepository.findAll();
  }

  public List<Widget> findWidgetsForTopic(String tid) {
//    List<Widget> widgetsForTopic = new ArrayList<Widget>();
//    for (Widget w : widgets) {
//      if (w.getTopicId().equals(tid)) {
//        widgetsForTopic.add(w);
//      }
//    }
//    return widgetsForTopic;
    return widgetRepository.findWidgetByTopicId(tid);
  }

  public Widget findWidgetById(
          @PathVariable("wid") Integer widgetId) {
//    for (Widget w : widgets) {
//      if (w.getId() == widgetId) {
//        return w;
//      }
//    }
//    return null;
    return widgetRepository.findById(widgetId).get();
  }

//  public Widget createWidget(Widget widget) {
//    // making the date the id for now until the DB can do it for us
////    Integer newId = Math.toIntExact(new Date().getTime());
////    widget.setId(newId);
////    widgets.add(widget);
////    return widget;
//    // returns a record that was inserted (id is autoincremented)
//  }
public Widget createWidget(Widget widget) {
//    Integer id = widget.getId();
//    widget.setId(id);
  widget.setId((int) new Date().getTime());
//    System.out.println(widget.getId());
//  if (widget.getType().equals("HEADING")) {
//    widget.setSize(1);
//  }
  return widgetRepository.save(widget);
//        widget.setId(123);
//        widgets.add(widget);
//        return widget;
}

  public void deleteWidget(
          @PathVariable("wid") Integer widgetId) {
//    Widget widgetToRemove = findWidgetById(widgetId);
//    // remove is a boolean method, if successful return 1, else return 0
//    if (widgets.remove(widgetToRemove)) {
//      return 1;
//    }
//    return 0;
    widgetRepository.deleteById(widgetId);
  }

  public Widget updateWidget(Integer widgetId, Widget newWidget) {
//    for (Widget widget : widgets) {
//      if (widget.getId().equals(widgetId)) {
//        System.out.println(newWidget.getName());
//        widget.setName(newWidget.getName());
//        widget.setText(newWidget.getText());
//        widget.setType(newWidget.getType());
//        if (newWidget.getType().equals("HEADING")) {
//          widget.setSize(newWidget.getSize());
//        }
//        return 1;
//      }
//    }
//    return 0;
    // retrieve
    Widget widget = widgetRepository.findById(widgetId).get();
    //update
    widget.setName(newWidget.getName());
    widget.setText(newWidget.getText());
    widget.setType(newWidget.getType());
    widget.setSize(newWidget.getSize());
    widget.setValue(newWidget.getValue());
    widget.setStyle(newWidget.getStyle());
    widget.setSrc(newWidget.getSrc());
    widget.setHeight(newWidget.getHeight());
    widget.setWidth(newWidget.getWidth());
    widget.setCssClass(newWidget.getCssClass());
    System.out.println("IMAGE SRC HELP: " + newWidget.getSrc());
//
    //save
    return widgetRepository.save(widget);
  }

//}

  public List<Widget> moveWidgetUp(String widgetId, String direction) {
    int index = 0;
    for (int i = 0; i < widgets.size(); i++) {
      if (widgets.get(i).getId().equals(widgetId)) {
        index = i;
      }
    }
    if (direction.equals("UP")) {
      if (index > 0) {
        // w is the widget we want to shift upwards
//        Widget widgetToMoveUpward = widgets.get(index);
        String name = widgets.get(index).getName();
        String text = widgets.get(index).getText();
        String type = widgets.get(index).getType();

        // set the fields of the one we want to shift upwarsd with the one above it
        widgets.get(index).setName(widgets.get(index - 1).getName());
        widgets.get(index).setText(widgets.get(index - 1).getText());
        widgets.get(index).setType(widgets.get(index - 1).getType());

        widgets.get(index - 1).setName(name);
        widgets.get(index - 1).setText(text);
        widgets.get(index - 1).setType(type);
      }
    }
    else {
      if (index < widgets.size()) {
        // w is the widget we want to shift upwards
        Widget widgetToMoveDownward = widgets.get(index);

        String name = widgets.get(index).getName();
        String text = widgets.get(index).getText();
        String type = widgets.get(index).getType();

        // set the fields of the one we want to shift upwarsd with the one above it
        widgets.get(index).setName(widgets.get(index + 1).getName());
        widgets.get(index).setText(widgets.get(index + 1).getText());
        widgets.get(index).setType(widgets.get(index + 1).getType());

        widgets.get(index + 1).setName(name);
        widgets.get(index + 1).setText(text);
        widgets.get(index + 1).setType(type);
      }
    }
    return widgets;
  }
}
    // if there is a widget found in the widgets
//    Widget widget = findWidgetById(widgetId);
//    if (widget != null) {
////      findWidgetById(widgetId).setClassName(newWidget.getClassName());
//      widget.setName(newWidget.getName());
//      findWidgetById(widgetId).setHtml(newWidget.getHtml());
//      findWidgetById(widgetId).setId(newWidget.getId());
//      findWidgetById(widgetId).setSrc(newWidget.getSrc());
//      findWidgetById(widgetId).setType(newWidget.getType());
//      findWidgetById(widgetId).setHeight(newWidget.getHeight());
//      findWidgetById(widgetId).setWidth(newWidget.getWidth());
//      findWidgetById(widgetId).setSize(newWidget.getSize());
//      findWidgetById(widgetId).setText(newWidget.getText());

