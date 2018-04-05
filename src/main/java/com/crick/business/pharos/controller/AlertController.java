package com.crick.business.pharos.controller;

import com.crick.business.pharos.entity.Restfulesponse;
import com.crick.business.pharos.service.AlertService;
import com.crick.business.pharos.service.WechatAlertService;
import io.swagger.annotations.*;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信企业号告警通知
 */
@RestController
@RequestMapping("/alert")
@Api("微信接口")
public class AlertController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlertService alertService;

    @PostMapping("/tag/{tag}")
    @ApiOperation("给标签下所有成员发送报警信息")
    @ApiResponses(@ApiResponse(code = 200, message = "success"))
    public Restfulesponse alertTag(
            @ApiParam(name = "tag", value = "标签", required = true) @PathVariable("tag") String tag,
            @ApiParam(name = "message", value = "报警消息内容", required = true) @RequestParam("message") String message) {
        this.logger.info("alertTag 收到消息: tag:{}, message:{}", tag, message);
        alertService.alertTextToTag(message, tag);
        return Restfulesponse.success();
    }

    @PostMapping("/department/{department}")
    @ApiOperation("给部门下所有成员发送报警信息")
    @ApiResponses(@ApiResponse(code = 200, message = "success"))
    public Restfulesponse alertDepartment(
            @ApiParam(name = "department", value = "部门", required = true) @PathVariable("department") Integer department,
            @ApiParam(name = "message", value = "报警消息内容", required = true) @RequestParam("message") String message) {
        this.logger.info("alertTag 收到消息: department:{}, message:{}", department, message);
        alertService.alertTextToDepartment(message, department);
        return Restfulesponse.success();
    }

    @PostMapping("/users")
    @ApiOperation("给用户发送报警信息")
    @ApiResponses(@ApiResponse(code = 200, message = "succ"))
    public Object alertUsers(
            @ApiParam(name = "users", value = "用户名列表", required = true) @RequestParam("users") List<String> users,
            @ApiParam(name = "message", value = "报警消息内容", required = true) @RequestParam("message") String message) throws WxErrorException {
        this.logger.info("alertUsers 收到消息: users:{}, message:{}", users, message);
        alertService.alertTextToUsers(message, users);
        return Restfulesponse.success();
    }

}