package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step04CallFirmwareGenerator extends Step {
    private String mcuSN1;
    private String mcuSN2;
    private String machineSN;

    private GetFirmwareResponse response;

    public Step04CallFirmwareGenerator(Map<String, Object> context) {
        super(context);
    }

    @Override
    protected Step99Fail setup() {
        try {
            machineSN = (String) context.get("MachineSN");
            mcuSN1 = (String) context.get("MCUSN1");
            mcuSN2 = (String) context.get("MCUSN2");
        } catch (Exception e) {
            return new Step99Fail(context, FailType.REQUIRED_PARAMS_NOT_EXIST);
        }

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        response = getFirmware(machineSN, mcuSN1, mcuSN2);
        return true;
    }

    @Override
    protected Step decideNext() {
        if (response == null) {
            return new Step99Fail(context, FailType.CALL_FIRMWARE_GENERATOR_FAIL);
        } else {
            context.put("UUID", response.uuid);
            context.put("BonjourId", response.bonjourId);
            context.put("Certificate", response.certificate);
            context.put("Key", response.key);
            context.put("FirmwareVersion", response.firmwareVersion);
            context.put("FirmwareBin", response.firmwareBin);

            return new Step05BurnFirmware(context);
        }
    }


    private GetFirmwareResponse getFirmware(String machineSN, String mcuSN1, String mcuSN2) {
        //TODO
        GetFirmwareResponse response = new GetFirmwareResponse();
        return response;
    }

    private class GetFirmwareResponse {
        private String uuid;
        private String bonjourId;
        private String certificate;
        private String key;
        private String firmwareVersion;
        private String firmwareBin;
    }
}
