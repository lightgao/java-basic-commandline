package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step05BurnFirmware extends Step {
    private String firmwareBin;

    private BurningStatus burningStatus;

    public Step05BurnFirmware(Map<String, Object> context) {
        super(context);
    }

    @Override
    protected Step99Fail setup() {
        if (!Utils.isJLinkConnected()) {
            return new Step99Fail(context, FailType.JLINK_NOT_CONNECT);
        }

        try {
            firmwareBin = (String) context.get("FirmwareBin");
        } catch (Exception e) {
            return new Step99Fail(context, FailType.REQUIRED_PARAMS_NOT_EXIST);
        }

        burningStatus = BurningStatus.BURNING;

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        burningStatus = burning();
        if (burningStatus==BurningStatus.BURNING) {
            return false;
        }
        return true;
    }

    @Override
    protected Step decideNext() {
        if (burningStatus==BurningStatus.DONE) {
            return new Step06SaveToProductionDatabase(context);
        }
        return new Step99Fail(context, FailType.FIRMWARE_BURNING_IS_INTERRUPTED);
    }


    private enum BurningStatus {
        BURNING, INTERRUPTED, DONE
    }

    private BurningStatus burning() {
        //TODO
        return BurningStatus.DONE;
    }
}
