package com.springboot.rest.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.springboot.rest.model.Shop;

/**
 * Database operation for adding and fetching shop details
 *
 */
public class ShopsDAO {
    
	/**
	 * Adds a new shop.Gets called from rest POST method
	 *
	 */
    public void addShop(Shop bean){
        Session session = SessionUtil.getSession();        
        Transaction tx = session.beginTransaction();
        addShop(session,bean);        
        tx.commit();
        session.close();
        
    }
    
    /**
	 * Saves the shop data in db
	 *
	 */
	private void addShop(Session session, Shop bean){
    	Shop shop = new Shop();
        shop.setShopName(bean.getShopName());
        shop.setShopAddressNumber(bean.getShopAddressNumber());
        shop.setShopAddressPostCode(bean.getShopAddressPostCode());
        shop.setLattitude(bean.getLattitude());
        shop.setLongitude(bean.getLongitude());
        session.save(shop);
    }
    
	/**
	 * Gets all added shops
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<Shop> getShops(){
        Session session = SessionUtil.getSession();    
        Query query = session.createQuery("from Shop");
        List<Shop> shops =  query.list();
        session.close();
        return shops;
    }
 	
	/**
	 * Checks if the shop is already added
	 *
	 */
 	public boolean alreadyExists(String shopName) {
 	  	Session session = SessionUtil.getSession();
 	      return session.createQuery("from Shop where shopName=:shopName")
 	                           .setParameter("shopName", shopName)
 	                           .uniqueResult() != null;
 	  }
 	
 	/**
	 * Updates provided shop information if the shop is alerady present
	 *
	 */
 	public void updateShop(String shopName, Shop shop){
        if(shopName == null)  
              return;  
           Session session = SessionUtil.getSession();
           Transaction tx = session.beginTransaction();
           String hql = "update Shop set shopAddressNumber = :shopAddressNumber, "
           		+ "shopAddressPostCode = :shopAddressPostCode, lattitude = :lattitude,"
           		+ "longitude = :longitude where shopName = :shopName";
           Query query = session.createQuery(hql);
           query.setString("shopName",shopName);
           query.setString("shopAddressNumber", shop.getShopAddressNumber());
           query.setInteger("shopAddressPostCode", shop.getShopAddressPostCode());
           query.setDouble("lattitude", shop.getLattitude());
           query.setDouble("longitude", shop.getLongitude());
           query.executeUpdate();
           tx.commit();
           session.close();
   }

}