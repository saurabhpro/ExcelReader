package jxcel;

import jxcel.model.EmpBiometricDetails;

import java.util.Comparator;

/**
 * Created by kumars on 2/12/2016.
 */
public class EmpIDComparator implements Comparator<EmpBiometricDetails> {


    @Override
    public int compare(EmpBiometricDetails o1, EmpBiometricDetails o2) {
        return o1.empId.compareTo(o2.empId);
    }
}
