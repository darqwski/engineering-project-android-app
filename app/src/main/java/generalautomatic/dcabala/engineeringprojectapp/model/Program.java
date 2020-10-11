package generalautomatic.dcabala.engineeringprojectapp.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Program implements IModel {
    private String programId;
    private String programDescription;

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
    public Program(){

    }

    public Program(String programId, String programDescription) {
        this.programId = programId;
        this.programDescription = programDescription;
    }

    @Override
    public void getFromJSON(JSONObject jsonObject) {
        try {
            setProgramDescription(jsonObject.getString("programDescription"));
            setProgramId(jsonObject.getString("programId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
