package com.interapp.externa.core.adoption.application

import com.interapp.externa.core.adoption.domain.Adoption
import com.interapp.externa.core.adoption.domain.AdoptionRepository
import org.springframework.stereotype.Service

@Service
class AdoptionService(private val adoptionRepository: AdoptionRepository) {
    fun findAdoptionsByRepresentativeId(representativeId: Long): MutableList<Adoption> {
        return adoptionRepository.findAdoptionsByRepresentativeId(representativeId)
    }

    fun findAdoptionsByGodfatherId(godfatherId: Long): MutableList<Adoption> {
        return adoptionRepository.findAdoptionsByGodfatherId(godfatherId)
    }

    fun findAdoptionById(id: Long): Adoption {
        return adoptionRepository.findById(id).orElseThrow()
    }

    fun findAdoptions(): MutableList<Adoption> {
        return adoptionRepository.findAll()
    }

    fun addAdoption(adoption: Adoption): Adoption {
        return adoptionRepository.save(adoption)
    }

    fun deleteAdoptionById(id: Long) {
        adoptionRepository.deleteById(id)
    }
}
