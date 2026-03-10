package com.sau.lpm.lpm_app2.service;

import com.sau.lpm.lpm_app2.dtos.PlaceDTO;
import com.sau.lpm.lpm_app2.exception.ErrorMessages;
import com.sau.lpm.lpm_app2.exception.ResourceAlreadyExistsException;
import com.sau.lpm.lpm_app2.exception.ResourceNotFoundException;
import com.sau.lpm.lpm_app2.model.Place;
import com.sau.lpm.lpm_app2.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceServiceImpl(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public PlaceDTO getPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_PLACE_NOT_FOUND + ": " + id)).viewAsPlaceDTO();
    }

    public List<PlaceDTO> getAllPlaces() {
        return placeRepository.findAll().stream().map(Place::viewAsPlaceDTO).toList();
    }

    public PlaceDTO createPlace(Place place) {
        if (placeRepository.findById(place.getId()).isPresent()) {
            throw new ResourceAlreadyExistsException(ErrorMessages.ERROR_PLACE_ALREADY_EXIST + ": " + place.getId());
        }
        return placeRepository.save(place).viewAsPlaceDTO();
    }

    public PlaceDTO updatePlace(Long id, Place place) {
        placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_PLACE_NOT_FOUND + ": " + id));
        place.setId(id);
        return placeRepository.save(place).viewAsPlaceDTO();
    }

    public void deletePlace(Long id) {
        placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ERROR_PLACE_NOT_FOUND + ": " + id));
        placeRepository.deleteById(id);
    }

}

