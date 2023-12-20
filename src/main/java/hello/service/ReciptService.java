package hello.service;

import hello.domain.Recipt;
import hello.repository.ReciptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReciptService {

    private final ReciptRepository reciptRepository;

    @Transactional
    public Long join(Recipt recipt) {
        reciptRepository.save(recipt);
        return recipt.getId();
    }

    public List<Recipt> findAll(Long id) {
        return reciptRepository.findAllByMemberId(id);
    }

}
