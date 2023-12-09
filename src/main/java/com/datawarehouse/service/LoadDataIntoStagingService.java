package com.datawarehouse.service;

import com.datawarehouse.model.Staging;
import com.datawarehouse.repositories.StagingRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LoadDataIntoStagingService {

    @Autowired
    StagingRepository stagingRepository;

    public void importDataIntoStaging() throws IOException {
        String folderPath = "data";
        List<String> listFileName = getAllFileName(folderPath);

        for (String fileName : listFileName) {

            String pathFile = folderPath + "/" + fileName;
            FileInputStream file = new FileInputStream(new File(pathFile));

//Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

//Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<Staging> listStaging = new ArrayList<>();
            for (Row row : sheet) {

                if (row.getRowNum() > 0 && row.getCell(0).getCellType() != CellType.BLANK) {
                    // Date time
                    Cell dateTimeCell = row.getCell(0);
                    String dateTimeString = dateTimeCell.getStringCellValue();
                    String[] splitDatetime = dateTimeString.split(" ");
                    String time = splitDatetime[1];
                    if (time.startsWith("24")) {
                        String newTime = time.replaceFirst("24", "00");
                        dateTimeString = dateTimeString.replace(time, newTime);
                        dateTimeString = dateTimeString.replace("PM", "AM");
                    }

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, format);

                    // Avatar link
                    Cell avatarLinkCell = row.getCell(1);
                    String avatarLink = avatarLinkCell.getStringCellValue();

                    // Film Name
                    Cell filmNameCell = row.getCell(2);
                    String filmName = filmNameCell.getStringCellValue();

                    //Path film
                    Cell pathFilmCell = row.getCell(3);
                    String pathFilm = pathFilmCell.getStringCellValue();
                    //Theater Name
                    Cell theaterNameCell = row.getCell(4);
                    String theaterName = theaterNameCell.getStringCellValue();

                    Staging staging = new Staging(dateTime, filmName, avatarLink, pathFilm, theaterName);
                    listStaging.add(staging);
                }
            }
            stagingRepository.saveAll(listStaging);
        }

    }

    public static void main(String[] args) throws IOException {
        LoadDataIntoStagingService a = new LoadDataIntoStagingService();
        a.importDataIntoStaging();
        ;
    }


    private List<String> getAllFileName(String folderPath) {
        File directoryPath = new File(folderPath);
        String[] fileNames = directoryPath.list();
        Pattern pattern = Pattern.compile("^\\d", Pattern.CASE_INSENSITIVE);
        List<String> listFileName = new ArrayList<>();
        for (int i = 0; i < fileNames.length; i++) {
            Matcher matcher = pattern.matcher(fileNames[i]);
            if (matcher.find()) {
                listFileName.add(fileNames[i]);
            }
        }
        return listFileName;
    }
}
