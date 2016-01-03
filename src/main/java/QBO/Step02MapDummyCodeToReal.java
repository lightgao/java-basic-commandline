package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step02MapDummyCodeToReal extends Step {
    private String dummyCode;

    private String realMachineSN;

    public Step02MapDummyCodeToReal(Map<String, Object> context, String dummyCode) {
        super(context);
        this.dummyCode = dummyCode;
    }

    @Override
    protected Step99Fail setup() {
        realMachineSN = null;

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        realMachineSN = MapDummyCodeToReal(this.dummyCode);
        return true;
    }

    @Override
    protected Step decideNext() {
        if (realMachineSN == null) {
            return new Step99Fail(context, FailType.BARCODE_MAPPING_FAIL);
        } else {
            context.put("MachineSN", realMachineSN);
            return new Step03ReadMCUSerialNumber(context);
        }
    }

    private String MapDummyCodeToReal(String barcode) {
        //TODO
        return barcode;
    }
}
