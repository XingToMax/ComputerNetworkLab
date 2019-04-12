package org.nuaa.tomax.mailclient.core;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/4/9 23:13
 */
public class ConstState {
    /**
     * 服务就绪
     */
    public final static int SERVICE_READY = 202;
    /**
     * 请求动作完成
     */
    public final static int REQUEST_FINISH = 250;
    /**
     * 认证通过
     */
    public final static int AUTH_ACCESS = 235;
    /**
     * 处理中
     */
    public final static int PROCESSING = 221;
    /**
     * 发送开始
     */
    public final static int START_SEND = 354;
    /**
     * 指令错误
     */
    public final static int INSTRUCTION_ERROR = 500;
    /**
     * 命令无法执行
     */
    public final static int COMMAND_UN_EXECUTABLE = 550;
}
