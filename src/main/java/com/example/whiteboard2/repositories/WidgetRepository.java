package com.example.whiteboard2.repositories;

import com.example.whiteboard2.models.Widget;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// we're going to inherit whoever already implemented this for us
public interface WidgetRepository
  // the type of object we wanna crud and the type of the PK
        extends CrudRepository<Widget, Integer> {
  // :tid is a placeholder,
  @Query(value = "SELECT * FROM widgets where topic_id=:tid", nativeQuery = true)
  public List<Widget> findWidgetByTopicId(
          @Param("tid") String topicId);

}
