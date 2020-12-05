package com.azarenka.votingsystem.service.api;

import com.azarenka.votingsystem.to.ResponseMessage;

/**
 * Interface for restaurant service.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
public interface IRestaurantService {

    ResponseMessage toVote(String id);
}
