package com.spring.cohort.assignment.service.impl;

import com.spring.cohort.assignment.dto.AuthorRequest;
import com.spring.cohort.assignment.dto.AuthorResponse;
import com.spring.cohort.assignment.entity.Author;
import com.spring.cohort.assignment.repository.AuthorRepository;
import com.spring.cohort.assignment.service.AuthorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;
    ModelMapper modelMapper;

    @Override
    public AuthorResponse createAuthor(AuthorRequest authorRequest) {

        Author author = authorRepository.findByEmail(authorRequest.getEmail());

        if(author != null)
            throw new RuntimeException("Author already exist by this email");

        Author newAuthor = modelMapper.map(authorRequest, Author.class);
        authorRepository.save(newAuthor);

        return modelMapper.map(newAuthor, AuthorResponse.class);
    }

    @Override
    public List<AuthorResponse> getAuthorList() {
        List<Author> authorList = authorRepository.findAll();
        return authorList.stream()
                .map(author -> modelMapper.map(author, AuthorResponse.class))
                .toList();
    }

    @Override
    public AuthorResponse getAuthorById(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id: "+id));
        return modelMapper.map(author, AuthorResponse.class);
    }

    @Override
    public AuthorResponse updateAuthor(UUID id, AuthorRequest authorRequest) {

        Author author = authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id: "+id));
        modelMapper.map(authorRequest, author);

        return modelMapper.map(author, AuthorResponse.class);
    }

    @Override
    public void deleteAuthor(UUID id) {
        Author author = authorRepository.findById(id).orElseThrow(()-> new RuntimeException("Author not found with id: "+id));
        authorRepository.delete(author);
    }

}
