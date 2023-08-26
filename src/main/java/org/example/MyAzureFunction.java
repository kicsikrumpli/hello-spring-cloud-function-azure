package org.example;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
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

    @FunctionName("spring")
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

