package com.zreview.api.domain.save.app;

import com.zreview.api.domain.save.dao.SaveRepository;
import com.zreview.api.domain.save.model.Save;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveService {
    private final SaveRepository saveRepository;

    public void save(String email,Long locationId){
        Save found = saveRepository.findByEmailAndLocationId(email, locationId);
        if (found!=null){ //이미 해당 장소를 저장한 경우
            throw new IllegalArgumentException("해당 유저가 이미 저장한 장소");
        }
        Save save= new Save(email,locationId);
        saveRepository.save(save);
    }

    public void unsave(String email,Long locationId){
        Save found = saveRepository.findByEmailAndLocationId(email, locationId);
        if (found==null){ //해당 장소를 저장한 적 없는 경우
            throw new IllegalArgumentException("저장한 적 없는 장소");
        }
        saveRepository.delete(found);
    }
}
