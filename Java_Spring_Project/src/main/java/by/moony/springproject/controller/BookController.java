package by.moony.springproject.controller;

import by.moony.springproject.forms.BookForm;
import by.moony.springproject.model.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
@Controller
public class BookController {
    private static List<Book> books = new ArrayList<Book>();
    static {
        books.add(new Book("Full Stack Development with JHipster", "Deepu K Sasidharan, Sendil Kumar N"));
        books.add(new Book("Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
    }
    //
// Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        return modelAndView;
    }
    @RequestMapping(value = {"/allbooks"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        model.addAttribute("books", books);
        return modelAndView;
    }
    @RequestMapping(value = {"/addbook"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addbook");
        BookForm bookForm = new BookForm();
        model.addAttribute("bookform", bookForm);
        return modelAndView;
    }
    // @PostMapping("/addbook")
    //GetMapping("/")
    @RequestMapping(value = {"/addbook"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, @ModelAttribute("bookform") BookForm bookForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("booklist");
        String title = bookForm.getTitle();
        String author = bookForm.getAuthor();
        if (title != null && title.length() > 0 && author != null && author.length() > 0) {
            Book newBook = new Book(title, author);
            books.add(newBook);
            model.addAttribute("books",books);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addbook");
        return modelAndView;
    }
}