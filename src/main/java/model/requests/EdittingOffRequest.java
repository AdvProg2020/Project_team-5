package model.requests;

import java.util.HashMap;

public class EdittingOffRequest extends Request {
    private int offId;
    private HashMap<String, String> edittedFields;

    public EdittingOffRequest(int offId, HashMap<String, String> edittedFields) {
        this.offId = offId;
        this.edittedFields = edittedFields;
    }

    @Override
    public void acceptRequest() {

    }

    @Override
    public String toString() {
        return "EdittingOffRequest :\n" +
                 offId +
                ", edittedFields=" + edittedFields +
                '}';
    }
}
