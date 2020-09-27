
package com.cdac.healthinfoprovider.domain.patientdiscoveryresponse;

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
public class PatientDiscoveryResponseVo {

    @SerializedName("requestId")
    @Expose
    public String requestId;
    @SerializedName("timestamp")
    @Expose
    public String timestamp;
    @SerializedName("transactionId")
    @Expose
    public String transactionId;
    @SerializedName("patient")
    @Expose
    public Patient patient;
    @SerializedName("error")
    @Expose
    public Error error;
    @SerializedName("resp")
    @Expose
    public Resp resp;

}
