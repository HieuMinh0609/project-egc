package com.example.file.processor;

import com.example.file.dto.EcgModel;
import com.example.file.dto.FileRequestModel;
import com.example.file.uitils.UploadUtils;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class EcgProcessor {

    @Value("${upload.folder}")
    private String uploadFolder;

    private UploadUtils uploadUtils;

    private static List<EcgModel> ecgModels = new ArrayList<>();

    public EcgProcessor(UploadUtils uploadUtils) {
        this.uploadUtils = uploadUtils;
    }

    private static final String MESSAGE_ERROR = "Có lỗi xảy ra";
    private static final Integer CHUNK_SIZE = 2000;

    public String uploadFileExcel(FileRequestModel fileRequestModel) throws Exception {
        if (!fileRequestModel.getMultipartFile().isEmpty()) {
            var fileName = uploadUtils.saveFile(fileRequestModel.getMultipartFile(), uploadFolder);
            return fileName;
        } else throw new Exception(MESSAGE_ERROR);
    }


    private List<EcgModel> getData(String fileName, String uploadFolder) {
        var data = new ArrayList<EcgModel>();
        File file = new File(uploadFolder + fileName);
        try (InputStream is = new FileInputStream(file);
             Workbook workbook = StreamingReader.builder()
                     .rowCacheSize(CHUNK_SIZE)
                     .bufferSize(2048)
                     .open(is)) {


            Sheet sheet = workbook.getSheetAt(0);

            Integer index = 0;
            for (Row row : sheet) {

                index++;

                if (index == 1 ) continue;
                data.add(mapRow(row));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data;
    }

    private EcgModel mapRow(Row row) {
        var model = new EcgModel();
            model.setTime(Long.parseLong(convertData(row.getCell(0))));
            model.setLeadI(Double.parseDouble(convertData(row.getCell(1))));
            model.setLeadII(Double.parseDouble(convertData(row.getCell(2))));

            model.setLeadIII(Double.parseDouble(convertData(row.getCell(3))));
            model.setAvR(Double.parseDouble(convertData(row.getCell(4))));
            model.setAvL(Double.parseDouble(convertData(row.getCell(5))));
            model.setAvF(Double.parseDouble(convertData(row.getCell(6))));

            model.setV1(Double.parseDouble(convertData(row.getCell(7))));
            model.setV2(Double.parseDouble(convertData(row.getCell(8))));
            model.setV3(Double.parseDouble(convertData(row.getCell(9))));
            model.setV4(Double.parseDouble(convertData(row.getCell(10))));
            model.setV5(Double.parseDouble(convertData(row.getCell(11))));
            model.setV6(Double.parseDouble(convertData(row.getCell(12))));
        return model;
    }

    private String convertData(Cell value) {
        if (Objects.isNull(value)) return null;
        return StringUtils.isBlank(value.getStringCellValue()) ? null: value.getStringCellValue().trim();
    }



    public List<EcgModel> readFileExcel(String nameFile) {

        if (ecgModels.size() < 1) {
            var list = getData(nameFile, uploadFolder);
            ecgModels =  list;
            return list;
        } else return ecgModels;

    }
}