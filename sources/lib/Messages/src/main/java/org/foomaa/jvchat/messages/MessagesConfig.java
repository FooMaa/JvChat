package org.foomaa.jvchat.messages;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.foomaa.jvchat.tools.StructTools;

@Configuration
public class MessagesConfig {
    @Bean
    public DefinesMessages beanDefinesMessages() {
        return DefinesMessages.builder().build();
    }

    @Bean
    public DeserializatorDataMessages beanDeserializatorDataMessages() {
        return DeserializatorDataMessages.builder().build();
    }

    @Bean
    public SerializatorDataMessages beanSerializatorDataMessages(StructTools structTools) {
        return SerializatorDataMessages.builder().structTools(structTools).build();
    }
}
