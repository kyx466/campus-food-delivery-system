package com.ch.schoolwaimai.dao;

import com.ch.schoolwaimai.entity.Canteen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CanteenDao extends JpaRepository<Canteen, Long> {
}