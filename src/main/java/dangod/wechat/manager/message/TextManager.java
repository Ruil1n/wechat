package dangod.wechat.manager.message;

import dangod.wechat.core.model.follower.Follower;
import dangod.wechat.core.model.message.req.TextMessage;
import dangod.wechat.core.model.message.send.TextSend;
import dangod.wechat.core.service.FollowerGetter;
import dangod.wechat.service.CalculateService;
import dangod.wechat.service.impl.CalculateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TextManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private FollowerGetter followerGetter;
    @Autowired
    private CalculateService calculateService;

    public String getResulte(Map xml){
        TextMessage message = new TextMessage(xml);
        String openId = (String)xml.get("FromUserName");
        String result = "收到文字";
        try{
            Follower follower = followerGetter.getFollower(openId);
            result = follower.getNickname()+"要的答案"+calculateService.cal(message.getContent());
            result = TextSend.send(openId, result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }
}