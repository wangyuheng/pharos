package wang.crick.business.pharos.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.WxCpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WechatAlertService implements AlertService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WxCpService wxCpService;
    @Autowired
    private AlertTextBuilder alertTextBuilder;

    @Override
    public void alertTextToTag(String content, String tag) {
        try {
            wxCpService.messageSend(alertTextBuilder.buildForTag(content, tag));
        } catch (WxErrorException e) {
            logger.error("alertTextToTag error! tag:{}", tag, e);
        }
    }

    @Override
    public void alertTextToUsers(String content, List<String> users) {
        try {
            wxCpService.messageSend(alertTextBuilder.buildForUsers(content, String.join(",", users)));
        } catch (WxErrorException e) {
            logger.error("alertTextToUsers error! users:{}", users, e);
        }
    }

    @Override
    public void alertTextToDepartment(String content, Integer department) {
        try {
            List<WxCpUser> wxCpUserList = wxCpService.getUserService().listSimpleByDepartment(department, true, 0);
            if (null != wxCpUserList) {
                String userList = wxCpUserList.stream()
                        .map(WxCpUser::getUserId)
                        .collect(Collectors.joining(","));
                wxCpService.messageSend(alertTextBuilder.buildForUsers(content, userList));
            }
        } catch (WxErrorException e) {
            logger.error("alertTextToDepartment error! department:{}", department, e);
        }
    }

}
