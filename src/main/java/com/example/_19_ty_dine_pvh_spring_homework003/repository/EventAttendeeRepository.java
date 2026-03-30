package com.example._19_ty_dine_pvh_spring_homework003.repository;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Attendee;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventAttendeeRepository {
    @Results(id = "eventAttendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
            @Result(property = "eventId", column = "event_id"),
    })
    @Select("""
        SELECT a.attendee_id, a.attendee_name, a.email
            FROM event_attendee ea
            INNER JOIN attendees a
                ON ea.attendee_id = a.attendee_id
            WHERE ea.event_id = #{eventId};
    """)
    public List<Attendee> getAttendeesByEventId(Long eventId);

    @ResultMap("eventAttendeeMapper")
    @Insert("""
        INSERT INTO event_attendee VALUES (#{attendeeId}, #{eventId});
    """)
    void addAttendeeAndEvent(Long attendeeId, Long eventId);

    @ResultMap("eventAttendeeMapper")
    @Delete("""
        DELETE FROM event_attendee WHERE event_id = #{eventId};
    """)
    void deleteEventAttendeeById(Long eventId);

    @Select("""
        SELECT COUNT(*) > 0 FROM event_attendee WHERE attendee_id = #{attendeeId};
    """)
    boolean existsByAttendeeId(Long attendeeId);
}