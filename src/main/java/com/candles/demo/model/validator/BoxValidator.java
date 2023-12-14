package com.candles.demo.model.validator;

import com.candles.demo.model.Box;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("beforeCreateBoxValidator")
public class BoxValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Box.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Box box = (Box) target;
        if (checkInputString(box.getTitleEn())) {
            errors.rejectValue("titleEn", "titleEn.empty");
        }
        if (checkInputString(box.getTitleUa())) {
            errors.rejectValue("titleUa", "titleUa.empty");
        }
        if (checkInputString(box.getNameEn())) {
            errors.rejectValue("nameEn", "nameEn.empty");
        }
        if (checkInputString(box.getNameUa())) {
            errors.rejectValue("nameUa", "nameUa.empty");
        }
        if (checkInputString(box.getSlug())) {
            errors.rejectValue("slug", "slug.empty");
        }
        if (checkInputString(box.getVolume())) {
            errors.rejectValue("volume", "volume.empty");
        }
        if (box.getPrice() == null) {
            errors.rejectValue("price", "price.empty");
        }
        if (checkInputString(box.getDescriptionEn())) {
            errors.rejectValue("descriptionEn", "descriptionEn.empty");
        }
        if (checkInputString(box.getDescriptionUa())) {
            errors.rejectValue("descriptionUa", "descriptionUa.empty");
        }
        if (box.getKitEn() == null) {
            errors.rejectValue("kitEn", "kitEn.empty");
        }
        if (box.getKitUa() == null) {
            errors.rejectValue("kitUa", "kitUa.empty");
        }

    }

    private boolean checkInputString(String input) {
        return (input == null || input.trim().length() == 0);
    }
}
