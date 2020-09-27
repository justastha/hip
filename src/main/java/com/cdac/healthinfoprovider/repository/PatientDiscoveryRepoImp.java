package com.cdac.healthinfoprovider.repository;
import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.CareContexts;
import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.Patient;
import com.cdac.healthinfoprovider.domain.patientdiscoveryresponse.PatientDiscoveryResponseVo;

//@Primary
public class PatientDiscoveryRepoImp {

	public Patient getPatientCareContexts(PatientDiscoveryResponseVo patientDiscoveryResponseVo) {
		
		make db connection 
		and plain query
		check hivt_requisiton_dtl 
		select 
		Patient patient = new Patient();
		
		for(int i=0; i<=5; i++) {
			CareContexts ccts = new CareContexts();
			ccts.referenceNumber="IPD-Visit_"+i;
			ccts.display="Test00"+1;
			patient.careContexts.add(i, ccts);
		}
		return patient;
	}

}
