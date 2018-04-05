package com.crick.business.pharos.service;

import me.chanjar.weixin.cp.bean.WxCpMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AlertTextBuilder {

    private Integer agentid;

    public AlertTextBuilder(Integer agentid) {
        this.agentid = agentid;
    }

    public WxCpMessage buildForTag(String content, String tag) {
        return WxCpMessage.TEXT().agentId(agentid).content(content).toTag(tag).build();
    }

    public WxCpMessage buildForUsers(String content, String users) {
        return WxCpMessage.TEXT().agentId(agentid).content(content).toUser(users).build();
    }

}
