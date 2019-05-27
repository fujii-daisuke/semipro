package red.semipro.domain.service.prefecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import red.semipro.domain.model.Prefecture;
import red.semipro.domain.repository.prefecture.PrefectureRepository;

@Service
public class PrefectureServiceImpl implements PrefectureService {

    @Autowired
    private PrefectureRepository prefectureRepository;
    
    @Override
    public List<Prefecture> findAll() {
        return prefectureRepository.findAll();
    }

}
