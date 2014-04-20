package com.hasan.rest.crud;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hasan.rest.model.Person;

public class PersonCRUD {
	
	private SqlSessionFactory sqlSessionFactory; 
	
	public PersonCRUD(){
		sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	@SuppressWarnings("unchecked")
	public List<Person> selectAll(){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			List<Person> list = session.selectList("Person.getAll");
			return list;
		} finally {
			session.close();
		}
	}


	public Person selectById(int id){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			Person person = (Person) session.selectOne("Person.getById",id);
			return person;
		} finally {
			session.close();
		}
	}

	public void update(Person person){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.update("Person.update", person);
			session.commit();
		} finally {
			session.close();
		}
	}


	public void insert(Person person){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.insert("Person.insert", person);
			session.commit();
		} finally {
			session.close();
		}
	}


	public void delete(int id){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.delete("Person.deleteById", id);
			session.commit();
		} finally {
			session.close();
		}
	}
}
