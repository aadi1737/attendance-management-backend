// HouseSessionMarkRequestDTO.java
package com.nvs.Mavli.dto;

import com.nvs.Mavli.entity.SessionType;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HouseSessionMarkRequestDTO {
    private String house;
    private LocalDate date;
    private SessionType sessionType;
    private int presentCount;
    private int absentCount;
}