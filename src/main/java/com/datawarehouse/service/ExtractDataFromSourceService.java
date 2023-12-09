package com.datawarehouse.service;

import com.datawarehouse.model.Theater;
import com.datawarehouse.repositories.TheaterRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Extract data from web -> .xlsx file
@Service
public class ExtractDataFromSourceService {

    @Autowired
    TheaterRepository theaterRepository;

    /**
     * Load data from Sources : https://www.cgv.vn/
     */
    public void extractData() throws IOException, InterruptedException {
        // Get all Theater -> URL
        List<Theater> theaterList = theaterRepository.selectAll();

        //Create blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet spreadsheet = workbook.createSheet("data");

        //Create row object
        XSSFRow row;

        Map<String, Object[]> dataMap = new TreeMap<>();

        dataMap.put(String.valueOf(1), new Object[]{
                "DateTime", "Avatar", "Name Film", "Director", "Actor", "Genre", "Length", "Language", "Path Film", "Theater Name", "Description"});

        // Get date current -> dateTime
        LocalDate currentDate = LocalDate.now();
        int index = 2;

        for (Theater theater : theaterList) {

            Document doc = Jsoup.connect(theater.getUrl()).get();
            Elements elements = doc.getElementsByClass("film-list");

            for (Element filmElement : elements) {
                Thread.sleep(60000*5);

                // Name film
                Element labelElement = filmElement.getElementsByClass("film-label").get(0).getElementsByTag("a").get(0);
                String nameFilm = labelElement.text();
                String pathFilm = labelElement.attr("href");
                MovieSource dataMovie = getDataMovie(pathFilm);


                // Avatar
                Element avatarElement = filmElement.getElementsByClass("film-poster").get(0).getElementsByTag("a").get(0);
                String avatarFilm = avatarElement.getElementsByTag("img").get(0).attr("src");

                // Times
                Elements elTime = filmElement.getElementsByClass("film-right");

                for (Element e : elTime) {
                    for (Element i : e.getElementsByTag("span")) {

                        String time = i.text();
                        String date = currentDate.toString();
                        String dateTime = date + " " + time;
                        System.out.println(dateTime);
                        dataMap.put(String.valueOf(index), new Object[]{
                                dateTime, avatarFilm, nameFilm, dataMovie.director, dataMovie.actor, dataMovie.genre,
                                dataMovie.length, dataMovie.language, pathFilm, theater.getName(), dataMovie.desc});
                        index++;
                    }
                }

                System.out.println(avatarElement.attr("src"));
            }
        }

        Set<String> keySet = dataMap.keySet();
        int rowId = 0;

        for (String key : keySet) {

            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = dataMap.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                cell.setCellValue((String) obj);
            }
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss");
        String formattedTimestamp = dtf.format(now);
        String directoryPath = "C:\\Users\\user\\Documents\\DW_2023_T5_Nhom7-main\\DW_2023_T5_Nhom7-main\\data\\";
        String filePath = directoryPath + formattedTimestamp + ".xlsx";

        File file = new File(filePath);

        if (!file.exists()) {
            // Create the directory structure if it doesn't exist
            file.getParentFile().mkdirs();
        }

        FileOutputStream out = new FileOutputStream(file);
        workbook.write(out);
        out.close();
    }

    private MovieSource getDataMovie(String path) throws IOException {
        MovieSource result = new MovieSource();
        Document doc = Jsoup.connect(path).get();
        //get director
        Element elDirector = doc.getElementsByClass("movie-director").get(0);
        result.director = elDirector.getElementsByClass("std").text();

        // get actor
        Element elActor = doc.getElementsByClass("movie-actress").get(0);
        result.actor = elActor.getElementsByClass("std").text();

        // desc
        Element elDesc = doc.getElementsByClass("tab-content").get(0);
        result.desc = elDesc.getElementsByClass("std").text();

        // get genre
        Element elGenre = doc.getElementsByClass("movie-genre").get(0);
        result.genre = elGenre.getElementsByClass("std").text();

        // get length
        Element elLength = doc.getElementsByClass("movie-actress").get(1);
        result.length = elLength.getElementsByClass("std").text();

        // get language
        Element elLanguage = doc.getElementsByClass("movie-language").get(0);
        result.language = elLanguage.getElementsByClass("std").text();

        return result;
    }

    private static class MovieSource {
        String desc;
        String director;
        String actor;
        String genre;
        String length;
        String language;
    }

}
