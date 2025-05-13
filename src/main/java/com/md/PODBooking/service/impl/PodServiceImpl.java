package com.md.PODBooking.service.impl;

import com.md.PODBooking.entity.PodEntity;
import com.md.PODBooking.repository.PodsRepository;
import com.md.PODBooking.service.PodService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PodServiceImpl implements PodService {
    private PodsRepository podsRepository;
    public PodServiceImpl(PodsRepository podsRepository) {
        this.podsRepository = podsRepository;
    }

    @Override
    public List<PodEntity> findAllPods() {
        List<PodEntity> pods = podsRepository.findAll();
        return pods;
    }

    @Override
    public void insertPod(PodEntity pod) {

    }
}
