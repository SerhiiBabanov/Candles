package com.candles.features.box.kit;

import com.candles.features.landTranslateSupport.Local;
import com.candles.service.Utils;
import org.springframework.stereotype.Service;

@Service
public class KitMapper {
    public KitModel toModel(Kit kit, Local locale) {
        KitModel kitModel = new KitModel();
        kitModel.setContainer(Utils.getPropertyByLocal(kit.getContainer(), locale));
        kitModel.setWax(Utils.getPropertyByLocal(kit.getWax(), locale));
        kitModel.setWick(Utils.getPropertyByLocal(kit.getWick(), locale));
        kitModel.setAromaToChoose(Utils.getPropertyByLocal(kit.getAromaToChoose(), locale));
        if (kit.getMatchsticks() != null) {
            kitModel.setMatchsticks(Utils.getPropertyByLocal(kit.getMatchsticks(), locale));
        }
        return kitModel;
    }

}
