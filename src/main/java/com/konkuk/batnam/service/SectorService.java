package com.konkuk.batnam.service;

import com.konkuk.batnam.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SectorService {
    private final SectorRepository sectorRepository;


}
