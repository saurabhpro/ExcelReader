package jxcel;

import jxcel.model.BiometricDetails;

import java.util.Comparator;

/**
 * Created by kumars on 2/12/2016.
 */
public class EmpIDComparator implements Comparator<BiometricDetails> {


    @Override
    public int compare(BiometricDetails o1, BiometricDetails o2) {
        return o1.empId.compareTo(o2.empId);
    }
}
