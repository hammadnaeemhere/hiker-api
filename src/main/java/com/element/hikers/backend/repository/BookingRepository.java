package com.element.hikers.backend.repository;

import com.element.hikers.backend.entity.Booking;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends CrudRepository<Booking, Long> {
    Optional<Booking> findByBookingIdAndBookingKey(Long bookingId, String bookingKey);

    @Query("Select b " +
            "From Booking b " +
            "WHERE " +
            "(:trailId IS NULL OR b.trail.trailId = :trailId ) AND " +
            "(:bookingDate IS NULL OR CAST(b.bookingDate AS date) = CAST(:bookingDate AS date)) AND " +
            "(:fromDate IS NULL OR CAST(b.bookingDate AS date) >= CAST(:fromDate AS date)) AND " +
            "(:toDate IS NULL OR CAST(b.bookingDate AS date) <= CAST(:toDate AS date)) AND " +
            "(:cancelled IS NULL OR b.isCancelled = :cancelled)")
    List<Booking> findAll(@Param("trailId") Long trailId, @Param("bookingDate") Date bookingDate, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("cancelled") Boolean cancelled);
}
