package com.example.javaseminar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class crudController {
    @Autowired
    private EmplRepo emplRepo;	// Dependency injection

    @GetMapping("/crud")
    public String MainPage(Model model, String message) {
        model.addAttribute("employees", emplRepo.findAll());
        model.addAttribute("message", model.getAttribute("message"));
        return "crud";
    }

    @GetMapping("/crud/new")
    public String newEmployeePage(Model model) {
        model.addAttribute("employee", new Employee());
        return "newemployee";
    }

    @PostMapping(value = "/crud/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirAttr) {
        for(Employee employee2: emplRepo.findAll())
            if(employee2.getName().equals(employee.getName()) && employee2.getAddress().equals(employee.getAddress())){
                redirAttr.addFlashAttribute("message","There is already an employee with this name and address. ID="+employee2.getId());
                return "redirect:/crud";
            }
        emplRepo.save(employee);
        redirAttr.addFlashAttribute("message","A new employee has been added! ID="+employee.getId());
        return "redirect:/crud";
    }

    @GetMapping("/crud/edit/{id}")
    public String updateEmployee(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("employee", emplRepo.findById(id));
        return "update";
    }

    @PostMapping(value = "/update")
    public String updateEmployee(@ModelAttribute Employee employee, RedirectAttributes redirAttr) {
        emplRepo.save(employee);
        redirAttr.addFlashAttribute("message","Employee is updated! ID="+employee.getId());
        return "redirect:/crud";
    }

    @GetMapping("/crud/delete/{id}")
    public String deleteEmployee(@PathVariable(name = "id") int id, RedirectAttributes redirAttr) {
        redirAttr.addFlashAttribute("message","Employee is deleted! ID="+ emplRepo.findById(id).get().getId());
        emplRepo.delete(emplRepo.findById(id).get());
        return "redirect:/crud";
    }

}
