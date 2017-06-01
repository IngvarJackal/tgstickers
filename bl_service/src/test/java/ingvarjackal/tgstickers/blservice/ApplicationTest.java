package ingvarjackal.tgstickers.blservice;

import com.pengrad.telegrambot.model.InlineQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.model.request.InlineQueryResultCachedPhoto;
import ingvarjackal.tgstickers.blservice.Application;
import ingvarjackal.tgstickers.blservice.ReceiverWorkerService;
import ingvarjackal.tgstickers.blservice.SenderWorkerService;
import ingvarjackal.tgstickers.blservice.db.ParcelService;
import ingvarjackal.tgstickers.mq.TgStanza;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(PowerMockRunner.class)
public class ApplicationTest {
    @Test(timeout = 10000)
    @PrepareForTest({SenderWorkerService.class, ReceiverWorkerService.class, ParcelService.class})
    public void addImageAndTags() throws Exception {
        PowerMockito.mockStatic(SenderWorkerService.class);
        PowerMockito.mockStatic(ReceiverWorkerService.class);
        PowerMockito.mockStatic(ParcelService.class);

        CountDownLatch hasResponse = new CountDownLatch(1);
        List<TgStanza> serviceResponses = new ArrayList<>(1);
        PowerMockito.doAnswer(invocation -> {serviceResponses.add(invocation.getArgument(0)); hasResponse.countDown(); return null;}).when(SenderWorkerService.class, "sendToOutService", any());

        CountDownLatch isReady = new CountDownLatch(1);
        final Consumer<TgStanza>[] callback = new Consumer[1];
        PowerMockito.doAnswer(invocation -> {callback[0] = invocation.getArgument(0); isReady.countDown(); return true;}).when(ReceiverWorkerService.class, "start", any());

        Update picUpdate = Mockito.mock(Update.class);
        Message picMsg = Mockito.mock(Message.class);
        User picUsr = Mockito.mock(User.class);
        Mockito.when(picUsr.id()).thenAnswer(invocation -> 1337);
        Mockito.when(picMsg.from()).thenAnswer(invocation -> picUsr);
        Mockito.when(picUpdate.message()).thenAnswer(invocation -> picMsg);

        Update tagsUpdate = Mockito.mock(Update.class);
        Message tagsMsg = Mockito.mock(Message.class);
        User tagsUsr = Mockito.mock(User.class);
        Mockito.when(tagsUsr.id()).thenAnswer(invocation -> 1337);
        Mockito.when(tagsMsg.from()).thenAnswer(invocation -> tagsUsr);
        Mockito.when(tagsMsg.text()).thenAnswer(invocation -> "werwr ururu");
        Mockito.when(tagsUpdate.message()).thenAnswer(invocation -> tagsMsg);

        PowerMockito.doAnswer(invocation -> (invocation.getArgument(0).equals(picMsg)) ? ParcelService.Type.Photo : ParcelService.Type.Null).when(ParcelService.class, "getMsgType", any());
        List<Integer> ids = new ArrayList<>(2);
        List<Message> messages = new ArrayList<>(2);
        List<List<String>> tags = new ArrayList<>(2);
        CountDownLatch addedCounter = new CountDownLatch(2);
        PowerMockito.doAnswer(invocation -> {ids.add(invocation.getArgument(0)); messages.add(invocation.getArgument(1)); tags.add(invocation.getArgument(2)); addedCounter.countDown(); return null;}).when(ParcelService.class, "addTag", any(), any(), any());

        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.setDaemon(true);
        thread.start();

        isReady.await();
        callback[0].accept(new TgStanza("UID").setRequest(picUpdate));

        hasResponse.await();
        assertTrue(serviceResponses.size() > 0);

        callback[0].accept(new TgStanza("UID").setRequest(tagsUpdate));
        addedCounter.await();
        assertEquals(Arrays.asList(1337, 1337), ids);
        assertEquals(Arrays.asList(picMsg, picMsg), messages);
        assertTrue(tags.contains(Arrays.asList("werwr", "ururu")));
    }

    @Test(timeout = 10000)
    @PrepareForTest({SenderWorkerService.class, ReceiverWorkerService.class, ParcelService.class})
    public void searchImage() throws Exception {
        PowerMockito.mockStatic(SenderWorkerService.class);
        PowerMockito.mockStatic(ReceiverWorkerService.class);
        PowerMockito.mockStatic(ParcelService.class);

        InlineQueryResultCachedPhoto getByTagsResponseEl = Mockito.mock(InlineQueryResultCachedPhoto.class);
        List<? extends InlineQueryResult> getByTagsResponse = Arrays.asList(getByTagsResponseEl);
        List<Integer> users = new ArrayList<>(1);
        List<List<String>> tags = new ArrayList<>(1);
        List<Boolean> matchAny = new ArrayList<>(1);
        PowerMockito.doAnswer(invocation -> {
            users.add(invocation.getArgument(0));
            tags.add(invocation.getArgument(1));
            matchAny.add(invocation.getArgument(2));
            return getByTagsResponse;}).when(ParcelService.class, "getByTags", any(), any(), any());
        PowerMockito.doAnswer(invocation -> ParcelService.Type.Photo).when(ParcelService.class, "getMsgType", any());

        CountDownLatch hasResponse = new CountDownLatch(1);
        List<TgStanza> serviceResponses = new ArrayList<>(1);
        PowerMockito.doAnswer(invocation -> {serviceResponses.add(invocation.getArgument(0)); hasResponse.countDown(); return null;}).when(SenderWorkerService.class, "sendToOutService", any());

        CountDownLatch isReady = new CountDownLatch(1);
        final Consumer<TgStanza>[] callback = new Consumer[1];
        PowerMockito.doAnswer(invocation -> {callback[0] = invocation.getArgument(0); isReady.countDown(); return true;}).when(ReceiverWorkerService.class, "start", any());

        Update searchUpdate = Mockito.mock(Update.class);
        InlineQuery searchQuery = Mockito.mock(InlineQuery.class);
        User tagsUsr = Mockito.mock(User.class);
        Mockito.when(tagsUsr.id()).thenAnswer(invocation -> 7331);
        Mockito.when(searchQuery.from()).thenAnswer(invocation -> tagsUsr);
        Mockito.when(searchQuery.id()).thenAnswer(invocation -> "1337");
        Mockito.when(searchQuery.query()).thenAnswer(invocation -> ". werwr  ururu");
        Mockito.when(searchUpdate.inlineQuery()).thenAnswer(invocation -> searchQuery);


        Thread thread = new Thread(() -> Application.main(new String[]{}));
        thread.setDaemon(true);
        thread.start();

        isReady.await();

        callback[0].accept(new TgStanza("UID").setRequest(searchUpdate));

        hasResponse.await();

        assertEquals(Arrays.asList("1337"), serviceResponses.stream().map(tgStanza -> tgStanza.getInlineResponse().id).collect(Collectors.toList()));
        assertEquals(Arrays.asList(getByTagsResponse), serviceResponses.stream().map(tgStanza -> tgStanza.getInlineResponse().inlineResponses).collect(Collectors.toList()));
        assertEquals(Arrays.asList(7331), users);
        assertTrue(tags.size() == 1 && tags.get(0).containsAll(Arrays.asList("werwr", "ururu")));
        assertFalse(matchAny.stream().anyMatch(aBoolean -> aBoolean));
    }
}