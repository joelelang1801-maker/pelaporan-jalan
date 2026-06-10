package com.pelaporan.pelaporanjalan.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pelaporan.pelaporanjalan.model.Laporan;
import com.pelaporan.pelaporanjalan.repository.LaporanRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LaporanRepository laporanRepository;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "user/dashboard";
    }

    // Form laporan
    @GetMapping("/lapor")
    public String formLapor(Model model) {

        model.addAttribute("laporan", new Laporan());

        return "user/lapor";
    }

    // Simpan laporan
    @PostMapping("/lapor")
    public String simpanLaporan(
            @ModelAttribute Laporan laporan,
            @RequestParam("file") MultipartFile file)
            throws IOException {

        if (!file.isEmpty()) {

            String namaFile =
                    System.currentTimeMillis()
                    + "_"
                    + file.getOriginalFilename();

            Path uploadPath = Paths.get("uploads");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            file.transferTo(
                    uploadPath.resolve(namaFile));

            laporan.setFoto(namaFile);
        }

        laporan.setStatus("MENUNGGU");
        laporan.setTanggal(LocalDateTime.now());

        laporanRepository.save(laporan);

        return "redirect:/user/laporan";
    }

    // Daftar laporan
    @GetMapping("/laporan")
    public String daftarLaporan(Model model) {

        model.addAttribute(
                "laporanList",
                laporanRepository.findAll());

        return "user/laporan";
    }
}