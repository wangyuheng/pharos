package wang.crick.business.pharos.controller;

import io.swagger.annotations.*;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 企业微信管理
 */
@RestController
@RequestMapping("/wechat")
@Api("微信接口")
public class WechatController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxCpService wxCpService;

    @GetMapping("/cp/tagList")
    @ApiOperation("获取标签列表")
    @ApiResponses(@ApiResponse(code = 200, message = "标签列表"))
    public Object tagList() throws WxErrorException {
        this.logger.info("tag 收到消息");
        return wxCpService.getTagService().listAll();
    }

    @GetMapping("/cp/tag/{tagId}/userList")
    @ApiOperation("标签下所有的成员列表")
    @ApiResponses(@ApiResponse(code = 200, message = "成员列表"))
    public Object tagUsers(@PathVariable("tagId") String tagId) throws WxErrorException {
        this.logger.info("tagUsers 收到消息. tag:{}", tagId);
        return wxCpService.getTagService().listUsersByTagId(tagId);
    }

    @GetMapping("/cp/departmentList")
    @ApiOperation("获取部门列表")
    @ApiResponses(@ApiResponse(code = 200, message = "部门列表"))
    public Object departmentList(@ApiParam(value = "部门id，可以为空") @RequestParam(value = "departmentId", required = false) Integer departmentId) throws WxErrorException {
        this.logger.info("ApiResponse 收到消息. departmentId:{}", departmentId);
        return wxCpService.getDepartmentService().list(departmentId);
    }

    @GetMapping("/cp/department/{departmentId}/userList")
    @ApiOperation("部门下所有的成员列表")
    @ApiResponses(@ApiResponse(code = 200, message = "部门列表"))
    public Object departmentUsers(@PathVariable(value = "departmentId") Integer departmentId) throws WxErrorException {
        this.logger.info("ApiResponse 收到消息. departmentId:{}", departmentId);
        return wxCpService.getUserService().listSimpleByDepartment(departmentId, true, 0);
    }

}