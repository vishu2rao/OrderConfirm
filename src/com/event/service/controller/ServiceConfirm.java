/**
 * 
 */
package com.event.service.controller;

/**
 * @author visweswarar
 *
 */
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod; 
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController; 

import com.sun.jmx.snmp.Timestamp;
@RestController




//@Controller

	@SuppressWarnings("unchecked") 
	
	@ResponseBody

public class ServiceConfirm {
	/**
	 * Logger for the class.
	 */
	private Logger LOG = Logger.getLogger(ServiceConfirm.class
			.getCanonicalName());
	
	
	//@RequestMapping("/service/{traxnid}/{serviceid}")
	//@RequestMapping(method = RequestMethod.GET) 
	@RequestMapping(value = "/serviceconfirmation/{traxnid}/{serviceid}", method = RequestMethod.GET, headers = "Accept=application/json")
	
	public String confirmStatus(HttpServletRequest request) {
		//LOG.info("Read request of ReadRequestController has been called.");
		
			
		System.out.println();		
		System.out.println("Read request of confirm status has been served !!!");
		System.out.println("pathinfo of URL is :"+request.getPathInfo());		
		String traxnID=null;
		String servID=null;
		
		
		
		//Beware that i am assuming only /txnid/servid in the path if you want expiry time then
		//you need to handle another tuple accordingly
		String[] uris=request.getPathInfo().split("/");		
		traxnID=uris[uris.length-2];
		servID=uris[uris.length-1];
		
		
				
		return "You have "+request.getQueryString()+ " for service with TransactionID="+traxnID+" and Service id="+ servID;
	}
	
	
	@RequestMapping(value = "/createlinks", method = RequestMethod.GET, headers = "Accept=application/json")
	public String createConfirmLinks(HttpServletRequest request) throws Exception {
		//LOG.info("Read request of ReadRequestController has been called.");
		Map<String, String[]> parameters = request.getParameterMap();
		String createdLinks = null;
		Map<String, String> mapData = new HashMap<String,String>();
		String requestData=request.getQueryString();
		String[] pairs = requestData.split(",");
		for (int i=0;i<pairs.length;i++) {
		    String pair = pairs[i];
		    String[] keyValue = pair.split("=");
		    mapData.put(keyValue[0], keyValue[1]);
		}
		
		//System.out.println("ID's Map is  :"+mapData.toString());
		
		
		//If you want both accepted,relected you can have both links or 
		//with accepted thing we could set timeout to automatically to have rejected
		
		
		createdLinks = request.getScheme() + "://" +
	             request.getServerName() + 
	             request.getServerPort()+"/eventing"+"/serviceconfirmation/"+mapData.get("tid")+"/"+mapData.get("sid")+"?accepted" ;
		
		
		//if you want have both tid and sid encrypted foolowing line can be un-commented
		/*Encryption ecr =new Encryption();
		  createdLinks = request.getScheme() + "://" +
	             request.getServerName() + 
	             request.getServerPort()+"/eventing"+"/serviceconfirmation/"+ecr.encrypt(mapData.get("tid"))+"/"+ecr.encrypt(mapData.get("sid"))+"?accepted" ;*/
		/*createdLinks[1] = request.getScheme() + "://" +
	             request.getServerName() + 
	             request.getServerPort()+"/eventing"+"/service/"+mapData.get("tid")+"/"+mapData.get("sid")+"?rejected" ;*/
		
		
		System.out.println("Read request of ReadRequestController has been served !!!");

		//return confirm link to accepted state
		return createdLinks;
	}
	

	 //If you want to set expiry time then we could add this long value in URL as part of one of the path
	 // Pass you expiry time in minutes and it will return unix timestamps in long so that easy to convert 
	//  and decide rejection if it is part of URL
	 
	  private static  long calculateExpiryDate(int expiryTimeInMinutes) 
	  {
		        Calendar cal = Calendar.getInstance();
		       // cal.setTime(date)
		        cal.setTimeInMillis(new Timestamp(cal.getTime().getTime()).getDateTime());
		       // cal.setTimeZone(new Timestamp(cal.getTime().getTime()));
		        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		        return new Date(cal.getTime().getTime()).getTime();
	   }
}
