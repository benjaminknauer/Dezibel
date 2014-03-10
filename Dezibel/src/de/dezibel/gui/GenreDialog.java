package de.dezibel.gui;

import de.dezibel.ErrorCode;
import de.dezibel.data.Database;
import de.dezibel.data.Genre;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;
import static javax.swing.TransferHandler.COPY_OR_MOVE;
import static javax.swing.TransferHandler.MOVE;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * Dialog to create a medium
 *
 * @author Bastian
 */
public class GenreDialog extends JDialog {

    private JTree tree;
    private JScrollPane scrollPane;
    private JButton btnNewGenre;
    private JButton btnEditGenre;

    /**
     * Constructor
     *
     * @param frame the frame to block
     */
    public GenreDialog(JFrame frame) {
        super(frame);
        createComponents();
        createLayout();
    }

    /**
     * Creates the gui components.
     */
    public void createComponents() {
        setTitle("Genre bearbeiten");
        setModal(true);

        // Create genre tree
        final DefaultMutableTreeNode topNode = new DefaultMutableTreeNode(Database.getInstance().getTopGenre());
        HashMap<Genre, DefaultMutableTreeNode> nodes = new HashMap<>();
        nodes.put(Database.getInstance().getTopGenre(), topNode);
        LinkedList<Genre> tmpList = new LinkedList<Genre>();
        tmpList.addAll(Database.getInstance().getTopGenre().getSubGenres());
        DefaultMutableTreeNode tmpNode;
        while (!tmpList.isEmpty()) {
            tmpNode = new DefaultMutableTreeNode(tmpList.getFirst());
            nodes.get(tmpList.getFirst().getSuperGenre()).add(tmpNode);
            nodes.put(tmpList.getFirst(), tmpNode);

            tmpList.addAll(tmpList.getFirst().getSubGenres());
            tmpList.removeFirst();
        }

        tree = new JTree(topNode) {
            @Override
            public String convertValueToText(Object value, boolean selected,
                    boolean expanded, boolean leaf, int row, boolean hasFocus) {
                return ((Genre) ((DefaultMutableTreeNode) value).getUserObject()).getName();
            }
        };
        tree.setDragEnabled(true);
        tree.setDropMode(DropMode.ON_OR_INSERT);
        tree.setTransferHandler(new TreeTransferHandler());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        expandAllNodes(topNode);
        // Create scroll pane
        scrollPane = new JScrollPane(tree);

        // Create buttons
        btnNewGenre = new JButton("Neues Genre");
        btnNewGenre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(GenreDialog.this,
                        "Name:", "Genre erstellen", JOptionPane.QUESTION_MESSAGE);
                if (result != null && !result.isEmpty()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    Genre selectedGenre;
                    if (node == null) {
                        selectedGenre = Database.getInstance().getTopGenre();
                    } else {
                        selectedGenre = (Genre) node.getUserObject();
                    }
                    ErrorCode retCode = Database.getInstance().addGenre(
                            result, selectedGenre);
                    if (retCode == ErrorCode.SUCCESS) {
                        final Genre g = Database.getInstance().getGenres().getLast();
                        topNode.add(new DefaultMutableTreeNode(g));
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        model.reload();
                        expandAllNodes(topNode);
                    }
                }
            }
        });
        btnEditGenre = new JButton("Genre bearbeiten");
        btnEditGenre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(GenreDialog.this,
                        "Name:", "Genre bearbeiten", JOptionPane.QUESTION_MESSAGE);
                if (result != null && !result.isEmpty()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    Genre g = (Genre) node.getUserObject();
                    g.setName(result);
                }
            }
        });
    }

    /**
     * Creates the layout.
     */
    public void createLayout() {
        GroupLayout layout = new GroupLayout(this.getContentPane());
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                .addComponent(scrollPane)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNewGenre)
                        .addComponent(btnEditGenre)
                )
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addGroup(layout.createParallelGroup()
                        .addComponent(btnNewGenre)
                        .addComponent(btnEditGenre)
                )
        );
        this.getContentPane().setLayout(layout);
    }

    /**
     * Expands all nodes of the genre tree.
     */
    private void expandAllNodes(DefaultMutableTreeNode topNode) {
        Enumeration e = topNode.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node
                    = (DefaultMutableTreeNode) e.nextElement();
            if (node.isLeaf()) {
                continue;
            }
            int row = tree.getRowForPath(new TreePath(node.getPath()));
            tree.expandRow(row);
        }
    }

    /**
     * TransferHandler that implements the drag and drop feature of the genre
     * tree.
     */
    class TreeTransferHandler extends TransferHandler {

        DataFlavor nodesFlavor;
        DataFlavor[] flavors = new DataFlavor[1];
        DefaultMutableTreeNode[] nodesToRemove;
        Genre last;

        public TreeTransferHandler() {
            try {
                String mimeType = DataFlavor.javaJVMLocalObjectMimeType
                        + ";class=\""
                        + javax.swing.tree.DefaultMutableTreeNode[].class.getName()
                        + "\"";
                nodesFlavor = new DataFlavor(mimeType);
                flavors[0] = nodesFlavor;
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFound: " + e.getMessage());
            }
        }

        public boolean canImport(TransferHandler.TransferSupport support) {
            last = (Genre) ((DefaultMutableTreeNode) tree.getLastSelectedPathComponent()).getUserObject();
            if (!support.isDrop()) {
                return false;
            }
            support.setShowDropLocation(true);
            if (!support.isDataFlavorSupported(nodesFlavor)) {
                return false;
            }
            // Do not allow a drop on the drag source selections.
            JTree.DropLocation dl
                    = (JTree.DropLocation) support.getDropLocation();
            JTree tree = (JTree) support.getComponent();
            int dropRow = tree.getRowForPath(dl.getPath());
            int[] selRows = tree.getSelectionRows();
            for (int i = 0; i < selRows.length; i++) {
                if (selRows[i] == dropRow) {
                    return false;
                }
            }
            // Do not allow MOVE-action drops if a non-leaf node is
            // selected unless all of its children are also selected.
            int action = support.getDropAction();
            if (action == MOVE) {
                return haveCompleteNode(tree);
            }
            // Do not allow a non-leaf node to be copied to a level
            // which is less than its source level.
            TreePath dest = dl.getPath();
            DefaultMutableTreeNode target
                    = (DefaultMutableTreeNode) dest.getLastPathComponent();
            TreePath path = tree.getPathForRow(selRows[0]);
            DefaultMutableTreeNode firstNode
                    = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (firstNode.getChildCount() > 0
                    && target.getLevel() < firstNode.getLevel()) {
                return false;
            }
            return true;
        }

        private boolean haveCompleteNode(JTree tree) {
            int[] selRows = tree.getSelectionRows();
            TreePath path = tree.getPathForRow(selRows[0]);
            DefaultMutableTreeNode first
                    = (DefaultMutableTreeNode) path.getLastPathComponent();
            int childCount = first.getChildCount();
            // first has children and no children are selected.
            if (childCount > 0 && selRows.length == 1) {
                return false;
            }
            // first may have children.
            for (int i = 1; i < selRows.length; i++) {
                path = tree.getPathForRow(selRows[i]);
                DefaultMutableTreeNode next
                        = (DefaultMutableTreeNode) path.getLastPathComponent();
                if (first.isNodeChild(next)) {
                    // Found a child of first.
                    if (childCount > selRows.length - 1) {
                        // Not all children of first are selected.
                        return false;
                    }
                }
            }
            return true;
        }

        protected Transferable createTransferable(JComponent c) {
            JTree tree = (JTree) c;
            TreePath[] paths = tree.getSelectionPaths();
            if (paths != null) {
                // Make up a node array of copies for transfer and
                // another for/of the nodes that will be removed in
                // exportDone after a successful drop.
                List<DefaultMutableTreeNode> copies
                        = new ArrayList<DefaultMutableTreeNode>();
                List<DefaultMutableTreeNode> toRemove
                        = new ArrayList<DefaultMutableTreeNode>();
                DefaultMutableTreeNode node
                        = (DefaultMutableTreeNode) paths[0].getLastPathComponent();
                DefaultMutableTreeNode copy = copy(node);
                copies.add(copy);
                toRemove.add(node);
                for (int i = 1; i < paths.length; i++) {
                    DefaultMutableTreeNode next
                            = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
                    // Do not allow higher level nodes to be added to list.
                    if (next.getLevel() < node.getLevel()) {
                        break;
                    } else if (next.getLevel() > node.getLevel()) {  // child node
                        copy.add(copy(next));
                        // node already contains child
                    } else {                                        // sibling
                        copies.add(copy(next));
                        toRemove.add(next);
                    }
                }
                DefaultMutableTreeNode[] nodes
                        = copies.toArray(new DefaultMutableTreeNode[copies.size()]);
                nodesToRemove
                        = toRemove.toArray(new DefaultMutableTreeNode[toRemove.size()]);
                return new NodesTransferable(nodes);
            }
            return null;
        }

        /**
         * Defensive copy used in createTransferable.
         */
        private DefaultMutableTreeNode copy(DefaultMutableTreeNode node) {
            return new DefaultMutableTreeNode(node.getUserObject());
        }

        protected void exportDone(JComponent source, Transferable data, int action) {
            if ((action & MOVE) == MOVE) {
                JTree tree = (JTree) source;
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                // Remove nodes saved in nodesToRemove in createTransferable.
                for (int i = 0; i < nodesToRemove.length; i++) {
                    model.removeNodeFromParent(nodesToRemove[i]);
                }
                try {
                    DefaultMutableTreeNode[] newNode = (DefaultMutableTreeNode[]) data.getTransferData(nodesFlavor);
                    Genre newTopGenre = (Genre) ((DefaultMutableTreeNode) newNode[0].getParent()).getUserObject();
                    last.setSuperGenre(newTopGenre);
                    last = null;
                } catch (UnsupportedFlavorException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        public int getSourceActions(JComponent c) {
            return COPY_OR_MOVE;
        }

        public boolean importData(TransferHandler.TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }
            // Extract transfer data.
            DefaultMutableTreeNode[] nodes = null;
            try {
                Transferable t = support.getTransferable();
                nodes = (DefaultMutableTreeNode[]) t.getTransferData(nodesFlavor);
            } catch (UnsupportedFlavorException ufe) {
                System.out.println("UnsupportedFlavor: " + ufe.getMessage());
            } catch (java.io.IOException ioe) {
                System.out.println("I/O error: " + ioe.getMessage());
            }
            // Get drop location info.
            JTree.DropLocation dl
                    = (JTree.DropLocation) support.getDropLocation();
            int childIndex = dl.getChildIndex();
            TreePath dest = dl.getPath();
            DefaultMutableTreeNode parent
                    = (DefaultMutableTreeNode) dest.getLastPathComponent();
            JTree tree = (JTree) support.getComponent();
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            // Configure for drop mode.
            int index = childIndex;    // DropMode.INSERT
            if (childIndex == -1) {     // DropMode.ON
                index = parent.getChildCount();
            }
            // Add data to model.
            for (int i = 0; i < nodes.length; i++) {
                model.insertNodeInto(nodes[i], parent, index++);
            }
            return true;
        }

        public String toString() {
            return getClass().getName();
        }

        public class NodesTransferable implements Transferable {

            DefaultMutableTreeNode[] nodes;

            public NodesTransferable(DefaultMutableTreeNode[] nodes) {
                this.nodes = nodes;
            }

            public Object getTransferData(DataFlavor flavor)
                    throws UnsupportedFlavorException {
                if (!isDataFlavorSupported(flavor)) {
                    throw new UnsupportedFlavorException(flavor);
                }
                return nodes;
            }

            public DataFlavor[] getTransferDataFlavors() {
                return flavors;
            }

            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return nodesFlavor.equals(flavor);
            }
        }
    }

}
