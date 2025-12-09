package com.example.javaseminar;

import org.springframework.web.bind.annotation.*;

@RestController
class PersonController {
    private final PersonRepository repo;
    PersonController(PersonRepository repo) {	// Dependency injection
        this.repo = repo;
    }
    @GetMapping("/persons")
    Iterable<Person> readAll() {
        return repo.findAll();
    }

    @GetMapping("/persons/{id}")
    Person readOne(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    @PostMapping("/persons")
    Person personSave(@RequestBody Person newPerson) {
        return repo.save(newPerson);
    }

    @PutMapping("/persons/{id}")
    Person personUpdate(@RequestBody Person dataPerson, @PathVariable Long id) {
        return repo.findById(id)
                .map(a -> {		// Lambda kifejezés
                    a.setName (dataPerson.getName());
                    a.setAddress (dataPerson.getAddress());
                    a.setAge (dataPerson.getAge());
                    a.setWeight(dataPerson.getWeight());
                    return repo.save(a);
                })
                .orElseGet(() -> {
                    dataPerson.setId(id);
                    return repo.save(dataPerson);
                });
    }
    @DeleteMapping("/persons/{id}")
    void deletePerson(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
