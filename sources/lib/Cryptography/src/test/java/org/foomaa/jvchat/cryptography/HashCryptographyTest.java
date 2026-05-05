package org.foomaa.jvchat.cryptography;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HashCryptographyTest {
    private HashCryptography hashCryptography;

    @BeforeEach
    void setUp() {
        hashCryptography = HashCryptography.builder().build();
    }

    @Test
    void shouldReturnSameHashForSameInput() {
        String input = "hello";

        String hash1 = hashCryptography.getHash(input);
        String hash2 = hashCryptography.getHash(input);

        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void shouldReturnDifferentHashForDifferentInputs() {
        String hash1 = hashCryptography.getHash("hello");
        String hash2 = hashCryptography.getHash("world");

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void shouldReturn64CharSha256Hash() {
        String hash = hashCryptography.getHash("hello");

        assertThat(hash).hasSize(64);
    }

    @Test
    void shouldThrowExceptionWhenInputIsNull() {
        assertThrows(NullPointerException.class, () -> hashCryptography.getHash(null));
    }

    @Test
    void shouldReturnConsistentHashForLongInput() {
        String input = "a".repeat(1000);

        String hash1 = hashCryptography.getHash(input);
        String hash2 = hashCryptography.getHash(input);

        assertThat(hash1).isEqualTo(hash2);
    }
}
