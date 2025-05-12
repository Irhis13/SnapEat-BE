package com.dam.web_cocina.common.utils;

import org.hashids.Hashids;

public class HashUtil {

    private static final String SALT = "tu_salt_secreta";
    private static final int MIN_HASH_LENGTH = 8;
    private static final Hashids HASHIDS = new Hashids(SALT, MIN_HASH_LENGTH);

    public static String encode(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return HASHIDS.encode(id);
    }

    public static Long decode(String hash) {
        if (hash == null || hash.trim().isEmpty()) {
            throw new IllegalArgumentException("El hash no puede ser nulo o vacío");
        }

        try {
            long[] decoded = HASHIDS.decode(hash);
            if (decoded.length == 0) {
                throw new IllegalArgumentException("Hash inválido: " + hash);
            }
            return decoded[0];
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al decodificar el hash: " + hash, e);
        }
    }
}
