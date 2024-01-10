package com.candles.features.orderNotification.telegram;

import com.candles.features.order.Order;
import jakarta.ws.rs.core.UriBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class TelegramNotifierService {
    private final String CHAT_ID;
    private final String TOKEN;

    public TelegramNotifierService(@Value("${telegram.bot.token}") String TOKEN,
                                   @Value("${telegram.bot.chat.id}") String CHAT_ID) {
        this.CHAT_ID = CHAT_ID;
        this.TOKEN = TOKEN;
    }

    @Async
    public void sendOrderNotification(Order order) throws IOException, InterruptedException {
        TelegramOrderMessage message = new TelegramOrderMessage(order);
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();

        UriBuilder builder = UriBuilder
                .fromUri("https://api.telegram.org")
                .path("/{token}/sendMessage")
                .queryParam("chat_id", CHAT_ID)
                .queryParam("text", message.toString());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build("bot" + TOKEN))
                .timeout(Duration.ofSeconds(5))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
