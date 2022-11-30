package com.zreview.api.domain.save.app;

import com.zreview.api.domain.location.dao.LocationRepository;
import com.zreview.api.domain.location.model.Location;
import com.zreview.api.domain.save.dao.SaveRepository;
import com.zreview.api.domain.save.model.Save;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveService {
    private final SaveRepository saveRepository;
    private final LocationRepository locationRepository;
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

    public List<Location> getSaveLocation(String email){
        List<Location> result=new ArrayList<>();
        List<Save> saveList = saveRepository.findByEmail(email);
        for (Save save : saveList){
            Long locationId=save.getLocationId();
            Location location = locationRepository.findById(locationId).get();
            result.add(location);
        }
        return result;
    }
}
