package com.igor.mamba.FilterAdapter;
import android.widget.Filter;

import com.igor.mamba.Dispatch.DriversAdapter;
import com.igor.mamba.Product;
import com.igor.mamba.driverdetail;
import com.igor.mamba.productAdapter;

import java.util.ArrayList;
import java.util.List;

public class FilterDriver extends Filter {
DriversAdapter drveradapter;
List<driverdetail> drdetails;

    public FilterDriver(DriversAdapter drveradapter, List<driverdetail> drdetails){
        this.drveradapter = drveradapter;
        this.drdetails = drdetails;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();

        if(constraint !=null && constraint.length() >0)
        {

            constraint = constraint.toString().toUpperCase();

            List<driverdetail> driverModels = new ArrayList();
            for(int i=0; i<drdetails.size(); i++){
                if(drdetails.get(i).getFullName().toUpperCase().contains(constraint)
                    || drdetails.get(i).getNumberPlate().toUpperCase().contains(constraint))
                {
                    driverModels.add(drdetails.get(i));
                }
            }
            results.count = driverModels.size();
            results.values = driverModels;
        }
        else{
            results.count = drdetails.size();
            results.values = drdetails;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        drveradapter.dyga =(List<driverdetail>) results.values;

        drveradapter.notifyDataSetChanged();
    }
}
