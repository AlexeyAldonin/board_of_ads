package com.board_of_ads.models.posting.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posting_job_languages")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "levels_languages",
            joinColumns = { @JoinColumn(name = "language_id") },
            inverseJoinColumns = { @JoinColumn(name = "id") }
    )
    private List<LevelLanguage> levelLanguages;
}