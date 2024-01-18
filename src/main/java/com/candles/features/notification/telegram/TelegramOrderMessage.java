package com.candles.features.notification.telegram;

import com.candles.features.order.Configuration;
import com.candles.features.order.CustomCandle;
import com.candles.features.order.Item;
import com.candles.features.order.Order;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TelegramOrderMessage {
    private final Order order;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(order.getId()).append(":\n");
        sb.append(getCustomerInfo());
        sb.append(getItemsInfo());
        sb.append(getCustomCandlesInfo());
        sb.append("total price=").append(order.getTotal());
        return sb.toString();

    }

    private StringBuilder getCustomerInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("customer= ").append(order.getCustomer().getFullName()).append("\n");
        sb.append("phone= ").append(order.getCustomer().getPhone()).append("\n");
        sb.append("email= ").append(order.getCustomer().getEmail()).append("\n");
        sb.append("address= ").append(order.getCustomer().getAddress()).append("\n");
        sb.append("comment= ").append(order.getCustomer().getComment()).append("\n");
        return sb;
    }

    private StringBuilder getItemsInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("items: \n");
        for (Item item : order.getItems()) {
            sb.append("  ").append(item.getCategory()).append(" - quantity:").append(item.getQuantity()).append("\n");
            if (item.getConfiguration() != null) {
                sb.append("    configuration: ").append("\n").append(getBoxConfiguration(item.getConfiguration())).append("\n");
            }
        }
        return sb;
    }

    private StringBuilder getCustomCandlesInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("custom candles: \n");
        for (CustomCandle customCandle : order.getCustomCandles()) {
            sb.append("  ").append("Custom candle").append(" - quantity:").append(customCandle.getQuantity()).append("\n");
            sb.append("    configuration: ").append("\n").append(getCustomCandleConfiguration(customCandle.getConfiguration())).append("\n");
        }
        return sb;
    }

    private StringBuilder getBoxConfiguration(Configuration configuration) {
        StringBuilder sb = new StringBuilder();
        sb.append("      aroma: ").append(configuration.getAroma()).append("\n");
        return sb;
    }

    private StringBuilder getCustomCandleConfiguration(Configuration configuration) {
        StringBuilder sb = new StringBuilder();
        sb.append("      container: ").append(configuration.getContainer()).append("\n");
        sb.append("      wax: ").append(configuration.getWax()).append("\n");
        sb.append("      aroma: ").append(configuration.getAroma()).append("\n");
        sb.append("      wick: ").append(configuration.getWicks()).append("\n");
        sb.append("      color: ").append(configuration.getColor()).append("\n");
        return sb;
    }
}
