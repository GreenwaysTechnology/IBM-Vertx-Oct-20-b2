package com.ibm.fp.interfaces.lambda.functionasparams;

import java.util.Arrays;
import java.util.List;

@FunctionalInterface
interface Handler {
    //this is function
    void handle();
}

@FunctionalInterface
interface HttpHandler<T> {
    void handle(T payload);
}

class Socket {
    //receive function as parameter
    public void requestHandler(Handler handler) {
        handler.handle();
    }
}

class HttpServer {
    public void requestHandler(HttpHandler<String> httpHandler) {
        String response = "This is response";
        httpHandler.handle(response);
    }
}


public class FuncitionAsParameter {
    public static void main(String[] args) {
        Socket socket = new Socket();
        //pass via inner class ;in line
        socket.requestHandler(new Handler() {
            @Override
            public void handle() {
                System.out.println("inner class connect");
            }
        });
        Handler handler = null;
        handler = new Handler() {
            @Override
            public void handle() {
                System.out.println("inner class connect");
            }
        };
        socket.requestHandler(handler);
        //pass via lambda ; inlne lambda
        socket.requestHandler(() -> System.out.println("lambda connect ; inline"));
        //pass via lambda ; in separate
        handler = () -> System.out.println("lambda connect ; Separate variable");
        socket.requestHandler(handler);
        /////////////////////////////////////////////////////////////////////////////////////
        HttpServer httpServer = new HttpServer();

        httpServer.requestHandler(response -> System.out.println(response));
        HttpHandler<String> httpHandler = response -> System.out.println(response);
        httpServer.requestHandler(httpHandler);

    }
}
