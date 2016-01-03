package QBO;

import java.util.Map;

/**
 * Created by gl on 12/31/15.
 */
public abstract class Step {

    //
    private enum Status {
        NOTSTART, RUNNING, STOPPED
    }
    private Status status = Status.NOTSTART;



    protected Map<String, Object> context;

    public Step(Map<String, Object> context) {
        this.context = context;
    }


    private Step next;

    public Step getNext() {
        return next;
    }




    abstract protected Step99Fail setup();
    abstract protected boolean loop(Map<EventType, Object> events);
    abstract protected Step decideNext();

    public boolean run(Map<EventType, Object> events) {

        if (status == Status.NOTSTART) {
            Step99Fail fail = setup();
            if(fail == null) {
                status = Status.RUNNING;
            } else {
                next = fail;
                status = Status.STOPPED;
            }
        }

        if (status == Status.RUNNING) {
            if (loop(events)) {
                next = decideNext();
                status = Status.STOPPED;
            }
        }

        boolean stopRun = status==Status.STOPPED ? true : false;
        return stopRun;
    }

}
