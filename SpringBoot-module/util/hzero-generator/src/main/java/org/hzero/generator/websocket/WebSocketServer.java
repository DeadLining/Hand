package org.hzero.generator.websocket;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author zws
 */

@Component
@ServerEndpoint(value = "/websocket")
public class WebSocketServer {
    private static Logger log = LoggerFactory.getLogger(WebSocketConfig.class);
    /**
     * 用来记录当前连接数的变量
     */
    private static volatile int onlineCount = 0;

    /**
     * 线程安全Set，用来存放每个客户端对应的 WebSocket 对象
     */
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    private static Map<String, Integer> lengthMap = new ConcurrentHashMap<String, Integer>();
    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    /**
     * 群发自定义消息
     *
     * @param message 自定义消息
     * @throws IOException
     */
    public static void sendInfo(String message) throws IOException {
        log.info("推送消息内容：" + message);
        for (WebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    /**
     * 获取连接数
     *
     * @return 连接数
     */
    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 连接数加1
     */
    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    /**
     * 连接数减1
     */
    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session 连接会话
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        // 加入set中
        webSocketSet.add(this);
        // 连接数加1
        addOnlineCount();
        // 默认从第一行开始
        lengthMap.put(session.getId(), 1);
        try {
            sendMessage("WebSocket连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
        // 获取日志信息
        boolean first = true;
        while (session != null) {
            BufferedReader reader = null;
            try {
                //日志文件路径，获取最新的
                String filePath = System.getProperty("user.dir") + File.separator + "app.log";
                //字符流
                reader = new BufferedReader(new FileReader(filePath));
                Object[] lines = reader.lines().toArray();

                //只取从上次之后产生的日志
                Object[] copyOfRange = Arrays.copyOfRange(lines, lengthMap.get(session.getId()), lines.length);

                //对日志进行着色，更加美观
                for (int i = 0; i < copyOfRange.length; i++) {
                    String line = (String) copyOfRange[i];
                    //处理等级
                    line = line.replace("DEBUG", "<span style='color: blue;'>DEBUG</span>");
                    line = line.replace("INFO", "<span style='color: #37FF05;'>INFO</span>");
                    line = line.replace("WARN", "<span style='color: orange;'>WARN</span>");
                    line = line.replace("ERROR", "<span style='color: red;'>ERROR</span>");

                    //处理类名
                    line = StringUtils.replace(line, StringUtils.substringBetween(line, "] ", " : "), "<span style='color: #051EFF;'>" + StringUtils.substringBetween(line, "] ", " : ") + "</span>");

                    copyOfRange[i] = line;
                }

                //存储最新一行开始
                lengthMap.put(session.getId(), lines.length);

                //第一次如果太大，截取最新的200行就够了，避免传输的数据太大
                if (first && copyOfRange.length > 200) {
                    copyOfRange = Arrays.copyOfRange(copyOfRange, copyOfRange.length - 200, copyOfRange.length);
                    first = false;
                }

                String result = StringUtils.join(copyOfRange, "<br/>");

                //发送
                sendMessage(result);
                //休眠500毫秒
                Thread.sleep(500);
            } catch (Exception e) {
                //捕获但不处理
            } finally {
                try {
                    clone();
                    reader.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        // 从set中删除
        webSocketSet.remove(this);
        // 连接数减1
        subOnlineCount();
        log.info("有一连接关闭！当前连接数为：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 消息
     * @param session 连接会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端的消息：" + message);
    }

    /**
     * 发生错误时调用的方法
     *
     * @param session 连接会话
     * @param error   错误信息
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发送错误,连接可能被中断");
    }

    /**
     * 实现服务器主动推送消息
     *
     * @param message 消息
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
}
