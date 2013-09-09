package cap.mizzou.rmtrx.app;

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
                HomeFragment.class
        })
public final class rmtrxModule {

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }
}
