package org.example;

import com.microsoft.azure.functions.ExecutionContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.adapter.azure.AzureFunctionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.function.Function;

@SpringBootApplication
public class Main {

    @Bean
    public Function<Message<String>, String> uppercase() {
        return message -> {
            String value = message.getPayload();

            // Azure Specific
            ExecutionContext context = (ExecutionContext) message
                    .getHeaders()
                    .get(AzureFunctionUtil.EXECUTION_CONTEXT);

            context.getLogger().info(() -> "Uppercase Called with " + value);

            return value.toUpperCase();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}