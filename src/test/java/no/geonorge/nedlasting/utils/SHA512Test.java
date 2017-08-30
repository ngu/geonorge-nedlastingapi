package no.geonorge.nedlasting.utils;

import junit.framework.TestCase;
import no.geonorge.nedlasting.utils.SHA512;

public class SHA512Test extends TestCase {

    public void testSHA512() {
        // echo -n "hello world" | shasum5.18 -a 512
        assertEquals(
                "309ecc489c12d6eb4cc40f50c902f2b4d0ed77ee511a7c7a9bcd3ca86d4cd86f989dd35bc5ff499670da34255b45b0cfd830e81f605dcf7dc5542e93ae9cd76f",
                SHA512.hash("hello world"));
    }

}
