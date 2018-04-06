package com.crick.business.pharos.config;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpDepartmentServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpTagServiceImpl;
import me.chanjar.weixin.cp.api.impl.WxCpUserServiceImpl;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import me.chanjar.weixin.cp.config.WxCpInMemoryConfigStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WxCpService.class)
public class WechatCpConfig {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wechat.cp.corpid}")
    private String corpid;
    @Value("${wechat.cp.corp.secret}")
    private String corpSecret;
    @Value("${wechat.cp.agentid}")
    private Integer agentid;

    @Bean
    @ConditionalOnMissingBean
    public WxCpConfigStorage configStorage() {
        WxCpInMemoryConfigStorage configStorage = new WxCpInMemoryConfigStorage();
        logger.info("****************wechat properties start****************");
        logger.info("corpid:{}", corpid);
        logger.info("corpSecret:{}", corpSecret);
        logger.info("agentid:{}", agentid);
        logger.info("****************wechat properties end****************");
        configStorage.setCorpId(corpid);
        configStorage.setCorpSecret(corpSecret);
        configStorage.setAgentId(agentid);
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxCpService WxCpService(WxCpConfigStorage configStorage) {
        WxCpService wxCpService = new WxCpServiceImpl();
        wxCpService.setWxCpConfigStorage(configStorage);
        wxCpService.setTagService(new WxCpTagServiceImpl(wxCpService));
        wxCpService.setDepartmentService(new WxCpDepartmentServiceImpl(wxCpService));
        wxCpService.setUserService(new WxCpUserServiceImpl(wxCpService));
        return wxCpService;
    }

}