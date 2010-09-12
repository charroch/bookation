
package novoda.bookation.gae.client;

import java.util.List;

import novoda.bookation.gae.client.widget.CustomMapClickEvent;
import novoda.bookation.gae.client.widget.CustomMapClickHandler;
import novoda.bookation.gae.client.widget.CustomMapWidget;
import novoda.bookation.gae.shared.Bookation;
import novoda.bookation.gae.shared.BookationMarker;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

public class EditorEntryPoint implements EntryPoint, CustomMapClickHandler {

    private final EditorServiceAsync editorService = GWT.create(EditorService.class);

    private static final String GWT_HOOK_ID = "gwtHook";

    private Button submit = new Button("submit");
    private Button load = new Button("load bookation");
    private Button reset = new Button("reset bookation");
    private CustomMapWidget map;
    private Label message = new Label();

    private Bookation bookation;
    
    private HandlerManager eventBus;

    @Override
    public void onModuleLoad() {
    	eventBus = new HandlerManager(this);
        FlowPanel panel = new FlowPanel();
        panel.add(new Label("Edit"));
        panel.add(id);
        reset.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                reset();
            }
        });
        panel.add(reset);
        load.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                String stringId = id.getText();
                Long id = null;
                if (stringId != null) {
                    id = Long.valueOf(stringId);
                }
                editorService.get(id, new AsyncCallback<Bookation>() {
                    @Override
                    public void onSuccess(Bookation result) {
                        if (result != null) {
                            load(result);
                            message.setText("success loading");
                        } else {
                            message.setText("success loading, but is null");
                        }
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        message.setText("failure loading");
                    }
                });
            }
        });
        panel.add(load);
        addWidget(panel);
        panel.add(new Label("Edit"));

        panel.add(new Label("Save"));
        submit.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                editorService.save(get(), new AsyncCallback<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        message.setText("success in saving");
                        reset();
                        editorService.getAccountBookations(new AsyncCallback<List<BookationMarker>>() {
                			@Override
                			public void onFailure(Throwable caught) {
                				message.setText("Problem getting bookations");
                			}
                			@Override
                			public void onSuccess(List<BookationMarker> result) {
                				map.setBookations(result);
                			}
                        	
                        });
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        message.setText("failure in saving");
                        reset();
                    }
                });
            }
        });
        panel.add(submit);
        panel.add(message);
        eventBus.addHandler(CustomMapClickEvent.TYPE, this);
        map = new CustomMapWidget(eventBus);
        panel.add(map);
        
        editorService.getAccountBookations(new AsyncCallback<List<BookationMarker>>() {
			@Override
			public void onFailure(Throwable caught) {
				message.setText("Problem getting bookations");
			}
			@Override
			public void onSuccess(List<BookationMarker> result) {
				map.setBookations(result);
			}
        	
        });
        
        RootPanel.get(GWT_HOOK_ID).add(panel);
    }
    
    /**
     * Specific fields for the manipulated model
     */

    private TextBox id = new TextBox();

    private TextBox description = new TextBox();

    private TextBox title = new TextBox();
    
    private TextBox tag = new TextBox();
    
    private TextBox latitude = new TextBox();
    
    private TextBox longitude = new TextBox();

    private void addWidget(FlowPanel panel) {
        panel.add(new Label("Title"));
        panel.add(title);
        panel.add(new Label("Description"));
        panel.add(description);
        panel.add(new Label("Tag"));
        panel.add(tag);
        panel.add(new Label("Latitude"));
        panel.add(latitude);
        panel.add(new Label("Longitude"));
        panel.add(longitude);
    }

    private void load(Bookation bookation) {
        this.bookation = bookation;
        id.setText("" + bookation.getId());
        title.setText("" + bookation.getTitle());
        description.setText("" + bookation.getDescription());
        tag.setText("" + bookation.getTag());
    }

    private void reset() {
        bookation = new Bookation();
        id.setText("");
        title.setText("");
        description.setText("");
        tag.setText("");
        longitude.setText("");
        latitude.setText("");
    }

    private Bookation get() {
        if (bookation == null) {
            bookation = new Bookation();
        }
        //TODO
        bookation.setDescription(description.getText());
        bookation.setTitle(title.getText());
        bookation.setTag(tag.getText());
        bookation.setLongitude(Double.valueOf(longitude.getText()));
        bookation.setLatitude(Double.valueOf(latitude.getText()));
        return bookation;
    }

	@Override
	public void create(CustomMapClickEvent event) {
		longitude.setText("" + event.getLongitude());
        latitude.setText("" + event.getLatitude());
	}

}
