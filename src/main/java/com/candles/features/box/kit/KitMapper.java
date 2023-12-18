package com.candles.features.box.kit;

import com.candles.features.local.Local;
import com.candles.service.Utils;
import org.springframework.stereotype.Service;

@Service
public class KitMapper {
    public KitModel toModel(Kit kit, Local locale) {
        KitModel kitModel = new KitModel();
        kitModel.setContainer(Utils.getPropertyByLocal(kit.getContainer(), locale));
        kitModel.setWax(kit.getWax());
        kitModel.setWick(kit.getWick());
        kitModel.setAromaToChoose(Utils.getPropertyByLocal(kit.getAromaToChoose(), locale));
        kitModel.setMatchsticks(kit.getMatchsticks());
        return kitModel;
    }

}
