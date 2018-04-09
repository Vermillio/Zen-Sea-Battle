package com.camisado.myapplication;

/**
 * Created by morga on 04.04.2018.
 */

public class index {
    public int i;
    public int j;

    public index(int i, int j) {
        if (i >= 0)
            this.i = i;
        else this.i = 0;
        if (j>=0)
            this.j = j;
        else this.j = 0;
    }

    public void normalize(int lb, int rb) {
        if (i<lb)
            i=lb;
        else if (i>rb)
            i=rb;
        if (j<lb)
            j=lb;
        else if (j>rb)
            j=rb;
    }

    public void normalize(index a, int range)
    {
        if (this.i<a.i-range)
            this.i=a.i-range;
        else if (this.i>a.i+range)
            this.i=a.i+range;
        if (this.j<a.j-range)
            this.j=a.j-range;
        else if (this.j>a.j+range)
            this.j=a.j+range;
    }

    public boolean equal(index ind){
        return i==ind.i && j == ind.j;
    };

    public boolean onSameLineWith(index a){
        return (a.i==this.i ||
                    a.j==this.j);
    };

    void adjustSameLine(index a)
    {
        if (Math.abs(this.i-a.i)<=Math.abs(this.j-a.j))
            this.i=a.i;//align horizontal
        else
            this.j=a.j;//align vertical
    }
}
