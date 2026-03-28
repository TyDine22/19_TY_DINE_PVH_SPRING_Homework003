package com.example._19_ty_dine_pvh_spring_homework003.repository;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Event;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.EventRequest;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EventRepository {

    @Results(id = "eventMapper", value = {
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id", one = @One(select = "com.example._19_ty_dine_pvh_spring_homework003.repository.VenueRepository.getVenueById")),
            @Result(property = "attendees", column = "event_id", many = @Many(select = "com.example._19_ty_dine_pvh_spring_homework003.repository.EventAttendeeRepository.getAttendeesByEventId"))
    })
    @Select("""
        SELECT * FROM events OFFSET #{offset} LIMIT #{size};
    """)
    List<Event> getAllEvents(Integer offset, Integer size);

    @ResultMap("eventMapper")
    @Select("""
        SELECT * FROM events WHERE event_id = #{eventId};
    """)
    Event getEventById(Long eventId);

    @ResultMap("eventMapper")
    @Select("""
        INSERT INTO events values (default, #{req.eventName}, #{req.eventDate}, #{req.venueId}) RETURNING *;
    """)
    Event addEvent(@Param("req") EventRequest eventRequest);

    @Select("""
        SELECT COUNT(*) > 0
        FROM events
        WHERE event_name = #{name} AND event_date = #{date};
    """)
    boolean existsByNameAndDate(String name, LocalDate date);

    @Select("""
    SELECT COUNT(*) > 0
    FROM events
    WHERE event_name = #{name} AND event_date = #{date} AND event_id != #{excludeId};
""")
    boolean existsByNameAndDateNoId(String name, LocalDate date, Long excludeId);

    @ResultMap("eventMapper")
    @Select("""
        UPDATE events 
        SET event_name = #{req.eventName}, event_date = #{req.eventDate}, venue_id = #{req.venueId}
        WHERE event_id = #{eventId}
        RETURNING *;
    """)
    Event updateEventById(Long eventId, @Param("req") EventRequest eventRequest);

    @ResultMap("eventMapper")
    @Delete("""
        DELETE FROM events WHERE event_id = #{eventId};
    """)
    boolean deleteEventById(Long eventId);

    @Select("""
        SELECT COUNT(*) > 0 FROM events WHERE venue_id = #{venueId};
    """)
    boolean existsByVenueId(Long venueId);
}
