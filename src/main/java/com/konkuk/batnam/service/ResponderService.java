package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Responder;
import com.konkuk.batnam.dto.request.responder.ResponderCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderUpdateDto;
import com.konkuk.batnam.dto.response.ResponderResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.ResponderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponderService {
    private final ResponderRepository responderRepository;
    private final AirStripRepository airStripRepository;

    @Transactional
    public ResponderResponseDto createResponder(ResponderCreateDto dto) {
        Optional<AirStrip> optional = airStripRepository.findById(dto.getAirStripId());
        if(optional.isEmpty()) return null;
        Responder saved = responderRepository.save(dto.toEntity(optional.get()));
        return ResponderResponseDto.toResponseDto(saved);
    }

    @Transactional
    public List<ResponderResponseDto> findAllByStripId(Long stripId) {
        Optional<AirStrip> optional = airStripRepository.findById(stripId);
        if(optional.isEmpty()) return null;
        AirStrip airStrip = optional.get();
        return responderRepository.findAllByAirStrip(airStrip)
                .stream()
                .map(responder -> ResponderResponseDto.toResponseDto(responder))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateResponder(ResponderUpdateDto dto) {
        Optional<Responder> optional = responderRepository.findById(dto.getId());
        if(optional.isEmpty()) return null;
        Responder entity = optional.get();
        dto.update(entity);
        return entity.getId();
    }

    @Transactional
    public void deleteResponder(Long id) {
        Optional<Responder> optional = responderRepository.findById(id);
        if(optional.isEmpty()) return;
        responderRepository.delete(optional.get());
    }
}
