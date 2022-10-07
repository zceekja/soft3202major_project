package major_project;

import major_project.model.db.*;
import major_project.model.*;


public class MockPane implements Observer{
    private App model;
    public MockPane(App model){
        this.model = model;
        model.getMyCryptoList().registerObserver(this);

    }

    @Override
    public void update(){
        ;

    }
}