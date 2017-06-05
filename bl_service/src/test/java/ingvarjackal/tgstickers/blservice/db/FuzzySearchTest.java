package ingvarjackal.tgstickers.blservice.db;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
public class FuzzySearchTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    public static class TestResult extends InlineQueryResult {
        public String testId;

        public TestResult(String type, String id, String testId) {
            super(type, id);
            this.testId = testId;
        }
    }

    @Test
    @PrepareForTest({ParcelDAO.class})
    public void fuzzyGetByTags() throws Exception {
        PowerMockito.mockStatic(ParcelDAO.class);
        System.out.println(TestResult.class.getName());
        Class.forName("ingvarjackal.tgstickers.blservice.db.FuzzySearchTest$TestResult");

        List<Key<ParcelAncestor>> usersArgs = new ArrayList<>(1);

        PowerMockito.doAnswer(invocation -> {usersArgs.add(invocation.getArgument(0));return Arrays.asList(
                new Parcel("id", Arrays.asList("QwErTy", "wasd"), 1337, "{\"type\":\"supertype\",\"id\":\"testid\", \"testId\":\"testId\"}", "ingvarjackal.tgstickers.blservice.db.FuzzySearchTest$TestResult"),
                new Parcel("id", Arrays.asList("qsefth", "wqsaxz"), 1337, "{\"type\":\"wrongtype\",\"id\":\"wrongid\", \"testId\":\"wrongId\"}", "ingvarjackal.tgstickers.blservice.db.FuzzySearchTest$TestResult")
                );}).when(ParcelDAO.class, "getByUser", any());

        assertEquals(Arrays.asList("testId"), ParcelService.getByTags(1337, Arrays.asList("qwrrty"), true).stream().map(o -> ((TestResult)o).testId).collect(Collectors.toList()));
        assertEquals(Arrays.asList("testId"), ParcelService.getByTags(1337, Arrays.asList("qwe"), true).stream().map(o -> ((TestResult)o).testId).collect(Collectors.toList()));
    }
}