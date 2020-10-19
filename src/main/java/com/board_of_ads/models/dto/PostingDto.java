package com.board_of_ads.models.dto;

import com.board_of_ads.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PostingDto {
    private Long id;
    private String title;
    private String description;
    private Long price;
    private String contact;
    private LocalDateTime datePosting;
    private List<Image> images;
    private CategoryDto category;
    private String city;
    private String meetingAddress;
    private Boolean isActive;

    public PostingDto(Long id, String title, String description, Long price, String contact, LocalDateTime datePosting, String meetingAddress) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.datePosting = datePosting;
        this.meetingAddress = meetingAddress;
    }
    public PostingDto(Long id, String title, String description, Long price, String contact, LocalDateTime datePosting, String meetingAddress, Boolean isActive) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.contact = contact;
        this.datePosting = datePosting;
        this.meetingAddress = meetingAddress;
        this.isActive = isActive;
    }
}
