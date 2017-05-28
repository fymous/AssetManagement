package com.springboot.rest.service;
import com.springboot.rest.dao.ShopsDAO;
import com.springboot.rest.geocodes.DistanceBetweenCoordinates;
import com.springboot.rest.geocodes.LatitudeAndLongitude;
import com.springboot.rest.model.Shop;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
/**
 * Contains implementaion of all api endpoints
 * 
 */ 
@Component
@Path("/all-shops")
public class ShopService {

	private static final Logger logger = Logger.getLogger(ShopService.class);

/**
 * Rest call to fetch all shops within a distance of 50 kms from the customer.
 * To get all shops in ascending order of distance with no restriction of distance
 * from customer,code is there in the below method which is commented.
 * 
 */ 
  @GET
  @Path("/shop-data")
  @Produces("application/json")
  public Set<Shop> getNearestShops( @QueryParam("latitude") double latitude, 
		  @QueryParam("longitude") double longitude) {
	  logger.info("Entering getNearestShops method");
	  if(latitude == 0){
		  throw new WebApplicationException(Response.status(
      			Response.Status.INTERNAL_SERVER_ERROR).type(
      			MediaType.APPLICATION_JSON).entity(
      			"Lattitude can't be empty").build());
	  }
	  else if(longitude == 0){
			  throw new WebApplicationException(Response.status(
	      			Response.Status.INTERNAL_SERVER_ERROR).type(
	      			MediaType.APPLICATION_JSON).entity(
	      			"Longitude can't be empty").build());
	  }
	  ShopsDAO shopDao = new ShopsDAO();
	  DistanceBetweenCoordinates distance = new DistanceBetweenCoordinates();
	  Shop shop;
	  double shop_latitude = 0.0;
	  double shop_longitude = 0.0;
      List<Shop> shops = shopDao.getShops();
      Map<Double, Shop> distanceFromCustomer = new TreeMap<Double, Shop>();
      Set<Shop> nearest_shops = new LinkedHashSet <Shop>();
      Iterator<Shop> iterator = shops.iterator();
	  	while (iterator.hasNext()) {
	  		shop = iterator.next();
	  		shop_latitude = shop.getLattitude();
	  		shop_longitude = shop.getLongitude();
	  		double distance_value = distance.distanceBetween(latitude, longitude, shop_latitude, shop_longitude);
	  		distanceFromCustomer.put(distance_value, shop);
	  	}
	  	
	  	/*the below code can be used get all shops in ascending order of distance from customer*/
	  	
	  	/*for (Map.Entry<Double, Shop> entry : distanceFromCustomer.entrySet())
	  	{
	  		nearest_shops.add(entry.getValue());
	  	}*/
	  	
	  	/*fetch all shops within a distance of 50 kms from customer*/
	  	for (Map.Entry<Double, Shop> entry : distanceFromCustomer.entrySet())
	  	{
	  		if(entry.getKey() < 50000)
	  			nearest_shops.add(entry.getValue());
	  	}
	  	logger.info("Exiting getNearestShops method");
	   return nearest_shops;
  }
  /**
   * Rest call to add a new shop.If the shop is already present with the same name,
   * other information related to that shop will be added.
   * 
   */ 
  @POST
  @Path("/create")
  @Consumes("application/json")
  public Response addShop(Shop shop){
	  logger.info("Entering addShop method");
	  if(shop.getShopName() == null){
      	throw new WebApplicationException(Response.status(
      			Response.Status.INTERNAL_SERVER_ERROR).type(
      			MediaType.APPLICATION_JSON).entity(
      			"Shop name can't be empty").build());
	  }
	  else if(shop.getShopAddressPostCode() == 0){
        	throw new WebApplicationException(Response.status(
        			Response.Status.INTERNAL_SERVER_ERROR).type(
        			MediaType.APPLICATION_JSON).entity(
        			"Shop address/postal code can't be empty0000").build());
	  }
	  else if(shop.getShopAddressNumber() == null){
      	throw new WebApplicationException(Response.status(
      			Response.Status.INTERNAL_SERVER_ERROR).type(
      			MediaType.APPLICATION_JSON).entity(
      			"Shop number can't be empty0000").build());
	  }
	  String shopName = shop.getShopName();
      String shopAddressNumber = shop.getShopAddressNumber();
      int shopAddressPostCode = shop.getShopAddressPostCode();
	  shop.setShopName(shopName);
	  shop.setShopAddressNumber(shopAddressNumber);
	  shop.setShopAddressPostCode(shopAddressPostCode);
      
	  /*Getting latitude and longitude based on postal code.
	  The below code can be used to get latitude and longitude based on full address too*/
	  LatitudeAndLongitude lat_long = new LatitudeAndLongitude();
	  String response = lat_long.getLocation(String.valueOf(shopAddressPostCode));
	  String[] result = lat_long.parseLocation(response);
	  double latitue = Double.parseDouble(result[0]);
	  double longitude = Double.parseDouble(result[1]);
	  shop.setLattitude(latitue);
	  shop.setLongitude(longitude);
	  
	  ShopsDAO shopsDAO = new ShopsDAO();
	  if(shopsDAO.alreadyExists(shopName)){
		  shopsDAO.updateShop(shopName, shop);
	  }
	  else{
		  shopsDAO.addShop(shop);  
	  }  
	  logger.info("Exiting addShop method");
      return Response.ok().build();
  }
  
}