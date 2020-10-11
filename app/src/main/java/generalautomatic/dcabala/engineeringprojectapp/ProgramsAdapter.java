package generalautomatic.dcabala.engineeringprojectapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import androidx.recyclerview.widget.RecyclerView;
import generalautomatic.dcabala.engineeringprojectapp.model.Program;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.ViewHolder> {

    private final Context context;
    private Program[] data;
    private LayoutInflater inflater;
    private int layout;

    public ProgramsAdapter(Context context, Program [] data, int layout) {
        this.inflater = LayoutInflater.from(context);
        this.data = data;
        this.layout = layout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Program program = data[(position)];
        ((TextView)holder.programName).setText(program.getProgramDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Request(context, new ResponseAction() {
                    @Override
                    public void action(int responseCode, String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Błąd danych serwera, prosimy spróbować póóźniej", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute("POST","/API/program/","program="+program.getProgramId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View programName;

        ViewHolder(View itemView) {
            super(itemView);
            programName = itemView.findViewById(R.id.programName);
        }
    }

    Program getItem(int id) {
        return data[id];
    }
}