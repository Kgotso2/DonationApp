package com.example.donationapp;

class Donor {
    String donor, donatedAmt;

    Donor(String Donor , String donatedAmt){

        this.donor = Donor;
        this.donatedAmt = donatedAmt;
    }


    public String getDonor() {
        return donor;
    }

    public void setDonor(String donor) {
        this.donor = donor;
    }

    public String getDonatedAmt() {
        return donatedAmt;
    }

    public void setDonatedAmt(String donatedAmt) {
        this.donatedAmt = donatedAmt;
    }
}
