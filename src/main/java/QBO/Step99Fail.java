package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step99Fail extends Step {
    private FailType type;

    public Step99Fail(Map<String, Object> context, FailType type) {
        super(context);
        this.type = type;
    }

    @Override
    protected Step99Fail setup() {
        return null;
    }

    @Override
    protected boolean loop(Map events) {
        if (events.containsKey(EventType.BUTTON_PRESS)) {
            return true;
        }
        return false;
    }

    @Override
    protected Step decideNext() {
        return null;
    }

    public FailType getType() {
        return type;
    }
}
