package com.azarenka.votingsystem.web;

import com.azarenka.votingsystem.service.api.IVoteService;
import com.azarenka.votingsystem.to.ResponseMessage;
import com.azarenka.votingsystem.to.VoteTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Vote controller.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 02.12.2020
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private IVoteService voteService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN') or" + "@voteValidator.checkVote(#vote)")
    public ResponseEntity<?> updateMenuByRestaurant(@Valid @RequestBody VoteTo vote) {
        voteService.save(vote);
        return new ResponseEntity<>(new ResponseMessage("Vote was created"), HttpStatus.OK);
    }
}
