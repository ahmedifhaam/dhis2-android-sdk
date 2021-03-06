package org.hisp.dhis.android.core.datavalue;

import org.hisp.dhis.android.core.D2;
import org.hisp.dhis.android.core.common.D2Factory;
import org.hisp.dhis.android.core.data.database.AbsStoreTestCase;
import org.hisp.dhis.android.core.data.server.RealServerMother;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

public class AggregatedDataCallRealIntegrationShould extends AbsStoreTestCase {
    /**
     * A quick integration test that is probably flaky, but will help with finding bugs related to
     * the
     * metadataSyncCall. It works against the demo server.
     */
    private D2 d2;
    Exception e;

    @Before
    @Override
    public void setUp() throws IOException {
        super.setUp();

        d2 = D2Factory.create(RealServerMother.url, databaseAdapter());
    }


   /* How to extract database from tests:
    edit: AbsStoreTestCase.java (adding database name.)
    DbOpenHelper dbOpenHelper = new DbOpenHelper(InstrumentationRegistry.getTargetContext()
    .getApplicationContext(), "test.db");
    make a debugger break point where desired (after sync complete)

    Then while on the breakpoint :
    Android/platform-tools/adb pull /data/user/0/org.hisp.dhis.android.test/databases/test.db
    test.db

    in datagrip:
    pragma foreign_keys = on;
    pragma foreign_key_check;*/

    //This test is uncommented because technically it is flaky.
    //It depends on a live server to operate and the login is hardcoded here.
    //Uncomment in order to quickly test changes vs a real server, but keep it uncommented after.
    //@Test
    public void response_successful_on_sync_data_once() throws Exception {
        retrofit2.Response response = null;
        d2.logout().call();
        response = d2.logIn("android", "Android123").call();
        assertThat(response.isSuccessful()).isTrue();

        //first metaData sync:
        response = d2.syncMetaData().call();
        assertThat(response.isSuccessful()).isTrue();

        //first dataValues sync:
        response = d2.syncAggregatedData().call();
        assertThat(response.isSuccessful()).isTrue();
    }

    //@Test
    public void response_successful_on_sync_data_value_two_times() throws Exception {
        retrofit2.Response response = null;
        d2.logout().call();
        response = d2.logIn("android", "Android123").call();
        assertThat(response.isSuccessful()).isTrue();

        //first metaData sync:
        response = d2.syncMetaData().call();
        assertThat(response.isSuccessful()).isTrue();

        //first dataValues sync:
        response = d2.syncAggregatedData().call();
        assertThat(response.isSuccessful()).isTrue();

        //second sync:
        response = d2.syncMetaData().call();
        assertThat(response.isSuccessful()).isTrue();

        //second dataValues sync:
        response = d2.syncAggregatedData().call();
        assertThat(response.isSuccessful()).isTrue();
    }

    @Test
    public void stub() {
    }
}
