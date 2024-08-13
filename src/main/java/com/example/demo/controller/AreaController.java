package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.annotation.WebLog;
import com.example.demo.component.Mail;
import com.example.demo.entity.Area;
import com.example.demo.interceptor.UserAuthInterceptor;
import com.example.demo.service.IAreaService;
import com.example.demo.util.MailUtil;
import groovy.lang.Grab;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author meihaibo
 * @since 2024-08-08
 */
@RestController
@RequestMapping("/area")
@Slf4j
public class AreaController {
    @Autowired
    private IAreaService areaService;
    @Resource
    private MailUtil mailUtil;

    @GetMapping("/save")
//    @WebLog(value="保存地区接口")
    public String save(
            @RequestParam(value = "areaId", required = false, defaultValue = "0") Integer areaId,
            @RequestParam(value = "areaName") String areaName,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority
    ) throws Exception {
        log.info("Area 1111saved successfully.");
        log.error("Area 1111saved error.");
        System.out.println(MDC.get(UserAuthInterceptor.TRACE_ID));
        if (areaId < 0) {
            throw new Exception("Area 1111saved exception.");
        }

        // 假设有一个 User 实体对象
        Area area = new Area();
        area.setAreaId(areaId);
        area.setAreaName(areaName);
        area.setPriority(priority);
        boolean result = areaService.save(area); // 调用 save 方法
        if (result) {
            return "Area saved successfully.";
        } else {
            return "Failed to save area.";
        }
    }

    @GetMapping("/sendMail")
    public void sendMail() {
        Mail mail = new Mail();
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        mail.setRecipient("herbert.mei@heavengifts.com");
        mail.setSubject("修改邮箱");
        mail.setContent("亲爱的用户：您好！\n" +
                "\n" + "    您收到这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了修改邮箱。假如这不是您本人所申请, 请不用理会这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。\n" +
                "\n" +
                "   请使用以下验证码完成后续修改邮箱流程\n" + "\n  " +
                code + "\n\n" + "  注意：请您收到邮件的十分钟内（" +
                DateFormatUtils.format(new Date().getTime() + 10 * 60 * 1000, "yyyy-MM-dd HH:mm:ss") +
                "）前使用，否则验证码将会失效。"
        );
        mailUtil.sendSimpleMail(mail);
    }

    @GetMapping("/saveBatch")
    public String saveBatch() {
        // 假设有一组 User 实体对象
        List<Area> users = Arrays.asList(
                new Area(0, "Area 1", 1),
                new Area(0, "Area 2", 2),
                new Area(0, "Area 3", 3)

        );
        // 使用默认批次大小进行批量插入
        boolean result = areaService.saveBatch(users, 2); // 调用 saveBatch 方法，默认批次大小
        if (result) {
            return "Users saved successfully.";
        } else {
            return "Failed to save users.";
        }
    }

    @GetMapping("/get")
    public Map<String, Object> get(
            @RequestParam(value = "areaId", required = false) Integer areaId,
            @RequestParam(value = "areaName", required = false) String areaName
    ) {
        //        return areaService.getById(areaId);
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        if (areaId != null) {
            queryWrapper.eq("area_id", areaId);
        }
        if (areaName != null) {
            queryWrapper.eq("area_name", areaName);
        }
        return areaService.getMap(queryWrapper);
    }

    @GetMapping("/getList")
    public List<Area> getList(
            @RequestParam(value = "areaId", required = false) Integer areaId,
            @RequestParam(value = "areaName", required = false) String areaName
    ) {
        QueryWrapper<Area> queryWrapper = new QueryWrapper<>();
        if (areaId != null) {
            queryWrapper.eq("area_id", areaId);
        }
        if (areaName != null) {
            queryWrapper.eq("area_name", areaName);
        }
        System.out.println(areaService.list(queryWrapper).stream().map(Area::getAreaName).toList());
        System.out.println(areaService.listMaps(queryWrapper));
        return areaService.list(queryWrapper);
    }
}
