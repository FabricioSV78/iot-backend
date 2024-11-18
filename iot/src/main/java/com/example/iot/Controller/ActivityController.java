package com.example.iot.Controller;

import com.example.iot.Model.Activity;
import com.example.iot.Service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@CrossOrigin(origins = "*")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    // POST para guardar una nueva actividad
    @PostMapping("/crearActividad")
    public String saveActivity(@RequestParam String description) {
        activityService.logActivity(description); // Usamos el método del servicio
        return "Actividad guardada con éxito";
    }

    @GetMapping
    public List<Activity> getActivities() {
        return activityService.getAllActivities();
    }
}
