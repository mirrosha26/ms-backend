package com.bondsbackend.ms.service.impl;

import com.bondsbackend.ms.dto.ProfileDto;
import com.bondsbackend.ms.entity.Profile;
import com.bondsbackend.ms.entity.Portfolio;
import com.bondsbackend.ms.mapper.ProfileMapper;
import com.bondsbackend.ms.repository.ProfileRepository;
import com.bondsbackend.ms.repository.PortfolioRepository;
import com.bondsbackend.ms.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final PortfolioRepository portfolioRepository;
    private final ProfileMapper profileMapper;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository,
                              PortfolioRepository portfolioRepository,
                              ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.portfolioRepository = portfolioRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public ProfileDto createOrUpdateProfile(ProfileDto profileDto) {
        Optional<Profile> existingProfile = profileRepository.findByUsername(profileDto.getUsername());
        if (existingProfile.isPresent()) {
            return null; // Имя пользователя уже существует
        }

        Profile profile = profileMapper.toEntity(profileDto);
        profile = profileRepository.save(profile);

        Portfolio portfolio = new Portfolio();
        portfolio.setProfile(profile);
        portfolioRepository.save(portfolio);

        return profileMapper.toDto(profile);
    }
}
