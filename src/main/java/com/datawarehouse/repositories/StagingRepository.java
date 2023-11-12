package com.datawarehouse.repositories;

import com.datawarehouse.model.Staging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface StagingRepository extends JpaRepository<Staging, Integer> {
}
