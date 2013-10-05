package cap.mizzou.rmtrx.app;

import cap.mizzou.rmtrx.app.Login.CreateResidenceActivity;
import cap.mizzou.rmtrx.app.Login.JoinResidenceActivity;
import cap.mizzou.rmtrx.app.Login.LoginActivity;
import cap.mizzou.rmtrx.app.Login.RegistrationActivity;
import cap.mizzou.rmtrx.app.Messages.MessagesActivity;
import cap.mizzou.rmtrx.app.SQLite.Comment;
import cap.mizzou.rmtrx.app.SQLite.CommentsDataSource;
import cap.mizzou.rmtrx.app.SQLite.MySQLiteHelper;
import cap.mizzou.rmtrx.app.SQLite.TestDatabaseActivity;
import cap.mizzou.rmtrx.app.grocery.GroceryActivity;
import cap.mizzou.rmtrx.app.settings.SettingsActivity;
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
                LoginActivity.class,
                DisplayMessageActivity.class,
                Comment.class,
                CommentsDataSource.class,
                MySQLiteHelper.class,
                TestDatabaseActivity.class,
                RegistrationActivity.class,
                CreateResidenceActivity.class,
                JoinResidenceActivity.class


        })
public final class rmtrxModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
