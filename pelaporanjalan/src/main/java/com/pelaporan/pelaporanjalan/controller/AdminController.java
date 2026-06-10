package com.pelaporan.pelaporanjalan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pelaporan.pelaporanjalan.repository.LaporanRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private LaporanRepository laporanRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalLaporan", laporanRepository.count());
        model.addAttribute("menunggu", laporanRepository.countByStatus("MENUNGGU"));
        model.addAttribute("diproses", laporanRepository.countByStatus("DIPROSES"));
        model.addAttribute("selesai", laporanRepository.countByStatus("SELESAI"));

        return "admin-dashboard";
    }

    @GetMapping("/laporan")
    public String laporan(Model model) {

        model.addAttribute("laporanList", laporanRepository.findAll());

        return "admin-laporan";
    }
}