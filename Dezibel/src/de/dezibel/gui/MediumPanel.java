package de.dezibel.gui;

import java.awt.Dimension;
import java.util.Date;
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
import de.dezibel.data.Genre;
import de.dezibel.data.Label;
import de.dezibel.data.Medium;
import de.dezibel.data.User;

/**
 * 
 * @author Pascal
 *
 */
public class MediumPanel extends DragablePanel {

	private Medium currentMedium;
	private User	artist;
	private Label 	label;
	
	private JLabel 	lbTitle;
    private JLabel 	lbAlbum;
    private JLabel 	lbUploadDate;
    private JLabel 	lbAvgRating;
    private JLabel 	lbArtist;
    private JLabel 	lbGenre;
    private JLabel 	lbLabel;
    private JLabel lbComments;
    
    private JLabel 	lbInfoTitle;
    private JLabel 	lbInfoAlbum;
    private JLabel 	lbInfoUploadDate;
    private JLabel 	lbInfoAvgRating;
    private JLabel 	lbInfoArtist;
    private JLabel 	lbInfoGenre;
    private JLabel 	lbInfoLabel;
    private JList<String>		comments;
    private DefaultListModel<String> commentsModel;
    private JScrollPane spComments;
    private JScrollPane spCommentArea;
    private JTextArea 	commentDetail;
    private LinkedList<String> details;
    
	public MediumPanel(DezibelPanel parent, Medium current) {
		super(parent);
		this.currentMedium = current;
		this.createComponents();
		this.createLayout();
		this.refresh();
	}

	@Override
	public void reset() {
		currentMedium = null;
		artist = null;
		label = null;
		lbInfoTitle.setText("");
	    lbInfoAlbum.setText("");
	    lbInfoUploadDate.setText("");
	   lbInfoAvgRating.setText("");
	    lbInfoArtist.setText("");
	    lbInfoGenre.setText("");
	    lbInfoLabel.setText("");
	    commentsModel.clear();
	    commentDetail.setText("");
	    details.clear();
	}

	@Override
	public void refresh() {
		if(currentMedium != null)
		{
			lbInfoTitle.setText("");
			lbInfoAlbum.setText("");
			lbInfoUploadDate.setText("");
			lbInfoAvgRating.setText("");
			lbInfoArtist.setText("");
			lbInfoGenre.setText("");
			lbInfoLabel.setText("");
			commentsModel.clear();
			commentDetail.setText("");
			
			if(currentMedium.getArtist() != null)
				lbInfoArtist.setText(currentMedium.getArtist().getPseudonym());
			else
				lbInfoArtist.setText("-");
			
			lbInfoTitle.setText(currentMedium.getTitle());
			
			if(currentMedium.getAlbum() != null)
				lbInfoAlbum.setText(currentMedium.getAlbum().getTitle());
			else
				lbInfoAlbum.setText("-");
			
			if(currentMedium.getGenre() != null)
				lbInfoGenre.setText(currentMedium.getGenre().getName());
			else
				lbInfoGenre.setText("-");
			
			if(currentMedium.getLabel() != null)
				lbInfoLabel.setText(currentMedium.getLabel().getName());
			else
				lbInfoLabel.setText("-");
			
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
	    lbInfoAlbum = new JLabel("");
	    lbInfoUploadDate = new JLabel("");
	    lbInfoAvgRating = new JLabel("");
	    lbInfoArtist = new JLabel("");
	    lbInfoGenre = new JLabel("");
	    lbInfoLabel = new JLabel("");
	    
	    
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
			    		.addComponent(lbInfoArtist)
						.addComponent(lbInfoTitle)
						.addComponent(lbInfoAlbum)
						.addComponent(lbInfoGenre)
						.addComponent(lbInfoLabel)
						.addComponent(lbInfoUploadDate)
						.addComponent(lbInfoAvgRating)
						.addGroup(layout.createSequentialGroup()
							.addComponent(spComments)
							.addComponent(spCommentArea)))
			);

			layout.setVerticalGroup(layout.createSequentialGroup()
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbArtist)
			        .addComponent(lbInfoArtist))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbTitle)
			        .addComponent(lbInfoTitle))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbAlbum)
			        .addComponent(lbInfoAlbum))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbGenre)
			        .addComponent(lbInfoGenre))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbLabel)
			        .addComponent(lbInfoLabel))
			    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbUploadDate)
			        .addComponent(lbInfoUploadDate))
			   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbAvgRating)
			        .addComponent(lbInfoAvgRating))
			   .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(lbComments)
			        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			        		.addComponent(spComments)
			        		.addComponent(spCommentArea)))
			);
			layout.linkSize(SwingConstants.HORIZONTAL, spComments, spCommentArea);
			layout.linkSize(SwingConstants.VERTICAL, spComments, spCommentArea);
		this.setLayout(layout);
	}
	
	private void onCommentChanged(){
		this.commentDetail.setText(this.details.get(comments.getSelectedIndex()));
	}
}
