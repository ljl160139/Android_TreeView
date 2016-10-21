package com.kl.tree.treeview.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kl.tree.treeview.R;

/**
 * Created by kl on 16-10-12.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTvLvTree;
    private TextView mTvExplvTree;
    private TextView mTvRecvTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvLvTree= (TextView) findViewById(R.id.tv_lv_tree);
        mTvExplvTree= (TextView) findViewById(R.id.tv_explv_tree);
        mTvRecvTree= (TextView) findViewById(R.id.tv_recv_tree);
        mTvLvTree.setOnClickListener(this);
        mTvExplvTree.setOnClickListener(this);
        mTvRecvTree.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==mTvLvTree){
            Intent intent=new Intent(this,LvTreeActivity.class);
            startActivity(intent);
        }else if(v==mTvExplvTree){
            Intent intent=new Intent(this,ExpdLvActivity.class);
            startActivity(intent);
        }else if(v==mTvRecvTree){
            Intent intent=new Intent(this,RecvTreeActivity.class);
            startActivity(intent);
        }
    }
}
