package de.dezibel.control;

import java.util.Comparator;

/**
 * MergeSort sorts comparable data with the mergesort
 * 
 * @author Tobias
 * @param <E> The type of the object to sort
 */
public class MergeSort <E>
{ 
    /**
     * Sorts the given array with the given Comparator
     * @param a The array
     * @param comp The comparator 
     */
    public void sort(E[] a, Comparator<E> comp)
    {
        mergeSortIntern(a, 0, a.length, (E[])new Object[a.length], comp); //start recursion
    }
    
    /**
     * Sortiert ein Teilfeld des Übergebenen Felds mit dem Mergesort
     * @param a array to sort
     * @param b start of the array
     * @param e end of the array
     * @param tmp array to merge in
     * @param comp the comparator
     */
    private void mergeSortIntern(E[] a, int b, int e, E[] tmp, Comparator<E> comp)
    {
        if(e-b > 1) {
            int m = (e+b)/2; //search for middle
            mergeSortIntern(a,b,m,tmp,comp); //sort array part
            mergeSortIntern(a,m,e,tmp,comp); //sort array part
            merge(a,b,m,e,tmp,comp);        //merge parts
        }
    }
    
    /**
     * 
     * @param a Array to sort
     * @param b upper border (inclusive)
     * @param m upper border of the first array part (exclusive) and 
     *          lower border of the second array part (inclusive)
     * @param e upper border of the second array part (exclusive)
     * @param tmp array to merge in
     * @param comp the comparator
     */
    private void merge(E[] a, int b, int m, int e, E[] tmp, Comparator<E> comp)
    {        
        //Index of the array parts
        int i=b; //first array part
        int j=m; //second array part
        
        for(int k=b; k<e; k++) {
            if( !(i<m) || ((j<e) && (comp.compare(a[i],a[j])>0))) {
                tmp[k]=a[j];
                j++;
            }
            else {
                tmp[k]=a[i];
                i++;
            }
        }
        System.arraycopy(tmp,b,a,b,(e-b)); //copy tmp to a
        
    }
}
