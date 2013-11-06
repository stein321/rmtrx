package cap.mizzou.rmtrx.app;

import cap.mizzou.rmtrx.app.BulletinBoard.BulletinBoardActivity;
import cap.mizzou.rmtrx.app.Finances.FinancesActivity;
import cap.mizzou.rmtrx.app.LogOut.LogOutActivity;
import cap.mizzou.rmtrx.app.Residence.UserInfoActivity;
import cap.mizzou.rmtrx.app.User_setup.CreateResidenceActivity;
import cap.mizzou.rmtrx.app.User_setup.JoinResidenceActivity;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.Messages.MessagesActivity;
import cap.mizzou.rmtrx.app.TestDbActivity.Comment;
import cap.mizzou.rmtrx.app.TestDbActivity.CommentsDataSource;
import cap.mizzou.rmtrx.app.TestDbActivity.MySQLiteHelper;
import cap.mizzou.rmtrx.app.TestDbActivity.TestDatabaseActivity;
import cap.mizzou.rmtrx.app.grocery.GroceryActivity;
import cap.mizzou.rmtrx.app.settings.SettingsActivity;
import cap.mizzou.rmtrx.app.ui.DashboardActivity;
import cap.mizzou.rmtrx.app.ui.HomeActivity;
import cap.mizzou.rmtrx.app.ui.HomeFragment;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author Steven Rodenberg
 */

@SuppressWarnings("MethodMayBeStatic")
@Module(complete = false,
        library = true,
/*        includes = {
                NetworkingModule.class,
                PersistenceModule.class,
                SecurityModule.class,
                SecurityUiModule.class
        },*/
        injects = {
                HomeActivity.class,
                GroceryActivity.class,
                SettingsActivity.class,
                MessagesActivity.class,
                HomeFragment.class,
                DashboardActivity.class,
                Comment.class,
                CommentsDataSource.class,
                MySQLiteHelper.class,
                TestDatabaseActivity.class,
                RegistrationActivity.class,
                CreateResidenceActivity.class,
                JoinResidenceActivity.class,
                LogOutActivity.class,
                UserInfoActivity.class,
                FinancesActivity.class,
                BulletinBoardActivity.class


        })
public final class rmtrxModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

//    @Provides
//    @Singleton
//    SharedPreferences pref() {
//            return getDefaultSharedPreferences();
//    }
    //figure this out
}
