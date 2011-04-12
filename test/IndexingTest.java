import java.util.List;

import org.junit.Test;

import com.github.scriptdonkey.model.Indexer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IndexingTest {

    @Test
    public void testIndexing_01() throws Exception {
        final List<String> tokens = Indexer.tokenize("a b");
        assertEquals(2, tokens.size());
        assertEquals("a", tokens.get(0));
        assertEquals("b", tokens.get(1));
    }

    @Test
    public void testIndexing_02() throws Exception {
        final List<String> tokens = Indexer.tokenize("A B");
        assertEquals(2, tokens.size());
        assertEquals("a", tokens.get(0));
        assertEquals("b", tokens.get(1));
    }

    @Test
    public void testIndexing_03() throws Exception {
        final List<String> tokens = Indexer.tokenize("A B;");
        assertEquals(2, tokens.size());
        assertEquals("a", tokens.get(0));
        assertEquals("b", tokens.get(1));
    }

    @Test
    public void testIndexing_04() throws Exception {
        final List<String> tokens = Indexer.tokenize("A<B");
        assertEquals(2, tokens.size());
        assertEquals("a", tokens.get(0));
        assertEquals("b", tokens.get(1));
    }

    @Test
    public void testIndexing_05() throws Exception {
        final List<String> tokens = Indexer
                .tokenize("System.out.println(\"Hello World\"");
        assertTrue(tokens.contains("system"));
        assertTrue(tokens.contains("out"));
        assertTrue(tokens.contains("println"));
        assertTrue(tokens.contains("hello"));
        assertTrue(tokens.contains("world"));
    }

}
