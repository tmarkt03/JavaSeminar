package com.example.javaseminar;
import org.springframework.data.repository.CrudRepository;
interface PersonRepository extends CrudRepository<Person, Long> {
}

