package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step07DisconnectAndClean extends Step {
    private boolean isClean;

    public Step07DisconnectAndClean(Map<String, Object> context) {
        super(context);
    }

    @Override
    protected Step99Fail setup() {
        if (!Utils.isUsbButtonConnected()) {
            return new Step99Fail(context, FailType.USB_BUTTON_NOT_CONNECT);
        }

        isClean = false;

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        isClean = clean();

        if (events.containsKey(EventType.BUTTON_PRESS)) {
            return true;
        }
        return false;
    }

    @Override
    protected Step decideNext() {
        if (isClean == false) {
            return new Step99Fail(context, FailType.CLEAN_FAIL);
        }
        return null;
    }


    private boolean clean() {
        //TODO
        return true;
    }
}
