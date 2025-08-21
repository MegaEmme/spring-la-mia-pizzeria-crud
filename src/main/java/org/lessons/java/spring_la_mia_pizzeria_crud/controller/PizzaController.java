package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository repository;

    // INDEX
    @GetMapping
    public String index(@RequestParam(name = "searchTerm", required = false) String searchTerm, Model model) {
        // SELECT * FROM 'pizzas' ==> lista di oggetti di tipo pizza
        List<Pizza> pizzas;

        if (searchTerm != null && !searchTerm.isBlank()) {
            pizzas = repository.findByNameContaining(searchTerm);
        } else {
            pizzas = repository.findAll();
        }

        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    // SHOW
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Pizza pizza = repository.findById(id).get();
        model.addAttribute("pizza", pizza);
        return "pizzas/show";
    }

    // QUERY CUSTOM TROVAPIZZADALNOME
    // @GetMapping("/searchByName")
    // public String searchByName(@RequestParam(name = "name") String name, Model
    // model) {

    // List<Pizza> pizzas = repository.findByNameContaining(name);
    // model.addAttribute("pizzas", pizzas);
    // return "pizzas/index";
    // }

}
