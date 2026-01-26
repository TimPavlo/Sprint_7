package api.models;

import java.util.ArrayList;
import java.util.List;

public class OrderGenerator {
    public static Order getOrderWithColor(List<String> color) {
        Order order = new Order(
                "Иван",
                "Иванов",
                "ул. Ленина, д.1",
                "Сокольники",
                "+79991112233",
                3,
                "2026-01-20",
                "Позвонить за час",
                color
        );
        return order;
    }

    public static Order getOrderBlack() {
        List<String> colors = new ArrayList<>();
        colors.add("BLACK");
        return getOrderWithColor(colors);
    }

    public static Order getOrderGrey() {
        List<String> colors = new ArrayList<>();
        colors.add("GREY");
        return getOrderWithColor(colors);
    }

    public static Order getOrderBothColors() {
        List<String> colors = new ArrayList<>();
        colors.add("BLACK");
        colors.add("GREY");
        return getOrderWithColor(colors);
    }

    public static Order getOrderWithoutColor() {
        return getOrderWithColor(null);
    }
}
