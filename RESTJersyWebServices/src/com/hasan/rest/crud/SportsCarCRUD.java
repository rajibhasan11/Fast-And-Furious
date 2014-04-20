package com.hasan.rest.crud;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.hasan.rest.model.SportsCar;

public class SportsCarCRUD {
	
	private SqlSessionFactory sqlSessionFactory; 
	
	public SportsCarCRUD(){
		sqlSessionFactory = MyBatisConnectionFactory.getSqlSessionFactory();
	}
	
	@SuppressWarnings("unchecked")
	public List<SportsCar> selectAll(){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			List<SportsCar> list = session.selectList("SportsCar.getAll");
			return list;
		} finally {
			session.close();
		}
	}

	public SportsCar selectById(int id){
		SqlSession session = sqlSessionFactory.openSession();
		try {
			SportsCar car = (SportsCar) session.selectOne("SportsCar.getById", id);
			return car;
		} finally {
			session.close();
		}
	}

}
