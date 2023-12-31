package com.candles.features.box;

import com.candles.features.landTranslateSupport.Local;
import com.candles.features.landTranslateSupport.Pair;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component("beforeCreateBoxValidator")
public class BoxValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BoxEntity.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BoxEntity boxEntity = (BoxEntity) target;
        if (checkListOfPairForError(boxEntity.getTitle())) {
            errors.rejectValue("title", "titleEn.empty");
        }
        if (checkListOfPairForError(boxEntity.getName())) {
            errors.rejectValue("name", "nameUa.empty");
        }
        if (checkInputStringForError(boxEntity.getSlug())) {
            errors.rejectValue("slug", "slug.empty");
        }
        if (checkInputStringForError(boxEntity.getVolume())) {
            errors.rejectValue("volume", "volume.empty");
        }
        if (boxEntity.getPrice() == null) {
            errors.rejectValue("price", "price.empty");
        }
        if (checkListOfPairForError(boxEntity.getDescription())) {
            errors.rejectValue("description", "descriptionEn.empty");
        }
        if (checkListOfPairForError(boxEntity.getText())) {
            errors.rejectValue("text", "text.empty");
        }
        if (checkListOfPairForError(boxEntity.getKit().getContainer())) {
            errors.rejectValue("kit", "kitEnContainer.empty");
        }
        if (checkListOfPairForError(boxEntity.getKit().getWax())) {
            errors.rejectValue("kit", "kitEnWax.empty");
        }
        if (checkListOfPairForError(boxEntity.getKit().getWick())) {
            errors.rejectValue("kit", "kitEnWick.empty");
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
