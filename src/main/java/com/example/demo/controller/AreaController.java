package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.component.Mail;
import com.example.demo.entity.Area;
import com.example.demo.interceptor.UserAuthInterceptor;
import com.example.demo.service.IAreaService;
import com.example.demo.service.MyService;
import com.example.demo.util.MailUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.MDC;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
    @Autowired
    private HttpServletRequest request;
    @Autowired
    MyService myService;

    @GetMapping("/save")
//    @WebLog(value="保存地区接口")
    public String save(
            @RequestParam(value = "areaId", required = false, defaultValue = "0") Integer areaId,
            @RequestParam(value = "areaName") String areaName,
            @RequestParam(value = "priority", required = false, defaultValue = "0") Integer priority
    ) throws Exception {
//        Marker notifyAdmin = MarkerFactory.getMarker("NOTIFY_ADMIN");
        log.info("{} saved successfully.", areaName);
        log.error("{} saved error", areaName);
        System.out.println(request.getAttribute("num1"));

        System.out.println(MDC.get(UserAuthInterceptor.TRACE_ID));
        if (areaId < 0) {
            log.error(MarkerFactory.getMarker("NOTIFY_ADMIN"), "something wrong", new Exception("exception occurs"));
//            throw new Exception("Area 1111saved exception.");
            return "illegal areaId.";
        }
//        areaService.test();
        // 假设有一个 User 实体对象
        LocalDateTime start = LocalDateTime.now();
        ZonedDateTime end = ZonedDateTime.now();

//        System.out.println("start:" + start + " end:" + end);
        Area area = new Area();
        area.setAreaId(areaId);
        area.setAreaName(areaName);
        area.setPriority(priority);
        area.setStartTime(start);
        area.setEndTime(end);
        boolean result = areaService.save(area); // 调用 save 方法

//        myService.doSomething();
//        myService.doSomething2();
//        myService.doSomething3();
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
        LocalDateTime now = LocalDateTime.now();
        List<Area> users = Arrays.asList(
                new Area(0, "Area 1", 1, now),
                new Area(0, "Area 2", 2, now),
                new Area(0, "Area 3", 3, now)

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
