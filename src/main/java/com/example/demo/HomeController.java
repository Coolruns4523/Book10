package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {


    @Autowired
    BookRepository bookRepository;

    @RequestMapping("/")
    public String listBooks(Model model)
    {
        model.addAttribute("books", bookRepository.findAll());
        return "list";
    }

    @RequestMapping("/add")
    public String bookForm(Model model)
    {
        model.addAttribute("aBook", new Book());
        return "bookForm";
    }

    @RequestMapping("/savebook")
    public String saveBook(@ModelAttribute("aBook") Book toSave, BindingResult result)
    {
        if(result.hasErrors())
        {
            return "bookForm";
        }
        bookRepository.save(toSave);
        return "redirect:/";
    }


    @RequestMapping("/changestatus/{id}")
    public String borrowReturn(@PathVariable("id")long id)
    {
        Book thisBook = bookRepository.findBookById(id);
        //Reverse the status
        thisBook.setBorrowed(!thisBook.isBorrowed());
        bookRepository.save(thisBook);
        return "redirect:/";
    }

/*    @GetMapping("/add")
    public String bookForm(Model model)
    {
        model.addAttribute("book", new Book());
        return "bookForm";
    }

    @PostMapping("/process")
    public String processForm(@Valid Book book, BindingResult result) {

        if (result.hasErrors())
        {
            return "bookForm";

        }
        bookRepository.save(book);
        return "redirect:/";
    }*/

    @RequestMapping("/detail/{id}")
    public String showBook (@PathVariable("id") long id, Model model)
    {
       /* bookRepository.findById(id).get()*/
        model.addAttribute("aBook", bookRepository.findBookById(id));
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateBook (@PathVariable("id") long id, Model model)
    {
        model.addAttribute("aBook", bookRepository.findBookById(id));
        return "bookForm";
    }


    @RequestMapping("/delete/{id}")
    public String delBook(@PathVariable("id") long id, Model model)
    {
       bookRepository.deleteById(id);
       return "redirect:/";
    }
}
