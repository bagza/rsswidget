package com.example.gryazin.rsswidget.domain;

import android.os.SystemClock;

import com.example.gryazin.rsswidget.domain.UpdateStatus.UpdateStatusVisitor;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

/**
 * Created by Zver on 16.11.2016.
 */
public class UpdateStatusTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public UpdateStatusVisitor statusVisitor;

    long timestamp = new Random(123).nextLong();
    String msg = "error";


    @Test
    public void acceptSuccess() throws Exception {
        UpdateStatus success = UpdateStatus.StatusSuccess.ofSyncTimestamp(timestamp);

        success.accept(statusVisitor);

        then(statusVisitor).should(only()).onSuccess(timestamp);
        then(statusVisitor).shouldHaveNoMoreInteractions();
    }

    @Test
    public void acceptPending() throws Exception {
        UpdateStatus pending = UpdateStatus.StatusPending.ofLastSyncTimestamp(timestamp);

        pending.accept(statusVisitor);

        then(statusVisitor).should(only()).onPending(timestamp);
        then(statusVisitor).shouldHaveNoMoreInteractions();
    }

    @Test
    public void acceptError() throws Exception {
        UpdateStatus error = UpdateStatus.StatusError.ofErrorMessage(msg);

        error.accept(statusVisitor);

        then(statusVisitor).should(only()).onError(msg);
        then(statusVisitor).shouldHaveNoMoreInteractions();
    }
}