package com.placemate.service;

import com.placemate.dto.CompanyDTO;
import com.placemate.entity.Company;
import com.placemate.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyDTO.CompanyResponse addCompany(CompanyDTO.CompanyRequest request) {
        Company company = Company.builder()
                .name(request.getName())
                .industry(request.getIndustry())
                .website(request.getWebsite())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .location(request.getLocation())
                .build();
        company = companyRepository.save(company);
        return mapToResponse(company);
    }

    public CompanyDTO.CompanyResponse updateCompany(Long id, CompanyDTO.CompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setName(request.getName());
        company.setIndustry(request.getIndustry());
        company.setWebsite(request.getWebsite());
        company.setDescription(request.getDescription());
        company.setLogoUrl(request.getLogoUrl());
        company.setLocation(request.getLocation());

        companyRepository.save(company);
        return mapToResponse(company);
    }

    public List<CompanyDTO.CompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CompanyDTO.CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return mapToResponse(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    private CompanyDTO.CompanyResponse mapToResponse(Company company) {
        return CompanyDTO.CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .industry(company.getIndustry())
                .website(company.getWebsite())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .location(company.getLocation())
                .jobCount(company.getJobs() != null ? company.getJobs().size() : 0)
                .build();
    }
}
