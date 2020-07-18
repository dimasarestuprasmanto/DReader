package com.dimasarp.dreader;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomFilter extends BottomSheetDialogFragment {
    private BottomSheetListener mlistener;
    CheckBox action,adventure,comedy,drama,ecchi,fantasy,harem,isekai,josei,magic,material_arts,mecha,mystery,psychologi,romance,school,sci_fi,seinen,shoujo,shounen,slice_of_life,sport,supranatural,tragedy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.buttom_filter_layout,container,false);

        Button buttonfilteraktif = v.findViewById(R.id.filter_test);
        View hide = v.findViewById(R.id.hide_filter);
        final RadioButton all = v.findViewById(R.id.all);
        final RadioButton ongoing = v.findViewById(R.id.ongoing);
        final RadioButton complete = v.findViewById(R.id.complete);

        action = v.findViewById(R.id.action);
        adventure = v.findViewById(R.id.adventure);
        comedy = v.findViewById(R.id.comedy);
        drama = v.findViewById(R.id.drama);
        ecchi = v.findViewById(R.id.ecchi);
        fantasy = v.findViewById(R.id.fantasy);
        harem = v.findViewById(R.id.harem);
        isekai = v.findViewById(R.id.isekai);
        josei = v.findViewById(R.id.josei);
        magic = v.findViewById(R.id.magic);
        material_arts = v.findViewById(R.id.material_arts);
        mecha = v.findViewById(R.id.mecha);
        mystery = v.findViewById(R.id.mystery);
        psychologi = v.findViewById(R.id.psychologi);
        romance = v.findViewById(R.id.romance);
        school = v.findViewById(R.id.school);
        sci_fi = v.findViewById(R.id.sci_fi);
        seinen = v.findViewById(R.id.seinen);
        shoujo = v.findViewById(R.id.shoujo);
        shounen = v.findViewById(R.id.shounen);
        slice_of_life = v.findViewById(R.id.slice_of_life);
        sport = v.findViewById(R.id.sport);
        supranatural = v.findViewById(R.id.supranatural);
        tragedy = v.findViewById(R.id.tragedy);

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        buttonfilteraktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder x = new StringBuilder();
                if (all.isChecked())
                    x.append("");
                if (ongoing.isChecked())
                    x.append("Ongoing");
                if (complete.isChecked())
                    x.append("Complete");
                String status = x.toString();

                StringBuilder y = new StringBuilder();
                if (action.isChecked())
                    y.append("action,");
                if (adventure.isChecked())
                    y.append("adventure,");
                if(comedy.isChecked())
                    y.append("comedy,");
                if (drama.isChecked())
                    y.append("drama,");
                if (ecchi.isChecked())
                    y.append("ecchi,");
                if (fantasy.isChecked())
                    y.append("fantasy,");
                if (harem.isChecked())
                    y.append("harem,");
                if(isekai.isChecked())
                    y.append("isekai,");
                if(josei.isChecked())
                    y.append("josei,");
                if(magic.isChecked())
                    y.append("magic,");
                if(material_arts.isChecked())
                    y.append("material_arts,");
                if (mecha.isChecked())
                    y.append("mecha,");
                if (mystery.isChecked())
                    y.append("mystery,");
                if (psychologi.isChecked())
                    y.append("psychologi,");
                if (romance.isChecked())
                    y.append("romance,");
                if (school.isChecked())
                    y.append("school,");
                if (sci_fi.isChecked())
                    y.append("sci-fi,");
                if (seinen.isChecked())
                    y.append("seinen,");
                if (shoujo.isChecked())
                    y.append("shoujo,");
                if (shounen.isChecked())
                    y.append("shounen,");
                if (slice_of_life.isChecked())
                    y.append("slice_of_life,");
                if (sport.isChecked())
                    y.append("sport,");
                if (supranatural.isChecked())
                    y.append("supranatural,");
                if (tragedy.isChecked())
                    y.append("tragedy,");

                if (y.length() >= 1){
                    y.setLength(y.length()-1);
                }

                String category = y.toString();
                if (y.length() == 0){
                    Toast.makeText(getContext(),"Genres Harus Dipilih",Toast.LENGTH_SHORT).show();
                }else {
                    mlistener.onButtonClicked(status,category);
                    dismiss();
                }

            }
        });
        return v;
    }
    public interface BottomSheetListener{
        void onButtonClicked(String status,String category);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mlistener = (BottomSheetListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
            + "Must Implement Bottom Listener");
        }

    }
}
