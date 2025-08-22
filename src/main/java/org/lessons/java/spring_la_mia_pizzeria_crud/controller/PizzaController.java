package org.lessons.java.spring_la_mia_pizzeria_crud.controller;

import java.util.List;

import org.lessons.java.spring_la_mia_pizzeria_crud.model.Pizza;
import org.lessons.java.spring_la_mia_pizzeria_crud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

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

    // STORE (Chiamata GET per form da popolare e chiamata POST per inviare il form
    // al db)
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "pizzas/create";
        }

        repository.save(formPizza);
        return "redirect:/pizzas";
    }

    // UPDATE (Chiamata GET per form da popolare e chiamata POST per inviare il form
    // al db)
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("pizza", repository.findById(id).get());
        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "pizzas/edit";
        }

        repository.save(formPizza);
        return "redirect:/pizzas";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        repository.deleteById(id);
        return "redirect:/pizzas";
    }
    // QUERY CUSTOM TROVAPIZZADALNOME --> spostata direttamete nella index ^
    // @GetMapping("/searchByName")
    // public String searchByName(@RequestParam(name = "name") String name, Model
    // model) {

    // List<Pizza> pizzas = repository.findByNameContaining(name);
    // model.addAttribute("pizzas", pizzas);
    // return "pizzas/index";
    // }

}
