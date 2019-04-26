package com.example.lambda;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.lambda.AWSLambdaAsync;
import com.amazonaws.services.lambda.AWSLambdaAsyncClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.nemo.offloading.client.NettyServerSideChannelHandler;
import org.apache.nemo.offloading.client.NettyServerTransport;
import org.apache.nemo.offloading.client.OffloadingEventHandler;
import org.apache.reef.tang.Injector;
import org.apache.reef.tang.JavaConfigurationBuilder;
import org.apache.reef.tang.Tang;
import org.apache.reef.tang.exceptions.InjectionException;
import org.apache.reef.wake.remote.ports.TcpPortProvider;
import org.apache.reef.wake.remote.ports.parameters.TcpPortRangeBegin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelloLambda {
    public static void main(String arg[]) throws InjectionException {

        final JavaConfigurationBuilder jcb = Tang.Factory.getTang().newConfigurationBuilder();
        jcb.bindNamedParameter(TcpPortRangeBegin.class, "5000");

        final Injector injector = Tang.Factory.getTang().newInjector(jcb.build());
        final TcpPortProvider tcpPortProvider = injector.getInstance(TcpPortProvider.class);
        final ChannelGroup serverChannelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        final OffloadingEventHandler nemoEventHandler = null;

        NettyServerTransport nettyServerTransport =  new NettyServerTransport(
                tcpPortProvider, new NettyServerSideChannelHandler(serverChannelGroup, nemoEventHandler));;

        AWSLambdaAsync awsLambda = AWSLambdaAsyncClientBuilder.standard().withClientConfiguration(
                new ClientConfiguration().withMaxConnections(500)).build();

        System.out.println("Create request");
        InvokeRequest request = new InvokeRequest()
                .withFunctionName(AWSUtils.SIDEINPUT_LAMBDA_NAME2)
                .withPayload(String.format("{\"address\":\"%s\", \"port\": %d}",
                        nettyServerTransport.getPublicAddress(), nettyServerTransport.getPort()));

        System.out.println("Invoke request");
        awsLambda.invokeAsync(request);
        System.out.println("Blocking invoke request.");
    }
}
