package com.pelaporan.pelaporanjalan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pelaporan.pelaporanjalan.model.Laporan;

public interface LaporanRepository
        extends JpaRepository<Laporan, Long> {

    long countByStatus(String status);

}