
package com.cdac.healthinfoprovider.model.patientdiscoveryrequest;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class Patient {

	@Expose
    @SerializedName("id")
    public String id;
    
    @Expose
    @SerializedName("referenceNumber")
    public String referenceNumber;
    
    @Expose
    @SerializedName("careContexts")
    public List<CareContexts> careContexts = new ArrayList<CareContexts>();
    
    @Expose
    @SerializedName("unverifiedIdentifiers")
    public List<VerifiedIdentifiers> verifiedIdentifiers = new ArrayList<VerifiedIdentifiers>();
    
    
    @Expose
    @SerializedName("verifiedIdentifiers")
    public List<UnverifiedIdentifiers> unverifiedIdentifiers = new ArrayList<UnverifiedIdentifiers>();
   
    @Expose
    @SerializedName("name")
    public String name;
    
    @Expose
    @SerializedName("gender")
    public String gender;
    
    @Expose
    @SerializedName("yearOfBirth")
    public Integer yearOfBirth;

}
