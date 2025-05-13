package com.md.PODBooking.service;


import com.md.PODBooking.entity.PodEntity;

import java.util.List;

public interface PodService {
    List<PodEntity> findAllPods();
    void insertPod(PodEntity pod);

}
