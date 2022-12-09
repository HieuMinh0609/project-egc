package com.example.file.processor;

import com.example.file.dto.DataModel;
import com.example.file.dto.FileRequestModel;
import com.example.file.entity.Users;
import com.example.file.service.DataService;
import com.example.file.service.UserService;
import com.example.file.uitils.UploadUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class EcgProcessor {

    @Value("${upload.folder}")
    private String uploadFolder;

    private UploadUtils uploadUtils;
    private UserService userService;
    private DataService dataService;

    private static List<DataModel> ecgModels = new ArrayList<>();

    public EcgProcessor(UploadUtils uploadUtils, UserService userService, DataService dataService) {
        this.uploadUtils = uploadUtils;
        this.userService = userService;
        this.dataService = dataService;
    }

    private static final String MESSAGE_ERROR = "Có lỗi xảy ra";


    @Transactional
    public Long uploadFileExcel(FileRequestModel fileRequestModel) throws Exception {
        if (!fileRequestModel.getMultipartFile().isEmpty()) {
            var fileName = uploadUtils.saveFile(fileRequestModel.getMultipartFile(), uploadFolder);

           Users user = userService.save(fileRequestModel.getUserName(), fileRequestModel.getPhoneName());

           List<DataModel> dataModels = getData(fileName, uploadFolder);
           dataService.saveAll(dataModels, user.getId());

            return user.getId();

        } else throw new Exception(MESSAGE_ERROR);
    }


    private List<DataModel> getData(String fileName, String uploadFolder) throws IOException {
        var data = new ArrayList<DataModel>();
        File file = new File(uploadFolder + fileName);
        Integer index = 0;
        Workbook workbook = WorkbookFactory.create(file);

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            try {
                index++;
                if (index == 1 ) continue;
                data.add(mapRow(row));
            } catch (Exception e) {
               System.out.println("Error data line "+ index);
            }
        }

        workbook.close();

        return data;
    }

    private DataModel mapRow(Row row) {
        var model =  DataModel.builder()
            .time(new BigDecimal(getCellValue(row.getCell(0))))
            .leadI(new BigDecimal(getCellValue(row.getCell(1))))
            .leadII(new BigDecimal(getCellValue(row.getCell(2))))
            .leadIII(new BigDecimal(getCellValue(row.getCell(3))))
            .avR(new BigDecimal(getCellValue(row.getCell(4))))
            .avL(new BigDecimal(getCellValue(row.getCell(5))))
            .avF(new BigDecimal(getCellValue(row.getCell(6))))
            .v1(new BigDecimal(getCellValue(row.getCell(7))))
            .v2(new BigDecimal(getCellValue(row.getCell(8))))
            .v3(new BigDecimal(getCellValue(row.getCell(9))))
            .v4(new BigDecimal(getCellValue(row.getCell(10))))
            .v5(new BigDecimal(getCellValue(row.getCell(11))))
            .v6(new BigDecimal(getCellValue(row.getCell(12)))).build();
        return model;
    }

    public String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) return cellValue;
        try {
            switch (cell.getCellType()) {
                case STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    cellValue = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case NUMERIC:
                    try {
                        if (DateUtil.isCellDateFormatted(cell)) {
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                            Date today = cell.getDateCellValue();
                            cellValue   = df.format(today);
                        } else {
                            cellValue = NumberToTextConverter.toText(cell.getNumericCellValue());
                        }
                    }catch (Exception e){
                        cellValue = cell.getStringCellValue();
                    }
                    break;
                case FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case STRING:
                            cellValue = cell.getRichStringCellValue().toString();
                            break;
                        case NUMERIC:
                            cellValue = String.valueOf(cell.getNumericCellValue());
                            break;
                    }
                    break;
                default:
                    cellValue = "";

            }
        } catch (Exception e) {
            return cellValue = cell.getStringCellValue();
        }
        return cellValue;
    }

}