package tom.chinesesuperleague;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Dialog;
import android.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Button;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import tom.chinesesuperleague.data.StatContract;

public class FormFragment extends android.support.v4.app.DialogFragment {


    int mNum;

    private static final String[] STAT_COLUMNS = {

            StatContract.StatEntry.COLUMN_TEAM,
            StatContract.StatEntry.COLUMN_CNAME,
            StatContract.StatEntry.COLUMN_GOAL,
            StatContract.StatEntry.COLUMN_PLAYER,
            StatContract.StatEntry.COLUMN_RATING,
            StatContract.StatEntry._ID
    };

    public static final int STAT_LOADER_MAIN = 0;

    public static final int COL_TEAM = 0;
    public static final int COL_CNAME = 1;
    public static final int COL_GOAL = 2;
    public static final int COL_PLAYER = 3;
    public static final int COL_RATING = 4;

    public FormFragment(){

    }

    /**
     * Create a new instance of MyDialogFragment, providing "num"
     * as an argument.
     */
    static FormFragment newInstance() {
        FormFragment f = new FormFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.form_fragment, container, false);

        GraphView graph = (GraphView) v.findViewById(R.id.graph);
        double [] recent_rating = new double[10];//TODO:Temp variable. Delete later.

        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, recent_rating[9]),
                new DataPoint(1, recent_rating[8]),
                new DataPoint(2, recent_rating[7]),
                new DataPoint(3, recent_rating[6]),
                new DataPoint(4, recent_rating[5]),
                new DataPoint(5, recent_rating[4]),
                new DataPoint(6, recent_rating[3]),
                new DataPoint(7, recent_rating[2]),
                new DataPoint(8, recent_rating[1]),
                new DataPoint(9, recent_rating[0]),
        });

        LineGraphSeries<DataPoint> series_line = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, recent_rating[9]),
                new DataPoint(1, recent_rating[8]),
                new DataPoint(2, recent_rating[7]),
                new DataPoint(3, recent_rating[6]),
                new DataPoint(4, recent_rating[5]),
                new DataPoint(5, recent_rating[4]),
                new DataPoint(6, recent_rating[3]),
                new DataPoint(7, recent_rating[2]),
                new DataPoint(8, recent_rating[1]),
                new DataPoint(9, recent_rating[0]),
        });

        series_line.setThickness(3);
        series.setColor(Color.BLUE);
        series.setSize(10);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Series1: On Data Point clicked: "+ dataPoint.getY(), Toast.LENGTH_SHORT).show();
            }
        });

        graph.addSeries(series);
        graph.addSeries(series_line);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(-1);
        graph.getViewport().setMaxX(10);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(4);
        graph.getViewport().setMaxY(12);


        return v;
    }


}
