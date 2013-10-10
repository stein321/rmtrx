package cap.mizzou.rmtrx.app.ui;

import cap.mizzou.rmtrx.app.ui.HomeFragment.RmtrxActivity;

/**
 * An activity that will be posted when the user would like to visit a {@link RmtrxActivity}
 */
final class ActivitySelectedEvent {

    final RmtrxActivity activity;

    ActivitySelectedEvent(RmtrxActivity activity) {
        this.activity = activity;
    }
}
