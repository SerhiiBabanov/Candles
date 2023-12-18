package com.candles.features.candle;

import com.candles.features.local.Local;
import com.candles.model.Pair;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component("beforeCreateCandleValidator")
public class CandleValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CandleEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CandleEntity candleEntity = (CandleEntity) target;
        if (checkListOfPairForError(candleEntity.getTitle())) {
            errors.rejectValue("title", "titleEn.empty");
        }
        if (checkListOfPairForError(candleEntity.getName())) {
            errors.rejectValue("name", "nameEn.empty");
        }
        if (checkInputStringForError(candleEntity.getWax())) {
            errors.rejectValue("wax", "wax.empty");
        }
        if (checkInputStringForError(candleEntity.getSlug())) {
            errors.rejectValue("slug", "slug.empty");
        }
        if (checkListOfPairForError(candleEntity.getAroma().getName())) {
            errors.rejectValue("aroma", "aromaEn.empty");
        }
        if (checkListOfPairForError(candleEntity.getContainer())) {
            errors.rejectValue("container", "container.empty");
        }
        if (checkInputStringForError(candleEntity.getVolume())) {
            errors.rejectValue("volume", "volume.empty");
        }
        if (candleEntity.getPrice() == null) {
            errors.rejectValue("price", "price.empty");
        }
        if (checkListOfPairForError(candleEntity.getDescription())) {
            errors.rejectValue("description", "descriptionEn.empty");
        }
        if (checkListOfPairForError(candleEntity.getContainerColor())) {
            errors.rejectValue("containerColor", "containerColorUa.empty");
        }
        if (checkListOfPairForError(candleEntity.getWaxColor())) {
            errors.rejectValue("waxColor", "waxColorUa.empty");
        }
    }

    private boolean checkListOfPairForError(List<Pair> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        boolean isEnNotPresentOrValueIsEmpty = list.stream()
                .noneMatch(pair -> (pair.getKey() == Local.EN) && !checkInputStringForError(pair.getValue()));
        boolean isUaNotPresentOrValueIsEmpty = list.stream()
                .noneMatch(pair -> (pair.getKey() == Local.UA) && !checkInputStringForError(pair.getValue()));
        return isEnNotPresentOrValueIsEmpty || isUaNotPresentOrValueIsEmpty;
    }

    private boolean checkInputStringForError(String input) {
        return (input == null || input.trim().isEmpty());
    }
}
