package com.cdac.healthinfoprovider.repository;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.CareContexts;
import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.Patient;
import com.cdac.healthinfoprovider.model.patientdiscoveryrequest.PatientDiscoveryRequestFb;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.mapping.List;
import org.springframework.stereotype.Repository;
@Repository
public class PatientDiscoveryRepoImp {

	public Patient getPatientCareContexts(PatientDiscoveryRequestFb patientDiscoveryRequestFb) {
		Patient patient = new Patient();
		Boolean fg=false;
		
		String idType=patientDiscoveryRequestFb.patient.verifiedIdentifiers.get(0).getType();
		String idValue=patientDiscoveryRequestFb.patient.verifiedIdentifiers.get(0).getValue();
		String name=patientDiscoveryRequestFb.patient.verifiedIdentifiers.get(0).getValue();
		String yearOfBirth=patientDiscoveryRequestFb.patient.verifiedIdentifiers.get(0).getValue();
		String gender=patientDiscoveryRequestFb.patient.verifiedIdentifiers.get(0).getValue();
		
		try{  
			Class.forName("com.mysql.cj.jdbc.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/db","root","");  
			 
			Statement stmt=con.createStatement(); 
			String query="select * from hivt_requisiton_dtl where mobile='"+idValue+"' and gender='"+gender+"' limit 1";
			ResultSet rs=stmt.executeQuery(query); 
			System.out.println("inside dao imlementation check pat");
			if (rs.next()) {
			while(rs.next()) {
			System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
			} }
			else { con.close(); fg= false;}
			con.close();
			
			}catch(Exception e)
		      { System.out.println(e);
		        fg= false;
		      }  
			fg= true;
		
		for(int i=0; i<=5; i++) {
			CareContexts ccts = new CareContexts();
			ccts.referenceNumber="IPD-Visit_"+i;
			ccts.display="Test00"+1;
			patient.careContexts.add(i, ccts);
		}
		return patient;
	}

}
