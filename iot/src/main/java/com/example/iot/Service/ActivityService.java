package com.example.iot.Service;

import com.example.iot.Model.Activity;
import com.example.iot.Repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public void logActivity(String description) {
        Activity activity = new Activity(description, LocalDateTime.now());
        activityRepository.save(activity);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }
}
