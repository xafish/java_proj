package ru.otus.protobuf;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.RemoteCountServiceGrpc;
import ru.otus.protobuf.generated.UserMessage;

import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8090;

    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);
    private static final long START_NUMBER = 0L;
    private static final long FIN_NUMBER = 30L;


    public static void main(String[] args) throws InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();
        CountDownLatch latch = new CountDownLatch(1);
        System.out.println("\n\n\nЗапускаем отсчет!!!\n\n");
        RemoteCountServiceGrpc.RemoteCountServiceStub newStub = RemoteCountServiceGrpc.newStub(channel);

        long currentValue = 0L;
        long prevServerValue = 0L;
        final long[] serverValue = {0L};

        newStub.startCount(UserMessage.newBuilder().setStartNum(START_NUMBER).setFinNum(FIN_NUMBER).build(),
                new StreamObserver<UserMessage>() {
                    @Override
                    public void onNext(UserMessage um) {
                        synchronized (serverValue) {
                            logger.info("Сервер вернул число: {} ", um.getCountNum());
                            serverValue[0] = um.getCountNum();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println(t);
                        latch.countDown();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("\n\nЯ все!");
                        latch.countDown();
                    }

                });

        try {
            while (latch.getCount() != 0) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (serverValue) {
                    if (prevServerValue == serverValue[0]) {
                        currentValue++;
                        logger.info("currentValue: {} ", currentValue);

                    } else {
                        currentValue = currentValue + serverValue[0] + 1;
                        logger.info("currentValue: {} ", currentValue);

                        prevServerValue = serverValue[0];

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        latch.await();

        channel.shutdown();
    }
}
