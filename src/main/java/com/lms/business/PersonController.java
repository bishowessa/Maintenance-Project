package com.lms.business;
import com.lms.persistence.Person;
import com.lms.persistence.Response;
import com.lms.service.PersonServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceImpl personService;

    @PostMapping("/add")
    public Response addPerson(@RequestBody Person p) {
        boolean res = personService.addPerson(p);
        Response response = new Response();
        response.setStatus(res);

        if (!res) {
            response.setMessage("Person already exists");
            return response;
        }
        response.setMessage("Person is created successfully");
        return response;
    }

    @DeleteMapping("/delete/{id}")
    public Response deletePerson(@PathVariable("id") int id) {
        boolean res = personService.deletePerson(id);
        Response response = new Response();
        response.setStatus(res);
        if (!res) {
            response.setMessage("Person doesn't exist");
            return response;
        }

        response.setMessage("Person is deleted successfully");
        return response;
    }

    @GetMapping("/get/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return personService.getPerson(id);
    }

    @GetMapping("/getDummy/{id}")
    public Person getDummyPerson(@PathVariable("id") int id) {
        return personService.getDummyPerson(id);
    }

    @GetMapping("/get")
    public List<Person> getAll() {
        return personService.getAllPersons();
    }

}
