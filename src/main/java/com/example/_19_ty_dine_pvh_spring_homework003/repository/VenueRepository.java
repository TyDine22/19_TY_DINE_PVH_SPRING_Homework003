package com.example._19_ty_dine_pvh_spring_homework003.repository;

import com.example._19_ty_dine_pvh_spring_homework003.model.entity.Venue;
import com.example._19_ty_dine_pvh_spring_homework003.model.request.VenueRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VenueRepository {
    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id"),
            @Result(property = "venueName", column = "venue_name")
    })
    @Select("""
        SELECT * FROM venues OFFSET #{offset} LIMIT #{size};
    """)
    List<Venue> getAllVenues(Integer offset, Integer size);

    @ResultMap("venueMapper")
    @Select("""
        SELECT * FROM venues WHERE venue_id = #{venueId};
    """)
    Venue getVenueById(Long venueId);

    @ResultMap("venueMapper")
    @Select("""
        INSERT INTO venues VALUES (default, #{req.venueName}, #{req.location}) RETURNING *;
    """)
    Venue addVenue(@Param("req") VenueRequest venueRequest);

    @ResultMap("venueMapper")
    @Select("""
        UPDATE venues SET venue_name = #{req.venueName}, location = #{req.location} WHERE venue_id = #{venueId} RETURNING *;
    """)
    Venue updateVenueById(Long venueId, @Param("req") VenueRequest venueRequest);

    @ResultMap("venueMapper")
    @Delete("""
        DELETE FROM venues WHERE venue_id = #{venueId};
    """)
    boolean deleteVenueById(Long venueId);
}