package com.candles.service;

import com.candles.features.box.BoxEntity;
import com.candles.features.box.BoxRepository;
import com.candles.features.box.kit.Kit;
import com.candles.features.candle.CandleEntity;
import com.candles.features.candle.CandleRepository;
import com.candles.features.candle.aroma.Aroma;
import com.candles.features.landTranslateSupport.Local;
import com.candles.features.order.Customer;
import com.candles.features.order.Item;
import com.candles.features.order.Order;
import com.candles.features.landTranslateSupport.Pair;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class InitTestDataService {
    private final BoxRepository boxRepository;
    private final CandleRepository candleRepository;

    @PostConstruct
    public void initTestData() {
        boxRepository.save(getBoxEntity());
        candleRepository.save(getCandleEntity());
    }

    public static BoxEntity getBoxEntity() {
        BoxEntity boxEntity = new BoxEntity();
        boxEntity.setId("Test Id");
        boxEntity.setTitle(Arrays.asList(new Pair(Local.EN, "Title EN"), new Pair(Local.UA, "Title UA")));
        boxEntity.setName(Arrays.asList(new Pair(Local.EN, "Name EN"), new Pair(Local.UA, "Name UA")));
        boxEntity.setSlug("Test Slug");
        Kit kit = getKit();
        boxEntity.setKit(kit);
        boxEntity.setVolume("Test Volume");
        boxEntity.setPrice(new BigDecimal("10.00"));
        boxEntity.setDescription(Arrays.asList(new Pair(Local.EN, "Description EN"), new Pair(Local.UA, "Description UA")));
        boxEntity.setImages(Arrays.asList("Image1", "Image2"));
        return boxEntity;
    }

    public static CandleEntity getCandleEntity() {
        CandleEntity candleEntity = new CandleEntity();
        candleEntity.setId("Test Id");
        candleEntity.setTitle(Arrays.asList(new Pair(Local.EN, "Title EN"), new Pair(Local.UA, "Title UA")));
        candleEntity.setName(Arrays.asList(new Pair(Local.EN, "Name EN"), new Pair(Local.UA, "Name UA")));
        candleEntity.setWax("Test Wax");
        candleEntity.setWick("Test Wick");
        candleEntity.setWicks(1);
        candleEntity.setContainer("Container EN");
        candleEntity.setSlug("Test Slug");
        candleEntity.setDescription(Arrays.asList(new Pair(Local.EN, "Description EN"), new Pair(Local.UA, "Description UA")));
        candleEntity.setContainerColor(Arrays.asList(new Pair(Local.EN, "Container color EN"), new Pair(Local.UA, "Container color UA")));
        candleEntity.setWaxColor(Arrays.asList(new Pair(Local.EN, "Wax color EN"), new Pair(Local.UA, "Wax color UA")));
        candleEntity.setPrice(BigDecimal.valueOf(100));
        Aroma aroma = getAroma();
        candleEntity.setAroma(aroma);
        candleEntity.setVolume("Test Volume");
        candleEntity.setImages(Collections.singletonList("Test image"));
        return candleEntity;
    }

    public static Kit getKit() {
        Kit kit = new Kit();
        kit.getContainer().add(new Pair(Local.UA, "Kit name UA"));
        kit.getContainer().add(new Pair(Local.EN, "Kit name UA"));
        kit.getWax().add(new Pair(Local.UA, "Kit wax UA"));
        kit.getWax().add(new Pair(Local.EN, "Kit wax EN"));
        kit.getWick().add(new Pair(Local.UA, "Kit wick UA"));
        kit.getWick().add(new Pair(Local.EN, "Kit wick EN"));
        kit.getAromaToChoose().add(new Pair(Local.UA, "Aroma UA"));
        kit.getAromaToChoose().add(new Pair(Local.EN, "Aroma EN"));
        kit.getMatchsticks().add(new Pair(Local.UA, "Matchsticks UA"));
        kit.getMatchsticks().add(new Pair(Local.EN, "Matchsticks EN"));
        return kit;
    }

    public static Aroma getAroma() {
        Aroma aroma = new Aroma();
        aroma.getName().add(new Pair(Local.UA, "Name UA"));
        aroma.getName().add(new Pair(Local.EN, "Name EN"));
        aroma.getBaseNotes().add(Arrays.asList(new Pair(Local.UA, "Base note UA"), new Pair(Local.EN, "Base note EN")));
        aroma.getTopNotes().add(Arrays.asList(new Pair(Local.UA, "Top note UA"), new Pair(Local.EN, "Top note EN")));
        return aroma;
    }

    public static Order getOrder() {
        Item box = new Item();
        box.setId("boxId");
        box.setCategory("box");
        box.setName("boxName");
        box.setDescription("boxDescription");
        box.setPrice(BigDecimal.valueOf(100));
        box.setQuantity(1);
        box.setTotal(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(1)));

        Item candle = new Item();
        candle.setId("candle");
        candle.setCategory("candleType");
        candle.setName("candleName");
        candle.setDescription("candleDescription");
        candle.setPrice(BigDecimal.valueOf(100));
        candle.setQuantity(1);
        candle.setTotal(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(1)));

        Customer customer = new Customer();
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        customer.setEmail("email");
        customer.setPhone("phone");
        customer.setAddress("address");

        Order order = new Order();
        List<Item> items = new ArrayList<>();
        items.add(box);
        items.add(candle);
        order.setItems(items);
        order.setCustomer(customer);
        order.setTotal(BigDecimal.valueOf(200));
        order.setPayed(false);
        order.setDate("date");
        return order;
    }

}
