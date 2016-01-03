package QBO;

import java.util.Map;

/**
 * Created by gl on 1/2/16.
 */
public class Step01ConnectAndScanBarcode extends Step {
    private String dummyCode;

    public Step01ConnectAndScanBarcode(Map<String, Object> context) {
        super(context);
    }

    @Override
    protected Step99Fail setup() {
        if (!Utils.isUsbButtonConnected()) {
            return new Step99Fail(context, FailType.USB_BUTTON_NOT_CONNECT);
        }
        if (!Utils.isBarcodeScannerConnected()) {
            return new Step99Fail(context, FailType.BARCODE_SCANNER_NOT_CONNECT);
        }

        dummyCode = null;

        return null;
    }

    @Override
    protected boolean loop(Map<EventType, Object> events) {
        Object v = events.get(EventType.BARCODE_SCAN);
        if (v != null) {
            dummyCode = (String)v;
        }

        if (dummyCode != null
                && events.containsKey(EventType.BUTTON_PRESS)) {
            return true;
        }

        return false;
    }

    @Override
    protected Step decideNext() {
        if (dummyCode == null) {
            return new Step99Fail(context, FailType.BARCODE_SCAN_FAIL);
        } else {
            return new Step02MapDummyCodeToReal(context, dummyCode);
        }
    }
}
