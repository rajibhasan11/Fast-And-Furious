package com.hasan.rest.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hasan.rest.model.User;

public class UserCRUD {
	
	private SqlSessionFactory sqlSessionFactory; 
	
	public UserCRUD(){
		sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}

	@SuppressWarnings("unchecked")
	public List<User> selectAll(){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			List<User> list = session.selectList("User.getAll");
			return list;
		} finally {
			session.close();
		}
	}


	public User selectById(int id){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			User user = (User) session.selectOne("User.getById", id);
			return user;
		} finally {
			session.close();
		}
	}
	
	final static String FIRST_NAME = "firstName";
	final static String LAST_NAME = "lastName";
	final static String EMAIL = "email";
	final static String PASSWORD = "password";
	
	public User selectByEmailPassword(String email, String password){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put(EMAIL, email);
			map.put(PASSWORD, password);
			User user = (User) session.selectOne("User.getByEmailPassword", map);
			System.out.println("SUSSESS USER: " + user.getEmail());
			return user;
		} finally {
			session.close();
		}
	}

	public void update(User user){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.update("User.update", user);
			session.commit();
		} finally {
			session.close();
		}
	}


	public void insert(User user){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.insert("User.insert", user);
			session.commit();
		} finally {
			session.close();
		}
	}


	public void delete(int id){

		SqlSession session = sqlSessionFactory.openSession();
		
		try {
			session.delete("User.deleteById", id);
			session.commit();
		} finally {
			session.close();
		}
	}
}
