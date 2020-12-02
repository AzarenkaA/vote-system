package com.azarenka.votingsystem.service.api;

import com.azarenka.votingsystem.to.VoteTo;

/**
 * Interface for vote service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 02.12.2020
 */
public interface IVoteService {

    void save(VoteTo voteTo);
}
