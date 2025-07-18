package com.example.demo.customvalidator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class SurveyAddValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return SurveyAddDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SurveyAddDTO dto = (SurveyAddDTO) target;

        if (dto.getType() == null) {
            errors.rejectValue("type", "type.required", "type 不能为空");
            return;
        }

        if (dto.getType() == 1) {
            if (StringUtils.isBlank(dto.getFormUrl())) {
                errors.rejectValue("formUrl", "formUrl.required", "formUrl 不能为空");
            }
            if (StringUtils.isBlank(dto.getEditableFormUrl())) {
                errors.rejectValue("editableFormUrl", "editableFormUrl.required", "editableFormUrl 不能为空");
            }
        }

        if (dto.getType() == 2) {
            if (StringUtils.isBlank(dto.getBannerPc())) {
                errors.rejectValue("bannerPc", "bannerPc.required", "bannerPc 不能为空");
            }
            if (dto.getSubmitLimit() == null || dto.getSubmitLimit() < 0) {
                errors.rejectValue("submitLimit", "submitLimit.invalid", "submitLimit 非法");
            }

            List<SurveyQuestionDTO> questions = dto.getQuestionList();
            if (questions == null || questions.isEmpty()) {
                errors.rejectValue("questionList", "questionList.required", "问题列表不能为空");
            } else {
                for (int i = 0; i < questions.size(); i++) {
                    SurveyQuestionDTO q = questions.get(i);
                    if (StringUtils.isBlank(q.getTitle())) {
                        errors.rejectValue("questionList[" + i + "].title", "title.required", "问题标题不能为空");
                    }

                    Integer contentOption = q.getContentOption();
                    if (requiresOptionJson(contentOption)) {
                        List<OptionJsonDTO> options = q.getOptionJson();
                        if (options == null || options.isEmpty()) {
                            errors.rejectValue("questionList[" + i + "].optionJson", "optionJson.required", "选项不能为空");
                        } else if (contentOption == 3 && options.size() != 1) {
                            errors.rejectValue("questionList[" + i + "].optionJson", "optionJson.invalid", "评分题只能有一个选项");
                        }

                        for (int j = 0; j < options.size(); j++) {
                            OptionJsonDTO opt = options.get(j);
                            if (StringUtils.isBlank(opt.getType())) {
                                errors.rejectValue("questionList[" + i + "].optionJson[" + j + "].type", "type.required", "type 必填");
                            }
                            if ("score".equals(opt.getType())) {
                                try {
                                    int val = Integer.parseInt(opt.getValue());
                                    if (val < 3 || val > 10) {
                                        errors.rejectValue("questionList[" + i + "].optionJson[" + j + "].value", "value.range", "评分值必须在3~10之间");
                                    }
                                } catch (NumberFormatException e) {
                                    errors.rejectValue("questionList[" + i + "].optionJson[" + j + "].value", "value.invalid", "评分值必须是数字");
                                }
                            } else if (!"other".equals(opt.getType()) && StringUtils.isBlank(opt.getValue())) {
                                errors.rejectValue("questionList[" + i + "].optionJson[" + j + "].value", "value.required", "value 必填");
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean requiresOptionJson(Integer type) {
        return List.of(1, 2, 3).contains(type);
    }
}
