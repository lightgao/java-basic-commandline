package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step06SaveToProductionDatabase extends Step {
    private String machineSN;

    private String mcuSN1;
    private String mcuSN2;

    private String uuid;
    private String bonjourId;
    private String certificate;
    private String key;
    private String firmwareVersion;
    private String firmwareBin;

    private boolean isSaved;

    public Step06SaveToProductionDatabase(Map<String, Object> context) {
        super(context);
    }

    @Override
    protected Step99Fail setup() {

        try {
            machineSN = (String) context.get("MachineSN");

            mcuSN1 = (String) context.get("MCUSN1");
            mcuSN2 = (String) context.get("MCUSN2");

            uuid = (String) context.get("UUID");
            bonjourId = (String) context.get("BonjourId");

            certificate = (String) context.get("Certificate");
            key = (String) context.get("Key");

            firmwareVersion = (String) context.get("FirmwareVersion");
            firmwareBin = (String) context.get("FirmwareBin");
        } catch (Exception e) {
            return new Step99Fail(context, FailType.REQUIRED_PARAMS_NOT_EXIST);
        }

        isSaved = false;

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        isSaved = saveToProductionDatabase();
        return true;
    }

    @Override
    protected Step decideNext() {
        if (isSaved) {
            return new Step07DisconnectAndClean(context);
        }
        return new Step99Fail(context, FailType.SAVE_TO_PROD_DB_FAIL);
    }


    private boolean saveToProductionDatabase() {
        //TODO
        return true;
    }
}
