package com.example.demo.customvalidator;

import lombok.Data;

import java.util.List;

@Data
public class SurveyAddDTO {
    private Integer type; // 1=Google表单, 2=自定义表单
    private String formUrl;
    private String editableFormUrl;

    private String bannerPc;
    private String bannerMobile;
    private Integer submitLimit;

    private List<SurveyQuestionDTO> questionList;
}
