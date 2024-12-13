package com.lms.service;

import java.util.List;

import com.lms.persistence.Person;

public interface PersonService {

	public Boolean addPerson(Person p);
	
	public Boolean deletePerson(int id);
	
	public Person getPerson(int id);
	
	public List<Person> getAllPersons();

}
