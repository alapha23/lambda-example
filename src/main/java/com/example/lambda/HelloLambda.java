package com.example.lambda;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;

public class HelloLambda {
    public static void main(String arg[]){
        //NettyServerTransport nettyServerTransport;

        AWSLambdaAsync awsLambda = AWSLambdaAsyncClientBuilder.standard().withClientConfiguration(
                new ClientConfiguration().withMaxConnections(500)).build();

        InvokeRequest request = new InvokeRequest()
                .withFunctionName(AWSUtils.SIDEINPUT_LAMBDA_NAME2)
                .withPayload(String.format("{\"address\":\"%s\", \"port\": %d}",
                        nettyServerTransport.getPublicAddress(), nettyServerTransport.getPort()));

    }
}
