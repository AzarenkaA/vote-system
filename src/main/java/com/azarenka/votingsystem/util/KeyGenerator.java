package com.azarenka.votingsystem.util;

import java.util.UUID;

/**
 * Generator of uniq identifiers.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 22.11.2020
 */
public class KeyGenerator {

    /**
     * Default constructor.
     */
    public KeyGenerator() {
    }

    /**
     * Set value id.
     *
     * @return {@link String}
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString();
    }
}
