import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.*;

class TwitterTest {

    @Test
    void actual_call() {

        Twitter twitter = new Twitter();

        boolean actual;

        actual = twitter.isMentionned("me");
        assertEquals(true, actual);
    }

    @Test
    void mock_full_object() {

        Twitter twitter = createMock("twitter", Twitter.class);

        expect(twitter.loadTweet()).andReturn("hello @me");
        expect(twitter.loadTweet()).andReturn("hello @you");
        replay(twitter);

        String actual;

        actual = twitter.loadTweet();
        assertEquals("hello @me", actual);

        actual = twitter.loadTweet();
        assertEquals("hello @you", actual);
    }

    @Test
    void mock_partial_object() {

        Twitter twitter = partialMockBuilder(Twitter.class)
          .addMockedMethod("loadTweet")
          .createMock();

        expect(twitter.loadTweet()).andReturn("hello @me").times(2);
        replay(twitter);

        boolean actual;

        actual = twitter.isMentionned("me");
        assertEquals(true, actual);

        actual = twitter.isMentionned("you");
        assertEquals(false, actual);
    }

    @Test
    void isMentioned_lookForAtSymbol() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn("I am tweet that likes to talk about @me").times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(true, actual);

        actual = twitter.isMentioned("you");
        assertEquals(false, actual);

        verify(twitter);
    }

    @Test
    void isMentioned_dontReturnSubstringMatches() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn("hello @meat").times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(false, actual);

        actual = twitter.isMentioned("meat");
        assertEquals(true, actual);

        verify(twitter);
    }

    @Test
    void isMentioned_superStringNotFound() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn("I am tweet that likes to talk about @me").times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(true, actual);

        actual = twitter.isMentioned("meat");
        assertEquals(false, actual);

        verify(twitter);
    }

    @Test
    void isMentioned_handleNull() {
        Twitter twitter = partialMockBuilder(Twitter.class)
            .addMockedMethod("loadTweet")
            .createMock();

        expect(twitter.loadTweet()).andReturn(null).times(2);
        replay(twitter);

        boolean actual = twitter.isMentioned("me");
        assertEquals(false, actual);

        actual = twitter.isMentioned("meat");
        assertEquals(false, actual);

        verify(twitter);
    }
}