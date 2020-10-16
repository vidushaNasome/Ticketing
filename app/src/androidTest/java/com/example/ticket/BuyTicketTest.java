package com.example.ticket;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertNotNull;

public class BuyTicketTest {
    @Rule
    public ActivityTestRule<BuyTicket> mActivityTestRule=new ActivityTestRule<BuyTicket>(BuyTicket.class);

    private BuyTicket mActivity=null;

    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(GuestCheckout.class.getName(),null,false);

    @Before
    public void setUp() throws Exception{
        mActivity=mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunchOfGuestCheckoutOnButtonClick()
    {
        assertNotNull(mActivity.findViewById(R.id.btncheck));

        onView(withId(R.id.btncheck)).perform(click());

        Activity secondActivity=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @After
    public void tearDown() throws Exception{
        mActivity=null;
    }
}
