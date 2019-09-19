package cn.mr.wang.wjprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private float progressNumber = 0;
    private float progressStraightLineNumber = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WJProgress wjProgress = findViewById(R.id.progress);
        final WJProgress progress_straightLine = findViewById(R.id.progress_straightLine);
        progressNumber=wjProgress.getProgress();
        progressStraightLineNumber = progress_straightLine.getProgress();
        Button add_btn = findViewById(R.id.add_btn);
        Button reset_btn=findViewById(R.id.reset_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressNumber += 10;
                wjProgress.setProgress(progressNumber).startAnimation();
                progressStraightLineNumber+=10;
                progress_straightLine.setProgress(progressStraightLineNumber).startAnimation();
            }
        });
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressNumber = 0;
                wjProgress.setProgress(progressNumber).startAnimation();
                progressStraightLineNumber=0;
                progress_straightLine.setProgress(progressStraightLineNumber).startAnimation();

            }
        });
    }
}
