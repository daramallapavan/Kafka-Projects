package com.example.stock_microservice.stock.repository;


import com.example.stock_microservice.stock.entity.WareHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<WareHouse,Long> {

    Iterable<WareHouse> findByItem(String item);
}
