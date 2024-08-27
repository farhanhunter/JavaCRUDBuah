package com.example.buah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/buah")
public class BuahController {

    @Autowired
    private BuahRepository buahRepository;

    @GetMapping
    public String viewBuahPage(Model model) {
        List<Buah> buahList = buahRepository.findAll();
        model.addAttribute("buahList", buahList);
        return "buah-list"; //template buah-list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("buah", new Buah());
        model.addAttribute("formTitle", "Tambah Buah");
        return "buah-form"; //template buah-form.html
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Buah buah = buahRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Buah Id:" + id));
        model.addAttribute("buah", buah);
        model.addAttribute("formTitle", "Edit Buah");
        return "buah-form"; //template buah-form.html
    }

    @PostMapping("/save")
    public String saveBuah(@ModelAttribute("buah") Buah buah) {
        buahRepository.save(buah);
        return "redirect:/buah"; // /buah setelah menyimpan
    }

    @GetMapping("/delete/{id}")
    public String deleteBuah(@PathVariable("id") Long id) {
        Buah buah = buahRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Buah Id:" + id));
        buahRepository.delete(buah);
        return "redirect:/buah"; // redirect /buah setelah menghapus
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error"; // template error.html
    }
}