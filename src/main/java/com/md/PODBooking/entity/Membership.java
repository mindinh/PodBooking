package com.md.PODBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor @NoArgsConstructor
public class Membership {

    private String membershipName;
    private long rewardPoint;
}
