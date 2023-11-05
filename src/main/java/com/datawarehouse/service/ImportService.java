package com.datawarehouse.service;

import com.datawarehouse.model.Theater;
import com.datawarehouse.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImportService {

    @Autowired
    private TheaterRepository theaterRepository;


    public void importTheater() {

        System.out.println("Import Movie Theater.....");
        Theater t1 = new Theater("Hồ Chí Minh", "CGV Hùng Vương Plaza", "https://www.cgv.vn/default/cinox/site/cgv-hung-vuong-plaza");
        Theater t2 = new Theater("Hồ Chí Minh", "CGV Vivo City", "https://www.cgv.vn/default/cinox/site/cgv-vivo-city");
        Theater t3 = new Theater("Hồ Chí Minh", "CGV Menas Mall (CGV CT Plaza)", "https://www.cgv.vn/default/cinox/site/cgv-ct-plaza");
        Theater t4 = new Theater("Hồ Chí Minh", "CGV Hoàng Văn Thụ", "https://www.cgv.vn/default/cinox/site/cgv-hoang-van-thu");
        Theater t5 = new Theater("Hồ Chí Minh", "CGV Vincom Center Landmark 81", "https://www.cgv.vn/default/cinox/site/cgv-vincom-landmark-81");
        Theater t6 = new Theater("Hồ Chí Minh", "CGV Crescent Mall", "https://www.cgv.vn/default/cinox/site/cgv-crescent-mall");
        Theater t7 = new Theater("Hồ Chí Minh", "CGV Pandora City", "https://www.cgv.vn/default/cinox/site/cgv-pandora-city");
        List<Theater> listTheater = new ArrayList<>();
        listTheater.add(t1);
        listTheater.add(t2);
        listTheater.add(t3);
        listTheater.add(t4);
        listTheater.add(t5);
        listTheater.add(t6);
        listTheater.add(t7);
        theaterRepository.saveAll(listTheater);

    }


}
