package com.example.demo.customvalidator;

import lombok.Data;

import java.util.List;

@Data
public class SurveyQuestionDTO {
    private String title;
    private Integer contentOption; // 比如 1=单选，2=多选，3=评分

    private List<OptionJsonDTO> optionJson;
}
