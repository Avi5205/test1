package com.kodder.tools.controller;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ToolsController {

    // ✅ Health check
    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }

    // ✅ BMI calculator
    @GetMapping("/bmi")
    public Map<String, Object> bmi(
            @RequestParam double weight,
            @RequestParam double height) {
        double bmi = weight / Math.pow(height / 100, 2);
        String category = (bmi < 18.5) ? "Underweight"
                : (bmi < 24.9) ? "Normal"
                : (bmi < 29.9) ? "Overweight"
                : "Obese";
        return Map.of("bmi", bmi, "category", category);
    }

    // ✅ Age calculator
    @GetMapping("/age")
    public Map<String, Object> age(@RequestParam String dob) {
        LocalDate birthDate = LocalDate.parse(dob);
        Period period = Period.between(birthDate, LocalDate.now());
        return Map.of(
                "years", period.getYears(),
                "months", period.getMonths(),
                "days", period.getDays()
        );
    }

    // ✅ Unit converter (basic: meters <-> cm <-> km)
    @GetMapping("/convert")
    public Map<String, Object> convert(
            @RequestParam String type,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double value) {

        double result = value;
        if (type.equalsIgnoreCase("length")) {
            if (from.equals("m") && to.equals("cm")) result = value * 100;
            else if (from.equals("cm") && to.equals("m")) result = value / 100;
            else if (from.equals("m") && to.equals("km")) result = value / 1000;
            else if (from.equals("km") && to.equals("m")) result = value * 1000;
        }
        return Map.of("result", result);
    }
}
