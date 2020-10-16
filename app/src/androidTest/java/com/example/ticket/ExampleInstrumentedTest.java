package com.example.ticket;

import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public final ActivityTestRule<StartActivity> rule = new ActivityTestRule<>(StartActivity.class, true);

    @Rule
    public ActivityTestRule<Ticketing> rule2  = new  ActivityTestRule<Ticketing>(Ticketing.class)
    {
        @Override
        protected Intent getActivityIntent() {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("id", "998353719V");
            return intent;
        }
    };

    @Rule
    public ActivityTestRule<TicketPurchaseActivity> rule3  = new  ActivityTestRule<TicketPurchaseActivity>(TicketPurchaseActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.putExtra("details", "hhhhhhhhh!90");
            intent.putExtra("un", "998353719V");
            return intent;
        }
    };


    @Rule
    public final ActivityTestRule<RegisterAct> rule4 = new ActivityTestRule<>(RegisterAct.class, true);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.ticket", appContext.getPackageName());
    }

    //Detecting whether fields are empty

    //class StartActivity
    @Test
    public void validateFieldsStartActivity(){
        StartActivity activity = rule.getActivity();
        View viewById1 = activity.findViewById(R.id.busname);
        assertThat(viewById1,notNullValue());

        View viewById2 = activity.findViewById(R.id.busname);
        assertThat(viewById2,notNullValue());

        //View viewById3 = activity.findViewById(R.id.bussubmit);



    }
    //register Activity
    @Test
    public void validateFieldsRegisterActivity(){
        StartActivity activity = rule.getActivity();
        View viewById1 = activity.findViewById(R.id.unid_Reg);
        assertThat(viewById1,notNullValue());

        View viewById2 = activity.findViewById(R.id.pw_Reg);
        assertThat(viewById2,notNullValue());

        View viewById3 = activity.findViewById(R.id.email_Reg);
        assertThat(viewById3,notNullValue());

        View viewById4 = activity.findViewById(R.id.address_Reg);
        assertThat(viewById4,notNullValue());

        View viewById5 = activity.findViewById(R.id.cpw_Reg);
        assertThat(viewById5,notNullValue());

        //View viewById3 = activity.findViewById(R.id.bussubmit);



    }
    //class Ticketing
    @Test
    public void ensureIntentDataIsDisplayed() throws Exception {
        Ticketing activity = rule2.getActivity();

        View viewById = activity.findViewById(R.id.unid);
        View viewById1 = activity.findViewById(R.id.emailid);
        View viewById2 = activity.findViewById(R.id.creditsId);

        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));
        TextView textView = (TextView) viewById;
        assertThat(textView.getText().toString(),is("998353719V"));

        assertThat(viewById1,notNullValue());
        assertThat(viewById1, instanceOf(TextView.class));
        TextView textView1 = (TextView) viewById1;
        assertThat(textView1.getText().toString(),is("shashi@gmail.com"));

        assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(TextView.class));
        TextView textView2 = (TextView) viewById2;
        assertThat(textView2.getText().toString(),is("200"));



    }
    //class TicketPurchaseActivity
    @Test
    public void ensureIntentDataIsDisplayedInTicketing2() throws Exception {
        TicketPurchaseActivity activity = rule3.getActivity();

        View viewById = activity.findViewById(R.id.unid);
        View viewById1 = activity.findViewById(R.id.emailid);
        View viewById2 = activity.findViewById(R.id.creditsId);

        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));
        TextView textView = (TextView) viewById;
        assertThat(textView.getText().toString(),is("998353719V"));

        assertThat(viewById1,notNullValue());
        assertThat(viewById1, instanceOf(TextView.class));
        TextView textView1 = (TextView) viewById1;
        assertThat(textView1.getText().toString(),is("shashi@gmail.com"));

        assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(TextView.class));
        TextView textView2 = (TextView) viewById2;
        assertThat(textView2.getText().toString(),is("200"));



    }
}
