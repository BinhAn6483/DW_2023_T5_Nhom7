package com.datawarehouse.repositories;

import com.datawarehouse.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TheaterRepository extends JpaRepository<Theater, Integer> {

    @Query("SELECT t FROM Theater t")
    public List<Theater> selectAll();
}
