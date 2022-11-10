package com.konkuk.batnam.service;

import com.konkuk.batnam.domain.AirStrip;
import com.konkuk.batnam.domain.Responder;
import com.konkuk.batnam.domain.Sector;
import com.konkuk.batnam.dto.request.responder.ResponderCreateDto;
import com.konkuk.batnam.dto.request.responder.ResponderUpdateDto;
import com.konkuk.batnam.dto.response.ResponderResponseDto;
import com.konkuk.batnam.dto.response.log.LogResponseDto;
import com.konkuk.batnam.repository.AirStripRepository;
import com.konkuk.batnam.repository.ResponderRepository;
import com.konkuk.batnam.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponderService {
    private final ResponderRepository responderRepository;
    private final AirStripRepository airStripRepository;
    private final SectorRepository sectorRepository;
    private final JavaMailSender mailSender;

    @Async
    public void sendMailToResponder(LogResponseDto dto) throws MessagingException {

        Optional<Sector> optionalSector = sectorRepository.findById(dto.getSectorId());
        if (optionalSector.isEmpty()) return;

        Sector sector = optionalSector.get();
        AirStrip airStrip = sector.getAirStrip();
        String airStripName = airStrip.getName();
        String sectorName = sector.getName();

        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject("BATMAN - ALERT MAIL");
        Multipart mp = new MimeMultipart();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(getTemplate(dto, airStripName, sectorName), "text/html;charset=UTF-8");
        mp.addBodyPart(htmlPart);
        message.setContent(mp);

        List<ResponderResponseDto> responseList = findAllByStripId(airStrip.getId());
        if (responseList.isEmpty()) return;
        for (ResponderResponseDto responder : responseList) {
            message.addRecipients(Message.RecipientType.TO, responder.getDest());
        }

        mailSender.send(message);
    }

    private String getTemplate(LogResponseDto dto, String airStripName, String sectorName) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                getBody(dto, airStripName, sectorName) +
                "</body>\n" +
                "</html>";
    }

    private String getBody(LogResponseDto log, String airStripName, String sectorName) {
        return "<h1>BATMAN-ALERT</h1>\n" +
                "<span>Too many objects detected</span>\n" +
                "<ul>\n" +
                "    <li>date : " + log.getLogDate() + "</li>\n" +
                "    <li>airStrip : " + airStripName + "</li>\n" +
                "    <li>sector : " + sectorName + "</li>\n" +
                "    <li>object : " + log.getObjectName() + "</li>\n" +
                "</ul>\n" +
                "<h2>Captured Image</h2>\n" +
                "<img src=\"" + log.getCaptureURL() + "\"/>";
    }



    @Transactional
    public ResponderResponseDto createResponder(ResponderCreateDto dto) {
        Optional<AirStrip> optional = airStripRepository.findById(dto.getAirStripId());
        if (optional.isEmpty()) return null;
        Responder saved = responderRepository.save(dto.toEntity(optional.get()));
        return ResponderResponseDto.toResponseDto(saved);
    }

    @Transactional
    public List<ResponderResponseDto> findAllByStripId(Long stripId) {
        Optional<AirStrip> optional = airStripRepository.findById(stripId);
        if (optional.isEmpty()) return null;
        AirStrip airStrip = optional.get();
        return responderRepository.findAllByAirStrip(airStrip)
                .stream()
                .map(responder -> ResponderResponseDto.toResponseDto(responder))
                .collect(Collectors.toList());
    }

    @Transactional
    public Long updateResponder(ResponderUpdateDto dto) {
        Optional<Responder> optional = responderRepository.findById(dto.getId());
        if (optional.isEmpty()) return null;
        Responder entity = optional.get();
        dto.update(entity);
        return entity.getId();
    }

    @Transactional
    public void deleteResponder(Long id) {
        Optional<Responder> optional = responderRepository.findById(id);
        if (optional.isEmpty()) return;
        responderRepository.delete(optional.get());
    }
}
