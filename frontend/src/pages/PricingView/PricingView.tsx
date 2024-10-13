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
import pricingViewHero from "../../assets/pricing-view-hero.png";

function PricingView() {

  const scrollToSection = (sectionName: string) => {
    const section = document.getElementById(sectionName);
    console.log(section);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  };

  return (
    <>
      <NavBar navBarItems={PRICING_VIEW_NAVBAR_ITEMS} />
      <div className="pricing-view-layout">
        <div className="row pricing-top-container" id="row pricing-top-container">
          <div className="pricing-top-container pricing-left-container">
            <h1 className="main-header">
              Gain <span style={{color:"var(--quaternary-color)"}}>exclusive</span> access to a stream of local leads to <span style={{color:"var(--quaternary-color)"}}>grow</span> your real
              estate business.
            </h1>
            <PrimaryButton
              text="Get Started"
              className="btn secondary"
              onClick={() => {
                scrollToSection("row pricing-bottom-container");
              }}
            />
          </div>
          <div className="pricing-top-container pricing-right-container">
            <img className="main-hero" src={pricingViewHero} alt="testttt" />
          </div>
        </div>
        <div className="row pricing-middle-container" id="row pricing-middle-container">
          <h3 className="pricing-features-header">How It Works</h3>
          <div className="pricing-features-container">
            <div className="features-grid">
              {FEATURES_CONTENT.map((feature, index) => (
                <FeatureCard key={index} featuresContent={feature} />
              ))}
            </div>
            <PrimaryButton
              text="Get Started"
              className="btn secondary"
              onClick={() => {
                scrollToSection("row pricing-bottom-container");
              }}
            />
          </div>
        </div>
        <div className="row pricing-bottom-container" id="row pricing-bottom-container">
          <div id="pricing-cards-container">
            <div className="pricing-header-container">
              <h1 className="pricing-header">Choose the best plan for you.</h1>
              <h5 className="pricing-header-sub-header">Plans starting at ${BASIC_PRICE}</h5>
            </div>
            <div className="pricing-cards-container">
              <PricingCard
                price={BASIC_PRICE}
                title="Basic"
                briefDescription="For agents focused on steady, consistent real estate opportunities in their local market."
                description={
                  <>
                    Choose up to <strong>2</strong> counties within your chosen
                    state
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
                    Choose up to <strong>3</strong> counties within your chosen
                    state
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
                    Choose up to <strong>7</strong> counties within your chosen
                    state
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
