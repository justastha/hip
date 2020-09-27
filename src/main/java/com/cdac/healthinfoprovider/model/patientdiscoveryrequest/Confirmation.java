
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
public class Confirmation {

    @SerializedName("linkRefNumber")
    @Expose
    public String linkRefNumber;
    
    @SerializedName("token")
    @Expose
    public String token;

}
