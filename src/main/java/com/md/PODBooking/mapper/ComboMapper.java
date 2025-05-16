package com.md.PODBooking.mapper;

import com.md.PODBooking.dto.ComboDto;
import com.md.PODBooking.entity.Combo;

public class ComboMapper {
    private ComboMapper() {}

    public static Combo mapToCombo(ComboDto comboDto) {
        Combo combo = new Combo();
        combo.setComboName(comboDto.getName());
        combo.setComboPrice(comboDto.getPrice());
        combo.setComboDescription(comboDto.getDescription());
        return combo;
    }

    public static ComboDto mapToComboDto(Combo combo) {
        ComboDto comboDto = new ComboDto();
        comboDto.setName(combo.getComboName());
        comboDto.setPrice(combo.getComboPrice());
        comboDto.setDescription(combo.getComboDescription());
        return comboDto;
    }
}
