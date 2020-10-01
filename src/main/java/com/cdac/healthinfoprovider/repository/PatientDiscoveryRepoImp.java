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
		
		String name=patientDiscoveryRequestFb.patient.getName();
		Integer yearOfBirth=patientDiscoveryRequestFb.patient.getYearOfBirth();
		Integer yearOfBirthl=patientDiscoveryRequestFb.patient.getYearOfBirth()-6;
		Integer yearOfBirthm=patientDiscoveryRequestFb.patient.getYearOfBirth()+6;
		String gender=patientDiscoveryRequestFb.patient.getGender();
		
		try{  
			Class.forName("org.postgresql.Driver");  
			Connection con=DriverManager.getConnection(  
			"jdbc:postgresql://10.226.80.35:5444/hmis_aiims_patna?currentSchema=ahiscl","hmisaiimsp","hmisaiimsp");  
			 
			Statement stmt=con.createStatement(); 
			String query="select hrgnum_puk_reqd, hgnum_visitno_reqd, hgnum_visit_date_reqd, gnum_lab_code, gnum_test_code from hivt_requisition_dtl where "
			+"hrgstr_patname_reqd='"+name+"'"
			+"and date_part('year', hrgdt_dob_reqd) >'"+yearOfBirthl+"'"
			+"and date_part('year', hrgdt_dob_reqd) <'"+yearOfBirthm+"'"
			+"and gstr_gender_code_reqd='"+gender+"'"
			+"and hrgstr_mobile_no_reqd='"+idValue+"'"
			+" limit 1";
			ResultSet rs=stmt.executeQuery(query); 
			System.out.println("inside dao imlementation check pat");
			if (rs.next()) {
			while(rs.next()) {
			System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4)+"  "+rs.getString(5));
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
			ccts.display="Test00"+i;
			patient.careContexts.add(i, ccts);
		}
		patient.matchedBy.add(idType);
		patient.matchedBy.add("name");
		patient.matchedBy.add("gender");
		patient.matchedBy.add("yearOfBirth");
		
		return patient;
	}

}
