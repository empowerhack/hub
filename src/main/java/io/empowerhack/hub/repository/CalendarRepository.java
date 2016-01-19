package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.Calendar;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends PagingAndSortingRepository<Calendar, Long> {

    Calendar findByUid(@Param("uid") String uid);
}
