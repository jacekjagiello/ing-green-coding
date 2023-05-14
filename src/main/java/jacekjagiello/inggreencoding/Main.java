package jacekjagiello.inggreencoding;

import jacekjagiello.inggreencoding.ports.HttpServer;
import jacekjagiello.inggreencoding.adapters.JavalinHttpServer;
import jacekjagiello.inggreencoding.usecase.CalculateAtmsOrder;
import jacekjagiello.inggreencoding.usecase.GenerateEventQueue;
import jacekjagiello.inggreencoding.usecase.ProcessTransactions;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer httpServer = new JavalinHttpServer(
            new CalculateAtmsOrder(),
            new GenerateEventQueue(),
            new ProcessTransactions()
        );

        httpServer.startServer(8080);
    }
}