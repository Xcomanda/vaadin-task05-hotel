package com.demo.app.hotel.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.demo.app.hotel.database.DataProvider;
import com.demo.app.hotel.entities.HotelCategory;

public class DAOHotelCategory implements DAOInterface<HotelCategory> {

	private EntityManager em = DataProvider.getEntityManager();

	@Override
	public void create(HotelCategory hotelCategory) {
		try {
			em.getTransaction().begin();
			em.persist(hotelCategory);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotelCategory create Exception", e);
		}
	}

	@Override
	public HotelCategory read(HotelCategory hotelCategory) {
		try {
			em.getTransaction().begin();
			HotelCategory hc = em.find(HotelCategory.class, hotelCategory.getId());
			em.getTransaction().commit();
			return hc;
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotelCategory read Exception", e);
		}
	}

	@Override
	public void update(HotelCategory hotelCategory) {
		try {
			em.getTransaction().begin();
			HotelCategory hc = em.find(HotelCategory.class, hotelCategory.getId());
			hc.setName(hotelCategory.getName());
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotelCategory update Exception", e);
		}
	}

	@Override
	public void delete(HotelCategory hotelCategory) {
		try {
			em.getTransaction().begin();
			new DAOHotel().getList().forEach(hotel -> {
				if (hotel.getCategory() != null && hotel.getCategory().getId() == hotelCategory.getId())
					hotel.setCategory(null);
			});
			HotelCategory hc = em.find(HotelCategory.class, hotelCategory.getId());
			em.remove(hc);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw new RuntimeException("DAOHotelCategory delete Exception", e);
		}
	}

	@Override
	public List<HotelCategory> getList() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HotelCategory> criteria = cb.createQuery(HotelCategory.class);
		Root<HotelCategory> root = criteria.from(HotelCategory.class);
		criteria.select(root);
		List<HotelCategory> list = em.createQuery(criteria).getResultList();
		return list;
	}
}
