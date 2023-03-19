package com.example.HRplatform.exceptions;

import com.example.HRplatform.model.Candidate;

public class CandidateExistsException extends EntityExistsException {

    public CandidateExistsException(String email) {
        super(email, "e-mail", Candidate.class);
    }

}