package ingvarjackal.tgstickers.inservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import ingvarjackal.tgstickers.mq.TgStanza;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.net.ssl.*")
public class ApplicationTest {
    @Test(timeout = 10000)
    @PrepareForTest({TelegramBotAdapter.class, SenderWorkerService.class})
    public void main() throws Exception {
        Update update1 = Mockito.mock(Update.class);
        Message message1 = Mockito.mock(Message.class);
        Mockito.when(update1.message()).thenAnswer(invocationOnMock -> message1);
        Mockito.when(update1.updateId()).thenAnswer(invocationOnMock -> 1);

        Update update2 = Mockito.mock(Update.class);
        Message message2 = Mockito.mock(Message.class);
        Mockito.when(update2.message()).thenAnswer(invocationOnMock -> message2);
        Mockito.when(update2.updateId()).thenAnswer(invocationOnMock -> 2);

        Update update3 = Mockito.mock(Update.class);
        Message message3 = Mockito.mock(Message.class);
        Mockito.when(update3.message()).thenAnswer(invocationOnMock -> message3);
        Mockito.when(update3.updateId()).thenAnswer(invocationOnMock -> 3);

        final BlockingQueue<GetUpdatesResponse> queue = new ArrayBlockingQueue<>(3);
        GetUpdatesResponse response1 = Mockito.mock(GetUpdatesResponse.class);
        Mockito.when(response1.updates()).thenAnswer(invocation -> Arrays.asList(update1, update2));
        GetUpdatesResponse response2 = Mockito.mock(GetUpdatesResponse.class);
        Mockito.when(response2.updates()).thenAnswer(invocation -> Arrays.asList(update3));
        GetUpdatesResponse response3 = Mockito.mock(GetUpdatesResponse.class);
        Mockito.when(response3.updates()).thenAnswer(invocation -> new ArrayList<>());


        final ArrayList<GetUpdates> getUpdatesRequests = new ArrayList<>(2);

        TelegramBot mockedBot = Mockito.mock(TelegramBot.class);
        Mockito.when(mockedBot.execute(any(GetUpdates.class))).thenAnswer(invocationOnMock -> {getUpdatesRequests.add(invocationOnMock.getArgument(0)); return queue.take();});

        PowerMockito.mockStatic(TelegramBotAdapter.class);
        Mockito.when(TelegramBotAdapter.buildCustom(any(), any(), any())).then(invocationOnMock -> mockedBot);


        final CountDownLatch latch = new CountDownLatch(3);

        final List<TgStanza> responses = new ArrayList<>(3);
        PowerMockito.mockStatic(SenderWorkerService.class);
        PowerMockito.doAnswer(invocationOnMock -> {responses.add(invocationOnMock.getArgument(0)); latch.countDown(); return null;}).when(SenderWorkerService.class, "sendToBlService", any());



        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.setDaemon(true);
        thread.start();

        queue.add(response1);
        queue.add(response2);
        queue.add(response3);


        latch.await();

        assertEquals(Arrays.asList(0, 3, 4, 4), getUpdatesRequests.stream().map(getUpdates -> getUpdates.getParameters().get("offset")).collect(Collectors.toList()));
    }
}