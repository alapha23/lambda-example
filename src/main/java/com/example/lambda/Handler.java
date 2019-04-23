package com.example.lambda;

//import java.util.Date;
//import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler implements RequestHandler<Map<String, Object>, Context> {

    private static final Logger LOG = Logger.getLogger(Handler.class);

    @Override
    public Context handleRequest(Map<String, Object> input, Context context) {
        LOG.info("received: " + input);
        System.out.println("HELLO WORLD");
        return null;
//        Response responseBody = new Response("Hello, the current time is " + new Date());
//        Map<String, String> headers = new HashMap<>();
//        headers.put("X-Powered-By", "AWS Lambda & Serverless");
//        headers.put("Content-Type", "application/json");
/*        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody("Response")
                .setHeaders(headers)
                .build();
                */
    }
}
