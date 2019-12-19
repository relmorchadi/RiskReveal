package com.scor.rr.domain.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TreeNode<T> implements Serializable {
    private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();
    @JsonIgnore
    private TreeNode<T> parent = null;
    private T data = null;
    private boolean selected;
    private boolean partialSelected;

    public TreeNode(T data) {
        this.data = data;
    }

    public TreeNode(T data, TreeNode<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setParent(TreeNode<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public void addChild(T data) {
        TreeNode<T> child = new TreeNode<T>(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(TreeNode<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children == null || this.children.isEmpty();
    }

    public void clearParent() {
        this.parent = null;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean value, boolean propagate) {
        if (propagate) {
            this.setSelected(value);
        } else {
            this.selected = value;
        }
    }

    public void setSelected(boolean value) {
        this.selected = value;
        this.partialSelected = false;
        if (!this.isLeaf()) {
            Iterator var2 = this.children.iterator();

            while(var2.hasNext()) {
                TreeNode child = (TreeNode)var2.next();
                child.propagateSelectionDown(value);
            }
        }

        if (this.getParent() != null) {
            this.getParent().propagateSelectionUp();
        }

    }

    protected void propagateSelectionDown(boolean value) {
        this.selected = value;
        this.partialSelected = false;
        Iterator var2 = this.children.iterator();

        while(var2.hasNext()) {
            TreeNode child = (TreeNode)var2.next();
            child.propagateSelectionDown(value);
        }

    }

    protected void propagateSelectionUp() {
        boolean allChildrenSelected = true;
        this.partialSelected = false;

        for(int i = 0; i < this.getChildren().size(); ++i) {
            TreeNode childNode = (TreeNode)this.getChildren().get(i);
            boolean childSelected = childNode.isSelected();
            boolean childPartialSelected = childNode.isPartialSelected();
            allChildrenSelected = allChildrenSelected && childSelected;
            this.partialSelected = this.partialSelected || childSelected || childPartialSelected;
        }

        this.selected = allChildrenSelected;
        if (allChildrenSelected) {
            this.setPartialSelected(false);
        }

        if (this.getParent() != null) {
            this.getParent().propagateSelectionUp();
        }

    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + (this.data == null ? 0 : this.data.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            TreeNode other = (TreeNode)obj;
            if (this.data == null) {
                if (other.getData() != null) {
                    return false;
                }
            } else if (!this.data.equals(other.getData())) {
                return false;
            }

            return true;
        }
    }

    public boolean isPartialSelected() {
        return this.partialSelected;
    }

    public void setPartialSelected(boolean value) {
        this.partialSelected = value;
    }
}
