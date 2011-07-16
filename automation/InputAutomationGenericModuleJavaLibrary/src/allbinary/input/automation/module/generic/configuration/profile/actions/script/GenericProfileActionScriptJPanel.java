/*
* AllBinary Open License Version 1
* Copyright (c) 2011 AllBinary
* 
* By agreeing to this license you and any business entity you represent are
* legally bound to the AllBinary Open License Version 1 legal agreement.
* 
* You may obtain the AllBinary Open License Version 1 legal agreement from
* AllBinary or the root directory of AllBinary's AllBinary Platform repository.
* 
* Created By: Travis Berthelot
* 
*/
package allbinary.input.automation.module.generic.configuration.profile.actions.script;


import abcs.logic.communication.log.Log;
import abcs.logic.communication.log.LogUtil;
import allbinary.input.automation.module.generic.configuration.profile.actions.script.condition.CustomTreeNodeInterface;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author  USER
 */
public class GenericProfileActionScriptJPanel
    extends javax.swing.JPanel
{
    private DefaultMutableTreeNode defaultMutableTreeNode =
        new DefaultMutableTreeNode("Root");
    
    private GenericProfileActionScript genericProfileActionScript;
    
    /** Creates new form GenericProfileActionScriptJPanel */
    public GenericProfileActionScriptJPanel()
    {
        LogUtil.put(new Log("Start", this, "Constructor"));
        
        initComponents();
    }
    
    public void updateJTree()
    {
        DefaultTreeModel defaultTreeModel =
            new DefaultTreeModel(this.getGenericProfileActionScript());
        
        this.getActionScriptJTree().setModel(defaultTreeModel);
        
        this.repaint();
        
        LogUtil.put(new Log("Updated UI", this, "updateJTree"));
    }
    
    public GenericProfileActionScript getGenericProfileActionScript()
    {
        return genericProfileActionScript;
    }
    
    public void setGenericProfileActionScript(GenericProfileActionScript genericProfileActionScript)
    {
        this.genericProfileActionScript = genericProfileActionScript;
    }

    public javax.swing.JTree getActionScriptJTree()
    {
        return actionScriptJTree;
    }

    public void setActionScriptJTree(javax.swing.JTree actionScriptJTree)
    {
        this.actionScriptJTree = actionScriptJTree;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        actionScriptJTree = new javax.swing.JTree();

        setLayout(new java.awt.GridLayout(1, 0));

        actionScriptJTree.setPreferredSize(new java.awt.Dimension(500, 500));
        actionScriptJTree.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                actionScriptJTreeMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                actionScriptJTreeMouseReleased(evt);
            }
        });

        jScrollPane1.setViewportView(actionScriptJTree);

        add(jScrollPane1);

    }// </editor-fold>//GEN-END:initComponents
    
    private void actionScriptJTreeMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_actionScriptJTreeMouseReleased
    {//GEN-HEADEREND:event_actionScriptJTreeMouseReleased
        int button = evt.getButton();
        if(button == MouseEvent.BUTTON3)
        {
            TreePath treePath = this.getActionScriptJTree().getSelectionPath();
            
            if(treePath != null)
            {
                Object object = treePath.getLastPathComponent();
                
                if(object instanceof CustomTreeNodeInterface)
                {
                    CustomTreeNodeInterface customTreeNodeInterface =
                        (CustomTreeNodeInterface) object;
                    
                    JPopupMenu jPopupMenu = customTreeNodeInterface.getJPopupMenu();
                    jPopupMenu.show((JComponent) evt.getSource(), evt.getX(), evt.getY());
                }
            }
        }
    }//GEN-LAST:event_actionScriptJTreeMouseReleased
    
    private void actionScriptJTreeMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_actionScriptJTreeMouseClicked
    {//GEN-HEADEREND:event_actionScriptJTreeMouseClicked
// TODO add your handling code here:
    }//GEN-LAST:event_actionScriptJTreeMouseClicked
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree actionScriptJTree;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
}
