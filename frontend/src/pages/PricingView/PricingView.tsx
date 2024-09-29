import React from "react";
import "../../App.css";
import "./pricingView.css";
import PricingCard from "../../components/PricingCard/PricingCard";
import PrimaryButton from "../../components/PrimaryButton/PrimaryButton";
import NavBar from "../../components/NavBar/NavBar";
import {
  BASIC_PRICE,
  STANDARD_PRICE,
  PREMIUM_PRICE,
  FEATURES_CONTENT,
  PRICING_VIEW_NAVBAR_ITEMS,
} from "../../constants";
import FeatureCard from "../../components/FeatureCard/FeatureCard";

function PricingView() {
  return (
    <>
      <NavBar navBarItems={PRICING_VIEW_NAVBAR_ITEMS}/>
      <div className="pricing-view-layout">
        <div className="top-half">
          <div className="main-title-container">
            <p id="main-title">
              Gain Exclusive Access to a Stream of Local Leads and Grow Your
              Real Estate Business.
            </p>
          </div>
          <div className="features-container" id="features-container">
            <p id="features-title">How It Works</p>
            <div className="features-grid">
              {FEATURES_CONTENT.map((feature, index) => (
                <FeatureCard key={index} featuresContent={feature} />
              ))}
            </div>
          </div>
        </div>
        <div className="bottom-half">
          <div id="pricing-cards-container">
            <div className="pricing-title-container">
              <h1>Choose the best plan for you.</h1>
              <p>Plans starting at ${BASIC_PRICE}</p>
            </div>
            <div className="pricing-cards-container">
              <PricingCard
                price={BASIC_PRICE}
                title="Basic"
                briefDescription="For agents focused on steady, consistent real estate opportunities in their local market."
                description={
                  <>
                    Choose up to <strong>2</strong> counties within your chosen state
                  </>
                }
                GetStartedButton={
                  <PrimaryButton
                    text="Get Started"
                    className="btn primary"
                    onClick={() => {
                      console.log("clicked");
                    }}
                  />
                }
              />
              <PricingCard
                price={STANDARD_PRICE}
                title="Standard"
                briefDescription="For agents handling a higher volume of transactions and looking to expand."
                description={
                  <>
                    Choose up to <strong>3</strong> counties within your chosen state
                  </>
                }
                GetStartedButton={
                  <PrimaryButton
                    text="Get Started"
                    className="btn primary"
                    onClick={() => {
                      console.log("clicked");
                    }}
                  />
                }
              />
              <PricingCard
                price={PREMIUM_PRICE}
                title="Premium"
                briefDescription="For agents or teams managing larger volumes."
                description={
                  <>
                    Choose up to <strong>7</strong> counties within your chosen state
                  </>
                }
                GetStartedButton={
                  <PrimaryButton
                    text="Get Started"
                    className="btn primary"
                    onClick={() => {
                      console.log("clicked");
                    }}
                  />
                }
              />
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default PricingView;
