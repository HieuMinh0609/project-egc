package com.example.file.processor;

import com.example.file.dto.EcgModel;
import com.example.file.dto.FileRequestModel;
import com.example.file.uitils.UploadUtils;
import com.monitorjbl.xlsx.StreamingReader;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    private List<EcgModel> getData(String fileName, String uploadFolder) throws IOException {
        var data = new ArrayList<EcgModel>();
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

    private EcgModel mapRow(Row row) {
        var model = new EcgModel();
            model.setTime(Long.parseLong(getCellValue(row.getCell(0))));
            model.setLeadI(Double.parseDouble(getCellValue(row.getCell(1))));
            model.setLeadII(Double.parseDouble(getCellValue(row.getCell(2))));

            model.setLeadIII(Double.parseDouble(getCellValue(row.getCell(3))));
            model.setAvR(Double.parseDouble(getCellValue(row.getCell(4))));
            model.setAvL(Double.parseDouble(getCellValue(row.getCell(5))));
            model.setAvF(Double.parseDouble(getCellValue(row.getCell(6))));

            model.setV1(Double.parseDouble(getCellValue(row.getCell(7))));
            model.setV2(Double.parseDouble(getCellValue(row.getCell(8))));
            model.setV3(Double.parseDouble(getCellValue(row.getCell(9))));
            model.setV4(Double.parseDouble(getCellValue(row.getCell(10))));
            model.setV5(Double.parseDouble(getCellValue(row.getCell(11))));
            model.setV6(Double.parseDouble(getCellValue(row.getCell(12))));
        return model;
    }

    private String convertData(Cell value) {
        if (Objects.isNull(value)) return null;
        return StringUtils.isBlank(value.getStringCellValue()) ? null: value.getStringCellValue().trim();
    }

    public List<EcgModel> readFileExcel(String nameFile) throws IOException {

        if (ecgModels.size() < 1) {
            var list = getData(nameFile, uploadFolder);
            ecgModels =  list;
            return list;
        } else return ecgModels;

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