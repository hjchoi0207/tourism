package com.ggoreb.weathertest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ggoreb.weathertest.model.Board;
public interface BoardRepository extends JpaRepository<Board, Long>{
}