package com.example._19_ty_dine_pvh_spring_homework003.repository;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Attendee;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.AttendeeRequest;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.UpdateAttendeeRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttendeeRepository {
    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
    })
    @Select("""
        SELECT * FROM attendees OFFSET #{offset} LIMIT #{size};
    """)
    List<Attendee> getAllAttendees(Integer offset, Integer size);

    @ResultMap("attendeeMapper")
    @Select("""
        SELECT * FROM attendees WHERE attendee_id = #{attendeeId};
    """)
    Attendee getAttendeeById(Long attendeeId);

    @ResultMap("attendeeMapper")
    @Select("""
        INSERT INTO attendees VALUES (default, #{req.attendeeName}, #{req.email}) RETURNING *;
    """)
    Attendee addAttendee(@Param("req") AttendeeRequest attendeeRequest);

    @ResultMap("attendeeMapper")
    @Select("""
        UPDATE attendees SET attendee_name = #{req.attendeeName} WHERE attendee_id = #{attendeeId} RETURNING *;
    """)
    Attendee updateAttendeeById(Long attendeeId, @Param("req") UpdateAttendeeRequest attendeeRequest);

    @ResultMap("attendeeMapper")
    @Delete("""
        DELETE FROM attendees WHERE attendee_id = #{attendeeId};
    """)
    boolean deleteAttendeeById(Long attendeeId);
}