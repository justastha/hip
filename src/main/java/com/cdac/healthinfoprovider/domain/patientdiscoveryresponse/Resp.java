
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
public class Resp {

    @SerializedName("requestId")
    @Expose
    public String requestId;

}
