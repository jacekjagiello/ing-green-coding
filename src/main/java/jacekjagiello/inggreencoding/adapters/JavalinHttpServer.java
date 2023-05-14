package jacekjagiello.inggreencoding.adapters;

import io.javalin.Javalin;
import io.javalin.http.Context;
import jacekjagiello.inggreencoding.entities.atmservice.Order;
import jacekjagiello.inggreencoding.entities.onlinegame.Clan;
import jacekjagiello.inggreencoding.entities.onlinegame.EventEntryRequest;
import jacekjagiello.inggreencoding.entities.transactions.Account;
import jacekjagiello.inggreencoding.entities.transactions.Transaction;
import jacekjagiello.inggreencoding.ports.HttpServer;
import jacekjagiello.inggreencoding.usecase.ProcessTransactions;
import jacekjagiello.inggreencoding.usecase.CalculateAtmsOrder;
import jacekjagiello.inggreencoding.usecase.GenerateEventQueue;

import java.util.ArrayList;
import java.util.List;

import static io.javalin.apibuilder.ApiBuilder.post;

public class JavalinHttpServer implements HttpServer {
    private final CalculateAtmsOrder calculateAtmsOrder;
    private final GenerateEventQueue calculateClansEntryOrder;
    private final ProcessTransactions generateAccountSummaryReport;
    private final Javalin server;

    public JavalinHttpServer(CalculateAtmsOrder calculateAtmsOrder, GenerateEventQueue calculateClansEntryOrder, ProcessTransactions generateAccountSummaryReport) {
        this.calculateAtmsOrder = calculateAtmsOrder;
        this.calculateClansEntryOrder = calculateClansEntryOrder;
        this.generateAccountSummaryReport = generateAccountSummaryReport;

        this.server = Javalin.create().routes(() -> {
            post("/atms/calculateOrder", this::calculateAtmOrderHandler);
            post("/onlinegame/calculate", this::generateEventQueueHandler);
            post("/transactions/report", this::processTransactionsHandler);
        });
    }

    public void startServer(int port) {
        this.server.start(port);
    }

    public Javalin getServer() {
        return server;
    }

    private void generateEventQueueHandler(Context ctx) {
        EventEntryRequest eventEntryRequest = ctx.bodyAsClass(EventEntryRequest.class);

        final List<List<Clan>> groups = this.calculateClansEntryOrder.run(
                eventEntryRequest.groupCount,
                new ArrayList<>(List.of(eventEntryRequest.clans))
        );

        ctx.json(groups);
    }

    private void calculateAtmOrderHandler(Context ctx) {
        Order[] orders = ctx.bodyAsClass(Order[].class);

        Order[] sortedOrders = this.calculateAtmsOrder.run(orders);

        ctx.json(sortedOrders);
    }

    private void processTransactionsHandler(Context ctx) {
        Transaction[] transactions = ctx.bodyAsClass(Transaction[].class);

        List<Account> accounts = this.generateAccountSummaryReport.run(transactions);

        ctx.json(accounts);
    }
}
