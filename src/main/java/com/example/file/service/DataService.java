package com.example.file.service;

import com.example.file.dto.DataModel;
import com.example.file.entity.Data;
import com.example.file.repository.DataRepository;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    public DataService(DataRepository repository) {
        this.repository = repository;
    }

    DataRepository repository;

    public void saveAll(List<DataModel> dataModelList, Long userId) {
        List<Data> data = convertTo(dataModelList, userId);
        repository.saveAll(data);
    }

    public List<Data> findAllByUserId(Long userId) {
       return repository.findAllByUserId(userId);
    }

    private List<Data> convertTo(List<DataModel> dataModelList, Long userId) {
        List<Data> list = new ArrayList<>();

        for (DataModel dataModel : dataModelList) {

            Data data = Data.builder()
                    .time(dataModel.getTime())
                    .leadI(dataModel.getLeadI())
                    .leadII(dataModel.getLeadII())
                    .leadIII(dataModel.getLeadIII())
                    .avR(dataModel.getAvR())
                    .avL(dataModel.getAvL())
                    .avF(dataModel.getAvF())
                    .v1(dataModel.getV1())
                    .v2(dataModel.getV2())
                    .v3(dataModel.getV3())
                    .v4(dataModel.getV4())
                    .v5(dataModel.getV5())
                    .v6(dataModel.getV6())
                    .userId(userId)
                    .build();

            list.add(data);
        }

        return list;
    }



}
