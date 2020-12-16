package com.azarenka.votingsystem.to;

/**
 * Vote transfer object.
 *
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 16.12.2020
 */
public class VoteTo {

    private String restaurantName;
    private Long countOfVotes;

    public VoteTo(String restaurantName, Long countOfVotes) {
        this.restaurantName = restaurantName;
        this.countOfVotes = countOfVotes;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Long getCountOfVotes() {
        return countOfVotes;
    }

    public void setCountOfVotes(Long countOfVotes) {
        this.countOfVotes = countOfVotes;
    }
}
