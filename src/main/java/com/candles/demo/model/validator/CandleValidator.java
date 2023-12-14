package com.candles.demo.model.validator;

import com.candles.demo.model.Candle;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateCandleValidator")
public class CandleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Candle.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Candle candle = (Candle) target;
        if (checkInputString(candle.getTitleEn())) {
            errors.rejectValue("titleEn", "titleEn.empty");
        }
        if (checkInputString(candle.getTitleUa())) {
            errors.rejectValue("titleUa", "titleUa.empty");
        }
        if (checkInputString(candle.getNameEn())) {
            errors.rejectValue("nameEn", "nameEn.empty");
        }
        if (checkInputString(candle.getNameUa())) {
            errors.rejectValue("nameUa", "nameUa.empty");
        }
        if (checkInputString(candle.getWax())) {
            errors.rejectValue("wax", "wax.empty");
        }
        if (checkInputString(candle.getSlug())) {
            errors.rejectValue("slug", "slug.empty");
        }
        if (candle.getAromaEn() == null) {
            errors.rejectValue("aromaEn", "aromaEn.empty");
        }
        if (candle.getAromaUa() == null) {
            errors.rejectValue("aromaUa", "aromaUa.empty");
        }
        if (checkInputString(candle.getContainer())) {
            errors.rejectValue("container", "container.empty");
        }
        if (checkInputString(candle.getVolume())) {
            errors.rejectValue("volume", "volume.empty");
        }
        if (candle.getPrice() == null) {
            errors.rejectValue("price", "price.empty");
        }
        if (checkInputString(candle.getDescriptionEn())) {
            errors.rejectValue("descriptionEn", "descriptionEn.empty");
        }
        if (checkInputString(candle.getDescriptionUa())) {
            errors.rejectValue("descriptionUa", "descriptionUa.empty");
        }
        if (checkInputString(candle.getContainerColorEn())) {
            errors.rejectValue("containerColorEn", "containerColorEn.empty");
        }
        if (checkInputString(candle.getContainerColorUa())) {
            errors.rejectValue("containerColorUa", "containerColorUa.empty");
        }
        if (checkInputString(candle.getWaxColorEn())) {
            errors.rejectValue("waxColorEn", "waxColorEn.empty");
        }
        if (checkInputString(candle.getWaxColorUa())) {
            errors.rejectValue("waxColorUa", "waxColorUa.empty");
        }
    }

    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
