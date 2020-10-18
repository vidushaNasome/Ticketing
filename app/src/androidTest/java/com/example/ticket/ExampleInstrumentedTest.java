package com.example.ticket;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
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

    //Testing for classes

    //StartActivity
    //Ticketing
    //TicketPurchaseActivity
    //RegisterAct

    //Start Activity Declaring rules
    @Rule
    public final ActivityTestRule<StartActivity> rule = new ActivityTestRule<>(StartActivity.class, true);

    //Ticketing Activity Declaring rules
    @Rule
    public ActivityTestRule<Ticketing> rule2  = new  ActivityTestRule<Ticketing>(Ticketing.class)
    {

        @Override
        protected Intent getActivityIntent() {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(appContext,Ticketing.class);
            intent.putExtra("id", "785634432v");
            return intent;
        }
    };

    //PurchaseTicketActivity Declaring rules
    @Rule
    public ActivityTestRule<TicketPurchaseActivity> rule3  = new  ActivityTestRule<TicketPurchaseActivity>(TicketPurchaseActivity.class)
    {
        @Override
        protected Intent getActivityIntent() {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(appContext,TicketPurchaseActivity.class);
            intent.putExtra("details", "hhhhhhhhh!90");
            intent.putExtra("un", "785634432v");
            return intent;
        }
    };


    //Register Activity Declaring rules
    @Rule
    public final ActivityTestRule<RegisterAct> rule4 = new ActivityTestRule<>(RegisterAct.class, true);

    //Checking Class Functionalities Main Test

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.ticket", appContext.getPackageName());
    }

    //Detecting whether fields are empty

    //class StartActivity
    //Checking Class Functionalities
    @Test
    public void validateFieldsStartActivity(){
        //validaing layout
        StartActivity activity = rule.getActivity();
        View viewById1 = activity.findViewById(R.id.busname);
        assertThat(viewById1,notNullValue());

        View viewById2 = activity.findViewById(R.id.busname);
        assertThat(viewById2,notNullValue());

        //View viewById3 = activity.findViewById(R.id.bussubmit);



    }
    //register Activity
    //Checking Class Functionalities
    @Test
    public void validateFieldsRegisterActivity(){
        StartActivity activity = rule.getActivity();
        //validaing layout
        View viewById1 = activity.findViewById(R.id.unid_Reg);
        assertThat(viewById1,nullValue());

        View viewById2 = activity.findViewById(R.id.pw_Reg);
        assertThat(viewById2,nullValue());

        View viewById3 = activity.findViewById(R.id.email_Reg);
        assertThat(viewById3,nullValue());

        View viewById4 = activity.findViewById(R.id.address_Reg);
        assertThat(viewById4,nullValue());

        View viewById5 = activity.findViewById(R.id.cpw_Reg);
        assertThat(viewById5,nullValue());

        //View viewById3 = activity.findViewById(R.id.bussubmit);



    }
    //class Ticketing
    //Checking Class Functionalities
    @Test
    public void ensureIntentDataIsDisplayed() throws Exception {
        Ticketing activity = rule2.getActivity();
        //rule2.getActivityResult();

        //validaing layout
        View viewById = activity.findViewById(R.id.unid);
        View viewById1 = activity.findViewById(R.id.emailid);
        View viewById2 = activity.findViewById(R.id.creditsId);

        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));
        TextView textView = (TextView) viewById;
        assertThat(textView.getText().toString(),is("785634432v"));

        assertThat(viewById1,notNullValue());
        assertThat(viewById1, instanceOf(TextView.class));
        TextView textView1 = (TextView) viewById1;
        assertThat(textView1.getText().toString(),is("cattom244@gmail.com"));

       /* assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(TextView.class));
        TextView textView2 = (TextView) viewById2;
        assertThat(textView2.getText().toString(),is(""));*/



    }
    @Test
    public void ensureIntentDataIsDisplayed2() throws Exception {
        Ticketing activity = rule2.getActivity();
        //rule2.getActivityResult();

        //validaing layout
        View viewById = activity.findViewById(R.id.unid);
        View viewById1 = activity.findViewById(R.id.emailid);
        View viewById2 = activity.findViewById(R.id.creditsId);

        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));
        TextView textView = (TextView) viewById;
        assertThat(textView.getText().toString(),is("785634432v"));

       /* assertThat(viewById1,notNullValue());
        assertThat(viewById1, instanceOf(TextView.class));
        TextView textView1 = (TextView) viewById1;
        assertThat(textView1.getText().toString(),is(""));

        assertThat(viewById2,notNullValue());
        assertThat(viewById2, instanceOf(TextView.class));
        TextView textView2 = (TextView) viewById2;
        assertThat(textView2.getText().toString(),is(""));*/



    }
    //class TicketPurchaseActivity
    //Checking Class Functionalities
    @Test
    public void ensureIntentDataIsDisplayedInTicketing2() throws Exception {
        TicketPurchaseActivity activity = rule3.getActivity();
        //validaing layout
        View viewById = activity.findViewById(R.id.unid);
        View viewById1 = activity.findViewById(R.id.emailid);
        View viewById2 = activity.findViewById(R.id.creditsId);

        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(TextView.class));
        TextView textView = (TextView) viewById;
        assertThat(textView.getText().toString(),is("785634432v"));

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
