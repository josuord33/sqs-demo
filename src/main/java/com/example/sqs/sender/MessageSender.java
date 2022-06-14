package com.example.sqs.sender;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sqs")
public class MessageSender {

  @Value("${cloud.aws.end-point.uri}")
  private String endpoint;

  @Autowired
  private QueueMessagingTemplate queueMessagingTemplate;


  @PostMapping("/send")
  public void send(@RequestBody String message) {
    Message payload = MessageBuilder.withPayload(message)
        .setHeader("message-group-id", "group1")
        .setHeader("message-duplication-id", "dedup1")
        .build();
    log.info("Sended message {} ", message);
    queueMessagingTemplate.send(endpoint, payload);
  }
}
