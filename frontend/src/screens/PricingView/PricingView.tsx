import React from "react";
import "../../App.css";
import "./pricingView.component.css";
import PricingCard from "../../components/PricingCard/PricingCard";
import PrimaryButton from "../../components/PrimaryButton/PrimaryButton";
import NavBar from "../../components/NavBar/NavBar";

function PricingView() {
  
  const basicPrice: string = "19";
  const standardPrice: string = "28";
  const premiumPrice: string = "59";

  return (
    <>
      <NavBar />
      <div className="pricing-view-layout">
        <div className="top-half">
          <div className="main-title-container">
            <p>
              Access a consistent stream of leads
              in your backyard.

              insert carousel of client cards
            </p>
          </div>
          <div className="features-container">
            <p id="features-title">Features</p>
            <p> 
              - Get notified of new leads
              - Simple client management
              - Export or route leads
            </p>
          </div>
          <div className="pricing-title-container">
            <h1>Choose the best plan for you.</h1>
            <p>Plans starting at ${basicPrice}</p>
          </div>
          <div className="pricing-cards-container">
            <PricingCard
              price={basicPrice}
              title="Basic"
              briefDescription="For agents focused on steady, consistent real estate opportunities in their local market."
              description="2 counties"
              GetStartedButton={<PrimaryButton 
                variant="primary" 
                text="Get Started" 
                className="btn btn-primary"
                onClick={() => {console.log("clicked")}}/>}
            />
            <PricingCard
              price={standardPrice}
              title="Standard"
              briefDescription="For agents handling a higher volume of transactions and looking to expand."
              description="3 counties"
              GetStartedButton={<PrimaryButton 
                variant="primary" 
                text="Get Started" 
                className="btn btn-primary"
                onClick={() => {console.log("clicked")}}/>}
            />
            <PricingCard
              price={premiumPrice}
              title="Premium"
              briefDescription="For agents or teams managing larger volumes."
              description="6 counties + 1 free"
              GetStartedButton={<PrimaryButton 
                variant="primary" 
                text="Get Started" 
                className="btn btn-primary"
                onClick={() => {console.log("clicked")}}/>}
            />
          </div>
        </div>
        <div className="bottom-half"></div>
      </div>
    </>
  );
}

export default PricingView;
