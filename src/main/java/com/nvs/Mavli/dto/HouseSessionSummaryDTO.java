// HouseSessionSummaryDTO.java
package com.nvs.Mavli.dto;

import lombok.Data;

@Data
public class HouseSessionSummaryDTO {
    private String house;
    private int totalStudents;
    private int onLeave;
    private int expectedToMark;   // total - onLeave (HM inhi ka headcount dega)
    private int presentCount;
    private int absentCount;
}