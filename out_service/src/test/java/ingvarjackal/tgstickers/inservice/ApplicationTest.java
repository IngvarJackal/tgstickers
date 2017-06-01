package ingvarjackal.tgstickers.inservice;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultCachedPhoto;
import com.pengrad.telegrambot.request.AnswerInlineQuery;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import ingvarjackal.tgstickers.mq.InlineResponse;
import ingvarjackal.tgstickers.mq.Response;
import ingvarjackal.tgstickers.mq.TgStanza;
import ingvarjackal.tgstickers.outservice.Application;
import ingvarjackal.tgstickers.outservice.ReceiverWorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
public class ApplicationTest {
    @Test(timeout = 10000)
    @PrepareForTest({TelegramBotAdapter.class, ReceiverWorkerService.class})
    public void main2() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        final ArrayList<SendMessage> getSentResults = new ArrayList<>(1);

        TelegramBot mockedBot = Mockito.mock(TelegramBot.class);

        final BaseResponse okResponse = Mockito.mock(BaseResponse.class);
        Mockito.when(okResponse.isOk()).thenAnswer(invocation -> true);

        Mockito.when(mockedBot.execute(any(SendMessage.class))).thenAnswer(invocationOnMock -> {getSentResults.add(invocationOnMock.getArgument(0)); latch.countDown(); return okResponse;});

        PowerMockito.mockStatic(TelegramBotAdapter.class);
        Mockito.when(TelegramBotAdapter.build(any())).then(invocationOnMock -> mockedBot);

        PowerMockito.mockStatic(ReceiverWorkerService.class);


        final CountDownLatch latch2 = new CountDownLatch(1);
        final Consumer<TgStanza>[] callback = new Consumer[1];
        PowerMockito.doAnswer(invocationOnMock -> {callback[0] = invocationOnMock.getArgument(0); latch2.countDown(); return true;}).when(ReceiverWorkerService.class, "start", any(Consumer.class));

        Response resp = new Response(1337, "testmsg");
        TgStanza tgStanza = new TgStanza("UID").setResponse(resp);



        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.setDaemon(true);
        thread.start();

        latch2.await();

        callback[0].accept(tgStanza);

        latch.await();

        assertEquals(Arrays.asList(resp.id), getSentResults.stream().map(getUpdates -> getUpdates.getParameters().get("chat_id")).collect(Collectors.toList()));
        assertEquals(Arrays.asList(resp.text), getSentResults.stream().map(getUpdates -> getUpdates.getParameters().get("text")).collect(Collectors.toList()));
    }

    @Test(timeout = 10000)
    @PrepareForTest({TelegramBotAdapter.class, ReceiverWorkerService.class})
    public void main() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);

        final ArrayList<AnswerInlineQuery> getSentResults = new ArrayList<>(1);

        TelegramBot mockedBot = Mockito.mock(TelegramBot.class);

        final BaseResponse okResponse = Mockito.mock(BaseResponse.class);
        Mockito.when(okResponse.isOk()).thenAnswer(invocation -> true);

        Mockito.when(mockedBot.execute(any(AnswerInlineQuery.class))).thenAnswer(invocationOnMock -> {getSentResults.add(invocationOnMock.getArgument(0)); latch.countDown(); return okResponse;});

        PowerMockito.mockStatic(TelegramBotAdapter.class);
        Mockito.when(TelegramBotAdapter.build(any())).then(invocationOnMock -> mockedBot);

        PowerMockito.mockStatic(ReceiverWorkerService.class);


        final CountDownLatch latch2 = new CountDownLatch(1);
        final Consumer<TgStanza>[] callback = new Consumer[1];
        PowerMockito.doAnswer(invocationOnMock -> {callback[0] = invocationOnMock.getArgument(0); latch2.countDown(); return true;}).when(ReceiverWorkerService.class, "start", any(Consumer.class));

        InlineQueryResult<InlineQueryResultCachedPhoto> inlineQueryResult = new InlineQueryResultCachedPhoto("", "");
        TgStanza tgStanza = new TgStanza("UID").setInlineResponse(new InlineResponse("1337", Arrays.asList(inlineQueryResult)));



        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.setDaemon(true);
        thread.start();

        latch2.await();

        callback[0].accept(tgStanza);

        latch.await();

        assertEquals(Arrays.asList("1337"), getSentResults.stream().map(getUpdates -> getUpdates.getParameters().get("inline_query_id")).collect(Collectors.toList()));
    }
}