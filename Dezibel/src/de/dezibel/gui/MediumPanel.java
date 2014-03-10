package de.dezibel.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import de.dezibel.data.Album;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;

/**
 * A small panel, which shows the medium details
 * Comments can be selected and the comment-text will be displayed in a
 * separate text-area.
 * @author Pascal
 *
 */
public class MediumPanel extends DragablePanel {

	// ref. to clickable properties for displaying them in other panels.
	private Medium currentMedium;
	private User	artist;
	private Label 	label;
	private Album	album;
	
	// Labels for properties
	private JLabel 	lbTitle;
    private JLabel 	lbAlbum;
    private JLabel 	lbUploadDate;
    private JLabel 	lbAvgRating;
    private JLabel 	lbArtist;
    private JLabel 	lbGenre;
    private JLabel 	lbLabel;
    private JLabel lbComments;
    
    // Labels that will be filled with the medium-properties
    private JLabel 	lbInfoTitle;
    private JLabel	bnInfoAlbum;
    private JLabel 	lbInfoUploadDate;
    private JLabel 	lbInfoAvgRating;
    private JLabel bnInfoArtist;
    private JLabel 	lbInfoGenre;
    private JLabel	bnInfoLabel;
    private JList<String>		comments;
    private DefaultListModel<String> commentsModel;
    private JScrollPane spComments;
    private JScrollPane spCommentArea;
    private JTextArea 	commentDetail;
    private LinkedList<String> details;
    private String clickable1 = "<HTML><FONT color=\"#000099\"><U>";
    private String clickable2 = "</U></FONT></HTML>";
    
	public MediumPanel(DezibelPanel parent, Medium current) {
		super(parent);
		this.currentMedium = current;
		this.createComponents();
		this.createLayout();
		this.refresh();
	}

	@Override
	/**
	 * @see de.dezibel.gui.DragablePanel#reset()
	 */
	public void reset() {
		currentMedium = null;
		artist = null;
		label = null;
		lbInfoTitle.setText("");
	    bnInfoAlbum.setText("");
	    lbInfoUploadDate.setText("");
	   lbInfoAvgRating.setText("");
	    bnInfoArtist.setText("");
	    lbInfoGenre.setText("");
	    bnInfoLabel.setText("");
	    commentsModel.clear();
	    commentDetail.setText("");
	    details.clear();
	}

	@Override
	/**
	 * @see de.dezibel.gui.DragablePanel#refresh()
	 */
	public void refresh() {
		if(currentMedium != null)
		{
			lbInfoTitle.setText("");
			bnInfoAlbum.setText("");
			lbInfoUploadDate.setText("");
			lbInfoAvgRating.setText("");
			bnInfoArtist.setText("");
			lbInfoGenre.setText("");
			bnInfoLabel.setText("");
			commentsModel.clear();
			commentDetail.setText("");
			
			if(currentMedium.getArtist() != null){
				artist = currentMedium.getArtist();
				bnInfoArtist.setText(clickable1 + currentMedium.getArtist().getPseudonym() + clickable2);
			}
			else
				bnInfoArtist.setText("-");
			
			lbInfoTitle.setText(currentMedium.getTitle());
			
			if(currentMedium.getAlbum() != null){
				album = currentMedium.getAlbum();
				bnInfoAlbum.setText(clickable1 + currentMedium.getAlbum().getTitle() + clickable2);
				//bnInfoAlbum.setText(currentMedium.getAlbum().getTitle());
			}
			else
				bnInfoAlbum.setText("-");
			
			if(currentMedium.getGenre() != null)
				lbInfoGenre.setText(currentMedium.getGenre().getName());
			else
				lbInfoGenre.setText("-");
			
			if(currentMedium.getLabel() != null){
				label = currentMedium.getLabel();
				bnInfoLabel.setText(clickable1 + currentMedium.getLabel().getName() + clickable2);
				//bnInfoLabel.setText(currentMedium.getLabel().getName());
			}
			else
				bnInfoLabel.setText("-");
			
			if(currentMedium.getUploadDate() != null)
				lbInfoUploadDate.setText(currentMedium.getUploadDate().toString());
			else
				lbInfoUploadDate.setText("-");
			
			Double rating = currentMedium.getAvgRating();
			lbInfoAvgRating.setText(rating.toString());
			
			if((currentMedium.getComments() != null) && (currentMedium.getComments().size() > 0))
			{
				ListIterator<de.dezibel.data.Comment> iter = currentMedium.getComments().listIterator();
				de.dezibel.data.Comment com;
				
				while(iter.hasNext())
				{
					com = iter.next();
					commentsModel.addElement(com.getAuthor().getLastname() + com.getAuthor().getFirstname());
					details.addLast(com.getText());
				}
			}
		}
	}

	
	/**
	 * Help function for creating all components needed by this panel
	 */
	private void createComponents(){
		details = new LinkedList<String>();
		lbArtist = new JLabel("Künstler:");
		lbTitle = new JLabel("Titel:");
	    lbAlbum = new JLabel("Album:");
	    lbGenre = new JLabel("Genre:");
	    lbLabel = new JLabel("Label:");
	    lbUploadDate = new JLabel("Hochgeladen am:");
	    lbAvgRating = new JLabel("Durchschnittliche Bewertung:");
	    lbComments = new JLabel("Kommentare:");
	    
	    lbInfoTitle = new JLabel("");
	    bnInfoAlbum = new JLabel("");
	    lbInfoUploadDate = new JLabel("");
	    lbInfoAvgRating = new JLabel("");
	    bnInfoArtist = new JLabel("");
	    lbInfoGenre = new JLabel("");
	    bnInfoLabel = new JLabel("");
	    
	    bnInfoArtist.setHorizontalAlignment(SwingConstants.LEFT);
	    bnInfoArtist.setHorizontalTextPosition( SwingConstants.LEFT );
	    bnInfoArtist.setOpaque(false);
	    bnInfoArtist.setBackground(this.getBackground());
	    bnInfoArtist.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					onClickArtist();
				}
			}
	    });
	    
	    bnInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
	    bnInfoLabel.setHorizontalTextPosition( SwingConstants.LEFT );
	    bnInfoLabel.setOpaque(false);
	    bnInfoLabel.setBackground(this.getBackground());
	    bnInfoLabel.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					onClickLabel();
				}
			}
	    });
	    
	    bnInfoAlbum.setHorizontalAlignment(SwingConstants.LEFT);
	    bnInfoAlbum.setHorizontalTextPosition( SwingConstants.LEFT );
	    bnInfoAlbum.setOpaque(false);
	    bnInfoAlbum.setBackground(this.getBackground());
	    bnInfoAlbum.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					onClickAlbum();
				}
			}
	    });
	  
	    commentsModel = new DefaultListModel<String>();
	    
	    comments = new JList<String>(commentsModel);
	    comments.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    comments.setLayoutOrientation(JList.VERTICAL);
	    comments.setVisibleRowCount(-1);
	    spComments = new JScrollPane(comments);
	    spComments.setPreferredSize(new Dimension(250, 80));
	    
	    comments.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(comments.getSelectedIndex() != -1)
					onCommentChanged();
			}
	    });
	    
	    commentDetail = new JTextArea();
	    commentDetail.setWrapStyleWord(true);
	    commentDetail.setLineWrap(true);
	    commentDetail.setText("");
	    commentDetail.setEditable(false);
	    spCommentArea = new JScrollPane(commentDetail);
	}
	
	/**
	 * Help function to create the panel layout using GroupLayout
	 */
	private void createLayout(){
		GroupLayout layout = new GroupLayout(this);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    		.addComponent(lbArtist)
						.addComponent(lbTitle)
						.addComponent(lbAlbum)
						.addComponent(lbGenre)
						.addComponent(lbLabel)
						.addComponent(lbUploadDate)
						.addComponent(lbAvgRating)
						.addComponent(lbComments))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    		.addComponent(bnInfoArtist)
						.addComponent(lbInfoTitle)
						.addComponent(bnInfoAlbum)
						.addComponent(lbInfoGenre)
						.addComponent(bnInfoLabel)
						.addComponent(lbInfoUploadDate)
						.addComponent(lbInfoAvgRating)
						.addGroup(layout.createSequentialGroup()
							.addComponent(spComments)
							.addComponent(spCommentArea)))
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbArtist)
			        .addComponent(bnInfoArtist))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbTitle)
			        .addComponent(lbInfoTitle))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbAlbum)
			        .addComponent(bnInfoAlbum))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbGenre)
			        .addComponent(lbInfoGenre))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbLabel)
			        .addComponent(bnInfoLabel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbUploadDate)
			        .addComponent(lbInfoUploadDate))
			   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbAvgRating)
			        .addComponent(lbInfoAvgRating))
			   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			    	.addComponent(lbComments)
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			        		.addComponent(spComments)
			        		.addComponent(spCommentArea)))
			);
			layout.linkSize(SwingConstants.HORIZONTAL, spComments, spCommentArea);
			layout.linkSize(SwingConstants.VERTICAL, spComments, spCommentArea);
			layout.linkSize(SwingConstants.HORIZONTAL,lbInfoTitle, bnInfoAlbum,lbInfoUploadDate,lbInfoAvgRating,
					bnInfoArtist,lbInfoGenre,bnInfoLabel);
			layout.linkSize(SwingConstants.VERTICAL,lbInfoTitle, bnInfoAlbum,lbInfoUploadDate,lbInfoAvgRating,
					bnInfoArtist,lbInfoGenre,bnInfoLabel);
		this.setLayout(layout);
	}
	
	/**
	 * This function is called, when the user changed the selection in the commentlist.
	 * The comment-details will be displayed in a JTextArea.
	 */
	private void onCommentChanged(){
		this.commentDetail.setText(this.details.get(comments.getSelectedIndex()));
	}
	
	/**
	 * This function is called, when the user click on the artist of the
	 * current displayed media.
	 */
	private void onClickArtist(){
		if(artist != null)
			this.parent.showProfile(artist);
	}
	
	/**
	 * This function is called, when the user click on the album of the
	 * current displayed media.
	 */
	private void onClickAlbum(){
		if(album != null)
			this.parent.showAlbum(album);
	}
	
	/**
	 * This function is called, when the user click on the label of the
	 * current displayed media.
	 */
	private void onClickLabel(){
		if(label != null)
			this.parent.showProfile(label);
	}
}
