package com.example.file.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Table
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
