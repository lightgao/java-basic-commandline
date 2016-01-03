package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step03ReadMCUSerialNumber extends Step {
    private String mcuSN1;
    private String mcuSN2;

    public Step03ReadMCUSerialNumber(Map<String, Object> context) {
        super(context);
    }

    @Override
    protected Step99Fail setup() {
        if (!Utils.isJLinkConnected()) {
            return new Step99Fail(context, FailType.JLINK_NOT_CONNECT);
        }

        mcuSN1 = null;
        mcuSN2 = null;

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        mcuSN1 = readMCUSN1();
        mcuSN2 = readMCUSN2();
        return true;
    }

    @Override
    protected Step decideNext() {
        if (mcuSN1 == null || mcuSN2 == null) {
            return new Step99Fail(context, FailType.READ_MCU_SN_FAIL);
        } else {
            context.put("MCUSN1", mcuSN1);
            context.put("MCUSN2", mcuSN2);

            return new Step04CallFirmwareGenerator(context);
        }
    }

    private String readMCUSN1() {
        //TODO
        return "0123ABCD";
    }

    private String readMCUSN2() {
        //TODO
        return "4567EFGH";
    }

}
