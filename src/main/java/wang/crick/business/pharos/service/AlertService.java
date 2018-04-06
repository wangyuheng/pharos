package wang.crick.business.pharos.service;

import java.util.List;

public interface AlertService {
    /**
     * 向标签下所有用户发送文本内容
     * @param content 文本内容
     * @param tag 标签
     */
    void alertTextToTag(String content, String tag);

    /**
     * 向用户发送文本内容
     * @param content 文本内容
     * @param users 用户列表
     *
     */
    void alertTextToUsers(String content, List<String> users);

    /**
     * 向部门下所有用户发送文本内容
     * @param content
     * @param department
     */
    void alertTextToDepartment(String content, Integer department);
}
