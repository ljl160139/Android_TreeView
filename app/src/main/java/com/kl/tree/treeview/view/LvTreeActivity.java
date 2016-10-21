package com.kl.tree.treeview.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kl.tree.library.TreeListView;
import com.kl.tree.library.domain.Node;
import com.kl.tree.treeview.R;
import com.kl.tree.treeview.view.adapter.ContactAdapter;
import com.kl.tree.treeview.data.ContactDao;
import com.kl.tree.treeview.data.module.ContactDir;
import com.kl.tree.treeview.data.module.ContactLeaf;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by kl on 16-10-12.
 */
public class LvTreeActivity extends AppCompatActivity implements TreeListView.ItemClickListener<ContactDir,ContactLeaf>{

    private List<Node<ContactDir,ContactLeaf>> mDatas = new LinkedList<Node<ContactDir, ContactLeaf>>();
    private TreeListView mLv;
    private ContactAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_tree_activity);

        initData();
        mLv = (TreeListView) findViewById(R.id.lv_content);
        mLv.registListener(this);
        mAdapter = new ContactAdapter(this, mDatas);
        mLv.setAdapter(mAdapter);

    }



    public void initData(){
        List<ContactDir> dirs= ContactDao.getInstance().getRootDir();
        for(int i=0;i<dirs.size();i++){
            Node node=new Node(dirs.get(i));
            mDatas.add(node);
        }
    }


    @Override
    public void onDirClick(int position,Node<ContactDir, ContactLeaf> node) {
        if(node.getDir().isExpand()){
            mAdapter.close(node);
        }else{
            List<Node<ContactDir,ContactLeaf>> nodeList=new LinkedList<Node<ContactDir, ContactLeaf>>();
            List<ContactLeaf> leafs=ContactDao.getInstance().getContactsByDeptId(node.getDir().getId());
            for(int i=0;i<leafs.size();i++){
                Node newNode=new Node(leafs.get(i),node);
                nodeList.add(newNode);
            }
            List<ContactDir> dirs=ContactDao.getInstance().getDirByDeptId(node.getDir().getId());
            for(int i=0;i<dirs.size();i++){
                Node newNode=new Node(dirs.get(i),node);
                nodeList.add(newNode);
            }
            mAdapter.addNodes(nodeList,node);
        }
    }

    @Override
    public void onLeafClick(int position, Node<ContactDir, ContactLeaf> node) {
        Toast.makeText(this,node.getLeaf().getName(),Toast.LENGTH_SHORT).show();
    }
}
