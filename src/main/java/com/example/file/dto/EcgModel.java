package com.example.file.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EcgModel {

    private Long time;
    private Double leadI;
    private Double leadII;

    private Double leadIII;
    private Double avR;
    private Double avL;
    private Double avF;

    private Double v1;
    private Double v2;
    private Double v3;
    private Double v4;
    private Double v5;
    private Double v6;

}
