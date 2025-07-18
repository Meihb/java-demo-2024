package com.example.demo.customvalidator;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyAddValidator surveyAddValidator;

    public SurveyController(SurveyAddValidator surveyAddValidator) {
        this.surveyAddValidator = surveyAddValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody SurveyAddDTO dto) {
        DataBinder binder = new DataBinder(dto);
        binder.addValidators(surveyAddValidator);
        binder.validate();

        BindingResult result = binder.getBindingResult();
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> b, LinkedHashMap::new));
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok("校验通过");
    }
}
