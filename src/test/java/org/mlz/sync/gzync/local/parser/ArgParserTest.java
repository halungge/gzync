package org.mlz.sync.gzync.local.parser;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArgParserTest {
    private ArgParser parser;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        parser = new ArgParser();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @Test
    void parseLocalPath_short(){
        parser.parse("-l", "foo/bar");
        assertThat(parser.getLocalFolder(), equalTo("foo/bar"));
    }


    @Test
    void parseLocalPath_long(){
        parser.parse("--localPath", "foo/baz");
        assertThat(parser.getLocalFolder(), equalTo("foo/baz"));
    }

    @Test
    void defaultValueOfForceIsFalse(){
        parser.parse("--localPath", "foo/baz");
        assertFalse(parser.isForcedDownload());
    }

    @Test
    void parseForceDownload_short(){
        parser.parse("-f");
        assertTrue(parser.isForcedDownload());
    }


    @Test
    void parseForceDownload_long(){
        parser.parse("--force-new");
        assertTrue(parser.isForcedDownload());
    }
    @Test
    void parseNull_returnsDefault(){
        parser.parse(null);
        assertThat(parser.getLocalFolder(), equalTo("."));
    }
    @Test
    void parseEmpty_returnsDefault(){
        parser.parse();
        assertThat(parser.getLocalFolder(), equalTo("."));
    }
    @Test
    void parseUnknownOption_throwsException(){
        assertThrows(UnknownCliArgumentException.class, ()-> parser.parse("-k", "foo/baz"));

    }
}