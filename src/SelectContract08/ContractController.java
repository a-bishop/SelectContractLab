package SelectContract08;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JDialog;

/**
 *
 * @author Home
 */
public class ContractController {
    
    private final ContractView theView;
    private final ContractModel theModel;

    public ContractController(ContractView theView, ContractModel theModel) {
        this.theView = theView;
        this.theModel = theModel;
        this.theView.addPrevListener(new PrevButtonListener());
        this.theView.addBidListener(new BidButtonListener());
        this.theView.addNextListener(new NextButtonListener());
        this.theView.addcomboBoxListener(new ComboListener());
        this.theView.setOriginCityList(this.theModel.getOriginCityList());
        setUpDisplay();
    }
    
    private void setUpDisplay() {
        
        try{
            if (theModel.foundContracts()) {
                Contract c = theModel.getTheContract();
                theView.setContractID(c.getContractID());
                theView.setDestCity(c.getDestCity());
                theView.setOriginCity(c.getOriginCity());
                theView.setOrderItem(c.getOrderItem());
                theView.updateContractViewPanel(theModel.getCurrentContractNum(), theModel.getContractCount());
            } else {
                theView.setContractID("????");
                theView.setDestCity("????");
                theView.setOriginCity("????");
                theView.setOrderItem("????");
            }           
        } catch (Error ex) {
            // Provide an error message to the console output.
            System.out.println(ex);
            theView.displayErrorMessage(
                "Error: There was a problem setting the contract.\n"
                +"      Contract number: " + theModel.getCurrentContractNum());
        }
    }
    
    class PrevButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // if current contract is the first in a list of contracts,
            // then you cannot view a non-existent contract behind it
            
            try {
               if (theModel.getCurrentContractNum() + 2 != theModel.getContractCount()) {
                   theView.addNext();
                }
                // retrieve the contract behind the currently displayed contract
               theModel.prevContract();
               if (theModel.getCurrentContractNum() == 0) {
                   theView.removePrev();
               }
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage(
                    "Error: There is a problem setting a previous contract.");
            }
            setUpDisplay();
        }
    }
    
    class NextButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            // if current contract is the last in a list of contracts,
            // then you cannot view a non-existent contract behind it
            try {
               if (theModel.getCurrentContractNum() == 0) {
                   theView.addPrev();
               }
                // retrieve the contract in front of the currently displayed contract
               theModel.nextContract();
               if (theModel.getCurrentContractNum() + 1 == theModel.getContractCount()) {
                   theView.removeNext();
               }
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage("Error: There is a problem setting the next contract.");
            }
            setUpDisplay();
        }
    }
    
    class BidButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Contract cont = theModel.getTheContract();
                String ID = cont.getContractID();
                JDialog cb = new ConfirmBid(theView, true, ID);               
                cb.setLocationRelativeTo(null);
                cb.setVisible(true);
            } catch (Exception ex) {
                System.out.println(ex);
                theView.displayErrorMessage(
                    "Error: the numbers must be integers.");
            }
        }
    }
    
    class ComboListener implements ItemListener {
    
        @Override 
        public void itemStateChanged(ItemEvent e) {
            System.out.println(e.getItem().toString()); 
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedCity = e.getItem().toString();
                System.out.println(selectedCity); 
                theModel.updateContractList(selectedCity); 
                setUpDisplay();
            }
        } 
    }
}
