package cap.mizzou.rmtrx.development;

import android.content.Context;
import cap.mizzou.rmtrx.app.rmtrxApplication;
import cap.mizzou.rmtrx.app.rmtrxModule;
import cap.mizzou.rmtrx.core.Application;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

/**
 * @author Steven Rodenberg
 */
public final class rmtrxDevelopmentApplication extends rmtrxApplication {


    @Override
    protected ObjectGraph createObjectGraph() {
        return ObjectGraph.create(new DevelopmentApplicationModule(this));
    }

    @SuppressWarnings("MethodMayBeStatic") // @Provides methods cannot be static
    @Module(includes = {
            rmtrxModule.class
    }, injects = {
            rmtrxDevelopmentApplication.class
    })
    public static final class DevelopmentApplicationModule {

        private final Context applicationContext;

        DevelopmentApplicationModule(Context context) {
            applicationContext = context.getApplicationContext();
        }

        @Provides
        @Application
        public Context provideApplicationContext() {
            return applicationContext;
        }

    }
}
