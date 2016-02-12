package jxcel;

import jxcel.model.EmpDetails;

import java.util.Comparator;

/**
 * Created by kumars on 2/12/2016.
 */
public class EmpIDComparator implements Comparator<EmpDetails> {


    @Override
    public int compare(EmpDetails o1, EmpDetails o2) {
        return o1.empId.compareTo(o2.empId);
    }
}
