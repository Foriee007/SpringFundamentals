package com.dictionaryapp.service;

import com.dictionaryapp.config.UserSession;
import com.dictionaryapp.model.dto.AddWordDto;
import com.dictionaryapp.model.entity.Language;
import com.dictionaryapp.model.entity.LanguageEnum;
import com.dictionaryapp.model.entity.User;
import com.dictionaryapp.model.entity.Word;
import com.dictionaryapp.repo.LanguageRepository;
import com.dictionaryapp.repo.UserRepository;
import com.dictionaryapp.repo.WordRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class WordService {

    private final LanguageRepository languageRepository;
    private final UserSession userSession;
    private final UserRepository userRepository;
    private final LanguageService languageService;
    private final UserService userService;
    private final WordRepository wordRepository;

    public WordService(LanguageRepository languageRepository, UserSession userSession, UserRepository userRepository, LanguageService languageService, UserService userService, WordRepository wordRepository) {
        this.languageRepository = languageRepository;
        this.userSession = userSession;
        this.userRepository = userRepository;
        this.languageService = languageService;
        this.userService = userService;
        this.wordRepository = wordRepository;
    }

    public void add(AddWordDto data, long id){
        Word word = new Word();
        Language language = languageService.findLanguage(data.getLanguage());
        User userById = userService.findUserById(id).orElse(null);

        if (userById == null) {
            throw new NoSuchElementException();
        }
        word.setTerm(data.getTerm());
        word.setTranslation(data.getTranslation());
        word.setExample(data.getExample());
        word.setDate(data.getInputDate());
        word.setLanguage(language);
        word.setAddedBy(userById);

        Set<Word> userByIdAssignedWords = userById.getAddedWords();
        userByIdAssignedWords.add(word);
        userById.setAddedWords(userByIdAssignedWords);

        this.wordRepository.save(word);
        this.userRepository.save(userById);
    }
    public List<Word> getAllWords() {
        return this.wordRepository.findAll();
    }

    public List<Word> germanWords() {
        return this.wordRepository.findAllByLanguage_LanguageName(LanguageEnum.GERMAN);
    }

    public List<Word> spanishWords() {
        return this.wordRepository.findAllByLanguage_LanguageName(LanguageEnum.SPANISH);
    }

    public List<Word> frenchWordsCount() {
        return this.wordRepository.findAllByLanguage_LanguageName(LanguageEnum.FRENCH);
    }

    public List<Word> italianWords() {
        return this.wordRepository.findAllByLanguage_LanguageName(LanguageEnum.ITALIAN);
    }

    @Transactional
    public void deleteSingleWord(Long id) {
        Word wordToDelete = wordRepository.findById(id).orElse(null);
        User user = wordToDelete.getAddedBy();
        Set<Word> allUserWords = user.getAddedWords();
        allUserWords.remove(wordToDelete);
        this.userRepository.save(user);
        this.wordRepository.deleteById(id);
    }


    public long getAllCount() {
        long count = wordRepository.count();
        return count;
    }
}
