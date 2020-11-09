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
//  List<Widget> widgets = new ArrayList<>();
//
//  {
//    widgets.add(new Widget(123, "HEADING", "Widget 1", "topic123"));
//    widgets.add(new Widget(234, "PARAGRAPH", "Widget 2", "topic123"));
//    widgets.add(new Widget(345, "HEADING", "Widget 3", "topic123"));
//    widgets.add(new Widget(456, "PARAGRAPH", "Widget 4", "topic123"));
//    widgets.add(new Widget(567, "HEADING", "Widget A", "topic234"));
//    widgets.add(new Widget(678, "PARAGRAPH", "Widget B", "topic234"));
//  }

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
public Widget createWidget(String tid, Widget widget) {
//    Integer id = widget.getId();
//    widget.setId(id);
  widget.setId((int) new Date().getTime());
  // if there is no widgets under that specific topicId, set the widgetOrder to be 1
  if (findWidgetsForTopic(tid) == null) {
    widget.setWidgetOrder(0);
  }
  else {
    int numWidgetsForTopic = findWidgetsForTopic(tid).size();
    widget.setWidgetOrder(numWidgetsForTopic);
  }

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
    widget.setWidgetOrder(newWidget.getWidgetOrder());
    System.out.println("IMAGE SRC HELP: " + newWidget.getSrc());
//
    //save
    return widgetRepository.save(widget);
  }

//}

  public List<Widget> moveWidgetUp(Integer widgetOrder, Integer wid, String topicId, String direction) {

    // find the widget that the move button up/down was clicked on, based on widgetId
    Widget widgetClickedOn = widgetRepository.findById(wid).get();

    // get the widgetOrder from the database
    int widgetClickedOnOrder = widgetClickedOn.getWidgetOrder();
    System.out.println("widgetClickedOnOrder: " + widgetClickedOnOrder);



    // get the index of the value below / above the widget you are sw
    int widgetOrderOfOther;
    if (direction.equals("UP") && widgetClickedOnOrder > 0) {
      widgetOrderOfOther = widgetClickedOnOrder - 1;
    }
    else if (direction.equals("DOWN") && widgetClickedOnOrder < findWidgetsForTopic(topicId).size()) {
      widgetOrderOfOther = widgetClickedOnOrder + 1;
    }
    else {
      // dont do anything bc u cant click swap up on the first one or swap down on the last one
      return findWidgetsForTopic(topicId);
    }
    System.out.println("widgetOrderOfOther: " + widgetOrderOfOther);

    int indexOfWidgetToSwap = -1;

    // find the widget whose order is one less / one more than the one that was clicked on
    for (int i = 0; i < findWidgetsForTopic(topicId).size(); i++) {
      if (findWidgetsForTopic(topicId).get(i).getWidgetOrder() == widgetOrderOfOther) {
        indexOfWidgetToSwap = i;
      }
    }
    System.out.println("indexOfWidgetToSwap: " + indexOfWidgetToSwap);

    // swap all of the values (besides for the widgetOrder)
    findWidgetsForTopic(topicId).get(indexOfWidgetToSwap).setWidgetOrder(widgetClickedOnOrder);
    widgetClickedOn.setWidgetOrder(widgetOrderOfOther);

    widgetRepository.save(findWidgetsForTopic(topicId).get(indexOfWidgetToSwap));
    widgetRepository.save(widgetClickedOn);

    return findWidgetsForTopic(topicId);

//    Widget widget = widgetRepository.findById(wid).get();
//
//    System.out.println(wid);
//    System.out.println(widgetOrder);
//    System.out.println(topicId);
//    System.out.println(direction);
//    widget.setWidgetOrder(10);

//
//    // widgets  are all widgets under the specific topicId
//    List<Widget> widgets = widgetRepository.findWidgetByTopicId(topicId);
//    System.out.println("topicId field: " + topicId);
//    System.out.println("Widget ORder Field: " + widgetOrder);
//    System.out.println("Dreiction field: "+ direction);
//
//
//    System.out.println("widgets array size: " + widgets.size());
//    int indexToBeMoved = -1;
//    int upIndex = -1;
//    int downIndex = -1;
//    for (int i = 0; i < widgets.size(); i++) {
//      if (widgets.get(i).getWidgetOrder() == widgetOrder) {
//        System.out.println("for loop iteration: " + i);
//        indexToBeMoved = i;
//      }
//    }
//    System.out.println("indexToBeMoved: " + indexToBeMoved);
//
//
////    System.out.println("Widget to be moved:" + widgets.get(indexToBeMoved));
////    String name = widgets.get(index).getName();
////    String text = widgets.get(index).getText();
////    String type = widgets.get(index).getType();
////    Integer size = widgets.get(index).getSize();
////    String value = widgets.get(index).getValue();
////    String style = widgets.get(index).getStyle();
////    String src = widgets.get(index).getSrc();
////    Integer height = widgets.get(index).getHeight();
////    Integer width = widgets.get(index).getWidth();
////    String cssClass = widgets.get(index).getCssClass();
//    if (direction.equals("UP")) {
//      if (indexToBeMoved > 0) {
//
//
//        for (int i = 0; i < widgets.size(); i++) {
//          if (widgets.get(i).getWidgetOrder() == widgetOrder - 1) {
//            upIndex = i;
//          }
//        }
//        System.out.println("upIndex: " + upIndex);
//
//        System.out.println("widgetOrder - 1 = " + (widgetOrder-1));
//        System.out.println("widgetOrder = " + (widgetOrder));
//        widgets.get(indexToBeMoved).setWidgetOrder(widgetOrder - 1);
//        widgets.get(upIndex).setWidgetOrder(widgetOrder);
//        widgetRepository.save(widgets.get(indexToBeMoved));
//        widgetRepository.save(widgets.get(upIndex));
//
//        //        Widget w = null;
////        for (int i = 0; i < widgets.size(); i++) {
////          if (widgets.get(i).getWidgetOrder() == widgetOrder - 1) {
////            w = widgets.get(i);
////          }
////        }
//
//        // set the fields of the one we want to shift upwarsd with the one above it
////        widgets.get(index).setName(w.getName());
////        widgets.get(index).setText(w.getText());
////        widgets.get(index).setType(w.getType());
////        widgets.get(index).setSize(w.getSize());
////        widgets.get(index).setValue(w.getValue());
////        widgets.get(index).setStyle(w.getStyle());
////        widgets.get(index).setSrc(w.getSrc());
////        widgets.get(index).setHeight(w.getHeight());
////        widgets.get(index).setWidth(w.getWidth());
////        widgets.get(index).setCssClass(w.getCssClass());
////
////        w.setName(name);
////        w.setText(text);
////        w.setType(type);
////        w.setSize(size);
////        w.setValue(value);
////        w.setStyle(style);
////        w.setSrc(src);
////        w.setHeight(height);
////        w.setWidth(width);
////        w.setCssClass(cssClass);
//      }
//    }
//    else {
//      if (indexToBeMoved < widgets.size() ) {
//
//        for (int i = 0; i < widgets.size(); i++) {
//          if (widgets.get(i).getWidgetOrder() == (widgetOrder + 1)) {
//            System.out.println("for loop iteration: " + i);
//            downIndex = i;
//          }
//        }
//        System.out.println("downIndex: " + downIndex);
//
//
//        System.out.println("widgetOrder + 1 = " + (widgetOrder+1));
//        System.out.println("widgetOrder = " + (widgetOrder));
//        widgets.get(indexToBeMoved).setWidgetOrder(widgetOrder + 1);
//        widgets.get(downIndex).setWidgetOrder(widgetOrder);
//        widgetRepository.save(widgets.get(downIndex));
//        widgetRepository.save(widgets.get(indexToBeMoved));
//
//
//        //        Widget w = null;
////        for (int i = 0; i < widgets.size(); i++) {
////          if (widgets.get(i).getWidgetOrder() == widgetOrder + 1) {
////            w = widgets.get(i);
////          }
////        }
//        // set the fields of the one we want to shift upwarsd with the one above it
////        widgets.get(index).setName(w.getName());
////        widgets.get(index).setText(w.getText());
////        widgets.get(index).setType(w.getType());
////        widgets.get(index).setSize(w.getSize());
////        widgets.get(index).setValue(w.getValue());
////        widgets.get(index).setStyle(w.getStyle());
////        widgets.get(index).setSrc(w. getSrc());
////        widgets.get(index).setHeight(w.getHeight());
////        widgets.get(index).setWidth( w.getWidth());
////        widgets.get(index).setCssClass( w.getCssClass());
////
////        w.setName(name);
////        w.setText(text);
////        w.setType(type);
////        w.setSize(size);
////        w.setValue(value);
////        w.setStyle(style);
////        w.setSrc(src);
////        w.setHeight(height);
////        w.setWidth(width);
////        w.setCssClass(cssClass);
//      }
//    }
//
//    List<Widget> widg = new ArrayList<>();
//    return widg;
  }
}


