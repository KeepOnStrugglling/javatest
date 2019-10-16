package com.javatest.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/{sid}")
@Component
public class WebSocketServer {

    private final static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 根据业务需求做调整。
     *    1、路径上所带的sid作为本连接的用户id；
     *    2、如果是本用户要发送信息给另外一个用户，则用toSid作为接收方的用户id。
     *      由于与前端约定发送过来的信息格式为：{"toSid":toSid,"content":content}，直接从message中获取即可
     */

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //旧：concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。由于遍历set费时，改用map优化
    //private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
    //新：使用map对象优化，便于根据sid来获取对应的WebSocket
    private static ConcurrentHashMap<String,WebSocketServer> websocketMap = new ConcurrentHashMap<>();
    //本用户的sid
    private String sid;

    /**
     * 连接成功后调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("sid") String sid) {
        this.session = session;
        //webSocketSet.add(this);     //加入set中
        websocketMap.put(sid,this); //加入map中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+sid+",当前在线人数为" + getOnlineCount());
        this.sid=sid;
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            log.error("websocket IO异常");
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if(websocketMap.get(this.sid)!=null){
            //webSocketSet.remove(this);  //从set中删除
            websocketMap.remove(this.sid);  //从map中删除
            subOnlineCount();           //在线数减1
            log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
        }
    }

    /**
     * 收到客户端消息后调用的方法，根据业务要求进行处理，这里就简单地将收到的消息直接群发推送出去
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // 对心跳进行回复(回复自己)
        if (message.equals("$$")) {
            try {
                sendInfo("1$",sid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 对正常信息进行处理
            log.info("收到来自窗口" + sid + "的信息:" + message);
//        if(StringUtils.isNotBlank(message)){
//            for(WebSocketServer server:websocketMap.values()) {
//                try {
//                    server.sendMessage(message);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//        }
            try {
                //从message中解析出toSid和content
                Map map = JSONObject.parseObject(message, Map.class);
                String toSid = (String) map.get("toSid");
                String content = "sid" + sid + ":" + (String) map.get("content");
                //验证toSid是否上线。如果toSid为""，视为群发
                if ("".equals(toSid) || websocketMap.get(toSid) != null) {
                    sendInfo(content, toSid);
                } else {
                    sendInfo(toSid + "未上线",sid);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时的回调函数
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送消息
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息（用set会方便些）
     * */
    public void sendInfo(String message, String toSid) throws IOException {
        if (!message.equals("1$")) {    // 加这个判断只是单独不想每次回复心跳时都记录一次日志
            log.info("推送消息到窗口" + toSid + "，推送内容:" + message);
        }
        /*for (WebSocketServer item : webSocketSet) {
            try {
                //这里可以设定只推送给这个sid的，为null则全部推送
                if(sid==null) {
                    item.sendMessage(message);
                }else if(item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                continue;
            }
        }*/
        if(StringUtils.isNotBlank(message)){
            for(WebSocketServer server:websocketMap.values()) {
                try {
                    // sid为""时群发，不为""则只发给对应的人和自己
                    if ("".equals(toSid)) {
                        server.sendMessage(message);
                    } else if (server.sid.equals(toSid) || server.sid.equals(sid)) {
                        server.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
