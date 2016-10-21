# android treeview
android tree
Use ListView,ExpandableListView,RecyclerView to achieve

![image](https://github.com/ljl160139/TreeView/blob/master/images/prew-1.png)
![image](https://github.com/ljl160139/TreeView/blob/master/images/prew--2.png)


Use :
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
