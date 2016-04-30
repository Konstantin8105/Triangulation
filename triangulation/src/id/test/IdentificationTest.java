package id.test;

import id.GeneratorId;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class IdentificationTest {

    @Test
    public void testGetID() throws Exception {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < 1000000; i++)
            ids.add(GeneratorId.getID());
        Collections.sort(ids, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return lhs - rhs;
            }
        });
        for (int i = 1; i < ids.size(); i++) {
            assertTrue(ids.get(i - 1) != ids.get(i));
        }
    }
}