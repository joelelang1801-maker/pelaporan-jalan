package com.pelaporan.pelaporanjalan.controller;

import com.pelaporan.pelaporanjalan.model.Laporan;
import com.pelaporan.pelaporanjalan.repository.LaporanRepository;
import java.io.IOException;
import java.nio.file.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class LaporanController {

    @Autowired
    private LaporanRepository laporanRepository;

    @GetMapping("/lapor")
    public String formLapor(Model model) {

        model.addAttribute("laporan", new Laporan());

        return "lapor";
    }

   @PostMapping("/lapor")
public String simpanLaporan(
        @ModelAttribute Laporan laporan,
        @RequestParam("file") MultipartFile file)
        throws IOException {

    if (!file.isEmpty()) {

        String namaFile = file.getOriginalFilename();

        Path path = Paths.get("uploads");

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        Files.copy(
            file.getInputStream(),
            path.resolve(namaFile),
            StandardCopyOption.REPLACE_EXISTING
        );

        laporan.setFoto(namaFile);
    }

    laporan.setStatus("MENUNGGU");

    laporanRepository.save(laporan);

    return "redirect:/daftar-laporan";
}
    @GetMapping("/daftar-laporan")
public String daftarLaporan(Model model) {

    model.addAttribute(
        "laporanList",
        laporanRepository.findAll()
    );

    return "daftar-laporan";
}
}