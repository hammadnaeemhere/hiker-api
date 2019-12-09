package com.element.hikers.backend.mappers;

import com.element.hikers.backend.dto.BookingDto;
import com.element.hikers.backend.dto.HikerDto;
import com.element.hikers.backend.entity.Booking;
import com.element.hikers.backend.entity.Hiker;

public class HikerMapper {

    public static Hiker hikerDtoToHiker(HikerDto hikerDto) {
        Hiker hiker = new Hiker();
        hiker.setFirstName(hikerDto.getFirstName());
        hiker.setLastName(hikerDto.getLastName());
        hiker.setAge(hikerDto.getAge());
        hiker.setEmail(hikerDto.getEmail());
        hiker.setMobileNo(hikerDto.getMobileNo());

        return hiker;
    }

    public static HikerDto hikerToHikerDto(Hiker hiker) {
        HikerDto hikerDto = new HikerDto();
        hikerDto.setFirstName(hiker.getFirstName());
        hikerDto.setLastName(hiker.getLastName());
        hikerDto.setAge(hiker.getAge());
        hikerDto.setEmail(hiker.getEmail());
        hikerDto.setMobileNo(hiker.getMobileNo());

        return hikerDto;
    }
}
