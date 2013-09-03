package cap.mizzou.rmtrx.core;

import android.app.Application;
import dagger.ObjectGraph;

/**
 * @author Steven Rodenberg
 */
public abstract class BaseApplication extends Application implements ObjectGraphProvider {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = createObjectGraph();
        objectGraph.injectStatics();
    }

    @Override
    public final ObjectGraph objectGraph() {
        return objectGraph;
    }


    protected abstract ObjectGraph createObjectGraph();
}
