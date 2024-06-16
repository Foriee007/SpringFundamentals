package com.dictionaryapp.service;

import com.dictionaryapp.model.entity.Language;
import com.dictionaryapp.model.entity.LanguageEnum;
import com.dictionaryapp.repo.LanguageRepository;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public Language findLanguage(LanguageEnum languageEnum) {
            return this.languageRepository.findByLanguageName(languageEnum).orElseThrow();
    }
}
