package edu.sjsu.cmpe.isns.repository;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.*;

import edu.sjsu.cmpe.isns.domain.User;
import edu.sjsu.cmpe.isns.dto.UserDto;

public class DBConnection {

	MongoClient mc;
	DB database;
	DBCollection coll;

	// String collectionTable;

	public DBConnection(String collectionTable) throws UnknownHostException {
		MongoClientURI uri = new MongoClientURI(
				"mongodb://admin:password@ds053648.mongolab.com:53648/cmpe273project");
		mc = new MongoClient(uri);
		database = mc.getDB(uri.getDatabase());
		coll = database.getCollection(collectionTable);
		System.out.println(collectionTable);
	}

	public void StoreUser(edu.sjsu.cmpe.isns.domain.User request) {
		BasicDBObject document = new BasicDBObject();
		System.out.println("In Store User"+request.getUserName());
		 if(request.getUserName()!=null)
		{
			BasicDBObject findQuery = new BasicDBObject("userName",request.getUserName());
			coll = database.getCollection("user");
			DBObject userNm = coll.findOne(findQuery);
			System.out.println(userNm);
			//System.out.println("User Name"+userNm.get("userName"));
			if((userNm==null)||(userNm.get("userName")==null))
			{
			//document.put("_id", request.getId());
			document.put("userName",request.getUserName());
			document.put("password",request.getPassword());
			document.put("firstName", request.getFirstName());
			document.put("lastName", request.getLastName());
			document.put("eMail", request.geteMail());
			document.put("phoneNumber", request.getPhoneNumber());
			document.put("department", request.getDepartment());
			coll.insert(document);
			}
			
		}
		
		

	}

	public ArrayList<User> getAllUsersInDepartment(String dept) {
		
		ArrayList<User> deptList = new ArrayList<User>();
		BasicDBObject findQuery = new BasicDBObject("department", dept);
		//findQuery.put("department", dept);
		//coll = database.getCollection("user");
		DBCursor docs = coll.find(findQuery);
		System.out.println(docs.size());
		while (docs.hasNext()) {
			DBObject doc = docs.next();
			User usr = new User();
			//usr.setId((Integer) doc.get("id"));
			usr.setFirstName(doc.get("firstName").toString());
			usr.setLastName(doc.get("lastName").toString());
			usr.seteMail(doc.get("eMail").toString());
			usr.setPhoneNumber(doc.get("phoneNumber").toString());
			usr.setDepartment(doc.get("department").toString());
			//System.out.println(usr.getFirstName()+""+usr.getId());
			deptList.add(usr);
			 System.out.println("id"+doc.get("id")+"Name "+doc.get("firstName"));
		}
		return deptList;
		 
	}

	public ArrayList<User> usersInStore() {
		// BasicDBObject findQuery = new BasicDBObject("firstName", "raj");
		ArrayList<User> usrList = new ArrayList<User>();

		DBCursor docs = coll.find();
		while (docs.hasNext()) {
			DBObject doc = docs.next();
			User usr = new User();
			//usr.setId(Integer.parseInt(doc.get("id").toString()));
			usr.setFirstName(doc.get("firstName").toString());
			usr.setLastName(doc.get("lastName").toString());
			usr.seteMail(doc.get("eMail").toString());
			usr.setPhoneNumber(doc.get("phoneNumber").toString());
			usr.setDepartment(doc.get("department").toString());
			usrList.add(usr);
			// System.out.println("id"+doc.get("id")+"Name "+doc.get("firstName"));
		}
		return usrList;
	}

}