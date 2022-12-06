package br.com.frederykantunnes.challenge.service;

import br.com.frederykantunnes.challenge.dto.StartSessionRequestDTO;
import br.com.frederykantunnes.challenge.dto.StartSessionResponseDTO;
import br.com.frederykantunnes.challenge.dto.StaveRequestDTO;
import br.com.frederykantunnes.challenge.dto.StaveResponseDTO;
import br.com.frederykantunnes.challenge.mapper.SessionMapper;
import br.com.frederykantunnes.challenge.mapper.StaveMapper;
import br.com.frederykantunnes.challenge.model.SessionModel;
import br.com.frederykantunnes.challenge.model.StaveModel;
import br.com.frederykantunnes.challenge.repository.SessionRepository;
import br.com.frederykantunnes.challenge.repository.StaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StaveService {

    private final StaveRepository staveRepository;
    private final SessionRepository sessionRepository;


    @Autowired
    public StaveService(StaveRepository staveRepository, SessionRepository sessionRepository) {
        this.staveRepository = staveRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<StaveResponseDTO> findAllStaves(){
        List<StaveModel> all = this.staveRepository.findAll();
        return all.stream().map(StaveMapper::staveMapperToResponse).collect(Collectors.toList());
    }

    public StaveResponseDTO create(StaveRequestDTO stave){
        StaveModel save = this.staveRepository.save(StaveMapper.staveMapperToNewModel(stave));
        return StaveMapper.staveMapperToResponse(save);
    }

    public StartSessionResponseDTO startSession(StartSessionRequestDTO stave){
        boolean existSession = sessionRepository.findByUuidStave(stave.getUuidStave()).isPresent();
        if(existSession)
            throw new RuntimeException("Session has already started");
        SessionModel save = this.sessionRepository.save(SessionMapper.startSessionToSessionModel(stave));
        return SessionMapper.sessionModelToResponse(save);
    }
}
