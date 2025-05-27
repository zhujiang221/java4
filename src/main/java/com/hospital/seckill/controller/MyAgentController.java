package com.hospital.seckill.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import com.coze.openapi.client.chat.CreateChatReq;
import com.coze.openapi.client.chat.model.ChatEvent;
import com.coze.openapi.client.chat.model.ChatEventType;
import com.coze.openapi.client.connversations.message.model.Message;
import com.coze.openapi.service.auth.TokenAuth;
import com.coze.openapi.service.service.CozeAPI;

import io.reactivex.Flowable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/")
public class MyAgentController {
    @RequestMapping("/ask")
    public SseEmitter ask(String msg){
        SseEmitter emitter = new SseEmitter();


            // Get an access_token through personal access token or oauth.
            String token = "pat_u4FSK3855xlxUzve7bW7LHGVjgN7WhkoQdqJYdpGcdByHXuq8mtcK3NGE3zaL5r7";
            String botID = "7502252864853868583";
            String userID = "user10123325";

            TokenAuth authCli = new TokenAuth(token);

            // Init the Coze client through the access_token.
            CozeAPI coze =
                    new CozeAPI.Builder()
                            .baseURL("https://api.coze.cn/v3/chat/")
                            .auth(authCli)
                            .readTimeout(10000)
                            .build();
            ;

            /*
             * Step one, create chat
             * Call the coze.chat().stream() method to create a chat. The create method is a streaming
             * chat and will return a Flowable ChatEvent. Developers should iterate the iterator to get
             * chat event and handle them.
             * */
            CreateChatReq req =
                    CreateChatReq.builder()
                            .botID(botID)
                            .userID(userID)
                            .messages(Collections.singletonList(Message.buildUserQuestionText(msg)))
                            .build();

            Flowable<ChatEvent> resp = coze.chat().stream(req);
            resp.blockingForEach(
                    event -> {
                        if (ChatEventType.CONVERSATION_MESSAGE_DELTA.equals(event.getEvent())) {
                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                            emitter.send(SseEmitter.event().data(event.getMessage().getContent()));
                        }
                        if (ChatEventType.CONVERSATION_CHAT_COMPLETED.equals(event.getEvent())) {
                            System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                            System.out.println("Token usage:" + event.getChat().getUsage().getTokenCount());
                        }
                    });
            System.out.println("done");
            coze.shutdownExecutor();
            System.out.println(emitter);
            return emitter;
    }
}
