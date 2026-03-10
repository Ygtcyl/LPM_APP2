package com.sau.lpm.lpm_app2.service;

import com.sau.lpm.lpm_app2.dtos.PlaceDTO;
import com.sau.lpm.lpm_app2.model.Place;

import java.util.List;

public interface PlaceService {
    public List<PlaceDTO> getAllPlaces();
    public PlaceDTO getPlaceById(Long id);
    public PlaceDTO createPlace(Place place);
    public PlaceDTO updatePlace(Long id, Place place);
    public void deletePlace(Long id);
}
