package com.klu.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klu.springmvc.Model.Book;
import com.klu.springmvc.Repository.BookRepository;
import com.klu.springmvc.Service.JWTService;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/books")
public class Controller {

    @Autowired
    BookRepository repo;
    
    @Autowired
    JWTService jwts;

    @PostMapping("/add")
    public Object add(@RequestBody Book b , @RequestHeader("Token") String token) {
    	Map<String, String> response = new HashMap<>();
		try {
			Map<String, String> parsedJWT = jwts.validateJWT(token);
			if(!"2".equals(parsedJWT.get("role"))) {
				response.put("message", "Access Denayed by MS8");
				return response;
			}
			repo.save(b);
			response.put("message", "Book added succesfully by MS8");
		}
		catch (Exception e) {
			response.put("code", "501");
			response.put("message", e.getMessage());
		}
		return response;
    }

    @GetMapping("/view")
    public Object view(@RequestHeader("Token") String token) {
    	Map<String, Object> response = new HashMap<>();
		try {
			Map<String, String> parsedJWT = jwts.validateJWT(token);
			response.put("code", "200");
			response.put("message", "Success");
			List<Book> bookList = new ArrayList<>();
			bookList.addAll(repo.findAll());
			response.put("books",bookList);
			return response;
		}
		catch (Exception e) {
			response.put("code", "501");
			response.put("message", e.getMessage());
		}
		return response;
        
    }

    @PutMapping("/update/{id}")
    public Object update(@PathVariable Integer id, @RequestBody Book b,@RequestHeader("Token") String token) {
    	Map<String, String> res = new HashMap<>();

        try {
        	Map<String, String> parsedJWT = jwts.validateJWT(token);
        	if(!"2".equals(parsedJWT.get("role"))) {
				res.put("message", "Access Denayed");
				return res;
			}
            Book x = repo.findById(id).orElse(null);

            if (x == null) {
                res.put("code", "404");
                res.put("message", "Book not found");
                return res;
            }

            x.setName(b.getName());
            x.setAuthor(b.getAuthor());
            x.setIsbn_num(b.getIsbn_num());

            repo.save(x);

            res.put("code", "200");
            res.put("message", "Book updated successfully BY MS8");

        } catch (Exception e) {
            res.put("code", "500");
            res.put("message", e.getMessage());
        }

        return res;
    }

    @DeleteMapping("/delete/{id}")
    public Object delete(@PathVariable Integer id,@RequestHeader("Token") String token) {
    	Map<String, String> res = new HashMap<>();

        try {
        	Map<String, String> parsedJWT = jwts.validateJWT(token);
        	if(!"2".equals(parsedJWT.get("role"))) {
				res.put("message", "Access Denayed");
				return res;
			}
            if (!repo.existsById(id)) {
                res.put("code", "404");
                res.put("message", "Book not found");
                return res;
            }

            repo.deleteById(id);

            res.put("code", "200");
            res.put("message", "Book deleted successfully BY MS8");

        } catch (Exception e) {
            res.put("code", "500");
            res.put("message", e.getMessage());
        }

        return res;
    }
}//http://localhost:8088/swagger-ui/index.html#/