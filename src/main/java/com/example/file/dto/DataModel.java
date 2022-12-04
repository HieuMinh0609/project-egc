package com.example.file.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataModel {

    private Long id;

    private Long time;
    private BigDecimal leadI;
    private BigDecimal leadII;

    private BigDecimal leadIII;
    private BigDecimal avR;
    private BigDecimal avL;
    private BigDecimal avF;

    private BigDecimal v1;
    private BigDecimal v2;
    private BigDecimal v3;
    private BigDecimal v4;
    private BigDecimal v5;
    private BigDecimal v6;

    private Long userId;

}
