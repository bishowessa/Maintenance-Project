package com.lms.service;

import java.util.*;
import com.lms.persistence.*;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    Map<Integer, Person> persons = new HashMap<>();

    @Override
    public Boolean addPerson(Person p) {
        if (persons.get(p.getId()) != null) {
            return false;
        }
        persons.put(p.getId(), p);
        return true;
    }

    @Override
    public Boolean deletePerson(int id) {
        if (persons.get(id) == null) {
            return false;
        }
        persons.remove(id);
        return true;
    }

    @Override
    public Person getPerson(int id) {
        return persons.get(id);
    }

    @Override
    public List<Person> getAllPersons() {
        Set<Integer> ids = persons.keySet();
        List<Person> p = new ArrayList<>(ids.size());
        for (Integer id : ids) {
            p.add(persons.get(id));
        }
        return p;
    }

    public Person getDummyPerson(int id) {
        Person p = new Person();
        p.setAge(99);
        p.setName("Dummy");
        p.setId(id);
        return p;
    }
}
