package io.empowerhack.hub.service;

import io.empowerhack.hub.domain.Calendar;
import io.empowerhack.hub.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CalendarService {

    @Autowired
    private CalendarRepository calendarRepository;

    public List<Calendar> findAll() {
        List<Calendar> project = new ArrayList<>();
        this.calendarRepository.findAll().forEach(item->{
            project.add(item);
        });

        return project;
    }

    public Calendar save(Calendar project) {

        Calendar existingCalendar = this.findByUid(project.getUid());
        if (existingCalendar != null) {
            existingCalendar.setName(project.getName());
            existingCalendar.setDescription(project.getDescription());
            existingCalendar.setLocation(project.getLocation());
            existingCalendar.setDate(project.getDate());

            return this.calendarRepository.save(existingCalendar);
        }

        project.setCreatedOn(new Date());

        return this.calendarRepository.save(project);
    }

    public Calendar findByUid(String uid) {
        return this.calendarRepository.findByUid(uid);
    }
}
