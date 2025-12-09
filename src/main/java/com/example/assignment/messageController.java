package com.example.assignment;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class messageController {
    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("contactClass", new MessageClass());
        return "contact";
    }
    @PostMapping("/messages1")
    public String message(@Valid @ModelAttribute() MessageClass messageClass, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "contact";
        model.addAttribute("messageClass", messageClass);
            return "messages";

    }
}
