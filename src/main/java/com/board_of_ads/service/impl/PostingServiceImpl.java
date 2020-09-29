package com.board_of_ads.service.impl;

import com.board_of_ads.model.City;
import com.board_of_ads.model.posting.Posting;
import com.board_of_ads.repository.CityRepository;
import com.board_of_ads.repository.PostingRepository;
import com.board_of_ads.service.interfaces.PostingService;
import com.board_of_ads.service.interfaces.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class PostingServiceImpl implements PostingService {

    private final PostingRepository postingRepository;
    private final RegionService regionService;
    private final CityRepository cityRepository;

    @Override
    public void save(Posting posting) {
        postingRepository.save(posting);
    }

    @Override
    public Posting getPostingById(Long id) {
        return postingRepository.getOne(id);
    }

    @Override
    public Optional<Posting> getPostingByTitle(String title) {
        return Optional.ofNullable(postingRepository.findPostingByTitle(title));
    }


    @Override
    public List<Posting> getPostingByCity(City city) {
        return postingRepository.findPostingByCity(city);
    }

    @Override
    public List<Posting> getPostingByFullRegionName(String name) {
        List<Posting> result = new ArrayList<>();
        var cities = cityRepository.findCitiesByRegion(
                regionService.findRegionByNameAndFormSubject(name).get());
        cities.forEach(city -> result.addAll(postingRepository.findPostingByCity(city)));
        return result;
    }

    @Override
    public List<Posting> getAllPostings() {
        return postingRepository.findAll();
    }
}