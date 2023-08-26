package org.example;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.adapter.azure.AzureFunctionUtil;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
public class MyAzureFunction {

    // Plain Spring bean - not a Spring Cloud Functions!
    @Autowired
    private Function<Message<String>, String> uppercase;

    // The FunctionCatalog leverages the Spring Cloud Function framework.
    @Autowired
    private FunctionCatalog functionCatalog;

    @FunctionName("name")
    public HttpResponseMessage upperUser(
            @HttpTrigger(
                    name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    methods = {HttpMethod.GET, HttpMethod.POST}
            ) HttpRequestMessage<String> request,
            ExecutionContext context
    ) {
        String name = request.getBody();

        context.getLogger().info("Received request with " + name);

        User user = User.builder()
                .name(name.toUpperCase())
                .salutation("MR")
                .build();

        return request.createResponseBuilder(HttpStatus.OK)
                .body(user)
                .header("Content-Type", "application/json")
                .build();
    }

    @FunctionName("user")
    public HttpResponseMessage upperUser2(
            @HttpTrigger(
                    name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    methods = {HttpMethod.GET, HttpMethod.POST}
            ) HttpRequestMessage<User> request,
            ExecutionContext context
    ) {
        context.getLogger().info("=^.^=");

        User user = request.getBody();

        context.getLogger().info("Received request with " + user);

        User upperUser = User.builder()
                .name(user.getName().toUpperCase())
                .salutation(user.getSalutation().toUpperCase())
                .build();

        return request.createResponseBuilder(HttpStatus.OK)
                .body(upperUser)
                .header("Content-Type", "application/json")
                .build();
    }

    @FunctionName("uppercase")
    public String anyNameIsOk(
            @HttpTrigger(
                    name = "req",
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    // specify method, or get 404! default is empty set!
                    methods = {HttpMethod.GET, HttpMethod.POST}
            ) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context
    ) {
        // Enhance message with ExecutionContext...
        @SuppressWarnings("unchecked")
        Message<String> message = (Message<String>) AzureFunctionUtil
                .enhanceInputIfNecessary(
                        request.getBody().get(), context
                );

        // ...so that it is available to the plain function as part of the message
        return this.uppercase.apply(message);
    }
}

