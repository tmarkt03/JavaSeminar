package com.example.javaseminar;
import jakarta.persistence.*;
@Entity
@Table(name="persons")
public class Person {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)	// AUTO_INCREMENT
    private Long id;
    private String name;
    private String address;
    private int age;
    private double weight;

    public Person() {
    }

    public Person(String name, String address, int age, double weight) {
        this.name = name;
        this.address = address;
        this.age = age;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

}
