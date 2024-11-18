package com.example.iot.Controller;


import com.example.iot.Service.ActivityService;
import com.example.iot.Service.LedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class Esp32Controller {

    @Autowired
    private LedService ledService;

    @Autowired
    private ActivityService activityService;
    private boolean alarmActive = false;


    @PostMapping("/toggle")
    public String toggleLed(@RequestParam int pin, @RequestParam boolean state) {

        String action = state ? "encendido" : "apagado";
        String result = ledService.controlLed(pin, state);  // Llamada al servicio para controlar el LED
        //activityService.logActivity("LED " + pin + " fue " + action);  // Registrar actividad
        return result;
    }


    @PostMapping("/alarm/activate")
    public String activateAlarm() {
        alarmActive = true;
        //activityService.logActivity("Se activó la alarma");
        return ledService.activateAlarm();
    }

    @PostMapping("/alarm/deactivate")
    public String deactivateAlarm() {
        alarmActive = false;
        //activityService.logActivity("Se desactivó la alarma");
        return ledService.deactivateAlarm();
    }

    @GetMapping("/motion/status")
    public String getMotionStatus() {
        String motionStatus = ledService.getMotionStatus();
        // Aquí puedes agregar un registro si deseas almacenar los eventos de detección de movimiento
        // activityService.logActivity("Estado de movimiento consultado: " + motionStatus);
        return motionStatus;
    }

    @GetMapping("/alarm/status")
    public String getAlarmStatus() {
        return alarmActive ? "activated" : "deactivated"; // Devuelve el estado actual de la alarma
    }

    // Endpoint para obtener el estado de todos los LEDs
    @GetMapping("/leds/status")
    public Map<Integer, Boolean> getLedStates() {
        return ledService.getAllLedStates();  // Devuelve el estado de todos los LEDs
    }


}

