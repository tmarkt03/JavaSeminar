package com.example.assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
    @GetMapping("/messages")
    public String message() {return "messages";}
    @GetMapping("/contact")
    public String contact() {return "contact";}
    @GetMapping("/home")
    public String user(Model model) {
        return "user";
    }
    @GetMapping("/admin/home")
    public String admin() {
        return "admin";
    }

    @GetMapping("/register")
    public String greetingForm(Model model) {
        model.addAttribute("reg", new User());
        return "register";
    }

    @Autowired
    private UserRepository userRepo;
    @PostMapping("/register_process")
    public String Register(@ModelAttribute User user, Model model) {
        for(User user2: userRepo.findAll())
            if(user2.getEmail().equals(user.getEmail())){
                model.addAttribute("message", "The registration email is already used!");
                return "regerror";
            }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepo.save(user);
        model.addAttribute("id", user.getId());
        return "reggood";
    }

    @Autowired private EmplRepo emplRepo;	// Dependency injection

    @GetMapping("/user.html")
    public String MainPage(Model model, String message) {
        model.addAttribute("employees", emplRepo.findAll());
        model.addAttribute("message", model.getAttribute("message"));
        return "user";
    }

    @GetMapping("/new")
    public String newEmployeePage(Model model) {
        model.addAttribute("employee", new Employee());
        return "newemployee";
    }

    @PostMapping(value = "/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirAttr) {
        for(Employee employee2: emplRepo.findAll())
            if(employee2.getName().equals(employee.getName()) && employee2.getAddress().equals(employee.getAddress())){
                redirAttr.addFlashAttribute("message","There is already an employee with this name and address. ID="+employee2.getId());
                return "redirect:/";
            }
        emplRepo.save(employee);
        redirAttr.addFlashAttribute("message","A new employee has been added! ID="+employee.getId());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String updateEmployee(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("employee", emplRepo.findById(id));
        return "update";
    }

    @PostMapping(value = "/update")
    public String updateEmployee(@ModelAttribute Employee employee, RedirectAttributes redirAttr) {
        emplRepo.save(employee);
        redirAttr.addFlashAttribute("message","Employee is updated! ID="+employee.getId());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable(name = "id") int id, RedirectAttributes redirAttr) {
        redirAttr.addFlashAttribute("message","Employee is deleted! ID="+ emplRepo.findById(id).get().getId());
        emplRepo.delete(emplRepo.findById(id).get());
        return "redirect:/";
    }
}
