package com.example.iot.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LedService {

    @Value("${esp32.url}")
    private String esp32Url;

    private final RestTemplate restTemplate;

    // Mapa para almacenar el estado de los LEDs (en memoria)
    private Map<Integer, Boolean> ledStates = new HashMap<>();

    // Definir los pines válidos
    private static final Set<Integer> VALID_PINS = Set.of(4, 5, 18, 19, 21, 13, 12); // Pines configurados

    // Constructor
    public LedService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        // Inicializa el estado de los LEDs como apagados
        for (int pin : VALID_PINS) {
            ledStates.put(pin, false);  // Por defecto, todos los LEDs están apagados
        }
    }

    // Controlar el LED (encender o apagar)
    public String controlLed(int pin, boolean state) {
        // Validar si el pin es válido
        if (!VALID_PINS.contains(pin)) {
            return "Error: Pin inválido o no configurado.";
        }

        try {
            // Construir la URL y enviar la solicitud al ESP32
            String endpoint = esp32Url + (state ? "/led/on" : "/led/off");
            String query = "?pin=" + pin;
            System.out.println("Enviando solicitud al ESP32: " + endpoint + query);

            String result = restTemplate.getForObject(endpoint + query, String.class);

            if (result == null) {
                return "Error: Respuesta nula del ESP32.";
            }

            // Actualizar el estado del LED
            ledStates.put(pin, state);
            return result;
        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace();  // Log de la excepción
            return "Error al controlar el LED: " + e.getMessage();
        }
    }

    // Obtener el estado de un LED
    public boolean getLedState(int pin) {
        return ledStates.getOrDefault(pin, false); // Retorna false si el estado no ha sido almacenado
    }

    // Obtener todos los estados de los LEDs
    public Map<Integer, Boolean> getAllLedStates() {
        return ledStates;
    }

    // Activar la alarma
    public String activateAlarm() {
        return restTemplate.getForObject(esp32Url + "/alarm/activate", String.class);
    }

    // Desactivar la alarma
    public String deactivateAlarm() {
        return restTemplate.getForObject(esp32Url + "/alarm/deactivate", String.class);
    }

    // Obtener el estado de movimiento
    public String getMotionStatus() {
        return restTemplate.getForObject(esp32Url + "/motion/status", String.class);
    }
}