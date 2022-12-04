package com.example.file.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HistoryEgcModel {

    private UserModel userModel;
    private List<DataModel> data;


}
