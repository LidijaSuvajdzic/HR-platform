package com.example.HRplatform.exceptions;

import com.example.HRplatform.model.Candidate;

public class CandidateNotFoundException extends EntityNotFoundException {

    public CandidateNotFoundException(String email) {
        super(email, "e-mail", Candidate.class);
    }

    public CandidateNotFoundException(String firstname, String lastname) {
        super(firstname+" "+lastname, "name", Candidate.class);
    }

    public CandidateNotFoundException(Long id) {
        super(id, Candidate.class);
    }
}