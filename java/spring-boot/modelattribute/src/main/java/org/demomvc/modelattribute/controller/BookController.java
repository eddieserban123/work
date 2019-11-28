package org.demomvc.modelattribute.controller;

import org.demomvc.modelattribute.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class BookController {

    Map<String, Book> bookMap = new HashMap<>();

    @ModelAttribute("book")
    public void initBooks() {
        bookMap.put("1", new Book("Jack London", "white fang", "223334411"));
        bookMap.put("2", new Book("Mark Twain", " A Horse's Tale", "22001543"));
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("bookHome", "book", new Book("","",""));
    }

    @RequestMapping(value = "/book/{Id}", produces = { "application/json", "application/xml" }, method = RequestMethod.GET)
    public @ResponseBody
    Book getBookById(@PathVariable final Long Id) {
        return bookMap.get(Id);
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public String submit(@ModelAttribute("book") final Book book, final BindingResult result, final ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("isbn", book.getIsbn());

        bookMap.put(book.getIsbn(), book);

        return "bookView";
    }

}