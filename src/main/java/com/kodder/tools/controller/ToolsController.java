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
        return Map.of("years", period.getYears(), "months", period.getMonths(), "days", period.getDays());
    }

    // ✅ Conversion example
    @GetMapping("/convert")
    public Map<String, Object> convert(@RequestParam double value, @RequestParam String unit) {
        if ("km-to-miles".equals(unit)) {
            return Map.of("result", value * 0.621371);
        } else if ("miles-to-km".equals(unit)) {
            return Map.of("result", value * 1.60934);
        } else {
            return Map.of("error", "Invalid unit");
        }
    }

    // 🚀 NEW: Calculator (POST) for frontend compatibility
    @PostMapping("/calculator/{operation}")
    public Map<String, Object> calculate(
            @PathVariable String operation,
            @RequestBody Map<String, Double> body) {
        double a = body.getOrDefault("a", 0.0);
        double b = body.getOrDefault("b", 0.0);

        double result = switch (operation) {
            case "add" -> a + b;
            case "subtract" -> a - b;
            case "multiply" -> a * b;
            case "divide" -> (b != 0) ? a / b : Double.NaN;
            default -> throw new IllegalArgumentException("Invalid operation");
        };

        return Map.of("result", result);
    }
}
