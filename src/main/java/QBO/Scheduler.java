package QBO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gl on 12/31/15.
 */
public class Scheduler {

    private Step currentStep;
    private List steps = new ArrayList<Step>();

    private Map context = new HashMap<String, Object>();

    public void reset() {
        context.clear();
        currentStep = null;
        steps.clear();
    }

    public void start() {
        reset();
        currentStep = new Step01ConnectAndScanBarcode(context);
        steps.add(currentStep);
    }

    public boolean run(Map<EventType, Object> events) {
        if (currentStep == null) { return true; }

        if( currentStep.run(events) ) {
            currentStep = currentStep.getNext();
            steps.add(currentStep);
        }
        return false;
    }


}
