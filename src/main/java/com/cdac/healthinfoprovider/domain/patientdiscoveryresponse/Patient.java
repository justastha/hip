
package com.cdac.healthinfoprovider.domain.patientdiscoveryresponse;

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

    @SerializedName("referenceNumber")
    @Expose
    public String referenceNumber;
    
    @SerializedName("display")
    @Expose
    public String display;
    
    @SerializedName("careContexts")
    @Expose
    public List<CareContexts> careContexts = new ArrayList<CareContexts>();
    
    @SerializedName("matchedBy")
    @Expose
    public List<String> matchedBy = new ArrayList<String>();

}
