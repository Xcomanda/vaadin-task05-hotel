package com.demo.app.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.demo.app.hotel.database.DataProvider;
import com.demo.app.hotel.entities.Hotel;

public class DAOHotel implements DAOInterface<Hotel> {

	private EntityManager em = DataProvider.getEntityManager();

	@Override
	public void create(Hotel hotel) {
		try {
			em.getTransaction().begin();
			em.persist(hotel);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel create Exception", e);
		}
	}

	@Override
	public Hotel read(Hotel hotel) {
		try {
			em.getTransaction().begin();
			Hotel h = em.find(Hotel.class, hotel.getId());
			em.getTransaction().commit();
			return h;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel read Exception", e);
		}
	}

	@Override
	public void update(Hotel hotel) {
		try {
			em.getTransaction().begin();
			Hotel h = em.find(Hotel.class, hotel.getId());
			h.setName(hotel.getName());
			h.setAddress(hotel.getAddress());
			h.setRating(hotel.getRating());
			h.setOperatesDays(hotel.getOperatesDays());
			h.setCategory(hotel.getCategory());
			h.setUrl(hotel.getUrl());
			h.setDescription(hotel.getDescription());
			h.setPayment(hotel.getPayment());
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel update Exception", e);
		}
	}

	@Override
	public void delete(Hotel hotel) {
		try {
			em.getTransaction().begin();
			Hotel h = em.find(Hotel.class, hotel.getId());
			em.remove(h);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotel delete Exception", e);
		}
	}

	@Override
	public List<Hotel> getList() {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Hotel> criteria = cb.createQuery(Hotel.class);
			Root<Hotel> root = criteria.from(Hotel.class);
			criteria.select(root);
			List<Hotel> list = em.createQuery(criteria).getResultList();
			return list;
	}

}
