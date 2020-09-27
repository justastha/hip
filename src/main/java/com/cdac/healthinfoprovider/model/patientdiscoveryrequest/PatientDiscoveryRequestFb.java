
package com.cdac.healthinfoprovider.model.patientdiscoveryrequest;

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
public class PatientDiscoveryRequestFb {

    @SerializedName("requestId")
    @Expose
    public String requestId;
    
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    
    @Expose
    @SerializedName("confirmation")
    public Confirmation confirmation;
    
    @SerializedName("transactionId")
    @Expose
    public String transactionId;
    
    @SerializedName("patient")
    @Expose
    public Patient patient;

}
