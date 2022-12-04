package com.example.file.processor;

import com.example.file.dto.DataModel;
import com.example.file.entity.Data;
import com.example.file.service.DataService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataProcessor {

    public DataProcessor(DataService dataService) {
        this.dataService = dataService;
    }

    private DataService dataService;
    private Map<Long, List<DataModel>> data = new HashMap<>();


    public List<DataModel> get(Long userId) {
        return convertTo(dataService.findAllByUserId(userId), userId);
    }

    private List<DataModel> convertTo(List<Data> allByUserId, Long userId) {
        List<DataModel> list = new ArrayList<>();

        if (data.size() > 0) {
            if (Objects.nonNull(data.get(userId))) return data.get(userId);
        }

        for (Data data  : allByUserId) {

            DataModel dataModel = DataModel.builder()
                    .time(data.getTime())
                    .leadI(data.getLeadI())
                    .leadII(data.getLeadII())
                    .leadIII(data.getLeadIII())
                    .avR(data.getAvR())
                    .avL(data.getAvL())
                    .avF(data.getAvF())
                    .v1(data.getV1())
                    .v2(data.getV2())
                    .v3(data.getV3())
                    .v4(data.getV4())
                    .v5(data.getV5())
                    .v6(data.getV6())
                    .userId(userId)
                    .build();

            list.add(dataModel);
        }

        data.put(userId, list);

        return list;

    }
}
